package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class PackageDependencies {
        private String name;
        private String version;
        private String operator;
        private String line;
        private String source;
        private String severity;
        private Remediation remediation;
        private ArrayList<CVE> match_cves;
        @SerializedName("dependencies-tree")
        private ArrayList<PackageDependencies> dependenciesTree;


        public Remediation getRemediation() {
            return remediation;
        }

        public void setRemediation(Remediation remediation) {
            this.remediation = remediation;
        }

        public boolean hasDepTree() {
            if (this.match_cves != null) {
                return this.match_cves.size() > 0;
            } else {
                return false;
            }
        }

        public ArrayList<CVE> safeGetCVE() {
            if (this.hasDepTree()) {
                return this.getMatch_cves();
            } else {
                return new ArrayList<CVE>();
            }
        }

        public ArrayList<CVE> getMatch_cves() {
            return match_cves;
        }

        public void setMatch_cves(ArrayList<CVE> match_cves) {
            this.match_cves = match_cves;
        }

        public String getSeverity() {
            return severity;
        }

        public void setSeverity(String severity) {
            this.severity = severity;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<PackageDependencies> getDependenciesTree() {
            return dependenciesTree;
        }

        public void setDependenciesTree(ArrayList<PackageDependencies> dependenciesTree) {
            this.dependenciesTree = dependenciesTree;
        }

        public PackageDependencies(String name, String version, String operator, String line, String source,
                String severity, Remediation remediation, ArrayList<CVE> match_cves,
                ArrayList<PackageDependencies> dependenciesTree) {
            this.name = name;
            this.version = version;
            this.operator = operator;
            this.line = line;
            this.source = source;
            this.severity = severity;
            this.remediation = remediation;
            this.match_cves = match_cves;
            this.dependenciesTree = dependenciesTree;
        }
    }
