package com.seventeen.common.exception;


import com.seventeen.common.enums.CodeEnum;

/**
 * 业务异常(warn级别)
 *
 * @author seventeen
 * @date 2018/11/5
 */

public class WarnException extends RuntimeException {

    private Integer code;

    private String msg;

    public WarnException() {

    }

    public WarnException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WarnException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public static WarnException systemError() {
        return new WarnException(CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getValue());
    }

    public static WarnException illegalArgument() {
        return new WarnException(CodeEnum.PARAM_INVALIDATE.getCode(), CodeEnum.PARAM_INVALIDATE.getValue());
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
