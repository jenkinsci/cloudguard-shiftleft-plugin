package io.jenkins.plugins.checkpoint.cloudguard.report;

public class Remediation {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Remediation(String message) {
        this.message = message;
    }

}
