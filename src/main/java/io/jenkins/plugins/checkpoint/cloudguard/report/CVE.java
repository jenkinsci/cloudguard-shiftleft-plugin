package io.jenkins.plugins.checkpoint.cloudguard.report;

public class CVE {
    private String description;
    private String id;
    private String severity;
    private String last_modified;

    public CVE(String description, String id, String severity, String last_modified) {
        this.setDescription(description);
        this.setId(id);
        this.setSeverity(severity);
        this.setLast_modified(last_modified);
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
