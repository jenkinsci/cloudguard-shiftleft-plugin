package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernameCredentials;
import com.cloudbees.plugins.credentials.common.UsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.ArtifactArchiver;
import io.jenkins.plugins.checkpoint.Common;
import io.jenkins.plugins.checkpoint.Utils;
import io.jenkins.plugins.checkpoint.cloudguard.report.ReportGenerator;
import io.jenkins.plugins.checkpoint.cloudguard.report.ReportGeneratorFactory;
import io.jenkins.plugins.checkpoint.cloudguard.report.ReportType;
import io.jenkins.plugins.checkpoint.cloudguard.shiftleft.BladeBuilder.Blade;

public class ShiftleftBuilderExecutor {

    private static int count;
    private static int buildId = 0;
    private static final String SHIFTLEFT_RESULTS_DIRNAME_PREFIX = "ShiftleftReport_";

    public ShiftleftBuilderExecutor(Blade blade, Run<?, ?> run, FilePath workspace, Launcher launcher,
            TaskListener listener) throws IOException, InterruptedException {

        if (blade.getCredentialsId() == null) {
            throw new AbortException("No credentials defined to access CloudGuard");
        }

        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials) CredentialsProvider.findCredentialById(
                blade.getCredentialsId(), StandardUsernameCredentials.class, run, Collections.emptyList());

        if (credentials == null) {
            throw new AbortException("No credentials found for id \"" + blade.getCredentialsId() + "\"");
        }

        listener.getLogger().println("Input blade:" + blade.getClass().getName());
        listener.getLogger().println("Credentials Id:" + blade.getCredentialsId());
        listener.getLogger().println("Build root dir:" + run.getRootDir());

        String artifactSuffix, scanReportFilename;
        if (run.hashCode() != buildId) {
            // New build
            setBuildId(run.hashCode());
            setCount(1);
            artifactSuffix = null; // When ther is only one step, there should be no suffix at all
            scanReportFilename = blade.getBladeName() + Common.getScanExtension();
        } else {
            setCount(count + 1);
            artifactSuffix = Integer.toString(count);
            scanReportFilename = blade.getBladeName() + "-" + artifactSuffix + Common.getScanExtension();

        }
        listener.getLogger().println("Scan Report Filename: " + scanReportFilename);

        FilePath scanResultsPath = initializeResultsDir(run, workspace);
        ShiftleftExecutor executor = new ShiftleftExecutor(run, workspace, launcher, listener, credentials, blade);
        ScanResults scanResults = executor.execute();
        processScanResults(scanResults.getStdout(), scanResults.getStderr(), listener, scanResultsPath,
                scanReportFilename, blade.getReportType());
        archiveArtifacts(run, workspace, launcher, listener, scanResultsPath, scanReportFilename);
        listener.getLogger().println("Exit code" + String.valueOf(scanResults.getStatus()));
        switch (scanResults.getStatus()) {
        case 0:
            listener.getLogger().println("Successful scan.");
            break;
        default:
            if (blade.getIgnoreFailure()) {
                listener.getLogger().println("Ignoring scan failures");
                break;
            } else {
                throw new AbortException("Failures in Shiftleft Scan");
            }
        }
    }

    private FilePath initializeResultsDir(Run<?, ?> run, FilePath workspace) throws IOException, InterruptedException {
        try {

            String scanResultsDirname = SHIFTLEFT_RESULTS_DIRNAME_PREFIX + run.getNumber();
            FilePath scanResultsPath = new FilePath(workspace, scanResultsDirname);

            // Create output directories
            if (!scanResultsPath.exists()) {
                scanResultsPath.mkdirs();
            }
            return scanResultsPath;
        } catch (IOException | InterruptedException e) {
            throw e;
        }
    }

    public void processScanResults(String stdout, String stderr, TaskListener listener, FilePath scanResultsPath,
            String scanReportFilename, ReportType reportType) throws IOException, InterruptedException, AbortException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FilePath target = new FilePath(scanResultsPath, scanReportFilename);
        JsonParser jp = new JsonParser();
        JsonElement je = null;
        try {
            je = jp.parse(stdout);
        } catch (JsonSyntaxException e) {
            listener.getLogger().println("Could not parse JSON object from stdout:");
            listener.getLogger().println(stdout);
            throw new AbortException("Shiftleft output could not be parsed");
        } catch (Exception e) {
            listener.getLogger().println(stdout);
            listener.getLogger().println(e);
            throw e;
        }

        String prettyStdout = gson.toJson(je);
        JsonObject jsonObject = null;
        try {
            jsonObject = je.getAsJsonObject();
        } catch (IllegalStateException e) {
            listener.getLogger().println("Shiftleft output could not be converted to JSON output");
            listener.getLogger().println(stdout);
            throw e;
        }
        ReportGenerator reportGenerator = ReportGeneratorFactory.getReportGenerator(reportType, jsonObject);
        listener.getLogger().println("Shiftleft Scan Results:");
        listener.getLogger().println(prettyStdout);
        try {
            reportGenerator.generateHTMLReport(target);
        } catch (Exception e) {
            listener.getLogger().println("Could not generate Shiftleft HTML report");
            listener.getLogger().println(e.getMessage());
        }
    }

    // Archive scan artifact
    @SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    private void archiveArtifacts(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener,
            FilePath scanResultsPath, String scanReportFilename) throws java.lang.InterruptedException, IOException {
        final EnvVars env = run.getEnvironment(listener);
        String cssFilesPath = env.get("JENKINS_HOME") + "/plugins/cloudguard-shiftleft/css/";
        Utils.copyFileToWorkspace(scanResultsPath, cssFilesPath, "style.css");

        ArtifactArchiver artifactArchiver = new ArtifactArchiver((scanResultsPath.getName() + "/*"));
        artifactArchiver.perform(run, workspace, launcher, listener);
        ArtifactArchiver styleArtifactArchiver = new ArtifactArchiver(scanResultsPath.getName() + "/*.css");
        styleArtifactArchiver.perform(run, workspace, launcher, listener);
        run.addAction(new CloudGuardAction(run, scanResultsPath.getName(), scanReportFilename));
    }

    public synchronized static void setCount(int count) {
        ShiftleftBuilderExecutor.count = count;
    }

    public synchronized static void setBuildId(int buildId) {
        ShiftleftBuilderExecutor.buildId = buildId;
    }

}
