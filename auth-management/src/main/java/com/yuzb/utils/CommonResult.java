package com.yuzb.utils;


import com.yuzb.exception.ExceptionCodeEnum;
import lombok.Data;

import java.util.List;

/*
 * @Author: yuzb
 * @Date: 2021/5/9
 * @Description: 公共返回对象
 **/
@Data
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;

    private CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @Description: 成功返回信息
     * @Param:
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * @Description: 成功返回信息
     * @Param: message
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> success(String message) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), message, null);
    }

    /**
     * @Description: 成功返回信息
     * @Param: data
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * @Description: 成功返回信息
     * @Param:  list
     * @return:  CommonResult
     * @Date: 2021/5/9
     */
    public static<T> CommonResult<List<T>> success(List<T> data) {
        return new CommonResult<List<T>>(ExceptionCodeEnum.SUCCESS.getCode(), ExceptionCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * @Description: 成功返回信息
     * @Param: data message
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ExceptionCodeEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * @Description: 失败返回信息
     * @Param:
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(ExceptionCodeEnum.FAILED.getCode(), ExceptionCodeEnum.FAILED.getMessage(), null);
    }

    /**
     * @Description: 失败返回信息
     * @Param: message
     * @return: CommonResult
     * @Date: 2021/5/9
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ExceptionCodeEnum.FAILED.getCode(), message, null);
    }

}
