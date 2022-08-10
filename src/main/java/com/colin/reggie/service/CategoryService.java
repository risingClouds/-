package com.colin.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.colin.reggie.entity.Category;

public interface CategoryService 
        extends IService<Category> {
    void addCategory(Category category);
    void remove(Long id);
}
