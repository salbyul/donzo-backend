package com.doneasy.don.exception.project.donationofproject;

public class DonationOfProjectException extends RuntimeException {

    public static final String OVERFLOW_PRICE = "overflowPrice";
    public static final String OVERFLOW_PRICE_CODE = "D001";

    public static final String ZERO_PRICE = "zeroPrice";
    public static final String ZERO_PRICE_CODE = "D002";

    public DonationOfProjectException() {
        super();
    }

    public DonationOfProjectException(String message) {
        super(message);
    }

    public DonationOfProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DonationOfProjectException(Throwable cause) {
        super(cause);
    }

    protected DonationOfProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
