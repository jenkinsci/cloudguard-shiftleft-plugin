package io.jenkins.plugins.checkpoint.cloudguard.report;

import static j2html.TagCreator.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import hudson.FilePath;
import j2html.tags.ContainerTag;

public abstract class ReportGenerator {

    protected ReportResults reportResults;
    protected final JsonObject jsonObject;
    protected final Integer status;

    public ReportGenerator(JsonObject jsonObject, Integer status) {
        this.jsonObject = jsonObject;
        this.status = status;
        this.initializeReportResults();
    }

    public abstract void initializeReportResults();

    public ReportResults getReportResults() {
        return reportResults;
    }

    public abstract String createHtmlFromScanResults();

    public static ContainerTag getButton(String severity) {
        Map<String, String> colors = new HashMap<String, String>();
        colors.put("CRITICAL", "darkred");
        colors.put("HIGH", "#F24E4E");
        colors.put("LOW", "#FFD546");
        String color = colors.getOrDefault(severity, "#FF9126");
        return tag("svg").attr("stroke", color).attr("fill", color).attr("stroke-width", "0")
                .attr("viewBox", "0 0 512 512").attr("size", "10").attr("height", "10").attr("width", "10")
                .attr("xmlns", "http://www.w3.org/2000/svg").attr("style", "margin-right: 5px;")
                .with(tag("path").attr("d", "M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8z"));
    }

    public void generateHTMLReport(FilePath target) throws InterruptedException, IOException {
        String htmlStr = createHtmlFromScanResults();
        try {
            target.write(htmlStr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
