package com.colin.reggie.interceptor;

import com.alibaba.fastjson.JSON;
import com.colin.reggie.common.BaseContext;
import com.colin.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Intercepted a request:{}",request.getRequestURI());
       if(request.getSession()
               .getAttribute("employee")!=null){
           log.info("用户:{},已登录",request.getSession().getAttribute("employee"));
           BaseContext.setCurrentId((Long)request.getSession().getAttribute("employee"));
           return true;
       }

        if(request.getSession()
                .getAttribute("user")!=null){
            log.info("用户:{},已登录",request.getSession().getAttribute("user"));
            BaseContext.setCurrentId((Long)request.getSession().getAttribute("user"));
            return true;
        }
       log.info("用户未登录");
       //配合前端，返回数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        response.sendRedirect("/backend/page/login/login.html");
       return false;
    }
}
