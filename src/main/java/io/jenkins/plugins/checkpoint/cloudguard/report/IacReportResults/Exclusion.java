package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

import java.util.ArrayList;

public class Exclusion {
    private ArrayList<String> cloudAccountIds;
    private String comment;
    private String dateRange;
    private String id;
    ArrayList<Object> rules;
    ArrayList<Object> logicExpressions;
    private String platform;
    private float rulesetId;
    ArrayList<String> organizationalUnitIds;

    // Getter Methods

    public ArrayList<String> getCloudAccountsIds() {
        return cloudAccountIds;
    }

    public String getComment() {
        return comment;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getId() {
        return id;
    }

    public String getPlatform() {
        return platform;
    }

    public float getRulesetId() {
        return rulesetId;
    }
}
