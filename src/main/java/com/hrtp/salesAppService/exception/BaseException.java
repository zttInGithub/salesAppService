package com.hrtp.salesAppService.exception;

/**
 * BaseException
 * description
 * create by lxj 2018/8/24
 **/
public class BaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String code;
    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable t) {
        super(t);
    }
}
