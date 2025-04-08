package io.fastpix.data.entity;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the customer view data associated with a specific video view session.
 * Extends from {@link QueryDataEntity} and contains fields related to view session, DRM type, and experiments.
 * This class holds data such as session IDs and video experiments for tracking and analytics.
 */
public class CustomerViewDataEntity extends QueryDataEntity {

    // DRM type of the view
    public static final String VIEW_DRM_TYPE = "vddmty";
    // Session ID for the view
    public static final String VIEW_SESSION_ID = "vesnid";
    // Internal session ID for the view
    public static final String INTERNAL_VIEW_SESSION_ID = "ilvesnid";
    // Internal video experiments associated with the view
    public static final String INTERNAL_VIDEO_EXPERIMENTS = "ilvdes";

    // List of keys related to customer view data
//    public static final List<String> keysCCVD;
    public static final List<String> keysCCVD = Collections.unmodifiableList(Arrays.asList(
            VIEW_SESSION_ID, INTERNAL_VIEW_SESSION_ID, INTERNAL_VIDEO_EXPERIMENTS, VIEW_DRM_TYPE
    ));

    // Default constructor
    public CustomerViewDataEntity() {
    }

    // Synchronization method (can be used for data syncing logic)
    public void sync() {
    }

    /**
     * Sets the DRM type for the video view.
     *
     * @param viewDrmType The DRM type (e.g., "Widevine", "PlayReady", etc.).
     * @throws JSONException If there is an error while storing the DRM type.
     */
    public void setViewDrmType(String viewDrmType) throws JSONException {
        if (viewDrmType != null) {
            this.put(VIEW_DRM_TYPE, viewDrmType);
        }
    }

    /**
     * Gets the DRM type for the video view.
     *
     * @return The DRM type for the video view.
     */
    public String getViewDrmType() {
        return this.get(VIEW_DRM_TYPE);
    }

    /**
     * Sets the session ID for the view. This is used to uniquely identify a view session.
     *
     * @param viewSessionId The session ID for the view session.
     * @throws JSONException If there is an error while storing the session ID.
     */
    public void setViewSessionId(String viewSessionId) throws JSONException {
        if (viewSessionId != null) {
            this.put(VIEW_SESSION_ID, viewSessionId);
        }
    }

    /**
     * Gets the session ID for the video view session.
     *
     * @return The session ID for the view session.
     */
    public String getViewSessionId() {
        return this.get(VIEW_SESSION_ID);
    }

    /**
     * Sets the internal session ID for the view. This can be used for internal tracking purposes.
     *
     * @param internalViewSessionId The internal session ID for the view session.
     * @throws JSONException If there is an error while storing the internal session ID.
     */
    public void setInternalViewSessionId(String internalViewSessionId) throws JSONException {
        if (internalViewSessionId != null) {
            this.put(INTERNAL_VIEW_SESSION_ID, internalViewSessionId);
        }
    }

    /**
     * Gets the internal session ID for the view. This can be used for internal tracking purposes.
     *
     * @return The internal session ID for the view session.
     */
    public String getInternalViewSessionId() {
        return this.get(INTERNAL_VIEW_SESSION_ID);
    }

    /**
     * Sets the internal video experiments data. This can store information about experiments or tests the user is part of.
     *
     * @param internalVideoExperiments The internal video experiments data (e.g., A/B tests).
     * @throws JSONException If there is an error while storing the experiments data.
     */
    public void setInternalVideoExperiments(String internalVideoExperiments) throws JSONException {
        if (internalVideoExperiments != null) {
            this.put(INTERNAL_VIDEO_EXPERIMENTS, internalVideoExperiments);
        }
    }

    /**
     * Gets the internal video experiments data. This can retrieve the information about experiments or tests the user is part of.
     *
     * @return The internal video experiments data.
     */
    public String getInternalVideoExperiments() {
        return this.get(INTERNAL_VIDEO_EXPERIMENTS);
    }


    // Static initialization block to populate the keys list
    /*static {
        List<String> keys = new ArrayList<>();
        keys.add(VIEW_SESSION_ID);
        keys.add(INTERNAL_VIEW_SESSION_ID);
        keys.add(INTERNAL_VIDEO_EXPERIMENTS);
        keys.add(VIEW_DRM_TYPE);
        keysCCVD = Collections.unmodifiableList(keys);
    }*/
}
