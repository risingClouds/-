package com.colin.reggie.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryVo {
    //类型 1 菜品分类 2 套餐分类
    private Integer type;

    private Long id;

    //分类名称
    private String name;


    //顺序
    private Integer sort;


    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //是否删除
    private Integer isDeleted;

}
