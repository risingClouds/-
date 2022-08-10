package com.colin.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.colin.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper
        extends BaseMapper<ShoppingCart> {
}
