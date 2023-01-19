package com.doneasy.don.exception.jwt;

public class JwtTokenException extends RuntimeException {

    public static final String INVALID_TOKEN = "invalidToken";
    public static final String INVALID_TOKEN_CODE = "J001";

    public JwtTokenException() {
        super();
    }

    public JwtTokenException(String message) {
        super(message);
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }

    protected JwtTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
