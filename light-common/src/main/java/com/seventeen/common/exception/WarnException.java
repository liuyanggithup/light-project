package com.seventeen.common.exception;


/**
 * 业务异常(warn级别)
 *
 * @author seventeen
 * @date 2018/11/5
 */

public class WarnException extends RuntimeException {

    private String code;

    private String msg;

    public WarnException() {

    }

    public WarnException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WarnException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
