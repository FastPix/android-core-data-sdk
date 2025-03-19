package io.fastpix.data.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * BaseQueryData is an abstract class designed to handle JSON-based query data.
 * It provides methods for managing, updating, and synchronizing JSON objects.
 *
 * Key Features:
 * - Stores query data as a JSONObject.
 * - Supports thread-safe updates using synchronization.
 * - Offers utility methods for checking, updating, and comparing JSON keys and values.
 * - Enforces subclasses to implement the `sync()` method for handling data consistency.
 *
 * This class is intended to be extended by specific query data implementations.
 */
public abstract class QueryDataEntity {
    protected JSONObject jsonObject = new JSONObject();
    private static ArrayList<String> arrayList = new ArrayList();
    private static ArrayList<String> arrayList1 = new ArrayList();
    private final Object lock = new Object();

    /**
     * Protected constructor to prevent direct instantiation of the abstract class.
     * Subclasses must implement the required functionality.
     */
    protected QueryDataEntity() {
    }

    /**
     * Abstract method to be implemented by subclasses for synchronizing data
     * after modifications. Ensures data consistency across implementations.
     *
     * @throws JSONException if a JSON processing error occurs.
     */
    public abstract void sync() throws JSONException;

    /**
     * Checks if a given key is expected to be a JSONObject.
     *
     * @param string The key to check.
     * @return true if the key corresponds to a JSONObject, false otherwise.
     */
    public static boolean isKeyJsonObject(String string) {
        return arrayList.contains(string);
    }

    /**
     * Checks if a given key is expected to be a JSONArray.
     *
     * @param string The key to check.
     * @return true if the key corresponds to a JSONArray, false otherwise.
     */
    public static boolean isKeyJsonArray(String string) {
        return arrayList1.contains(string);
    }

    /**
     * Replaces the current JSON object with a new one and synchronizes the data.
     *
     * @param jsonObject The new JSONObject to replace the current data.
     * @throws JSONException if a JSON processing error occurs.
     */
    public void replace(JSONObject jsonObject) throws JSONException {
        if (jsonObject != null) {
            this.jsonObject = new JSONObject(jsonObject.toString());
            this.sync();
        }
    }

    /**
     * Updates the current JSON object with new key-value pairs from the provided JSONObject.
     * If the key is recognized as a JSONObject or JSONArray, it is stored accordingly.
     * Otherwise, the value is treated as a string and stored in the JSON object.
     *
     * @param jsonObject The JSONObject containing the new data to update.
     * @throws JSONException if a JSON processing error occurs.
     */
    public void update(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray;
        // Retrieve the keys from the input JSON object.
        if ((jsonArray = jsonObject.names()) != null) {
            for (int i = 0; i < jsonArray.length(); ++i) {
                String stringJsonArray = (String) jsonArray.get(i);

                // Check if the key corresponds to a JSONObject.
                if (isKeyJsonObject(stringJsonArray)) {
                    this.jsonObject.put(stringJsonArray, jsonObject.getJSONObject(stringJsonArray));
                }
                // Check if the key corresponds to a JSONArray.
                else if (isKeyJsonArray(stringJsonArray)) {
                    this.jsonObject.put(stringJsonArray, jsonObject.getJSONArray(stringJsonArray));
                }
                // Otherwise, treat it as a regular string value.
                else {
                    this.put(stringJsonArray, jsonObject.getString(stringJsonArray));
                }
            }
        }
    }

    /**
     * Updates the current JSON object using data from another BaseQueryData instance.
     * Ensures thread safety using a synchronization lock to prevent concurrent modifications.
     *
     * @param queryDataEntity The BaseQueryData instance whose JSON data will be used for the update.
     * @throws JSONException if a JSON processing error occurs.
     */
    public void update(QueryDataEntity queryDataEntity) throws JSONException {
        if (queryDataEntity != null) {
            // Synchronize on a single lock object to prevent concurrent modifications.
            synchronized (lock) {
                // Update the current object using the JSON data from the provided BaseQueryData instance.
                this.update(queryDataEntity.jsonObject);
            }
        }
    }

    /**
     * Retrieves the keys present in the current JSON object.
     *
     * @return A JSONArray containing the keys of the JSON object.
     *         If there are no keys, returns an empty JSONArray instead of null.
     */
    public JSONArray keys() {
        return this.jsonObject.names() == null ? new JSONArray() : this.jsonObject.names();
    }

    /**
     * Compares the current object's JSON data with another BaseQueryData instance.
     *
     * @param queryDataEntity The object to compare against.
     * @return true if both objects contain the same keys and values, false otherwise.
     * @throws JSONException if there is an issue accessing JSON data.
     */
    public boolean isEqualTo(QueryDataEntity queryDataEntity) throws JSONException {
        if (queryDataEntity == null) {
            return false;
        }

        JSONArray jsonArray = queryDataEntity.keys();
        if (jsonArray.length() != this.jsonObject.names().length()) {
            return false;
        }
        // Compare each key-value pair in both JSON objects
        for (int i = 0; i < jsonArray.length(); ++i) {
            String key1 = (String) jsonArray.get(i);
            String key2 = (String) this.jsonObject.names().get(i);
            String value1 = queryDataEntity.get(key1);
            String value2 = this.get(key2);

            // Ensure keys and values match (case-insensitive)
            if (!key1.equalsIgnoreCase(key2) || !value1.equalsIgnoreCase(value2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (String).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, String value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (Long).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, Long value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (Integer).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, Integer value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (Boolean).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, Boolean value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (Double).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, Double value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (JSONObject).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, JSONObject value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Inserts a key-value pair into the JSON object.
     *
     * @param key   The key under which the value will be stored.
     * @param value The value to store (JSONArray).
     * @throws JSONException If a JSON error occurs.
     */
    public void put(String key, JSONArray value) throws JSONException {
        this.jsonObject.put(key, value);
    }

    /**
     * Removes a key from the JSON object.
     *
     * @param key The key to remove from the JSON object.
     */
    public void remove(String key) {
        this.jsonObject.remove(key);
    }

    /**
     * Retrieves the value associated with the given key from the JSON object.
     *
     * @param key The key whose associated value is to be retrieved.
     * @return The value associated with the key as a String, or null if the key is not found or the value is empty.
     */
    public String get(String key) {
        if (!this.jsonObject.has(key)) {
            return null; // Return null if the key doesn't exist in the JSON object
        }

        // Retrieve the value associated with the key, or null if it's not present
        key = this.jsonObject.optString(key, null);
        return key.isEmpty() ? null : key; // Return null if the value is empty
    }

    /**
     * Creates and returns a new JSONObject with the same content as the current JSON object.
     *
     * @return A deep copy of the current JSON object.
     * @throws JSONException If a JSON error occurs.
     */
    public JSONObject getFPDictionary() throws JSONException {
        return new JSONObject(this.jsonObject.toString()); // Create a new JSONObject with the current data
    }

    /**
     * Clears the content of the current JSON object, making it an empty object.
     */
    public void clear() {
        this.jsonObject = new JSONObject(); // Reset to an empty JSON object
    }



    /**
     * Static block that initializes predefined lists with specific keys.
     * These keys are used to determine if a particular string corresponds to a JSON object or array.
     */
    static {
        // Adding specific keys to the array lists that represent objects and arrays
        arrayList.add("rqrphs");
        arrayList.add("rqrnls");
        arrayList1.add("media");
    }

}
