package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.Category;
import com.colin.reggie.entity.Dish;
import com.colin.reggie.entity.DishFlavor;
import com.colin.reggie.entity.dto.DishDto;
import com.colin.reggie.entity.dto.PageDto;
import com.colin.reggie.entity.vo.DishVo;
import com.colin.reggie.service.CategoryService;
import com.colin.reggie.service.DishFlavorService;
import com.colin.reggie.service.DishService;
import com.colin.reggie.util.BeanUtilCopy;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Resource
    private DishService dishService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private DishFlavorService dishFlavorService;

    @PostMapping("/status/{statue}")
    private R<String> statue(@PathVariable Integer statue,String ids){
        List<Long> collect = Arrays.stream(ids.split(",")).map(Long::new).collect(Collectors.toList());
        collect.forEach(id->{
            LambdaUpdateWrapper<Dish> wrapper = new LambdaUpdateWrapper<>();
            wrapper.set(Dish::getStatus,statue);
            wrapper.eq(Dish::getId,id);
            dishService.update(null,wrapper);}
            );
        return R.success("成功");
    }

    @DeleteMapping()
    public R<String> delete(String ids){
        List<Long> idList = Stream.of(ids.split(",")).map(Long::new).collect(Collectors.toList());
        idList.forEach(dishService::removeDish);
        return R.success("成功");
    }
    @GetMapping("/list")
    public R<List<DishVo>> list(Dish dish){
        Long categoryId = dish.getCategoryId();
        String name = dish.getName();
        LambdaQueryWrapper<Dish> q = new LambdaQueryWrapper<>();
        q.eq(categoryId!=null,Dish::getCategoryId,categoryId).
                eq(StringUtils.isNotBlank(name),Dish::getName,name).
        eq(Dish::getStatus,1).
        orderByAsc(Dish::getSort).orderByAsc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(q);
        List<DishVo> dishVos = BeanUtilCopy.copyListProperties(list, DishVo::new);
        dishVos.forEach(each->{
            Long dishId = each.getId();
            LambdaUpdateWrapper<DishFlavor> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(DishFlavor::getDishId,dishId);
            List<DishFlavor> list1 = dishFlavorService.list(wrapper);
            each.setFlavors(list1);
        });
        return R.success(dishVos);
    }
    @GetMapping("/{id}")
    public R<DishVo> get(@PathVariable Long id){
        DishVo dishVo= dishService.getInfoById(id);

        return R.success(dishVo);
    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateInfo(dishDto);
        return R.success("成功");
    }


    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("成功");
    }


    @GetMapping("/page")
    public R<Page<DishVo>> page(PageDto pageDto){
        Integer pageSize = pageDto.getPageSize();
        Integer page = pageDto.getPage();
        String name = pageDto.getName();
        Page<Dish> info = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> q = new LambdaQueryWrapper<>();
        q.orderByAsc(Dish::getSort).like(StringUtils.isNotBlank(name),Dish::getName,name);
        dishService.page(info,q);
        Page<DishVo> res  =new Page<>(page,pageSize);
        BeanUtilCopy.copyProperties(info,res);
        List<DishVo> dishVos = BeanUtilCopy.copyListProperties(info.getRecords(), DishVo::new);
        dishVos.forEach(item->{
            Long id = item.getCategoryId();
            Category category = categoryService.getById(id);
            item.setCategoryName(category.getName());
//            item.setCategoryId(null);
        });
        res.setRecords(dishVos);
        return R.success(res);
    }

}
