package com.yuzb.exception;


/*
 * @Author: yuzb
 * @Date: 2021/5/11
 * @Description: 自定义异常
 **/
public class AuthManageException extends RuntimeException {
    static final long serialVersionUID = -7034897190745761568L;

    public AuthManageException() {
        super();
    }

    public AuthManageException(String message) {
        super(message);
    }
}
