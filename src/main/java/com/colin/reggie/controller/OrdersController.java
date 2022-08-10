package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.colin.reggie.common.BaseContext;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.OrderDetail;
import com.colin.reggie.entity.Orders;
import com.colin.reggie.entity.dto.OrdersDto;
import com.colin.reggie.entity.dto.PageDto;
import com.colin.reggie.service.OrderDetailService;
import com.colin.reggie.service.OrdersService;
import com.colin.reggie.util.BeanUtilCopy;
import com.sun.org.apache.bcel.internal.generic.LADD;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Resource
    private OrdersService ordersService;
    @Resource
    private OrderDetailService orderDetailService;
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
       ordersService.submit(orders);
       return R.success("成功");

    }
    @GetMapping("/userPage")
    public R<Page<OrdersDto>> page(PageDto pageDto){
        Integer pageSize = pageDto.getPageSize();
        Integer page = pageDto.getPage();
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> q = new LambdaQueryWrapper<>();
        q.eq(Orders::getUserId, BaseContext.getCurrentId());
        ordersService.page(ordersPage,q);
        Page<OrdersDto> ordersDtoPage = new Page<>(page, pageSize);
        BeanUtilCopy.copyProperties(ordersPage,ordersDtoPage);
        List<Orders> records= ordersPage.getRecords();
        List<OrdersDto> ordersDtos = BeanUtilCopy.copyListProperties(records, OrdersDto::new);
        ordersDtos.forEach(item->{
            Long id = item.getId();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId,id);
            List<OrderDetail> li = orderDetailService.list(wrapper);
            item.setOrderDetails(li);
        });
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }
}
