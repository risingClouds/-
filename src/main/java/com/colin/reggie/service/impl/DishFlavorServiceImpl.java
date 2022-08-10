package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.DishFlavor;
import com.colin.reggie.mapper.DishFlavorMapper;
import com.colin.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl
           extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
