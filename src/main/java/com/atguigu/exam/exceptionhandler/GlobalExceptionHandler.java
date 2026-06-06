package com.atguigu.exam.exceptionhandler;

import com.atguigu.exam.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 项目：exam-system-server-new
 * 包名：com.atguigu.exam.exceptionhandler
 * 类名：GlobalExceptionHandler
 * 描述：全局异常处理类
 * 作者：MechrevoUser1
 * 日期：2026/6/5 22:56
 * 用到的注解依赖：
 */
@Slf4j
//这个注解表示这是一个全局异常处理类
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理方法
     * @param e 异常对象
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        // 打印异常信息 到控制台 堆栈信息
        e.printStackTrace();
        log.error("******全局异常信息为：{}", e.getMessage());
        return Result.error(e.getMessage());
    }


}
