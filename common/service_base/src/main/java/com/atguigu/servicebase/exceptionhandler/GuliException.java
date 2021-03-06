package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LS
 * @date 2021-12-08
 */
@Data
@AllArgsConstructor //全参构造
@NoArgsConstructor  //无参构造
public class GuliException extends RuntimeException {
    private Integer code;   //状态码
    private String msg;     //异常信息
}
