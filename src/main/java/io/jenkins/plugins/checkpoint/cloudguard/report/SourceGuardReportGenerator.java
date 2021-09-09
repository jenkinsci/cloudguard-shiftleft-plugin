package io.jenkins.plugins.checkpoint.cloudguard.report;

import static j2html.TagCreator.b;
import static j2html.TagCreator.br;
import static j2html.TagCreator.p;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.filter;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.iff;
import static j2html.TagCreator.iffElse;
import static j2html.TagCreator.join;
import static j2html.TagCreator.link;
import static j2html.TagCreator.meta;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tag;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;

public abstract class SourceGuardReportGenerator extends ReportGenerator {

        protected final static String REPORT_TITLE = "CloudGuard Scan";

        public SourceGuardReportGenerator(JsonObject jsonObject, Integer status) {
                super(jsonObject, status);
        }

        abstract String getScanHeaderTitle();

        abstract String getScanIcon();

        public static String getReportTitle() {
                return REPORT_TITLE;
        }

        @Override
        public String createHtmlFromScanResults() {
                SourceGuardReportResults sr = (SourceGuardReportResults) this.reportResults;
                return html(generateHeader(),
                                // Body
                                body(join(generateScanHeaderDetails(sr), div().withClass("spacer"),
                                                // Report sections
                                                generateMaliciousFilesReport(sr), div().withClass("spacer"),
                                                generatePackageFindingsSection(sr), div().withClass("spacer"),
                                                generateCodeFindingsReport(sr), div().withClass("spacer"),
                                                generateContentFindingsReport(sr), div().withClass("spacer"),
                                                generateIpFindingsReport(sr), div().withClass("spacer"),
                                                generateUrlFindingsReport(sr)))).render();
        }

        public DomContent generateScanHeaderDetails(SourceGuardReportResults sr) {
                return join(div().withClass("wrapper-header").with(div().withClass("header-icon").with(tag("svg")
                                .attr("xmlns", "http://www.w3.org/2000/svg").attr("style", "color: rgb(46, 63, 88)")
                                .attr("stroke-width", "0").attr("viewBox", "0 0 640 512").attr("color", "#2e3f58")
                                .attr("stroke", "currentColor").attr("fill", "currentColor").attr("height", "50")
                                .attr("width", "50").attr("size", "30").with(tag("path").attr("d", getScanIcon()))),
                                div(getScanHeaderTitle()).withClass("scan-header-project-name")),
                                div().withClass("scan-header-row").with(generateHeaderMainDetails(sr),
                                                generateHeaderSecondaryDetails(sr), generateHeaderSpecificDetails(sr)));
        }

        public DomContent generateHeader() {
                return join(head(title(getReportTitle()), link().withRel("stylesheet").withHref("style.css"),
                                meta().withName("viewport").withContent("widht=device-width, initial-scale=1"),
                                meta().withCharset("utf-8")));

        }

        abstract DomContent generateHeaderSpecificDetails(SourceGuardReportResults sr);

        private DomContent generateHeaderSecondaryDetails(SourceGuardReportResults sr) {
                return join(div(join(b("Scan ID:"), br(), sr.getScanId())).withClass("scan-header-column"),
                                div(join(b("Repository:"), br(), sr.getMetadata().getRepoUrl()))
                                                .withClass("scan-header-column"));
        }

