package io.fastpix.data.Interfaces;

public interface DeviceContract {
    String getHardwareArchitecture();

    String getOSFamily();

    String getOSVersion();

    String getDeviceName();

    String getDeviceCategory();

    String getManufacturer();

    String getModelName();

    String getPlayerVersion();

    String getDeviceId();

    String getAppName();

    String getAppVersion();

    String getPluginName();

    String getPluginVersion();

    String getPlayerSoftware();

    String getNetworkConnectionType();

    long getElapsedRealtime();

    void outputLog(String  logKey, String message, String messageCode, Throwable throwable);

    void outputLog(String  logKey, String message, String messageCode);

    void outputLog(String message, String messageCode);
}
