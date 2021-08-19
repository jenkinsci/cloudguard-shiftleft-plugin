package io.jenkins.plugins.checkpoint.cloudguard.report;

import static j2html.TagCreator.*;

import io.jenkins.plugins.checkpoint.cloudguard.report.IacReportResults.IacReportResults;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import j2html.tags.DomContent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class IacAssessmentReportGenerator extends ReportGenerator {

    private final static String REPORT_TITLE = "CloudGuard Assessment";
    private final static String ASSESSMENT_HEADER_TITLE = "Infra as Code Assessment Results";
    private final static String IAC_ICON = "";

    public IacAssessmentReportGenerator(JsonObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void initializeReportResults() {
        Gson gson = new Gson();
        this.reportResults = gson.fromJson(this.jsonObject, IacReportResults.class);
    }

    @Override
    public String createHtmlFromScanResults() {
        IacReportResults ir = (IacReportResults) this.reportResults;
        return html(
                generateHeader(),
                body(join(generateScanHeaderDetails(ir), div().withClass("spacer"), div().withClass("wrapper-header")))
        ).render();
    }

    private DomContent generateHeader() {
        return join(head(title(REPORT_TITLE), link().withRel("stylesheet").withHref("style.css"),
                meta().withName("viewport").withContent("widht=device-width, initial-scale=1"),
                meta().withCharset("utf-8")));

    }

    private DomContent generateScanHeaderDetails(IacReportResults ir) {
        return join(
                // todo: Add IaC icon
//                div().withClass("wrapper-header")
//                        .with(div().withClass("header-icon").with(tag("svg").attr("xmlns", "http://www.w3.org/2000/svg")
//                                .attr("style", "color: rgb(46, 63, 88)").attr("stroke-width", "0")
//                                .attr("viewBox", "0 0 640 512").attr("color", "#2e3f58").attr("stroke", "currentColor")
//                                .attr("fill", "currentColor").attr("height", "50").attr("width", "50")
//                                .attr("size", "30").with(tag("path").attr("d", IAC_ICON))),
//                                div(ASSESSMENT_HEADER_TITLE).withClass("scan-header-project-name")),
                div(ASSESSMENT_HEADER_TITLE).withClass("scan-header-project-name"),
                div().withClass("spacer").with(
                        generateHeaderMainDetails(ir),
                        generateHeaderSecondaryDetails(ir),
                        generateHeaderStats(ir)
                ),
                div().withClass("spacer").with(
                        generateLinkToResult(ir)
                )
        );
    }

    private DomContent generateHeaderMainDetails(IacReportResults ir) {
        return join(
            div(join(b(ir.getTestResult()))).withClass("scan-header-row report-section-header " + (ir.getAssessmentPassed() ? "scan-result-pass" : "scan-result-fail")),
            div(join(b("Assessment Time:"), span(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).withClass("scan-header-value"))).withClass("scan-header-row spacer")
        );
    }

    private DomContent generateHeaderSecondaryDetails(IacReportResults ir) {
        return join(
                div(join(b("Ruleset:"), span(ir.getRequest().getRulesetDisplayName()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Environment:"), span(ir.getEnvironmentDisplayName()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details")
        );
    }

    private DomContent generateHeaderStats(IacReportResults ir) {
        return join(
                div(join(b("Passed:"), span(ir.getStats().getPassed().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Failed:"), span(ir.getStats().getFailed().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Error:"), span(ir.getStats().getError().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Failed Tests:"), span(ir.getStats().getFailedTests().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Logically Tested:"), span(ir.getStats().getLogicallyTested().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Failed Entities:"), span(ir.getStats().getFailedEntities().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Excluded Tests:"), span(ir.getStats().getExcludedTests().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Excluded Failed Tests:"), span(ir.getStats().getExcludedFailedTests().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details"),
                div(join(b("Excluded Rules:"), span(ir.getStats().getExcludedRules().toString()).withClass("scan-header-value"))).withClass("scan-header-row scan-header-details")
        );
    }

    private DomContent generateLinkToResult(IacReportResults ir) {
        return join(div(a("Open assessment results in CloudGuard").withHref(ir.getResultUrl())));
    }
}
