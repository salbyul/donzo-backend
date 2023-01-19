package com.doneasy.don.exception.user;

public class MemberException extends RuntimeException {

    public final static String DUPLICATE_ID = "duplicateId";
    public final static String DUPLICATE_ID_CODE = "M001";
    public final static String DUPLICATE_NICKNAME = "duplicateNickname";
    public final static String DUPLICATE_NICKNAME_CODE = "M002";
    public final static String INVALID = "invalid";
    public final static String INVALID_CODE = "M003";

    public MemberException() {
        super();
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(Throwable cause) {
        super(cause);
    }

    protected MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
