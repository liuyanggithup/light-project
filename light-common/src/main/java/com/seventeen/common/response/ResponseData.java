package com.seventeen.common.response;

import com.seventeen.common.enums.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Api 数据返回统一格式
 *
 * @author seventeen
 * @date 2018/11/2
 */

@ApiModel(description = "平台统一数据格式")
@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 101L;

    @ApiModelProperty(value = "业务状态码")
    private String code;
    @ApiModelProperty(value = "业务状态信息")
    private String msg;
    @ApiModelProperty(value = "结果集")
    private T data;
    @ApiModelProperty(value = "服务器时间")
    private long currentTime;

    private ResponseData(T data) {
        this.code = CodeEnum.SUCCESS.getCode();
        this.msg = CodeEnum.SUCCESS.getValue();
        this.currentTime = System.currentTimeMillis();
        this.data = data;
    }

    private ResponseData(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        if (StringUtils.isBlank(code)) {
            this.code = CodeEnum.FAIL.getCode();
        }
        if (msg == null) {
            this.msg = StringUtils.EMPTY;
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

    public static <T> ResponseData<T> fail(String code, String msg) {
        code = code == null ? CodeEnum.FAIL.getCode() : code;
        return new ResponseData<>(code, msg, null);
    }


    public static <T> ResponseData<T> toSystemError() {
        return new ResponseData<>(
                CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getValue(), null);
    }

}
