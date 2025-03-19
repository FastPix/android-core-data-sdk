package io.fastpix.data.request;

public class RequestFailureException extends Exception {
    private final int errorCode;
    private final String context;

    public RequestFailureException(int errorCode, String errorMessage, String errorMessageContext) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.context = errorMessageContext;
    }

    public RequestFailureException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, (String)null);
    }

    public int getCode() {
        return this.errorCode;
    }

    public String getContext() {
        return this.context;
    }
}
