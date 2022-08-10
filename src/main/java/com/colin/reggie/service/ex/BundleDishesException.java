package com.colin.reggie.service.ex;

public class BundleDishesException
        extends ServiceException{
    public BundleDishesException() {
        this("当前分类与已有菜品关联");
    }

    public BundleDishesException(String message) {
        super(message);
    }

    public BundleDishesException(String message, Throwable cause) {
        super(message, cause);
    }

    public BundleDishesException(Throwable cause) {
        super(cause);
    }

    protected BundleDishesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
