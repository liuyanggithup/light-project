package com.seventeen.common.exception;

import com.seventeen.common.enums.CodeEnum;

/**
 * @author seventeen
 */
public class MqException extends RuntimeException {

    private Integer code;

    private String msg;

    public MqException() {

    }

    public MqException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MqException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public static MqException systemError() {
        return new MqException(CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getValue());
    }

    public static MqException illegalArgument() {
        return new MqException(CodeEnum.PARAM_INVALIDATE.getCode(), CodeEnum.PARAM_INVALIDATE.getValue());
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
