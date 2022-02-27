package com.seventeen.common.exception;

import com.seventeen.common.enums.CodeEnum;

/**
 * 业务异常(error级别)
 *
 * @author seventeen
 * @date 2018/11/5
 */

public class ErrorException extends RuntimeException {

    private Integer code;

    private String msg;

    public ErrorException() {

    }

    public ErrorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ErrorException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public static ErrorException illegalArgument() {
        return new ErrorException(CodeEnum.PARAM_INVALIDATE.getCode(), CodeEnum.PARAM_INVALIDATE.getValue());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
