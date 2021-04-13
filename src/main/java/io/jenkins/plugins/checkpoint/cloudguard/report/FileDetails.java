package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FileDetails {
    @SerializedName("file-path")
    private String filePath;
    private String sha256;
    private ArrayList<FileContents> contents;

    public String getSha256() {
        return sha256;
    }
    public String getFilePath() {
        return filePath;
    }
    public ArrayList<FileContents> getContents() {
        return contents;
    }
    public void setContents(ArrayList<FileContents> contents) {
        this.contents = contents;
    }
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public FileDetails(String filePath, String sha256, ArrayList<FileContents> contents) {
        this.filePath = filePath;
        this.sha256 = sha256;
        this.contents = contents;
    }

}
