package com.colin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.entity.dto.SetmealDto;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto dto);

    void removeWithDish(List<Long> collect);
}
