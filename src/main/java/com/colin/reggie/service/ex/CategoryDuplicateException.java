package com.colin.reggie.service.ex;

public class CategoryDuplicateException
    extends DuplicateException{
    public CategoryDuplicateException() {
        super();
    }

    public CategoryDuplicateException(String message) {
        super(message);
    }

    public CategoryDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryDuplicateException(Throwable cause) {
        super(cause);
    }

    protected CategoryDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
