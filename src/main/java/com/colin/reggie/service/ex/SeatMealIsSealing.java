package com.colin.reggie.service.ex;

public class SeatMealIsSealing extends ServiceException {
    public SeatMealIsSealing() {
        this("当前套餐正在售卖中");
    }

    public SeatMealIsSealing(String message) {
        super(message);
    }

    public SeatMealIsSealing(String message, Throwable cause) {
        super(message, cause);
    }

    public SeatMealIsSealing(Throwable cause) {
        super(cause);
    }

    protected SeatMealIsSealing(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
