package com.colin.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.colin.reggie.entity.Category;
import com.colin.reggie.entity.Dish;
import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.mapper.CategoryMapper;
import com.colin.reggie.service.CategoryService;
import com.colin.reggie.service.DishService;
import com.colin.reggie.service.SetMealService;
import com.colin.reggie.service.ex.BundleDishesException;
import com.colin.reggie.service.ex.BundleSetMealException;
import com.colin.reggie.service.ex.CategoryDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl
        extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    private DishService dishService;
    @Autowired
    public void setDishService(DishService dishService) {
        this.dishService = dishService;
    }

    @Resource
    private SetMealService setMealService;

    @Override
    public void addCategory(Category category) {
        LambdaQueryWrapper<Category> q = new LambdaQueryWrapper<>();
        q.eq(Category::getName,category.getName()).
                select(Category::getName);
        Category one = getOne(q);
        if(one!=null){
            String message = (category.getType()==1?"菜品":"套餐")+":"+category.getName()+"已存在";
            throw new CategoryDuplicateException(message);
        }
        save(category);
    }

    /**
     * 查询是否有关联在删除
     * @param id id
     */
    @Override
    public void remove(Long id) {
        //查询当前是否关联了菜品，如果关联，抛出异常
        LambdaQueryWrapper<Dish> dishQ = new LambdaQueryWrapper<>();
        dishQ.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishQ);
        if(count1>0){
            throw new BundleDishesException();
        }
        //查询当前是否关联了套餐
        LambdaQueryWrapper<Setmeal> setmealQ = new LambdaQueryWrapper<>();
        setmealQ.eq(Setmeal::getCategoryId,id);
        int count2 = setMealService.count(setmealQ);
        if(count2>0){
            throw new BundleSetMealException();
        }
        //均正常，则删除
        removeById(id);
    }
}
