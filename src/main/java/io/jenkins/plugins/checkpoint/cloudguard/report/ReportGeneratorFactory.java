package io.jenkins.plugins.checkpoint.cloudguard.report;

import com.google.gson.JsonObject;

public class ReportGeneratorFactory {
    
    public static ReportGenerator getReportGenerator(ReportType reportType, JsonObject jsonObject, Integer status){
        switch (reportType) {
            case CODE_SCAN:
                return new CodeScanReportGenerator(jsonObject, status);
            case IMAGE_SCAN:
                return new ImageScanReportGenerator(jsonObject, status);
            case IAC_ASSESSMENT:
                return new IacAssessmentReportGenerator(jsonObject, status);
            default:
                return null;
        }
    }
}
