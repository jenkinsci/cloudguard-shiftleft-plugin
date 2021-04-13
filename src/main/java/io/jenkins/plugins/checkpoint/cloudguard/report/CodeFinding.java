package io.jenkins.plugins.checkpoint.cloudguard.report;

import com.google.gson.annotations.SerializedName;

public class CodeFinding {
    
    @SerializedName("file-path")
    private String filePath;
    private String id;
    private String name;
    private String severity;
    private String payload;
    private Integer line;
    private Remediation remediation;

    public String getName() {
        return name;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Remediation getRemediation() {
        return remediation;
    }
    public void setRemediation(Remediation remediation) {
        this.remediation = remediation;
    }
    public Integer getLine() {
        return line;
    }
    public void setLine(Integer line) {
        this.line = line;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
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
    public CodeFinding(String filePath, String id, String name, String severity, String payload, Integer line,
            Remediation remediation) {
        this.filePath = filePath;
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.payload = payload;
        this.line = line;
        this.remediation = remediation;
    }
}
