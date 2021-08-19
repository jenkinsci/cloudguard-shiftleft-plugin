package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

public class StatsBySeverityViewModel {
    private int critical;
    private int high;
    private int informational;
    private int low;
    private int medium;

    public int getInformational() {
        return informational;
    }

    public int getLow() {
        return low;
    }

    public int getMedium() {
        return medium;
    }

    public int getHigh() {
        return high;
    }

    public int getCritical() {
        return critical;
    }
}
