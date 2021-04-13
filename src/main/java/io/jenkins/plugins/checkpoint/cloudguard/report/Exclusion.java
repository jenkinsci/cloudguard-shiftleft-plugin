package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

public class Exclusion {
    private String source;
    private ArrayList<String> paths;

    public String getSource() {
        return source;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Exclusion(String source, ArrayList<String> paths) {
        this.source = source;
        this.paths = paths;
    }

}
