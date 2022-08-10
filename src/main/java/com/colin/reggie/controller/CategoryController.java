package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.Category;
import com.colin.reggie.entity.dto.PageDto;
import com.colin.reggie.entity.vo.CategoryVo;
import com.colin.reggie.service.CategoryService;
import com.colin.reggie.util.BeanUtilCopy;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @PostMapping
    public R<String> addCategory(@RequestBody Category category){
        categoryService.addCategory(category);
        return R.success("新增分类成功");
    }
    @GetMapping("/list")
    public R<List<CategoryVo>> list(Category category){
        LambdaQueryWrapper<Category> q = new LambdaQueryWrapper<>();
        q.eq(category.getType()!=null,Category::getType,category.getType());
        q.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(q);
        List<CategoryVo> categoryVos = BeanUtilCopy.copyListProperties(list, CategoryVo::new);
        return R.success(categoryVos);
    }

    @GetMapping("/page")
    public R<Page<CategoryVo>> list(PageDto pageDto){
        Integer page = pageDto.getPage();
        Integer pageSize = pageDto.getPageSize();
        Page<Category> info = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> q = new LambdaQueryWrapper<>();
        q.orderByAsc(Category::getSort);
        categoryService.page(info,q);
        Page<CategoryVo> res = new Page<>(page,pageSize);
        BeanUtilCopy.copyProperties(info,res);
        List<CategoryVo> categoryVos = BeanUtilCopy.copyListProperties(info.getRecords(), CategoryVo::new);
        res.setRecords(categoryVos);
        return R.success(res);
    }
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id为{}",ids.toString());
//        categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("成功");
    }
}
