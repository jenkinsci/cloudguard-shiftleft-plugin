package io.jenkins.plugins.checkpoint.cloudguard.shiftleft;

import hudson.model.RootAction;

/**
 * Root action to provide a link towards CloudGuard portal
 */
public class CloudGuardRootAction implements RootAction {

    @Override
    public String getIconFileName() {
        return "clipboard.png";
    }

    @Override
    public String getDisplayName() {
        return "CloudGuard Shiftleft";
    }

    @Override
    public String getUrlName() {
        return "https://secure.dome9.com";
    }
}
