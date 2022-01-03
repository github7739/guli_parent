package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LS
 * @date 2021-12-08
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {   //三个异常的优先级: 自定义异常 > 特定异常 > 全局异常
    //此为全局异常,不管出现什么异常都会执行此方法
    @ExceptionHandler(Exception.class)
    //为了返回数据
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理!!!");
    }

    //此为特定异常处理,当出现特定异常会执行此方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error1(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了特定异常处理!!!");
    }

    //此为自定义异常处理,可以制定可能出现的异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error2(GuliException e){
        log.error(e.getMessage());  //把错误的日志文件也输出到文件中去
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
