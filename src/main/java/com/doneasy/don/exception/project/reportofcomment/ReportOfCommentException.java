package com.doneasy.don.exception.project.reportofcomment;

public class ReportOfCommentException extends RuntimeException {

    public static final String DUPLICATE = "duplicate";
    public static final String DUPLICATE_CODE = "RC001";
    public static final String INVALID_ACCESS = "invalidAccess";
    public static final String INVALID_ACCESS_CODE = "RC002";

    public ReportOfCommentException() {
        super();
    }

    public ReportOfCommentException(String message) {
        super(message);
    }

    public ReportOfCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportOfCommentException(Throwable cause) {
        super(cause);
    }

    protected ReportOfCommentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
