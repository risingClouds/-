package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.Dish;
import com.colin.reggie.entity.DishFlavor;
import com.colin.reggie.entity.SetmealDish;
import com.colin.reggie.entity.dto.DishDto;
import com.colin.reggie.entity.vo.DishVo;
import com.colin.reggie.mapper.DishMapper;
import com.colin.reggie.service.DishFlavorService;
import com.colin.reggie.service.DishService;
import com.colin.reggie.service.SetMealDishService;
import com.colin.reggie.service.SetMealService;
import com.colin.reggie.service.ex.BundleSetMealException;
import com.colin.reggie.util.BeanUtilCopy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl
            extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    private DishFlavorService dishFlavorService;
    @Resource
    private SetMealDishService setMealDishService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(item-> item.setDishId(id));
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishVo getInfoById(Long id) {
        Dish dish = this.getById(id);
        LambdaQueryWrapper<DishFlavor> q = new LambdaQueryWrapper<>();
        q.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(q);
        DishVo dishVo = new DishVo();
        BeanUtilCopy.copyProperties(dish,dishVo);
        dishVo.setFlavors(list);
        return dishVo;
    }

    @Override
    public void updateInfo(DishDto dishDto) {
        //dish
        this.updateById(dishDto);
        //清理口味
        LambdaQueryWrapper<DishFlavor> q = new LambdaQueryWrapper<>();
        q.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(q);
        //插入口味
        dishDto.getFlavors().forEach(item-> item.setDishId(dishDto.getId()));
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    @Override
    public void removeDish(Long id) {
        LambdaQueryWrapper<SetmealDish> q = new LambdaQueryWrapper<>();
        q.eq(SetmealDish::getDishId,id).last(" limit 1");
        if (null!=setMealDishService.getOne(q)){
            throw new BundleSetMealException("当前菜品与已有套餐关联");
        }
        this.removeById(id);
    }
}
