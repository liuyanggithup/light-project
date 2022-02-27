package com.seventeen.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seventeen.common.enums.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * Api 数据返回统一格式
 *
 * @author seventeen
 * @date 2018/11/2
 */

@ApiModel(description = "平台统一数据格式")
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 101L;

    @ApiModelProperty(value = "状态码")
    private Status status;
    @ApiModelProperty(value = "结果集")
    private T data;
    @ApiModelProperty(value = "服务器时间")
    private long currentTime;

    private ResponseData() {
    }

    private ResponseData(T data) {
        this.status = new Status(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getValue());
        this.currentTime = System.currentTimeMillis();
        this.data = data;
    }

    private ResponseData(Integer code, String msg, T data) {
        this.status = new Status(code, msg);
        if (code == null) {
            this.status.setRetCode(CodeEnum.FAIL.getCode());
        }
        if (msg == null) {
            this.status.setMsg("");
        }

        this.data = data;
        this.currentTime = System.currentTimeMillis();
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(data);
    }

    public static <T> ResponseData<T> success(String msg, T data) {
        return new ResponseData<>(CodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseData<T> fail(String msg) {
        return new ResponseData<>(CodeEnum.FAIL.getCode(), msg, null);
    }

    public static <T> ResponseData<T> fail(CodeEnum codeEnum) {
        return new ResponseData<>(codeEnum.getCode(), codeEnum.getValue(), null);
    }

    public static <T> ResponseData<T> fail(Integer code, String msg) {
        code = code == null ? CodeEnum.FAIL.getCode() : code;
        return new ResponseData<>(code, msg, null);
    }

    public static <T> ResponseData<T> toParamInvalidateJson() {
        return new ResponseData<>(
                CodeEnum.PARAM_INVALIDATE.getCode(), CodeEnum.PARAM_INVALIDATE.getValue(), null);
    }

    public static <T> ResponseData<T> toSystemError() {
        return new ResponseData<>(
                CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getValue(), null);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        Integer retCode = status.getRetCode();
        return Objects.equals(CodeEnum.SUCCESS.getCode(), retCode);
    }

    @JsonIgnore
    public String getErrorMsg() {
        return status.getMsg();
    }
}
