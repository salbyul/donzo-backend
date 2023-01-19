package com.doneasy.don.exception.project.projectreview;

public class ProjectReviewException extends RuntimeException {

    public static final String DUPLICATE = "duplicate";
    public static final String DUPLICATE_CODE = "PR001";

    public ProjectReviewException() {
        super();
    }

    public ProjectReviewException(String message) {
        super(message);
    }

    public ProjectReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectReviewException(Throwable cause) {
        super(cause);
    }

    protected ProjectReviewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
