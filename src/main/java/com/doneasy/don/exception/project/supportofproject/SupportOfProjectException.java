package com.doneasy.don.exception.project.supportofproject;

public class SupportOfProjectException extends RuntimeException {

    public static final String DUPLICATED = "duplicated";
    public static final String DUPLICATED_CODE = "S001";

    public static final String INVALID_ACCESS = "invalidAccess";
    public static final String INVALID_ACCESS_CODE = "S002";

    public static final String OVERFLOW_PRICE = "overflowPrice";
    public static final String OVERFLOW_PRICE_CODE = "S003";

    public SupportOfProjectException() {
        super();
    }

    public SupportOfProjectException(String message) {
        super(message);
    }

    public SupportOfProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupportOfProjectException(Throwable cause) {
        super(cause);
    }

    protected SupportOfProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
