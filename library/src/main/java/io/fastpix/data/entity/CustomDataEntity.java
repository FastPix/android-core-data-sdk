package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * CustomData class that extends the BaseQueryData class to provide specific implementations
 * for handling custom data structures and operations on JSON objects.
 *
 * This class inherits methods from BaseQueryData to manage a JSON object and can override
 * abstract methods like `sync()` to implement specific synchronization logic for custom data.
 */
public class CustomDataEntity extends QueryDataEntity {
    // Custom fields or methods can be added here to provide functionality specific to CustomData
    public static final String CUSTOM_DATA_1 = "cm1";
    public static final String CUSTOM_DATA_2 = "cm2";
    public static final String CUSTOM_DATA_3 = "cm3";
    public static final String CUSTOM_DATA_4 = "cm4";
    public static final String CUSTOM_DATA_5 = "cm5";
    public static final String CUSTOM_DATA_6 = "cm6";
    public static final String CUSTOM_DATA_7 = "cm7";
    public static final String CUSTOM_DATA_8 = "cm8";
    public static final String CUSTOM_DATA_9 = "cm9";
    public static final String CUSTOM_DATA_10 = "cm10";
//    public static final List<String> keysCD;

    public static final List<String> keysCD = Collections.unmodifiableList(Arrays.asList(
            CUSTOM_DATA_1, CUSTOM_DATA_2, CUSTOM_DATA_3, CUSTOM_DATA_4, CUSTOM_DATA_5,
            CUSTOM_DATA_6, CUSTOM_DATA_7, CUSTOM_DATA_8, CUSTOM_DATA_9, CUSTOM_DATA_10
    ));
    // Default constructor
    public CustomDataEntity() {
    }

    /**
     * This method is a placeholder for synchronization functionality.
     * Currently, it does nothing but can be extended for future synchronization logic.
     */
    public void sync() {
    }

    /**
     * Retrieves the value of the custom data field "CUSTOM_DATA_1"....CUSTOM_DATA_10
     *
     * @return the value of "CUSTOM_DATA_1".....CUSTOM_DATA_10
     */
    public String getCustomData1() {
        return this.get(CUSTOM_DATA_1);
    }

    /**
     * Sets the value of the custom data field "CUSTOM_DATA_1"....CUSTOM_DATA_10
     *
     * @param customData1 the value to be set for "CUSTOM_DATA_1"....CUSTOM_DATA_10
     * @throws JSONException if an error occurs while setting the value in the JSON object.
     */
    public void setCustomData1(String customData1) throws JSONException {
        if (customData1 != null) {
            this.put(CUSTOM_DATA_1, customData1);
        }

    }

    public String getCustomData2() {
        return this.get(CUSTOM_DATA_2);
    }

    public void setCustomData2(String customData2) throws JSONException {
        if (customData2 != null) {
            this.put(CUSTOM_DATA_2, customData2);
        }
    }

    public String getCustomData3() {
        return this.get(CUSTOM_DATA_3);
    }

    public void setCustomData3(String customData3) throws JSONException {
        if (customData3 != null) {
            this.put(CUSTOM_DATA_3, customData3);
        }
    }

    public String getCustomData4() {
        return this.get(CUSTOM_DATA_4);
    }

    public void setCustomData4(String customData4) throws JSONException {
        if (customData4 != null) {
            this.put(CUSTOM_DATA_4, customData4);
        }
    }

    public String getCustomData5() {
        return this.get(CUSTOM_DATA_5);
    }

    public void setCustomData5(String customData5) throws JSONException {
        if (customData5 != null) {
            this.put(CUSTOM_DATA_5, customData5);
        }
    }

    public String getCustomData6() {
        return this.get(CUSTOM_DATA_6);
    }

    public void setCustomData6(String customData6) throws JSONException {
        if (customData6 != null) {
            this.put(CUSTOM_DATA_6, customData6);
        }
    }

    public String getCustomData7() {
        return this.get(CUSTOM_DATA_7);
    }

    public void setCustomData7(String customData7) throws JSONException {
        if (customData7 != null) {
            this.put(CUSTOM_DATA_7, customData7);
        }
    }

    public String getCustomData8() {
        return this.get(CUSTOM_DATA_8);
    }

    public void setCustomData8(String customData8) throws JSONException {
        if (customData8 != null) {
            this.put(CUSTOM_DATA_8, customData8);
        }
    }

    public String getCustomData9() {
        return this.get(CUSTOM_DATA_9);
    }

    public void setCustomData9(String customData9) throws JSONException {
        if (customData9 != null) {
            this.put(CUSTOM_DATA_9, customData9);
        }
    }

    public String getCustomData10() {
        return this.get(CUSTOM_DATA_10);
    }

    public void setCustomData10(String customData10) throws JSONException {
        if (customData10 != null) {
            this.put(CUSTOM_DATA_10, customData10);
        }
    }



    /**
     * Static block to initialize the unmodifiable list of custom data keys.
     *
     * This static block is executed when the class is first loaded into memory. It initializes
     * the list `keysCD` by adding all the custom data keys (CUSTOM_DATA_1 to CUSTOM_DATA_10) to
     * the list, and then makes the list unmodifiable using `Collections.unmodifiableList()` to
     * prevent any modifications to the list after it is created.
     *
     * This ensures that the list of custom data keys remains immutable throughout the lifetime
     * of the `CustomData` class, promoting safety and preventing accidental modification.
     */
    /*static {
        List<String> keys = new ArrayList<>();
        keys.add(CUSTOM_DATA_1);
        keys.add(CUSTOM_DATA_2);
        keys.add(CUSTOM_DATA_3);
        keys.add(CUSTOM_DATA_4);
        keys.add(CUSTOM_DATA_5);
        keys.add(CUSTOM_DATA_6);
        keys.add(CUSTOM_DATA_7);
        keys.add(CUSTOM_DATA_8);
        keys.add(CUSTOM_DATA_9);
        keys.add(CUSTOM_DATA_10);
        keysCD = Collections.unmodifiableList(keys);
    }*/
}
