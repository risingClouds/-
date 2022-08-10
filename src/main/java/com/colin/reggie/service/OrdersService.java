package com.colin.reggie.service;

import com.colin.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders o);
}
