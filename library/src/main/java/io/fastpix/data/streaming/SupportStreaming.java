package io.fastpix.data.streaming;

import io.fastpix.data.Interfaces.EventContract;
import io.fastpix.data.entity.ViewDataEntity;

public class SupportStreaming implements EventContract {

    public enum Type {
        debugEvent,
        viewMetricEvent

    }

    private final Type type;
    private final String mes;

    private final ViewDataEntity viewData;
    public boolean isViewMetricTrue = false;


    public SupportStreaming(Type type, ViewDataEntity viewData) {
        this.viewData = viewData;
        this.type = type;
        this.mes = null;
        isViewMetricTrue = true;
    }

    public SupportStreaming(Type type, String mes) {
        this.type = type;
        this.mes = mes;
        this.viewData = null;

    }


    /**
     * Returns the type of the event.
     * @return The event type as a string.
     */
    public String getType() {
        return type.toString();
    }

    public String getMes() {
        return mes;
    }

    /**
     * Indicates whether this event contains session-related data.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isSessionData() {
        return false;
    }

    /**
     * Indicates whether this event is trackable.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isTrackable() {
        return false;
    }

    /**
     * Indicates whether this event is related to media playback.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isPlayback() {
        return false;
    }

    /**
     * Indicates whether this event contains data-related information.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isData() {
        return false;
    }

    /**
     * Indicates whether this event represents an error.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isError() {
        return false;
    }

    /**
     * Indicates whether this event is related to view metrics.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isViewMetric() {
        return isViewMetricTrue;
    }

    /**
     * Indicates whether this event is used for debugging purposes.
     * @return Always returns false (can be overridden in subclasses).
     */
    public boolean isDebug() {
        return false;
    }

    /**
     * Gets the ViewData associated with the event.
     * @return The ViewData object associated with this event.
     */
    public ViewDataEntity getViewData() {
        return this.viewData;
    }
}
