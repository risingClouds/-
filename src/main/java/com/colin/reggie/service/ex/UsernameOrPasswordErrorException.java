package com.colin.reggie.service.ex;

public class UsernameOrPasswordErrorException
            extends ServiceException{
    public UsernameOrPasswordErrorException() {
        super();
    }

    public UsernameOrPasswordErrorException(String message) {
        super(message);
    }

    public UsernameOrPasswordErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameOrPasswordErrorException(Throwable cause) {
        super(cause);
    }

    protected UsernameOrPasswordErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
