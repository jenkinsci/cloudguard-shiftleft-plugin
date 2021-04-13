package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SourceGuardReportResults extends ReportResults {
        @SerializedName("scan-id")
        private String scanId;
        private String status;
        private String action;
        private float score;
        private String type;
        private Metadata metadata;
        private ArrayList<CodeFinding> code;
        private ArrayList<MaliciousFile> files;
        private ArrayList<ContentFinding> content;
        private ArrayList<URL> urls;
        private ArrayList<IP> ips;
        private Exclusion exclusions;

        private ArrayList<PackageFindings> packages;

        public String getAction() {
            return action;
        }

        public String getScanId() {
            return scanId;
        }

        public void setScanId(String scanId) {
            this.scanId = scanId;
        }

        public ArrayList<IP> getIps() {
            return ips;
        }

        public void setIps(ArrayList<IP> ips) {
            this.ips = ips;
        }

        public ArrayList<MaliciousFile> getFiles() {
            return files;
        }

        public void setFiles(ArrayList<MaliciousFile> files) {
            this.files = files;
        }

        public ArrayList<ContentFinding> getContent() {
            return content;
        }

        public void setContent(ArrayList<ContentFinding> content) {
            this.content = content;
        }

        public ArrayList<CodeFinding> getCode() {
            return code;
        }

        public void setCode(ArrayList<CodeFinding> code) {
            this.code = code;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Exclusion getExclusions() {
            return exclusions;
        }

        public void setExclusions(Exclusion exclusions) {
            this.exclusions = exclusions;
        }

        public ArrayList<URL> getUrls() {
            return urls;
        }

        public void setUrls(ArrayList<URL> urls) {
            this.urls = urls;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public ArrayList<PackageFindings> getPackages() {
            return packages;
        }

        public void listPackages() {
            for (PackageFindings s : this.packages) {
                System.out.println(s.getPackageManager());
            }
        }

}
