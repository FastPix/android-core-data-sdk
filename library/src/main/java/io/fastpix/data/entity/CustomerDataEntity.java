package io.fastpix.data.entity;

import org.json.JSONException;

/**
 * Represents customer-related data, including player, video, view, viewer, and custom data.
 *
 * This class extends `BaseQueryData` to manage and synchronize customer-related data. It contains
 * the following fields:
 * - `customerPlayerData`: Stores data related to the customer’s player.
 * - `customerVideoData`: Stores data related to the customer’s video.
 * - `customerViewData`: Stores data related to the customer’s view interactions.
 * - `customerViewerData`: Stores data related to the customer’s viewer information.
 * - `customData`: Stores any additional custom data associated with the customer.
 *
 * The `sync()` method is overridden, but it currently does not perform any actions. This can be
 * expanded to implement synchronization logic for updating the various data fields.
 */
public class CustomerDataEntity extends QueryDataEntity {
    private CustomerPlayerDataEntity customerPlayerData;
    private CustomerVideoDataEntity customerVideoData;
    private CustomerViewDataEntity customerViewData;
    private CustomerViewerDataEntity customerViewerData;
    private CustomDataEntity customData;

    public void sync() {
    }

    /**
     * Constructs a new `CustomerData` object with the provided customer-related data.
     *
     * This constructor initializes the `CustomerData` object by accepting instances of
     * `CustomerPlayerData`, `CustomerVideoData`, and `CustomerViewData`. These objects are
     * then used to update the `CustomerData` object by calling the `update()` method on each
     * of them. This allows for the `CustomerData` object to incorporate data from these
     * specific models.
     *
     * @param customerPlayerData The data related to the customer’s player.
     * @param customerVideoData The data related to the customer’s video.
     * @param customerViewData The data related to the customer’s view interactions.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public CustomerDataEntity(CustomerPlayerDataEntity customerPlayerData, CustomerVideoDataEntity customerVideoData, CustomerViewDataEntity customerViewData) throws JSONException {
        this.customerPlayerData = customerPlayerData;
        this.customerVideoData = customerVideoData;
        this.customerViewData = customerViewData;
        this.update(customerPlayerData);
        this.update(customerVideoData);
        this.update(customerViewData);
    }

    /**
     * Constructs a new `CustomerData` object with the provided customer-related data and custom data.
     *
     * This constructor initializes the `CustomerData` object by accepting instances of
     * `CustomerPlayerData`, `CustomerVideoData`, `CustomerViewData`, and `CustomData`. It first
     * calls the other constructor to initialize the `CustomerData` object with the player, video,
     * and view data. Then, it assigns the provided `CustomData` to the `customData` field and
     * updates the `CustomerData` object using the `update()` method with the `CustomData`.
     *
     * @param customerPlayerData The data related to the customer’s player.
     * @param customerVideoData The data related to the customer’s video.
     * @param customerViewData The data related to the customer’s view interactions.
     * @param customData The custom data specific to the customer.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public CustomerDataEntity(CustomerPlayerDataEntity customerPlayerData, CustomerVideoDataEntity customerVideoData, CustomerViewDataEntity customerViewData, CustomDataEntity customData) throws JSONException {
        this(customerPlayerData, customerVideoData, customerViewData);
        this.customData = customData;
        this.update(customData);
    }

    /**
     * Constructs a new `CustomerData` object with the provided customer-related data, custom data, and viewer data.
     *
     * This constructor initializes the `CustomerData` object by accepting instances of
     * `CustomerPlayerData`, `CustomerVideoData`, `CustomerViewData`, `CustomerViewerData`, and `CustomData`.
     * It first calls the other constructor to initialize the `CustomerData` object with player, video, view, and
     * custom data. Then, it assigns the provided `CustomerViewerData` to the `customerViewerData` field and
     * updates the `CustomerData` object using the `update()` method with the `CustomerViewerData`.
     *
     * @param customerPlayerData The data related to the customer’s player.
     * @param customerVideoData The data related to the customer’s video.
     * @param customerViewData The data related to the customer’s view interactions.
     * @param customerViewerData The data related to the customer’s viewer information.
     * @param customData The custom data specific to the customer.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public CustomerDataEntity(CustomerPlayerDataEntity customerPlayerData, CustomerVideoDataEntity customerVideoData, CustomerViewDataEntity customerViewData, CustomerViewerDataEntity customerViewerData, CustomDataEntity customData) throws JSONException {
        this(customerPlayerData, customerVideoData, customerViewData, customData);
        this.customerViewerData = customerViewerData;
        this.update(customerViewerData);
    }

    /**
     * Default constructor for creating a new instance of `CustomerData`.
     * This constructor initializes an empty `CustomerData` object without any data.
     */
    public CustomerDataEntity() {
    }

