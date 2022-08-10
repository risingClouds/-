package com.colin.reggie.entity.dto;

import com.colin.reggie.entity.Setmeal;
import com.colin.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
