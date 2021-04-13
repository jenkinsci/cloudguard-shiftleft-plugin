package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Logger;

import com.cloudbees.plugins.credentials.common.UsernamePasswordCredentials;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Proc;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.ArtifactArchiver;
import hudson.util.ArgumentListBuilder;
import hudson.util.IOUtils;
import io.jenkins.plugins.checkpoint.Utils;
import io.jenkins.plugins.checkpoint.cloudguard.shiftleft.BladeBuilder.Blade;

public class ShiftleftExecutor {
    private static final Logger LOG = Logger.getLogger(ShiftleftExecutor.class.getName());

    private static final String JENKINS_DIR_NAME_PREFIX = "ShiftleftReport_";
    // Private members
    Run<?, ?> run;
    FilePath workspace;
    Launcher launcher;
    TaskListener listener;
    // TODO: Credentials and builder information should be injected into the scanner
    // object
    UsernamePasswordCredentials credentials;
    Blade blade;

    private String jenkinsOutputDirName;
    private FilePath jenkinsReportDir;

    public ShiftleftExecutor(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener,
            UsernamePasswordCredentials credentials, Blade blade) throws IOException, InterruptedException {
            if (listener == null) {
                LOG.warning("CloudGuard Shiftleft plugin plugin cannot initialize Jenkins task listener");
                throw new AbortException("Cannot initialize Jenkins task listener. Aborting step");
            }

            this.run = run;
            this.workspace = workspace;
            this.launcher = launcher;
            this.listener = listener;
            this.credentials = credentials;
            this.blade = blade;

            // Verify and initialize Jenkins launcher for executing processes
            try {
                this.launcher = workspace.createLauncher(listener);
                initializeJenkinsWorkspace();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
    }

    private void initializeJenkinsWorkspace() throws IOException, InterruptedException {
        try {

            jenkinsOutputDirName = JENKINS_DIR_NAME_PREFIX + run.getNumber();
            jenkinsReportDir = new FilePath(workspace, jenkinsOutputDirName);

            // Create output directories
            if (!jenkinsReportDir.exists()) {
                jenkinsReportDir.mkdirs();
            }
        } catch (IOException | InterruptedException e) {
            throw e;
        }
    }

    public ScanResults execute() throws IOException, InterruptedException {

        final EnvVars env = run.getEnvironment(listener);
        // Create process start and inject required environment variables to interact
        // with CloudGuard
        // TODO: Extract credentials to specific scanner
        Map<String, String> envVars = Utils.populateCloudGuardEnvMap(credentials);
        // TODO: If we create new launcher in the ShiftLeftExecutor class, that is the
        // one we should use
        Launcher.ProcStarter ps = launcher.launch().pwd(run.getExecutor().getCurrentWorkspace()).envs(envVars);
        ArgumentListBuilder args = new ArgumentListBuilder();
        listener.getLogger()
                .println("Current workspace for proc starter: " + run.getExecutor().getCurrentWorkspace().toString());

        args.add("shiftleft");
        args.addTokenized(blade.getGeneralOptions());
        args.add(blade.getBladeName());
        args.addTokenized(blade.getBladeOptions());
        // Need to force the json output in order to parse it
        args.add("-j");

        ps.cmds(args);
        ps.stdin(null);
        ps.readStdout().readStderr();

        int status;
        String stdout;
        String stderr;
        Proc process = ps.start();
        status = process.join();

        stdout = readProcessIntoString(process, "UTF-8", false);
        stderr = readProcessIntoString(process, "UTF-8", true);
        ScanResults scanResults = new ScanResults(stdout, stderr, status);
        // Execute "on-failure command"
        if (scanResults.getStatus() != 0 && ! blade.getOnFailureCmd().isEmpty()) {
            listener.getLogger().println("Executing on-failure command");
            ps = launcher.launch();
            args = new ArgumentListBuilder();
            args.add("bash", "-c", blade.getOnFailureCmd());
            ps.cmds(args);
            ps.stdin(null);
            ps.stderr(listener.getLogger());
            ps.stdout(listener.getLogger());
            ps.join();
        }
        return scanResults;
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", justification = "earlier readStderr()/readStdout() call prevents null return")
    private static String readProcessIntoString(Proc process, String encoding, boolean useStderr)
            throws IOException, UnsupportedEncodingException {
        if (useStderr) {
            /* process.getStderr reference is the findbugs warning to be suppressed */
            return IOUtils.toString(process.getStderr(), encoding);
        }
        /* process.getStdout reference is the findbugs warning to be suppressed */
        return IOUtils.toString(process.getStdout(), encoding);
    }

}
