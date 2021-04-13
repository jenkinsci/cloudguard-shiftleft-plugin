package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Metadata {
    @SerializedName("client-flags")
    private String  clientFlags;
    @SerializedName("client-version")
    private String clientVersion;
    @SerializedName("info-source")
    private String infoSource;
    @SerializedName("project-name")
    private String projectName;
    @SerializedName("repo-url")
    private String repoUrl;
    private String os;
    private String branch;
    private String user;
    private String email;
    private Integer size;
    @SerializedName("files-count")
    private Integer filesCount;
    private String tag;
    private ArrayList<Exclusion> exclusions;
    public ArrayList<Exclusion> getExclusions() {
        return exclusions;
    }
    public String getClientFlags() {
        return clientFlags;
    }
    public void setClientFlags(String clientFlags) {
        this.clientFlags = clientFlags;
    }
    public String getClientVersion() {
        return clientVersion;
    }
    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
    public String getInfoSource() {
        return infoSource;
    }
    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getRepoUrl() {
        return repoUrl;
    }
    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getFilesCount() {
        return filesCount;
    }
    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setExclusions(ArrayList<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }
    public Metadata(String clientFlags, String clientVersion, String infoSource, String projectName, String repoUrl,
            String os, String branch, String user, String email, Integer size, Integer filesCount, String tag,
            ArrayList<Exclusion> exclusions) {
        this.setClientFlags(clientFlags);
        this.clientVersion = clientVersion;
        this.infoSource = infoSource;
        this.projectName = projectName;
        this.repoUrl = repoUrl;
        this.os = os;
        this.branch = branch;
        this.user = user;
        this.email = email;
        this.size = size;
        this.filesCount = filesCount;
        this.tag = tag;
        this.exclusions = exclusions;
    }

}
