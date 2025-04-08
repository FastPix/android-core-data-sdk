package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The ViewerData class extends the BaseQueryData class and is used to represent
 * data associated with a viewer's activity or state within the system.
 * It includes methods for setting and getting various viewer-related attributes
 * such as view time, device orientation, network throughput, and other performance
 * metrics related to viewing content.
 *
 * This class likely serves as a container for viewer-specific information,
 * which can be stored, retrieved, or processed during analytics or logging activities.
 */
public class ViewerDataEntity extends QueryDataEntity {

    /**
     * The VIEWER_APPLICATION_ENGINE constant represents the engine used by the viewer application.
     * It is typically used to track the specific engine powering the application.
     */
    public static final String VIEWER_APPLICATION_ENGINE = "viapei";

    /**
     * The VIEWER_APPLICATION_NAME constant holds the name of the viewer application.
     * This is useful for identifying which application the data pertains to.
     */
    public static final String VIEWER_APPLICATION_NAME = "br";

    /**
     * The VIEWER_APPLICATION_VERSION constant stores the version number of the viewer application.
     * This can help in identifying specific versions of the app during analytics or troubleshooting.
     */
    public static final String VIEWER_APPLICATION_VERSION = "brvn";

    /**
     * The VIEWER_OS_ARCHITECTURE constant contains the architecture type of the viewer's operating system.
     * This is important for understanding compatibility and performance on different system architectures.
     */
    public static final String VIEWER_OS_ARCHITECTURE = "viosar";

    /**
     * The VIEWER_CONNECTION_TYPE constant represents the type of network connection the viewer is using.
     * This could include types like Wi-Fi, mobile data, or Ethernet.
     */
    public static final String VIEWER_CONNECTION_TYPE = "vicity";

    /**
     * The VIEWER_TIMESTAMP constant is used to store the timestamp when the viewer's data was captured.
     * It ensures that the data is correctly timestamped for accurate analytics and reporting.
     */
    public static final String VIEWER_TIMESTAMP = "vitp";

    /**
     * The VIEWER_DEVICE_CATEGORY constant identifies the category of the viewer's device.
     * Categories could include types like smartphone, tablet, or desktop.
     */
    public static final String VIEWER_DEVICE_CATEGORY = "decg";

    /**
     * The VIEWER_DEVICE_MANUFACTURER constant contains the name of the manufacturer of the viewer's device.
     * This helps to identify which brands are being used by the viewers.
     */
    public static final String VIEWER_DEVICE_MANUFACTURER = "demr";

    /**
     * The VIEWER_DEVICE_NAME constant stores the name of the viewer's device.
     * This could be a specific model name used for further analysis or personalization.
     */
    public static final String VIEWER_DEVICE_NAME = "dena";

    /**
     * The VIEWER_OS_FAMILY constant represents the family of the operating system the viewer's device is running.
     * Examples include Android, iOS, or Windows.
     */
    public static final String VIEWER_OS_FAMILY = "viosfy";

    /**
     * The VIEWER_OS_VERSION constant holds the version number of the operating system on the viewer's device.
     * This is important for tracking device performance across different OS versions.
     */
    public static final String VIEWER_OS_VERSION = "viosvn";

