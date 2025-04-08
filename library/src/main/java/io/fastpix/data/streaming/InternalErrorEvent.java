package io.fastpix.data.streaming;

/**
 * Represents an internal error event.
 * This class extends {@link AbstractEventContract} and includes specific error details such as
 * error message, error code, and error context.
 */
public class InternalErrorEvent extends AbstractEventContract {
    public static final String TYPE = "internalError"; // Constant event type
    private final String errorMessage; // Error message describing the issue
    private final int errorCodeId; // Unique identifier for the error code
    private final String errorContext; // Contextual information about the error

    /**
     * Constructs an {@code InternalErrorEvent} with an error code and message.
     * The error context is set to {@code null}.
     *
     * @param errorCodeId The unique identifier for the error.
     * @param errorMessage The message describing the error.
     */
    public InternalErrorEvent(int errorCodeId, String errorMessage) {
        this(errorCodeId, errorMessage, (String) null);
    }

    /**
     * Constructs an {@code InternalErrorEvent} with an error code, message, and context.
     *
     * @param errorCodeId The unique identifier for the error.
     * @param errorMessage The message describing the error.
     * @param errorContext Additional contextual information about the error.
     */
    public InternalErrorEvent(int errorCodeId, String errorMessage, String errorContext) {
        this.errorCodeId = errorCodeId;
        this.errorMessage = errorMessage;
        this.errorContext = errorContext;
    }

    /**
     * Retrieves the error context.
     *
     * @return The error context, or {@code null} if none was provided.
     */
    public String getErrorContext() {
        return this.errorContext;
    }

    /**
     * Retrieves the error code.
     *
     * @return The error code.
     */
    public int getErrorCode() {
        return this.errorCodeId;
    }

    /**
     * Retrieves the error message.
     *
     * @return The error message describing the error.
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Indicates that this event represents an error.
     *
     * @return {@code true} because this is an error event.
     */
    public boolean isError() {
        return true;
    }

    /**
     * Returns the type of the event (always {@code "internalerror"}).
     *
     * @return The event type.
     */
    public String getType() {
        return TYPE;
    }

}
