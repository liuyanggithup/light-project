package com.seventeen.common.exception;

/**
 * MQ 消息转换异常
 *
 * @author seventeen
 */
public class MsgConverterException extends RuntimeException {
    private String message;

    public MsgConverterException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}