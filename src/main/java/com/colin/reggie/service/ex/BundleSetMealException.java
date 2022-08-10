package com.colin.reggie.service.ex;

public class BundleSetMealException
        extends ServiceException{
    public BundleSetMealException() {
        super("当前分类与已有套餐关联");
    }

    public BundleSetMealException(String message) {
        super(message);
    }

    public BundleSetMealException(String message, Throwable cause) {
        super(message, cause);
    }

    public BundleSetMealException(Throwable cause) {
        super(cause);
    }

    protected BundleSetMealException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
