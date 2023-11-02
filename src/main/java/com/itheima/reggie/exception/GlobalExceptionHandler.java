package com.itheima.reggie.exception;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> businessException(BusinessException businessException){
        log.error(businessException.getMessage());
        return R.error(businessException.getMessage());
    }

    /**
     * 未知异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> exception(Exception exception){
        log.error(exception.getMessage());
        return R.error(exception.getMessage());
    }
}