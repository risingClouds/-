package com.colin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.colin.reggie.entity.Dish;
import com.colin.reggie.entity.dto.DishDto;
import com.colin.reggie.entity.vo.DishVo;

public interface DishService
        extends IService<Dish> {
    //新增菜品同时插入口味数据
    void saveWithFlavor(DishDto dishDto);
    DishVo getInfoById(Long id);

    void updateInfo(DishDto dishDto);

    void removeDish(Long id);
}
