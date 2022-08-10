package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.ShoppingCart;
import com.colin.reggie.mapper.ShoppingCartMapper;
import com.colin.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl
           extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
