package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

public class ScanResults {
  
    private final String stdout;
    private final String stderr;
    private final Integer status;

    public ScanResults(String stdout, String stderr, Integer status) {
        this.stdout = stdout;
        this.stderr = stderr;
        this.status = status;
    }
    public String getStdout() {
        return stdout;
    }
    public String getStderr() {
        return stderr;
    }
    public Integer getStatus() {
        return status;
    }
    
}
