package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.colin.reggie.common.BaseContext;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.ShoppingCart;
import com.colin.reggie.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> q = new LambdaQueryWrapper<>();
        q.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        q.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(q);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> q = new LambdaQueryWrapper<>();
        q.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(q);
        return  R.success("清空购物车成功");
    }

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getCurrentId());
        Long dishId = shoppingCart.getDishId();
        shoppingCart.setCreateTime(LocalDateTime.now());
        LambdaQueryWrapper<ShoppingCart> q = new LambdaQueryWrapper<>();
        q.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        if (dishId != null) {
            q.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            q.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(q);
        if (cartServiceOne != null) {
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }

}
