package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class PackageFindings {
        private String sha256;
        @SerializedName("file-path")
        private String filePath;
        @SerializedName("package-manager")
        private String packageManager;
        private String os;
        private String severity;
        private String layer;
        @SerializedName("is-os-package")
        private Boolean isOSPackage;
        @SerializedName("matched-dependencies")
        private Integer matchedDependencies;
        @SerializedName("dependencies-tree")
        private ArrayList<PackageDependencies> dependenciesTree;

        public PackageFindings(String severity, String sha256, String filePath, String packageManager, String os,
                String layer, Boolean isOSPackage, Integer matchedDependencies,
                ArrayList<PackageDependencies> dependenciesTree) {
            this.setSeverity(severity);
            this.setSha256(sha256);
            this.filePath = filePath;
            this.packageManager = packageManager;
            this.setOs(os);
            this.setLayer(layer);
            this.setIsOSPackage(isOSPackage);
            this.setMatchedDependencies(matchedDependencies);
            this.setDependenciesTree(dependenciesTree);
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getSha256() {
            return sha256;
        }

        public void setSha256(String sha256) {
            this.sha256 = sha256;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getPackageManager() {
            return packageManager;
        }

        public void setPackageManager(String packageManager) {
            this.packageManager = packageManager;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public Boolean getIsOSPackage() {
            return isOSPackage;
        }

        public void setIsOSPackage(Boolean isOSPackage) {
            this.isOSPackage = isOSPackage;
        }

        public Integer getMatchedDependencies() {
            return matchedDependencies;
        }

        public void setMatchedDependencies(Integer matchedDependencies) {
            this.matchedDependencies = matchedDependencies;
        }

        public ArrayList<PackageDependencies> getDependenciesTree() {
            return dependenciesTree;
        }

        public void setDependenciesTree(ArrayList<PackageDependencies> dependenciesTree) {
            this.dependenciesTree = dependenciesTree;
        }

    }