    /**
     * The VIEWER_DEVICE_MODEL constant stores the specific model number or name of the viewer's device.
     * This information is useful for identifying specific device capabilities or performance metrics.
     */
    public static final String VIEWER_DEVICE_MODEL = "demo";
   /* public static final List<String> KEYS_VIEWER_DATA;

    static {
        List<String> keys = new ArrayList<>();
        keys.add(VIEWER_APPLICATION_ENGINE);
        keys.add(VIEWER_APPLICATION_NAME);
        keys.add(VIEWER_APPLICATION_VERSION);
        keys.add(VIEWER_DEVICE_CATEGORY);
        keys.add(VIEWER_DEVICE_MANUFACTURER);
        keys.add(VIEWER_DEVICE_MODEL);
        keys.add(VIEWER_OS_ARCHITECTURE);
        keys.add(VIEWER_OS_FAMILY);
        keys.add(VIEWER_OS_VERSION);
        keys.add(VIEWER_CONNECTION_TYPE);
        keys.add(VIEWER_TIMESTAMP);

        KEYS_VIEWER_DATA = Collections.unmodifiableList(keys);
    }*/
   public static final List<String> KEYS_VIEWER_DATA = Collections.unmodifiableList(Arrays.asList(
           VIEWER_APPLICATION_ENGINE, VIEWER_APPLICATION_NAME, VIEWER_APPLICATION_VERSION, VIEWER_DEVICE_CATEGORY,
           VIEWER_DEVICE_MANUFACTURER, VIEWER_DEVICE_MODEL,VIEWER_DEVICE_NAME, VIEWER_OS_ARCHITECTURE, VIEWER_OS_FAMILY,
           VIEWER_OS_VERSION, VIEWER_CONNECTION_TYPE, VIEWER_TIMESTAMP
   ));


    public ViewerDataEntity() {
    }

    public void sync() {
    }

    /**
     * Sets the application engine used by the viewer.
     *
     * @param viewerApplicationEngine The name of the viewer application engine.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerApplicationEngine(String viewerApplicationEngine) throws JSONException {
        if (viewerApplicationEngine != null) {
            this.put(VIEWER_APPLICATION_ENGINE, viewerApplicationEngine);
        }
    }

    /**
     * Retrieves the application engine used by the viewer.
     *
     * @return The viewer's application engine.
     */
    public String getViewerApplicationEngine() {
        return this.get(VIEWER_APPLICATION_ENGINE);
    }

    /**
     * Sets the name of the viewer application.
     *
     * @param viewerApplicationName The name of the viewer application.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerApplicationName(String viewerApplicationName) throws JSONException {
        if (viewerApplicationName != null) {
            this.put(VIEWER_APPLICATION_NAME, viewerApplicationName);
        }
    }

    /**
     * Retrieves the name of the viewer application.
     *
     * @return The viewer's application name.
     */
    public String getViewerApplicationName() {
        return this.get(VIEWER_APPLICATION_NAME);
    }

    /**
     * Sets the version of the viewer application.
     *
     * @param viewerApplicationVersion The version of the viewer application.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerApplicationVersion(String viewerApplicationVersion) throws JSONException {
        if (viewerApplicationVersion != null) {
            this.put(VIEWER_APPLICATION_VERSION, viewerApplicationVersion);
        }
    }

    /**
     * Retrieves the version of the viewer application.
     *
     * @return The viewer's application version.
     */
    public String getViewerApplicationVersion() {
        return this.get(VIEWER_APPLICATION_VERSION);
    }

    /**
     * Sets the device category of the viewer's device (e.g., smartphone, tablet, desktop).
     *
     * @param viewerDeviceCategory The category of the viewer's device.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerDeviceCategory(String viewerDeviceCategory) throws JSONException {
        if (viewerDeviceCategory != null) {
            this.put(VIEWER_DEVICE_CATEGORY, viewerDeviceCategory);
        }
    }

    /**
     * Retrieves the device category of the viewer's device.
     *
     * @return The viewer's device category.
     */
    public String getViewerDeviceCategory() {
        return this.get(VIEWER_DEVICE_CATEGORY);
    }

    /**
     * Sets the manufacturer of the viewer's device.
     *
     * @param viewerDeviceManufacturer The manufacturer of the viewer's device.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerDeviceManufacturer(String viewerDeviceManufacturer) throws JSONException {
        if (viewerDeviceManufacturer != null) {
            this.put(VIEWER_DEVICE_MANUFACTURER, viewerDeviceManufacturer);
        }
    }

    /**
     * Retrieves the manufacturer of the viewer's device.
     *
     * @return The viewer's device manufacturer.
     */
    public String getViewerDeviceManufacturer() {
        return this.get(VIEWER_DEVICE_MANUFACTURER);
    }

