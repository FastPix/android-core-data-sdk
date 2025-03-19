package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The `CustomerViewerData` class holds viewer-specific data related to the device and operating system
 * for video streaming or viewing contexts. This includes information like device category, manufacturer,
 * model, OS family, and version.
 *
 * It provides the following constants representing keys for each type of viewer data:
 * - `FASTPIX_VIEWER_DEVICE_CATEGORY`: Device category (e.g., mobile, tablet, etc.)
 * - `FASTPIX_VIEWER_DEVICE_MANUFACTURER`: Device manufacturer (e.g., Apple, Samsung, etc.)
 * - `FASTPIX_VIEWER_DEVICE_NAME`: Device name (e.g., iPhone 12, Galaxy S21, etc.)
 * - `FASTPIX_VIEWER_OS_FAMILY`: Operating system family (e.g., iOS, Android, etc.)
 * - `FASTPIX_VIEWER_OS_VERSION`: Operating system version (e.g., 14.4, 11, etc.)
 * - `FASTPIX_VIEWER_DEVICE_MODEL`: Device model (e.g., iPhone12,1, SM-G998B, etc.)
 *
 * The class inherits from `BaseQueryData`, which likely provides common functionality for querying
 * and managing data across various entities.
 */
public class CustomerViewerDataEntity extends QueryDataEntity {
    public static final String FASTPIX_VIEWER_DEVICE_CATEGORY = "fpvidecg";
    public static final String FASTPIX_VIEWER_DEVICE_MANUFACTURER = "fpvidemr";
    public static final String FASTPIX_VIEWER_DEVICE_NAME = "fpvidena";
    public static final String FASTPIX_VIEWER_OS_FAMILY = "fpviosfy";
    public static final String FASTPIX_VIEWER_OS_VERSION = "fpviosvn";
    public static final String FASTPIX_VIEWER_DEVICE_MODEL = "fpvidemo";
//    public static final List<String> keysCVVD;

    public static final List<String> keysCVVD = Collections.unmodifiableList(Arrays.asList(
            FASTPIX_VIEWER_DEVICE_CATEGORY, FASTPIX_VIEWER_DEVICE_MANUFACTURER, FASTPIX_VIEWER_DEVICE_NAME,
            FASTPIX_VIEWER_OS_FAMILY, FASTPIX_VIEWER_OS_VERSION, FASTPIX_VIEWER_DEVICE_MODEL
    ));
    // Default constructor
    public CustomerViewerDataEntity() {
    }

    // Synchronization method (can be used for data syncing logic)
    public void sync() {
    }

    /**
     * Sets the device category for the viewer's device.
     * This category represents the type or classification of the device (e.g., mobile, tablet).
     *
     * @param fpviewerdevicecategory The device category to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerDeviceCategory(String fpviewerdevicecategory) throws JSONException {
        if (fpviewerdevicecategory != null) {
            this.put(FASTPIX_VIEWER_DEVICE_CATEGORY, fpviewerdevicecategory);
        }
    }

    /**
     * Retrieves the device category for the viewer's device.
     * This category represents the type or classification of the device (e.g., mobile, tablet).
     *
     * @return The device category of the viewer's device.
     */
    public String getFPViewerDeviceCategory() {
        return this.get(FASTPIX_VIEWER_DEVICE_CATEGORY);
    }

    /**
     * Sets the manufacturer of the viewer's device (e.g., Apple, Samsung).
     *
     * @param fpViewerDeviceManufacturer The manufacturer to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerDeviceManufacturer(String fpViewerDeviceManufacturer) throws JSONException {
        if (fpViewerDeviceManufacturer != null) {
            this.put(FASTPIX_VIEWER_DEVICE_MANUFACTURER, fpViewerDeviceManufacturer);
        }
    }

    /**
     * Retrieves the manufacturer of the viewer's device (e.g., Apple, Samsung).
     *
     * @return The manufacturer of the viewer's device.
     */
    public String getFPViewerDeviceManufacturer() {
        return this.get(FASTPIX_VIEWER_DEVICE_MANUFACTURER);
    }

    /**
     * Sets the name of the viewer's device (e.g., iPhone, Galaxy S21).
     *
     * @param fpViewerDeviceName The device name to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerDeviceName(String fpViewerDeviceName) throws JSONException {
        if (fpViewerDeviceName != null) {
            this.put(FASTPIX_VIEWER_DEVICE_NAME, fpViewerDeviceName);
        }
    }

    /**
     * Retrieves the name of the viewer's device (e.g., iPhone, Galaxy S21).
     *
     * @return The name of the viewer's device.
     */
    public String getFPViewerDeviceName() {
        return this.get(FASTPIX_VIEWER_DEVICE_NAME);
    }

    /**
     * Sets the operating system family of the viewer's device (e.g., iOS, Android).
     *
     * @param fpviewerosfamily The operating system family to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerOsFamily(String fpviewerosfamily) throws JSONException {
        if (fpviewerosfamily != null) {
            this.put(FASTPIX_VIEWER_OS_FAMILY, fpviewerosfamily);
        }
    }

    /**
     * Retrieves the operating system family of the viewer's device (e.g., iOS, Android).
     *
     * @return The operating system family of the viewer's device.
     */
    public String getFPViewerOsFamily() {
        return this.get(FASTPIX_VIEWER_OS_FAMILY);
    }

    /**
     * Sets the operating system version of the viewer's device (e.g., iOS 14.4, Android 11).
     *
     * @param fpviewerosversion The operating system version to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerOsVersion(String fpviewerosversion) throws JSONException {
        if (fpviewerosversion != null) {
            this.put(FASTPIX_VIEWER_OS_VERSION, fpviewerosversion);
        }
    }

    /**
     * Retrieves the operating system version of the viewer's device (e.g., iOS 14.4, Android 11).
     *
     * @return The operating system version of the viewer's device.
     */
    public String getFPViewerOsVersion() {
        return this.get(FASTPIX_VIEWER_OS_VERSION);
    }

    /**
     * Sets the model of the viewer's device (e.g., iPhone 12, Galaxy S21).
     *
     * @param fpViewerDeviceModel The model name to set for the viewer's device.
     * @throws JSONException If the provided value cannot be added to the JSON object.
     */
    public void setFPViewerDeviceModel(String fpViewerDeviceModel) throws JSONException {
        if (fpViewerDeviceModel != null) {
            this.put(FASTPIX_VIEWER_DEVICE_MODEL, fpViewerDeviceModel);
        }
    }

    /**
     * Retrieves the model of the viewer's device (e.g., iPhone 12, Galaxy S21).
     *
     * @return The model name of the viewer's device.
     */
    public String getFPViewerDeviceModel() {
        return this.get(FASTPIX_VIEWER_DEVICE_MODEL);
    }



    // Static initialization block to populate the keys list
  /*  static {
        List<String> keys = new ArrayList<>();
        keys.add(FASTPIX_VIEWER_DEVICE_CATEGORY);
        keys.add(FASTPIX_VIEWER_DEVICE_MANUFACTURER);
        keys.add(FASTPIX_VIEWER_DEVICE_NAME);
        keys.add(FASTPIX_VIEWER_OS_FAMILY);
        keys.add(FASTPIX_VIEWER_OS_VERSION);
        keys.add(FASTPIX_VIEWER_DEVICE_MODEL);
        keysCVVD = Collections.unmodifiableList(keys);
    }*/
}

