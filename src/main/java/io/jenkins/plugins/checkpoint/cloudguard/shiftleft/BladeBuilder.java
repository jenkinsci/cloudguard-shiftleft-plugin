package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractDescribableImpl;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.model.Item;
import hudson.model.Queue;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.queue.Tasks;
import hudson.security.ACL;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import io.jenkins.cli.shaded.org.apache.commons.lang.StringUtils;
import io.jenkins.plugins.checkpoint.cloudguard.report.ReportType;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;

public class BladeBuilder extends Builder implements SimpleBuildStep {

    private List<Blade> blades;

    @DataBoundConstructor
    public BladeBuilder(List<Blade> blades) {
        this.blades = blades != null ? new ArrayList<Blade>(blades) : Collections.<Blade>emptyList();
    }

    public List<Blade> getBlades() {
        return Collections.unmodifiableList(blades);
    }

    public static abstract class Blade extends AbstractDescribableImpl<Blade> {
        private Boolean debug;
        private String directory;
        private String environment;
        private String forceVersion;
        private Integer timeout;
        private Boolean autoUpdate;
        private String credentialsId;
        private String onFailureCmd;
        private Boolean ignoreFailure;

        private final static String DISPLAY_NAME = "CheckPoint Shiftleft";

        protected StringBuilder generalOptions = new StringBuilder();

        abstract String getBladeName();

        abstract String getBladeOptions();

        abstract ReportType getReportType();

        public String getGeneralOptions() {
            return this.generalOptions.toString();
        }

        protected Blade() {
        }

        public String getCredentialsId() {
            return credentialsId;
        }

        @DataBoundSetter
        public void setCredentialsId(String credentialsId) {
            this.credentialsId = credentialsId;
        }

        public Boolean getAutoUpdate() {
            return autoUpdate;
        }

        @DataBoundSetter
        public void setAutoUpdate(Boolean autoUpdate) {
            this.autoUpdate = autoUpdate;
            this.generalOptions.append(autoUpdate == null ? "" : ("-u=" + autoUpdate.toString() + " "));
        }

        public Integer getTimeout() {
            return timeout;
        }

        @DataBoundSetter
        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
            this.generalOptions.append(timeout == null ? "" : ("-t=" + timeout.toString() + " "));
        }

        public String getForceVersion() {
            return forceVersion;
        }

        @DataBoundSetter
        public void setForceVersion(String forceVersion) {
            this.forceVersion = forceVersion;
            this.generalOptions.append(StringUtils.isEmpty(forceVersion) ? "" : ("-f=" + forceVersion + " "));
        }

        public String getEnvironment() {
            return environment;
        }

        @DataBoundSetter
        public void setEnvironment(String environment) {
            this.environment = environment;
            this.generalOptions.append(StringUtils.isEmpty(environment) ? "" : ("-e=" + environment + " "));
        }

        public String getDirectory() {
            return directory;
        }

        @DataBoundSetter
        public void setDirectory(String directory) {
            this.directory = directory;
            this.generalOptions.append(StringUtils.isEmpty(directory) ? "" : ("-d=" + directory + " "));
        }

        public Boolean getDebug() {
            return debug;
        }

        @DataBoundSetter
        public void setDebug(Boolean debug) {
            this.debug = debug;
            this.generalOptions.append(debug == null ? "" : ("-D=" + debug.toString() + " "));

        }

        public String getOnFailureCmd() {
            return onFailureCmd;
        }

        @DataBoundSetter
        public void setOnFailureCmd(String onFailureCmd) {
            this.onFailureCmd = onFailureCmd;
        }

        public Boolean getIgnoreFailure() {
            return ignoreFailure;
        }

        @DataBoundSetter
        public void setIgnoreFailure(Boolean ignoreFailure) {
            this.ignoreFailure = ignoreFailure;
        }

        protected static abstract class BladeDescriptor extends Descriptor<Blade> {

