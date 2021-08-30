package io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults;

public class StatsBySeverityViewModel {
    private Integer critical;
    private Integer high;
    private Integer informational;
    private Integer low;
    private Integer medium;

    public Integer getInformational() {
        return informational;
    }

    public Integer getLow() {
        return low;
    }

    public Integer getMedium() {
        return medium;
    }

    public Integer getHigh() {
        return high;
    }

    public Integer getCritical() {
        return critical;
    }
}
