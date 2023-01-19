package com.doneasy.don.exception.admin;

public class AdminException extends RuntimeException {

    public static final String INVALID_ACCESS = "invalidAccess";
    public static final String INVALID_ACCESS_CODE = "A001";

    public AdminException() {
        super();
    }

    public AdminException(String message) {
        super(message);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminException(Throwable cause) {
        super(cause);
    }

    protected AdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