        private DomContent generateHeaderMainDetails(SourceGuardReportResults sr) {
                return join(div(join(b("Scan Time:"), br(),
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                                                .withClass("scan-header-column"));
        }

        private DomContent generateContentFindingsReport(SourceGuardReportResults sr) {
                try {
                        if (sr.getContent().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Content Findings").withClass("report-section-header"),
                                                div(div().withClass("package-header").with(div().withClass("spacer")),
                                                                this.renderContentFindingsTable(sr.getContent())));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        private DomContent generateIpFindingsReport(SourceGuardReportResults sr) {
                try {
                        if (sr.getIps().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Malicious IPs").withClass("report-section-header"),
                                                div(div().withClass("package-header").with(div().withClass("spacer")),
                                                                this.renderIpFindingsTable(sr.getIps())));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        private DomContent generateUrlFindingsReport(SourceGuardReportResults sr) {
                try {
                        if (sr.getUrls().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Malicious URLs").withClass("report-section-header"),
                                                div(div().withClass("package-header").with(div().withClass("spacer")),
                                                                this.renderUrlFindingsTable(sr.getUrls())));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        private DomContent generateMaliciousFilesReport(SourceGuardReportResults sr) {
                try {
                        if (sr.getFiles().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Malicious Files").withClass("report-section-header"),
                                                div(div().withClass("package-header").with(div().withClass("spacer")),
                                                                this.renderMaliciousFilesTable(sr.getFiles())));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        private DomContent generateCodeFindingsReport(SourceGuardReportResults sr) {
                try {
                        if (sr.getCode().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Code Findings").withClass("report-section-header"),
                                                div(div().withClass("package-header").with(div().withClass("spacer")),
                                                                this.renderCodeFindingsTable(sr.getCode())));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        private DomContent generatePackageFindingsSection(SourceGuardReportResults sr) {
                try {
                        if (sr.getPackages().size() <= 0) {
                                return div();
                        } else {
                                return div().withClass("report-section").with(
                                                div("Dependencies").withClass("report-section-header"),
                                                each(sr.getPackages(), dep -> div(
                                                                div().withClass("package-header").with(
                                                                                div(join(b("Package manager: "), dep
                                                                                                .getPackageManager())),
                                                                                div(join(b("Package-path: "),
                                                                                                dep.getFilePath())),
                                                                                div(join(b("Vulnerable deps: "), String
                                                                                                .valueOf(dep.getMatchedDependencies()))),
                                                                                div().withClass("spacer")),
                                                                this.renderPackageFindingsTable(dep))));
                        }
                } catch (NullPointerException e) {
                        return div();
                }
        }

        public DomContent renderCVETable(PackageDependencies dt) {
                return each(dt.safeGetCVE(), cve -> tr(td(dt.getName()).attr("data-th", "Dependecy Name"),
                                td(iff(dt.hasDepTree(), cve.getId())).attr("data-th", "ID"),
                                td(join(iffElse(dt.hasDepTree(), ReportGenerator.getButton(cve.getSeverity()),
                                                ReportGenerator.getButton(dt.getSeverity())),
                                                (iffElse(dt.hasDepTree(), cve.getSeverity(), dt.getSeverity()))))
                                                                .attr("data-th", "Severity"),
                                td(iff(dt.hasDepTree(), cve.getLast_modified())).attr("data-th", "Last Modified"),
                                td(iff(dt.hasDepTree(), cve.getDescription())).attr("data-th", "Description")));
        }

        // TODO: Check how-to use the recursive processing
        public List<DomContent> recurseDepTree(PackageDependencies dt) {
                List<DomContent> content = new ArrayList<>();
                content.add(this.renderCVETable(dt));
                while (dt.hasDepTree()) {
                        for (PackageDependencies depTree : dt.getDependenciesTree()) {
                                content.addAll(this.recurseDepTree(depTree));
                        }
                }
                return content;
        }

        public ContainerTag getPackageFindingsTableHeader() {
                return tr(th("Dependency Name"), th("ID"), th("Severity"), th("Last modified"), th("Description"));
        }

        public ContainerTag getContentFindingsTableHeader() {
                return tr(th("Name"), th("Payload"), th("Severity"), th("Description"), th("File Path"), th("Line"),
                                th("Remediation"), th("Blame"));
        }

        public ContainerTag getMaliciousFilesTableHeader() {
                return tr(th("File Path"), th("Verdict"), th("Severity"), th("Classification"), th("Protection Name"),
                                th("Remediation"), th("Blame"));
        }

        public ContainerTag getUrlsTableHeader() {
                return tr(th("URL"), th("Severity"), th("Verdict"), th("Classification"), th("File Path"), th("Line"),
                                th("Remediation"), th("Blame"));
        }

        public ContainerTag getIpsTableHeader() {
                return tr(th("IP"), th("Severity"), th("Verdict"), th("Classification"), th("File Path"), th("Line"),
                                th("Remediation"), th("Blame"));
        }

        public ContainerTag getCodeFindingsTableHeader() {
                return getContentFindingsTableHeader();
        }

        private DomContent renderCodeFindingRow(CodeFinding finding) {
                return tr(td(finding.getName()).attr("data-th", "Content Name"),
                                td(finding.getPayload()).attr("data-th", "PayLoad"),
                                td(join(ReportGenerator.getButton(finding.getSeverity()), finding.getSeverity()))
                                                .attr("data-th", "Severity"),
                                td("Need description").attr("data-th", "Description"),
                                td(finding.getFilePath()).attr("data-th", "File-Path"),
                                td(finding.getLine().toString()).attr("data-th", "Line"),
                                td(finding.getRemediation().getMessage()).attr("data-th", "Remediation"),
                                td("Need Blame data").attr("data-th", "Blame"));
        }

        private DomContent renderContentFindingRow(ContentFinding finding) {
                return tr(td(finding.getName()).attr("data-th", "Content Name"),
                                td(finding.getPayload()).attr("data-th", "PayLoad"),
                                td(join(ReportGenerator.getButton(finding.getSeverity()), finding.getSeverity()))
                                                .attr("data-th", "Severity"),
                                td("Need description").attr("data-th", "Description"),
                                td(finding.getFilePath()).attr("data-th", "File Path"),
                                td(finding.getLine().toString()).attr("data-th", "Line"),
                                td(finding.getRemediation().getMessage()).attr("data-th", "Remediation"),
                                td("Need Blame data").attr("data-th", "Blame"));
        }

        private DomContent renderMaliciousFileFindingRow(MaliciousFile finding) {
                return tr(td(finding.getFilePath()).attr("data-th", "File Path"),
                                td(finding.getVerdict()).attr("data-th", "Verdict"),
                                td(join(ReportGenerator.getButton(finding.getRepSeverity()), finding.getRepSeverity()))
                                                .attr("data-th", "Severity"),
                                td(finding.getClassification()).attr("data-th", "Classification"),
                                td(finding.getProtectionName()).attr("data-th", "Protection Name"),
                                td("Need remediation").attr("data-th", "Remediation"),
                                td("Need Blame data").attr("data-th", "Blame"));
        }

        private DomContent renderIpFindingRow(IP finding) {
                return tr(td(finding.getValue()).attr("data-th", "IP"),
                                td(join(ReportGenerator.getButton(finding.getRepSeverity()), finding.getRepSeverity()))
                                                .attr("data-th", "Severity"),
                                td(finding.getVerdict()).attr("data-th", "Verdict"),
                                td(finding.getClassification()).attr("data-th", "Classification"),
                                td(finding.getFilePath()).attr("data-th", "File Path"),
                                td(finding.getLine().toString()).attr("data-th", "Line"),
                                td(finding.getRemediation().getMessage()).attr("data-th", "Remediation"),
                                td("Need Blame data").attr("data-th", "Blame"));
        }

        private DomContent renderUrlFindingRow(URL finding) {
                return tr(td(finding.getValue()).attr("data-th", "URL"),
                                td(join(ReportGenerator.getButton(finding.getRepSeverity()), finding.getRepSeverity()))
                                                .attr("data-th", "Severity"),
                                td(finding.getVerdict()).attr("data-th", "Verdict"),
                                td(finding.getClassification()).attr("data-th", "Classification"),
                                td(finding.getFilePath()).attr("data-th", "File Path"),
                                td(finding.getLine().toString()).attr("data-th", "Line"),
                                td(finding.getRemediation().getMessage()).attr("data-th", "Remediation"),
                                td("Need Blame data").attr("data-th", "Blame"));
        }

        public ContainerTag renderCodeFindingsTable(ArrayList<CodeFinding> codeFindings) {
                return table().withClass("rwd-table").with(this.getCodeFindingsTableHeader(),
                                each(filter(codeFindings, finding -> finding != null),
                                                finding -> this.renderCodeFindingRow(finding)));
        }

        public ContainerTag renderContentFindingsTable(ArrayList<ContentFinding> contentFinding) {
                return table().withClass("rwd-table").with(this.getContentFindingsTableHeader(),
                                each(filter(contentFinding, finding -> finding != null),
                                                finding -> this.renderContentFindingRow(finding)));
        }

        public ContainerTag renderMaliciousFilesTable(ArrayList<MaliciousFile> filesFinding) {
                return table().withClass("rwd-table").with(this.getMaliciousFilesTableHeader(),
                                each(filter(filesFinding, finding -> finding != null),
                                                finding -> this.renderMaliciousFileFindingRow(finding)));
        }

        public ContainerTag renderPackageFindingsTable(PackageFindings dep) {
                if (dep.getDependenciesTree().stream().filter(o -> o.getMatch_cves() != null).count() > 0) {
                        return table().withClass("rwd-table").with(this.getPackageFindingsTableHeader(),
                                        each(dep.getDependenciesTree(), dt -> this.renderCVETable(dt)));
                } else {
                        return div().withClass("spacer");
                }
        }

        public ContainerTag renderUrlFindingsTable(ArrayList<URL> urlFindings) {
                return table().withClass("rwd-table").with(this.getUrlsTableHeader(),
                                each(filter(urlFindings, finding -> finding != null),
                                                finding -> this.renderUrlFindingRow(finding)));
        }

        public ContainerTag renderIpFindingsTable(ArrayList<IP> ipFindings) {
                return table().withClass("rwd-table").with(this.getIpsTableHeader(),
                                each(filter(ipFindings, finding -> finding != null),
                                                finding -> this.renderIpFindingRow(finding)));
        }

        @Override
        public void initializeReportResults() {
                // this.reportResults = new CodeScanReportResults(this.jsonObject);
                Gson gson = new Gson();
                this.reportResults = gson.fromJson(this.jsonObject, SourceGuardReportResults.class);

        }
}
