package com.colin.reggie.entity.vo;

import com.colin.reggie.entity.DishFlavor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishVo {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;

    //分类名称
//    private String CategoryName;


    //菜品价格
    private BigDecimal price;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;


    //顺序
    private Integer sort;


    private LocalDateTime updateTime;


    //是否删除
    private Integer isDeleted;
}
