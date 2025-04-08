package io.fastpix.data.request;

public class CustomOptions {
    private String string;
    private String string1;
    private boolean aBoolean;

    public CustomOptions() {
    }

    public boolean isLongBeaconDispatch() {
        return this.aBoolean;
    }

    public void setLongBeaconDispatch(boolean longBeaconDispatch) {
        this.aBoolean = longBeaconDispatch;
    }

    public String getBeaconCollectionDomain() {
        return this.string1;
    }

    public CustomOptions setBeaconCollectionDomain(String beaconCollectionDomain) {
        this.string1 = beaconCollectionDomain;
        return this;
    }

    public String getBeaconDomain() {
        return this.string;
    }

    public CustomOptions setBeaconDomain(String beaconDomain) {
        this.string = beaconDomain.startsWith(".") ? beaconDomain : "." + beaconDomain;
        return this;
    }

    public CustomOptions setSentryEnabled(boolean sentryEnabled) {
        return this;
    }

    public boolean isSentryEnabled() {
        return false;
    }
}
