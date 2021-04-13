package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

import hudson.model.Action;
import hudson.model.Run;
import jenkins.model.Jenkins;

public class CloudGuardAction implements Action {

    private final Run<?, ?> build;
    private final String jenkinsOutputDirName;
    private final String scanReportName;
    private final String scanOutputURL;

    @Override
    public String getIconFileName() {
        return Jenkins.RESOURCE_PATH + "/plugin/cloudguard-shiftleft/images/topic_shiftleft.svg";
    }

    public String getJenkinsOutputDirName() {
		return jenkinsOutputDirName;
	}

    public String getScanReportName() {
		return scanReportName;
	}

    public String getScanOutputURL() {
		return scanOutputURL;
	}

    @Override
    public String getDisplayName() {
        return this.getScanReportName();
    }

    @Override
    public String getUrlName() {
        return scanOutputURL;
    }

    public Run<?, ?> getBuild() {
        return this.build;
    }

    public CloudGuardAction(Run<?, ?> build, String jenkinsOutputDirName, String scanReportName) {
        this.build = build;
        this.jenkinsOutputDirName = jenkinsOutputDirName;
        this.scanReportName = scanReportName;
        this.scanOutputURL = "./artifact/" + jenkinsOutputDirName + "/" + scanReportName;
    }

}