    /**
     * Gets the `CustomerPlayerData` associated with this `CustomerData` object.
     *
     * @return The `CustomerPlayerData` object associated with the customer.
     */
    public CustomerPlayerDataEntity getCustomerPlayerData() {
        return this.customerPlayerData;
    }

    /**
     * Sets the `CustomerPlayerData` for this `CustomerData` object and updates the data.
     *
     * This method assigns the provided `CustomerPlayerData` to the `customerPlayerData` field
     * and calls the `update()` method to incorporate this new data into the object.
     *
     * @param customerPlayerData The `CustomerPlayerData` to be set.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public void setCustomerPlayerData(CustomerPlayerDataEntity customerPlayerData) throws JSONException {
        this.customerPlayerData = customerPlayerData;
        this.update(customerPlayerData);
    }

    /**
     * Gets the `CustomerVideoData` associated with this `CustomerData` object.
     *
     * @return The `CustomerVideoData` object associated with the customer.
     */
    public CustomerVideoDataEntity getCustomerVideoData() {
        return this.customerVideoData;
    }

    /**
     * Sets the `CustomerVideoData` for this `CustomerData` object and updates the data.
     *
     * This method assigns the provided `CustomerVideoData` to the `customerVideoData` field
     * and calls the `update()` method to incorporate this new data into the object.
     *
     * @param customerVideoData The `CustomerVideoData` to be set.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public void setCustomerVideoData(CustomerVideoDataEntity customerVideoData) throws JSONException {
        this.customerVideoData = customerVideoData;
        this.update(customerVideoData);
    }

    /**
     * Gets the `CustomerViewData` associated with this `CustomerData` object.
     *
     * @return The `CustomerViewData` object associated with the customer.
     */
    public CustomerViewDataEntity getCustomerViewData() {
        return this.customerViewData;
    }

    /**
     * Sets the `CustomerViewData` for this `CustomerData` object and updates the data.
     *
     * This method assigns the provided `CustomerViewData` to the `customerViewData` field
     * and calls the `update()` method to incorporate this new data into the object.
     *
     * @param customerViewData The `CustomerViewData` to be set.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public void setCustomerViewData(CustomerViewDataEntity customerViewData) throws JSONException {
        this.customerViewData = customerViewData;
        this.update(customerViewData);
    }


    /**
     * Gets the `CustomData` associated with this `CustomerData` object.
     *
     * @return The `CustomData` object associated with the customer.
     */
    public CustomDataEntity getCustomData() {
        return this.customData;
    }

    /**
     * Sets the `CustomData` for this `CustomerData` object and updates the data.
     *
     * This method assigns the provided `CustomData` to the `customData` field
     * and calls the `update()` method to incorporate this new data into the object.
     *
     * @param customData The `CustomData` to be set.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public void setCustomData(CustomDataEntity customData) throws JSONException {
        this.customData = customData;
        this.update(customData);
    }

    /**
     * Gets the `CustomerViewerData` associated with this `CustomerData` object.
     *
     * @return The `CustomerViewerData` object associated with the customer.
     */
    public CustomerViewerDataEntity getCustomerViewerData() {
        return this.customerViewerData;
    }

    /**
     * Sets the `CustomerViewerData` for this `CustomerData` object and updates the data.
     *
     * This method assigns the provided `CustomerViewerData` to the `customerViewerData` field
     * and calls the `update()` method to incorporate this new data into the object.
     *
     * @param customerViewerData The `CustomerViewerData` to be set.
     * @throws JSONException If there is an error during the update process (e.g., invalid JSON format).
     */
    public void setCustomerViewerData(CustomerViewerDataEntity customerViewerData) throws JSONException {
        this.customerViewerData = customerViewerData;
        this.update(customerViewerData);
    }
}