    /**
     * Sets the model name of the viewer's device.
     *
     * @param viewerDeviceModel The model name of the viewer's device.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerDeviceModel(String viewerDeviceModel) throws JSONException {
        if (viewerDeviceModel != null) {
            this.put(VIEWER_DEVICE_MODEL, viewerDeviceModel);
        }
    }

    /**
     * Retrieves the device name of the viewer's device.
     *
     * @return The viewer's device name.
     */
    public String getViewerDeviceName() {
        return this.get(VIEWER_DEVICE_NAME);
    }

    /**
     * Sets the device name of the viewer's device.
     *
     * @param viewerDeviceName The name of the viewer's device.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerDeviceName(String viewerDeviceName) throws JSONException {
        if (viewerDeviceName != null) {
            this.put(VIEWER_DEVICE_NAME, viewerDeviceName);
        }
    }

    /**
     * Retrieves the model of the viewer's device.
     *
     * @return The viewer's device model.
     */
    public String getViewerDeviceModel() {
        return this.get(VIEWER_DEVICE_MODEL);
    }

    /**
     * Sets the architecture of the viewer's device operating system.
     *
     * @param viewerOsArchitecture The architecture of the operating system.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerOsArchitecture(String viewerOsArchitecture) throws JSONException {
        if (viewerOsArchitecture != null) {
            this.put(VIEWER_OS_ARCHITECTURE, viewerOsArchitecture);
        }
    }

    /**
     * Retrieves the architecture of the viewer's device operating system.
     *
     * @return The viewer's device operating system architecture.
     */
    public String getViewerOsArchitecture() {
        return this.get(VIEWER_OS_ARCHITECTURE);
    }

    /**
     * Sets the family of the operating system running on the viewer's device.
     *
     * @param viewerOsFamily The family of the viewer's operating system (e.g., Windows, iOS, Android).
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerOsFamily(String viewerOsFamily) throws JSONException {
        if (viewerOsFamily != null) {
            this.put(VIEWER_OS_FAMILY, viewerOsFamily);
        }
    }

    /**
     * Retrieves the family of the operating system running on the viewer's device.
     *
     * @return The viewer's device operating system family.
     */
    public String getViewerOsFamily() {
        return this.get(VIEWER_OS_FAMILY);
    }

    /**
     * Sets the version of the operating system running on the viewer's device.
     *
     * @param viewerOsVersion The version of the viewer's operating system.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerOsVersion(String viewerOsVersion) throws JSONException {
        if (viewerOsVersion != null) {
            this.put(VIEWER_OS_VERSION, viewerOsVersion);
        }
    }

    /**
     * Retrieves the version of the operating system running on the viewer's device.
     *
     * @return The viewer's device operating system version.
     */
    public String getViewerOsVersion() {
        return this.get(VIEWER_OS_VERSION);
    }

    /**
     * Sets the connection type used by the viewer's device (e.g., Wi-Fi, Mobile Data).
     *
     * @param viewerConnectionType The type of network connection the viewer is using.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerConnectionType(String viewerConnectionType) throws JSONException {
        if (viewerConnectionType != null) {
            this.put(VIEWER_CONNECTION_TYPE, viewerConnectionType);
        }
    }

    /**
     * Sets the timestamp representing the viewer's time.
     *
     * @param viewerTime The timestamp representing the viewer's time.
     * @throws JSONException If there is an error while setting the value.
     */
    public void setViewerTime(Long viewerTime) throws JSONException {
        if (viewerTime != null) {
            this.put(VIEWER_TIMESTAMP, viewerTime);
        }
    }

    /**
     * Retrieves the timestamp representing the viewer's time.
     *
     * @return The viewer's timestamp.
     */
    public Long getViewerTime() {
        String viewerTime = this.get(VIEWER_TIMESTAMP);
        return viewerTime == null ? null : Long.parseLong(viewerTime);
    }

    /**
     * Retrieves the connection type used by the viewer's device.
     *
     * @return The viewer's device connection type.
     */
    public String getViewerConnectionType() {
        return this.get(VIEWER_CONNECTION_TYPE);
    }


}
