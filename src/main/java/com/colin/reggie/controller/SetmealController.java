package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.Category;
import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.entity.SetmealDish;
import com.colin.reggie.entity.dto.PageDto;
import com.colin.reggie.entity.dto.SetmealDto;
import com.colin.reggie.entity.vo.SetmealVo;
import com.colin.reggie.service.CategoryService;
import com.colin.reggie.service.SetMealDishService;
import com.colin.reggie.service.SetMealService;
import com.colin.reggie.util.BeanUtilCopy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Resource
    private SetMealService setMealService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private SetMealDishService setMealDishService;

    @DeleteMapping
    public R<String> deleteBatch(String ids){
        List<Long> collect = Stream.of(ids.split(",")).map(Long::new).collect(Collectors.toList());
        setMealService.removeWithDish(collect);
        return R.success("成功");
    }

    @PostMapping("status/{statue}")
    public R<String> status(@PathVariable Integer statue,String ids){
        List<Long> collect = Arrays.stream(ids.split(",")).map(Long::new).collect(Collectors.toList());
        collect.forEach(item-> {
            LambdaUpdateWrapper<Setmeal> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(Setmeal::getStatus,statue);
            wrapper.eq(Setmeal::getId,ids);
            setMealService.update(wrapper);
        });
        return R.success("成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id){
        Setmeal setmeal = setMealService.getById(id);
        String name = categoryService.getById(setmeal.getCategoryId()).getName();
        SetmealDto setmealDto = new SetmealDto();
        LambdaQueryWrapper<SetmealDish> q = new LambdaQueryWrapper<>();
        q.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setMealDishService.list(q);

        BeanUtilCopy.copyProperties(setmeal,setmealDto);
        setmealDto.setCategoryName(name);
        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }

    @GetMapping("/page")
    public R<Page<SetmealVo>> page(PageDto pageDto){
        Integer page = pageDto.getPage();
        Integer pageSize = pageDto.getPageSize();
        String name = pageDto.getName();
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> q = new LambdaQueryWrapper<>();
        q.like(StringUtils.isNotBlank(name),Setmeal::getName,name);
        setMealService.page(setmealPage,q);
        Page<SetmealVo> data = new Page<>(page,pageSize);
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealVo> setmealVos = BeanUtilCopy.copyListProperties(records, SetmealVo::new);
        setmealVos.forEach(item->{
            Long id = item.getCategoryId();
            String categoryName = categoryService.getById(id).getName();
            item.setCategoryName(categoryName);
            item.setCategoryId(null);
        });
        data.setRecords(setmealVos);
        return R.success(data);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto dto){
        setMealService.saveWithDish(dto);
        return R.success("成功");
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto dto){
//        log.info(dto.toString());
        setMealService.saveWithDish(dto);
        return R.success("成功");
    }

    @GetMapping("/list")
    public R<List<SetmealVo>> list(Setmeal setmeal){
        Long categoryId = setmeal.getCategoryId();
        LambdaUpdateWrapper<Setmeal> q = new LambdaUpdateWrapper<>();
        q.eq(categoryId!=null,Setmeal::getCategoryId, categoryId);
        q.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        q.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setMealService.list(q);
        List<SetmealVo> setmealVos = BeanUtilCopy.copyListProperties(list, SetmealVo::new);
        return R.success(setmealVos);


    }
}
