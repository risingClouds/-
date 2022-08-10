package com.colin.reggie.common;

import com.colin.reggie.service.ex.ServiceException;
import com.colin.reggie.service.ex.UsernameDuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {
    @ExceptionHandler(ServiceException.class)
    public R<String> exceptionHandle(ServiceException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

}
