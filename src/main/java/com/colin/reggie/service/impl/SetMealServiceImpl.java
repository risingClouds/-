package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.entity.SetmealDish;
import com.colin.reggie.entity.dto.SetmealDto;
import com.colin.reggie.mapper.SetMealMapper;
import com.colin.reggie.service.SetMealDishService;
import com.colin.reggie.service.SetMealService;
import com.colin.reggie.service.ex.SeatMealIsSealing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SetMealServiceImpl
        extends ServiceImpl<SetMealMapper, Setmeal>
        implements SetMealService {
    @Resource
    private SetMealDishService setMealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto dto) {
        saveOrUpdate(dto);
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes.forEach(item-> item.setSetmealId(dto.getId()));
        setMealDishService.saveOrUpdateBatch(setmealDishes);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaUpdateWrapper<Setmeal> q = new LambdaUpdateWrapper<>();
        q.in(Setmeal::getId,ids);
        q.eq(Setmeal::getStatus,1);
        int count = this.count(q);
        if(count>0){
            throw new SeatMealIsSealing();
        }
        this.removeByIds(ids);
        LambdaUpdateWrapper<SetmealDish> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(SetmealDish::getSetmealId,ids);
        setMealDishService.remove(lambdaUpdateWrapper);
    }
}
