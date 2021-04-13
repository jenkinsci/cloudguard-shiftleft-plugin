package io.jenkins.plugins.checkpoint.cloudguard.report;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class FileContents {
    @SerializedName("payload-sha256")
    private String payloadSHA256;
    private String payload;
    private ArrayList<Integer> lines;

    public ArrayList<Integer> getLines() {
        return lines;
    }
    public String getPayload() {
        return payload;
    }

    public String getPayloadSHA256() {
        return payloadSHA256;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public void setPayloadSHA256(String payloadSHA256) {
        this.payloadSHA256 = payloadSHA256;
    }
    public void setLines(ArrayList<Integer> lines) {
        this.lines = lines;
    }
    public FileContents(String payloadSHA256, String payload, ArrayList<Integer> lines) {
        this.payloadSHA256 = payloadSHA256;
        this.payload = payload;
        this.lines = lines;
    }
    

}
