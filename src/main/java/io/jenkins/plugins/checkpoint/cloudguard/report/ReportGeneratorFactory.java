package io.jenkins.plugins.checkpoint.cloudguard.report;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ReportGeneratorFactory {
    
    public static ReportGenerator getReportGenerator(ReportType reportType, JsonObject jsonObject){
        switch (reportType) {
            case CODE_SCAN:
                return new CodeScanReportGenerator(jsonObject);
            case IMAGE_SCAN:
                return new ImageScanReportGenerator(jsonObject);
            default:
                return null;
        }
    }
}
