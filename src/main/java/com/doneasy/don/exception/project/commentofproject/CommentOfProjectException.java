package com.doneasy.don.exception.project.commentofproject;

public class CommentOfProjectException extends RuntimeException{

    public final static String DUPLICATED = "duplicated";
    public final static String DUPLICATED_CODE = "C001";

    public final static String INVALID_ACCESS = "invalidAccess";
    public final static String INVALID_ACCESS_CODE = "C002";

    public final static String OVERFLOW_PRICE = "overflowPrice";
    public final static String OVERFLOW_PRICE_CODE = "C003";


    public CommentOfProjectException() {
        super();
    }

    public CommentOfProjectException(String message) {
        super(message);
    }

    public CommentOfProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentOfProjectException(Throwable cause) {
        super(cause);
    }

    protected CommentOfProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
