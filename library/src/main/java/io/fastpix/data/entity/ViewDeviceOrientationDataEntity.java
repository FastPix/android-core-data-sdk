package io.fastpix.data.entity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ViewDeviceOrientationData class inherits from BaseQueryData and stores device orientation data.
public class ViewDeviceOrientationDataEntity extends QueryDataEntity {
    // Constant representing the X-axis orientation of the device.
    public static final String DEVICE_ORIENTATION_X = "deorx";

    // Constant representing the Y-axis orientation of the device.
    public static final String DEVICE_ORIENTATION_Y = "deory";

    // Constant representing the Z-axis orientation of the device.
    public static final String DEVICE_ORIENTATION_Z = "deorz";

    // A static list to hold the keys associated with device orientation data (likely for serialization or processing).
    public static final List<String> keysVDOD;

    public void sync() {
    }

    // Constructor for the ViewDeviceOrientationData class that initializes the orientation values.
// The orientation values (X, Y, Z) are set to 0 by default when an object is instantiated.
    public ViewDeviceOrientationDataEntity() throws JSONException {
        this.setOrientationX(0);  // Sets the X-axis orientation to 0.
        this.setOrientationY(0);  // Sets the Y-axis orientation to 0.
        this.setOrientationZ(0);  // Sets the Z-axis orientation to 0.
    }

    // Constructor that initializes the ViewDeviceOrientationData object from a JSONObject.
// It extracts the orientation values for X, Y, and Z from the provided JSON object.
    public ViewDeviceOrientationDataEntity(JSONObject jsonObject) throws JSONException {
        this.setOrientationX(jsonObject.getInt(DEVICE_ORIENTATION_X));  // Sets the X orientation from the JSON object.
        this.setOrientationY(jsonObject.getInt(DEVICE_ORIENTATION_Y));  // Sets the Y orientation from the JSON object.
        this.setOrientationZ(jsonObject.getInt(DEVICE_ORIENTATION_Z));  // Sets the Z orientation from the JSON object.
    }

    // Method to set the X-axis orientation value and store it in the object.
    public void setOrientationX(Integer orientationX) throws JSONException {
        if (orientationX != null) {
            this.put(DEVICE_ORIENTATION_X, orientationX);  // Stores the X orientation value.
        }
    }

    // Method to get the X-axis orientation value.
    public Integer getOrientationX() {
        String orientationX = this.get(DEVICE_ORIENTATION_X);  // Retrieves the X orientation value.
        return orientationX == null ? null : Integer.parseInt(orientationX);  // Returns the X orientation or null if not available.
    }

    // Method to set the Y-axis orientation value and store it in the object.
    public void setOrientationY(Integer orientationY) throws JSONException {
        if (orientationY != null) {
            this.put(DEVICE_ORIENTATION_Y, orientationY);  // Stores the Y orientation value.
        }
    }

    // Method to get the Y-axis orientation value.
    public Integer getOrientationY() {
        String orientationY = this.get(DEVICE_ORIENTATION_Y);  // Retrieves the Y orientation value.
        return orientationY == null ? null : Integer.parseInt(orientationY);  // Returns the Y orientation or null if not available.
    }

    // Method to set the Z-axis orientation value and store it in the object.
    public void setOrientationZ(Integer orientationZ) throws JSONException {
        if (orientationZ != null) {
            this.put(DEVICE_ORIENTATION_Z, orientationZ);  // Stores the Z orientation value.
        }
    }

    // Method to get the Z-axis orientation value.
    public Integer getOrientationZ() {
        String orientationZ = this.get(DEVICE_ORIENTATION_Z);  // Retrieves the Z orientation value.
        return orientationZ == null ? null : Integer.parseInt(orientationZ);  // Returns the Z orientation or null if not available.
    }

    // Converts the ViewDeviceOrientationData object to a JSON string representation.
    public String toJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();  // Creates a new JSONObject.
        jsonObject.put(DEVICE_ORIENTATION_X, this.getOrientationX());  // Puts the X orientation into the JSON object.
        jsonObject.put(DEVICE_ORIENTATION_Y, this.getOrientationY());  // Puts the Y orientation into the JSON object.
        jsonObject.put(DEVICE_ORIENTATION_Z, this.getOrientationZ());  // Puts the Z orientation into the JSON object.
        return jsonObject.toString();  // Converts the JSONObject to a string and returns it.
    }

    // Static block to initialize the keys list and make it immutable.
    static {
        List<String> keys = new ArrayList<>();  // Creates a new list to hold orientation keys.
        keys.add(DEVICE_ORIENTATION_X);  // Adds the X orientation key to the list.
        keys.add(DEVICE_ORIENTATION_Y);  // Adds the Y orientation key to the list.
        keys.add(DEVICE_ORIENTATION_Z);  // Adds the Z orientation key to the list.

        keysVDOD = Collections.unmodifiableList(keys);  // Makes the list immutable and assigns it to keysVDOD.
    }
}
