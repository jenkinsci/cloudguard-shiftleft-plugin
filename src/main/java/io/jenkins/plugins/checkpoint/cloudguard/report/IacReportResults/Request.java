package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

import com.google.gson.annotations.SerializedName;

public class Request {
    private String cloudAccountId;
    private String cloudAccountType;
    private String requestId;
    @SerializedName("id")
    private Integer rulesetId;
    @SerializedName("name")
    private String rulesetName;

    public String getCloudAccountId() {
        return cloudAccountId;
    }

    public String getCloudAccountType() {
        return cloudAccountType;
    }

    public String getRequestId() {
        return requestId;
    }

    public Integer getRulesetId() {
        return rulesetId;
    }

    public String getRulesetName() {
        return rulesetName;
    }

    // View getters

    public String getRulesetDisplayName() {
        return String.format("%s (%d)", rulesetName, rulesetId );
    }
}
