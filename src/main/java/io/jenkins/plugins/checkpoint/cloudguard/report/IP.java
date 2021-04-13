package io.jenkins.plugins.checkpoint.cloudguard.report;

import com.google.gson.annotations.SerializedName;

public class IP {
    private String value;
    private String verdict;
    @SerializedName("rep-severity")
    private String repSeverity;
    private String classification;
    @SerializedName("file-path")
    private String filePath;
    private String id;
    private String name;
    private String payload;
    private Integer line;
    private Remediation remediation;

    public String getClassification() {
        return classification;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getRepSeverity() {
        return repSeverity;
    }
    public void setRepSeverity(String repSeverity) {
        this.repSeverity = repSeverity;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public Integer getLine() {
        return line;
    }
    public void setLine(Integer line) {
        this.line = line;
    }
    public Remediation getRemediation() {
        return remediation;
    }
    public void setRemediation(Remediation remediation) {
        this.remediation = remediation;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
    public String getVerdict() {
        return verdict;
    }
    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public IP(String value, String verdict, String repSeverity, String classification, String filePath, String id,
            String name, String payload, Integer line, Remediation remediation) {
        this.value = value;
        this.verdict = verdict;
        this.repSeverity = repSeverity;
        this.classification = classification;
        this.filePath = filePath;
        this.id = id;
        this.name = name;
        this.payload = payload;
        this.line = line;
        this.remediation = remediation;
    }
}
