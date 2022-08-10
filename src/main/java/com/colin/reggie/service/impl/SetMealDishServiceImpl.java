package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.entity.SetmealDish;
import com.colin.reggie.mapper.SetMealDishMapper;
import com.colin.reggie.service.SetMealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetMealDishServiceImpl
        extends ServiceImpl<SetMealDishMapper, SetmealDish> implements SetMealDishService {
}
