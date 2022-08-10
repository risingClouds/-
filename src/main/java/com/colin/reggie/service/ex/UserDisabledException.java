package com.colin.reggie.service.ex;

public class UserDisabledException
        extends ServiceException{
    public UserDisabledException() {
        super();
    }

    public UserDisabledException(String message) {
        super(message);
    }

    public UserDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDisabledException(Throwable cause) {
        super(cause);
    }

    protected UserDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
