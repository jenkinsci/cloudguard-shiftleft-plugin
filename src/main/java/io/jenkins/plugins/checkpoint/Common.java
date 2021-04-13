package io.jenkins.plugins.checkpoint;

public class Common {

    private static final String SCAN_OUTPUT_PREFIX = "shiftleftoutput";
    private static final String SCAN_EXTENSION = ".html";

	public static String getScanOutputPrefix() {
		return SCAN_OUTPUT_PREFIX;
    }
	public static String getScanExtension() {
		return SCAN_EXTENSION;
    }
}
