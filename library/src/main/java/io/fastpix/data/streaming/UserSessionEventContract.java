package io.fastpix.data.streaming;

import io.fastpix.data.entity.CustomDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import io.fastpix.data.entity.CustomerViewerDataEntity;
import io.fastpix.data.entity.UserSessionTag;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.request.AnalyticsEventLogger;
import io.fastpix.data.request.Helper;
import io.fastpix.data.request.SessionDataKeys;

import java.util.Iterator;
import java.util.List;

/**
 * A class representing a session data event that holds various session-related data,
 * including view, video, customer video, player, view, viewer, and custom data.
 * It is used for tracking and debugging session data in the system.
 */
public class UserSessionEventContract extends AbstractEventContract {
    public static final String TYPE = "sessionDataEvent";  // Event type constant
    private ViewDataEntity viewData;
    private VideoDataEntity videoData;
    private CustomerVideoDataEntity customerVideoData;
    private CustomerPlayerDataEntity customerPlayerData;
    private CustomerViewDataEntity customerViewData;
    private CustomerViewerDataEntity customerViewerData;
    private CustomDataEntity customData;

    /**
     * Default constructor for creating an empty SessionDataEvent.
     */
    public UserSessionEventContract() {
    }

    /**
     * Constructor that initializes a SessionDataEvent with session tags.
     *
     * @param userSessionTagList A list of session tags that represent session data.
     * @throws Exception If there is an error processing session tags.
     */
    public UserSessionEventContract(List<UserSessionTag> userSessionTagList) throws Exception {
        this();
        this.setSessionData(userSessionTagList);
    }

    /**
     * Copy constructor to create a new SessionDataEvent from an existing one.
     *
     * @param userSessionEvent The SessionDataEvent to copy.
     */
    public UserSessionEventContract(UserSessionEventContract userSessionEvent) {
        this();
        this.viewData = userSessionEvent.viewData;
        this.videoData = userSessionEvent.videoData;
        this.customerVideoData = userSessionEvent.customerVideoData;
        this.customerPlayerData = userSessionEvent.customerPlayerData;
        this.customerViewData = userSessionEvent.customerViewData;
        this.customData = userSessionEvent.customData;
    }

    /**
     * Sets session data from a list of session tags.
     *
     * @param userSessionTags The list of session tags to process.
     * @throws Exception If there is an error processing session tags.
     */
    public void setSessionData(List<UserSessionTag> userSessionTags) throws Exception {
        // Resetting session data before setting new values
        this.viewData = null;
        this.videoData = null;
        this.customerVideoData = null;
        this.customerPlayerData = null;
        this.customerViewData = null;
        this.customData = null;
        if (userSessionTags != null) {
            this.updateSessionData(userSessionTags);
        }
    }

    /**
     * Updates session data using a list of session tags.
     *
     * @param userSessionTags The list of session tags to process.
     * @throws Exception If there is an error processing session tags.
     */
    public void updateSessionData(List<UserSessionTag> userSessionTags) throws Exception {
        if (userSessionTags != null) {
            Iterator<UserSessionTag> iterator = userSessionTags.iterator();

            while (iterator.hasNext()) {
                UserSessionTag userSessionTag = iterator.next();
                Class<?> type = SessionDataKeys.type(userSessionTag.key);

                // Logging session data key-value pairs for debugging
                AnalyticsEventLogger.w(TYPE, "Data key is " + userSessionTag.key + " Data is " + userSessionTag);

                // Handle unknown data type
                if (type == null) {
                    AnalyticsEventLogger.w(TYPE, "Unknown experiment value ignored: " + userSessionTag.key);
                } else {
                    AnalyticsEventLogger.d(TYPE, "Obtained class " + type.getSimpleName());

                    // Assign session tag values to corresponding session data classes
                    if (type.equals(ViewDataEntity.class)) {
                        this.viewData = Helper.lazyGet(this.viewData, ViewDataEntity::new);
                        this.viewData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(VideoDataEntity.class)) {
                        this.videoData = Helper.lazyGet(this.videoData, VideoDataEntity::new);
                        this.videoData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(CustomerVideoDataEntity.class)) {
                        this.customerVideoData = Helper.lazyGet(this.customerVideoData, CustomerVideoDataEntity::new);
                        this.customerVideoData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(CustomerPlayerDataEntity.class)) {
                        this.customerPlayerData = Helper.lazyGet(this.customerPlayerData, CustomerPlayerDataEntity::new);
                        this.customerPlayerData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(CustomerViewDataEntity.class)) {
                        this.customerViewData = Helper.lazyGet(this.customerViewData, CustomerViewDataEntity::new);
                        this.customerViewData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(CustomerViewerDataEntity.class)) {
                        this.customerViewerData = Helper.lazyGet(this.customerViewerData, CustomerViewerDataEntity::new);
                        this.customerViewerData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    }
                    else if (type.equals(CustomDataEntity.class)) {
                        this.customData = Helper.lazyGet(this.customData, CustomDataEntity::new);
                        this.customData.put(SessionDataKeys.shortCode(userSessionTag.key), userSessionTag.value);
                    } else {
                        AnalyticsEventLogger.d(TYPE, "Unknown session data with key [" + userSessionTag.key + "] was ignored");
                    }
                }
            }
        }
    }

    /**
     * Returns the event type.
     *
     * @return The event type as a string.
     */
    public String getType() {
        return TYPE;
    }

    /**
     * Returns whether this event is session data.
     *
     * @return True if the event is session data, false otherwise.
     */
    public boolean isSessionData() {
        return true;
    }

    // Getter and setter methods for session data properties

    public ViewDataEntity getViewData() {
        return this.viewData;
    }

    public void setViewData(ViewDataEntity viewData) {
        this.viewData = viewData;
    }

    public VideoDataEntity getVideoData() {
        return this.videoData;
    }

    public void setVideoData(VideoDataEntity videoData) {
        this.videoData = videoData;
    }

    public CustomerVideoDataEntity getCustomerVideoData() {
        return this.customerVideoData;
    }

    public void setCustomerVideoData(CustomerVideoDataEntity customerVideoData) {
        this.customerVideoData = customerVideoData;
    }

    public CustomerPlayerDataEntity getCustomerPlayerData() {
        return this.customerPlayerData;
    }

    public void setCustomerPlayerData(CustomerPlayerDataEntity customerPlayerData) {
        this.customerPlayerData = customerPlayerData;
    }

    public CustomerViewDataEntity getCustomerViewData() {
        return this.customerViewData;
    }

    public void setCustomerViewData(CustomerViewDataEntity customerViewData) {
        this.customerViewData = customerViewData;
    }

    public CustomerViewerDataEntity getCustomerViewerData() {
        return this.customerViewerData;
    }

    public void setCustomerViewerData(CustomerViewerDataEntity customerViewerData) {
        this.customerViewerData = customerViewerData;
    }

    public CustomDataEntity getCustomData() {
        return this.customData;
    }

    public void setCustomData(CustomDataEntity customData) {
        this.customData = customData;
    }



}
