package io.jenkins.plugins.checkpoint;

import java.io.File;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;

import com.cloudbees.plugins.credentials.common.UsernamePasswordCredentials;

import hudson.FilePath;

public class Utils {
    private static final String ENV_CLOUDGUARD_ID = "CHKP_CLOUDGUARD_ID";
    private static final String ENV_CLOUDGUARD_SECRET = "CHKP_CLOUDGUARD_SECRET";
    public static Map<String, String> populateCloudGuardEnvMap(UsernamePasswordCredentials credentials) {
        Map<String, String> envVars = new HashMap<String, String>();
        envVars.put(ENV_CLOUDGUARD_ID, credentials.getUsername());
        envVars.put(ENV_CLOUDGUARD_SECRET, credentials.getPassword().toString());
        return envVars;
    }


    public static void copyFileToWorkspace(FilePath workspace, String origin, String filename)
            throws IOException, InterruptedException {
        FilePath target = new FilePath(workspace, filename);
        File origFile = new File(origin + filename);
        FilePath origFilePath = new FilePath(origFile);
        origFilePath.copyTo(target);
    }

    public static String humanReadableByteCountSI(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }


}
