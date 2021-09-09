package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

public class Stats {
    private Integer passed;
    private StatsBySeverityViewModel passedRulesBySeverity;
    private Integer failed;
    private StatsBySeverityViewModel failedRulesBySeverity;
    private Integer error;
    private Integer failedTests;
    private Integer logicallyTested;
    private Integer failedEntities;
    private Integer excludedTests;
    private Integer excludedFailedTests;
    private Integer excludedRules;
    private StatsBySeverityViewModel excludedRulesBySeverity;

    public Integer getPassed() {
        return passed;
    }

    public StatsBySeverityViewModel getPassedRulesBySeverity() {
        return passedRulesBySeverity;
    }

    public Integer getFailed() {
        return failed;
    }

    public StatsBySeverityViewModel getFailedRulesBySeverity() {
        return failedRulesBySeverity;
    }

    public Integer getError() {
        return error;
    }

    public Integer getFailedTests() {
        return failedTests;
    }

    public Integer getLogicallyTested() {
        return logicallyTested;
    }

    public Integer getFailedEntities() {
        return failedEntities;
    }

    public Integer getExcludedTests() {
        return excludedTests;
    }

    public Integer getExcludedFailedTests() {
        return excludedFailedTests;
    }

    public Integer getExcludedRules() {
        return excludedRules;
    }

    public StatsBySeverityViewModel getExcludedRulesBySeverity() {
        return excludedRulesBySeverity;
    }
}
