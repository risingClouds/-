package com.colin.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 元数据对象处理器
 */
@Component
public class MetaObjectHandlerImpl
            implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long empId = BaseContext.getCurrentId();
        if (empId != null){
            metaObject.setValue("createUser", empId);
            metaObject.setValue("updateUser", empId);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long empId = BaseContext.getCurrentId();
        metaObject.setValue("updateUser",empId);
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}
