package io.jenkins.plugins.checkpoint.cloudguard.report;

import com.google.gson.annotations.SerializedName;

public class MaliciousFile {

    @SerializedName("file-path")
    private String filePath;
    private String value;
    private String verdict;
    @SerializedName("rep-severity")
    private String repSeverity;
    private String classification;
    @SerializedName("protection-name")
    private String protectionName;


    public String getProtectionName() {
        return protectionName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getVerdict() {
        return verdict;
    }
    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }
    public String getRepSeverity() {
        return repSeverity;
    }
    public void setRepSeverity(String repSeverity) {
        this.repSeverity = repSeverity;
    }
    public String getClassification() {
        return classification;
    }
    public void setClassification(String classification) {
        this.classification = classification;
    }
    public void setProtectionName(String protectionName) {
        this.protectionName = protectionName;
    }
    public MaliciousFile(String filePath, String value, String verdict, String repSeverity, String classification,
            String protectionName) {
        this.filePath = filePath;
        this.value = value;
        this.verdict = verdict;
        this.repSeverity = repSeverity;
        this.classification = classification;
        this.protectionName = protectionName;
    }
}
