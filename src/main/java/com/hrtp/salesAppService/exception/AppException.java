package com.hrtp.salesAppService.exception;

/**
 * AppException
 * description 自定义异常
 * create by lxj 2018/8/20
 **/
public class AppException extends BaseException {

    private static final long serialVersionUID = 1L;
    private String code;

    public AppException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(String msg,Throwable t){
        super(msg,t);
    }

    public AppException(Throwable cause) {
        super(cause);
    }


    public AppException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
