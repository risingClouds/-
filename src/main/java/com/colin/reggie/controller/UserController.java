package com.colin.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.colin.reggie.common.R;
import com.colin.reggie.entity.User;
import com.colin.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user){

        return R.success("成功");
    }
    @PostMapping("/loginout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("user");
        return R.success("");
    }

    @PostMapping("/login")
    public R<User> login(HttpSession session, @RequestBody User user){
        String phone = user.getPhone();
        LambdaQueryWrapper<User> q = new LambdaQueryWrapper<>();
        q.eq(User::getPhone,phone);
         user = userService.getOne(q);
        if(null==user){
            user = new User();
            user.setPhone(phone);
            userService.save(user);
            user = userService.getOne(q);
        }
        session.setAttribute("user",user.getId());
        return R.success(user);
    }
}
