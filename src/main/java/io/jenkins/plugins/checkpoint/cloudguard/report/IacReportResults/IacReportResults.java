package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import io.jenkins.plugins.checkpoint.cloudguard.report.ReportResults;

public class IacReportResults extends ReportResults {
    @SerializedName("id")
    private Long resultId;
    private String assessmentId;
    private boolean assessmentPassed;
    private String storedKey;
    private String triggeredBy;
    private String environmentId;
    private String environmentName;
    private ArrayList<Exclusion> exclusions;
    private ArrayList<Object> remediations;
    private ArrayList<Object> tests;
    private Stats stats;
    private Request request;
    private String resultUrl;

    public String getAssessmentId() {
        return assessmentId;
    }

    public boolean getAssessmentPassed() {
        return assessmentPassed;
    }

    public String getStoredKey() {
        return storedKey;
    }

    public String getTriggeredBy() {
        return triggeredBy;
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public ArrayList<Exclusion> getExclusions() {
        return exclusions;
    }

    public ArrayList<Object> getRemediations() {
        return remediations;
    }

    public Stats getStats() {
        return stats;
    }

    public Request getRequest() {
        return request;
    }
    public String getResultUrl() {
        return resultUrl;
    }

    // View getters

    public String getEnvironmentDisplayName() {
        return environmentName + " (" + environmentId + ")";
    }

    public String getTestResult() {
        return assessmentPassed ? "Assessment Passed" : "Assessment Failed";
    }
}
