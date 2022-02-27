package com.seventeen.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Api 数据返回统一格式
 * @author seventeen
 * @date 2018/11/2
 */

public class Status {

    @ApiModelProperty(value = "业务状态码")
    private Integer retCode;
    @ApiModelProperty(value = "业务状态信息")
    private String msg;
    @ApiModelProperty(value = "城市重定向(特殊字段，尽量不用)")
    private RedirectCity redirectCity;

    public Status() {}

    public Status(Integer retCode, String msg) {
        this.retCode = retCode;
        this.msg = msg;
    }

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RedirectCity getRedirectCity() {
        return redirectCity;
    }

    public void setRedirectCity(RedirectCity redirectCity) {
        this.redirectCity = redirectCity;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedirectCity{

        private Integer cid;

        private String name;

    }
}