            public ListBoxModel doFillCredentialsIdItems(@AncestorInPath Item context,
                    @QueryParameter String credentialsId) {
                StandardListBoxModel result = new StandardListBoxModel();
                if (context == null) {
                    if (!Jenkins.get().hasPermission(Jenkins.ADMINISTER)) {
                        return result.includeCurrentValue(credentialsId);
                    }
                } else {
                    if (!context.hasPermission(Item.EXTENDED_READ)
                            && !context.hasPermission(CredentialsProvider.USE_ITEM)) {
                        return result.includeCurrentValue(credentialsId);
                    }
                }
                return result.includeEmptyValue()
                        .includeMatchingAs(
                                context instanceof Queue.Task ? Tasks.getAuthenticationOf((Queue.Task) context)
                                        : ACL.SYSTEM,
                                context, StandardUsernamePasswordCredentials.class, Collections.emptyList(),
                                CredentialsMatchers.instanceOf(StandardUsernamePasswordCredentials.class))
                        .includeCurrentValue(credentialsId);
            }
        }

    }

    public static final class IacAssessment extends Blade {

        private final String infrastructureType;
        private final String path;
        private final String entryPath;
        private final String ruleset;
        private final ReportType reportType;

        private static final String BLADE_NAME = "iac-assessment";

        @Extension
        public static class DescriptorImpl extends BladeDescriptor {
            @Override
            public String getDisplayName() {
                return "Infra as Code Assessment";
            }

            public ListBoxModel doFillInfrastructureTypeItems() {
                ListBoxModel items = new StandardListBoxModel();
                items.add("Terraform", "Terraform");
                items.add("AWS Cloud Formation", "Cft");
                return items;
            }

            public FormValidation doCheckPath(@QueryParameter String path) {
                if (path.isEmpty()) {
                    return FormValidation.error("Path must be provided");
                }
                return FormValidation.ok();
            }

            public FormValidation doCheckRuleset(@QueryParameter String ruleset) {
                try {
                    long rulesetVal = Long.parseLong(ruleset);
                    if (rulesetVal == 0) {
                        throw new Exception("Ruleset ID can not be 0");
                    }
                    return FormValidation.ok();
                } catch (NumberFormatException ex) {
                    return FormValidation.error("Ruleset ID number must be provided");
                } catch (Exception e) {
                    return FormValidation.error(e.getMessage());
                }
            }
        }

        @DataBoundConstructor
        public IacAssessment(String infrastructureType, String path, String entryPath, String ruleset) {
            this.infrastructureType = infrastructureType;
            this.path = path;
            this.entryPath = entryPath;
            this.ruleset = ruleset;
            this.reportType = ReportType.IAC_ASSESSMENT;
        }

        public String getInfrastructureType() {
            return infrastructureType;
        }

        public String getPath() {
            return path;
        }

        public String getEntryPath() {
            return entryPath;
        }

        public String getRuleset() {
            return ruleset;
        }

        public ReportType getReportType() {
            return reportType;
        }

        @Override
        String getBladeName() {
            return BLADE_NAME;
        }

        @Override
        String getBladeOptions() {
            return new StringBuilder()
                .append(" -i=" + infrastructureType)
                .append(" -p=" + path)
                .append(StringUtils.isEmpty(entryPath) ? "" : " -E=" + entryPath)
                .append(" -r=" + ruleset)
                .append(StringUtils.isEmpty(this.getEnvironment()) ? "" : (" -e=" + this.getEnvironment()))
                .toString();
        }
    }

    public static final class CodeScan extends Blade {

        private final String source;
        private final String exclude;
        private final Boolean noCache;
        private final Boolean noProxy;
        private final Boolean noBlame;
        private final String ruleset;
        private final String severityLevel;
        private final Integer severityThreshold;
        private final ReportType reportType;

        private StringBuilder codeScanOptions;

        private static final String BLADE_NAME = "code-scan";

        @Extension
        public static class DescriptorImpl extends BladeDescriptor {
            @Override
            public String getDisplayName() {
                return "Code Scan";
            }
        }

        @DataBoundConstructor
        public CodeScan(String source, String exclude, Boolean noCache, Boolean noProxy, Boolean noBlame,
                String ruleset, String severityLevel, Integer severityThreshold) {
            this.source = source;
            this.exclude = exclude;
            this.noCache = noCache;
            this.noProxy = noProxy;
            this.noBlame = noBlame;
            this.ruleset = ruleset;
            this.severityLevel = severityLevel;
            this.severityThreshold = severityThreshold;
            this.reportType = ReportType.CODE_SCAN;
            this.codeScanOptions = new StringBuilder();
            codeScanOptions.append(StringUtils.isEmpty(source) ? "" : ("-s=" + source + " "))
                    .append(StringUtils.isEmpty(exclude) ? "" : ("-x=" + exclude + " "))
                    .append(noCache == null ? "" : ("-nc=" + noCache.toString() + " "))
                    .append(noProxy == null ? "" : ("-np=" + noProxy.toString() + " "))
                    .append(noBlame == null ? "" : ("-nb=" + noBlame.toString() + " "))
                    .append(StringUtils.isEmpty(ruleset) ? "" : ("-r=" + noBlame.toString() + " "))
                    .append(StringUtils.isEmpty(severityLevel) ? "" : ("-sev=" + severityLevel + " "))
                    .append(severityThreshold == null ? "" : ("-sevt=" + severityLevel.toString() + " "));
        }

        public ReportType getReportType() {
            return reportType;
        }

        public String getSeverityLevel() {
            return severityLevel;
        }

        public String getRuleset() {
            return ruleset;
        }

        public Boolean getNoBlame() {
            return noBlame;
        }

        public Boolean getNoProxy() {
            return noProxy;
        }

        public Boolean getNoCache() {
            return noCache;
        }

        public String getExclude() {
            return exclude;
        }

        public String getSource() {
            return source;
        }

        public Integer getSeverityThreshold() {
            return severityThreshold;
        }

        @Override
        String getBladeName() {
            return BLADE_NAME;
        }

        @Override
        String getBladeOptions() {
            return this.codeScanOptions.toString();
        }

    }

    public static final class ImageScan extends Blade {

        private final String image;
        private final String exclude;
        private final Boolean noCache;
        private final Boolean noProxy;
        private final Boolean noBlame;
        private final String ruleset;
        private final String severityLevel;
        private final Integer severityThreshold;

        private static final String BLADE_NAME = "image-scan";
        private final ReportType reportType;
        private StringBuilder imageScanOptions;

        @Extension
        public static class DescriptorImpl extends BladeDescriptor {

            @Override
            public String getDisplayName() {
                return "Image Scan";
            }
        }

        @DataBoundConstructor
        public ImageScan(String image, String exclude, Boolean noCache, Boolean noProxy, Boolean noBlame,
                String ruleset, String severityLevel, Integer severityThreshold) {
            this.image = image;
            this.exclude = exclude;
            this.noCache = noCache;
            this.noProxy = noProxy;
            this.noBlame = noBlame;
            this.ruleset = ruleset;
            this.severityLevel = severityLevel;
            this.severityThreshold = severityThreshold;
            this.reportType = ReportType.IMAGE_SCAN;
            this.imageScanOptions = new StringBuilder();
            imageScanOptions.append(StringUtils.isEmpty(image) ? "" : ("-i=" + image + " "))
                    .append(StringUtils.isEmpty(exclude) ? "" : ("-x=" + exclude + " "))
                    .append(noCache == null ? "" : ("-nc=" + noCache.toString() + " "))
                    .append(noProxy == null ? "" : ("-np=" + noProxy.toString() + " "))
                    .append(noBlame == null ? "" : ("-nb=" + noBlame.toString() + " "))
                    .append(StringUtils.isEmpty(ruleset) ? "" : ("-r=" + noBlame.toString() + " "))
                    .append(StringUtils.isEmpty(severityLevel) ? "" : ("-sev=" + severityLevel + " "))
                    .append(severityThreshold == null ? "" : ("-sevt=" + severityLevel.toString() + " "));
        }

        public ReportType getReportType() {
            return reportType;
        }

        public String getImage() {
            return image;
        }

        public String getExclude() {
            return exclude;
        }

        public Boolean getNoCache() {
            return noCache;
        }

        public Boolean getNoProxy() {
            return noProxy;
        }

        public Boolean getNoBlame() {
            return noBlame;
        }

        public String getRuleset() {
            return ruleset;
        }

        public String getSeverityLevel() {
            return severityLevel;
        }

        public Integer getSeverityThreshold() {
            return severityThreshold;
        }

        @Override
        String getBladeName() {
            return BLADE_NAME;
        }

        @Override
        String getBladeOptions() {
            return this.imageScanOptions.toString();
        }
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public String getDisplayName() {
            return "CheckPoint Shiftleft";
        }
    }

    public BuildStepDescriptor getDescriptor() {
        return (BuildStepDescriptor) Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        for (Blade blade : this.blades) {
            new ShiftleftBuilderExecutor(blade, run, workspace, launcher, listener);
        }
    }

}
