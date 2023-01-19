package com.doneasy.don.exception.user;

public class OrganizationException extends RuntimeException{

    public final static String DUPLICATE_ID = "duplicate id";
    public final static String DUPLICATE_ID_CODE = "O001";
    public final static String DUPLICATE_NICKNAME = "duplicate nickname";
    public final static String DUPLICATE_NICKNAME_CODE = "O002";
    public final static String INVALID = "invalid";
    public final static String INVALID_CODE = "O003";

    public OrganizationException() {
        super();
    }

    public OrganizationException(String message) {
        super(message);
    }

    public OrganizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrganizationException(Throwable cause) {
        super(cause);
    }

    protected OrganizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
