package com.seventeen.common.enums;

/**
 * Api 状态码
 *
 * @author seventeen
 * @date 2018/11/2
 */
public enum CodeEnum {

    /**
     * 成功
     */
    SUCCESS("0", "成功"),
    /**
     * 不合法参数
     */
    PARAM_INVALIDATE("2", "不合法参数"),
    /**
     * 失败
     */
    FAIL("1001", "失败"),
    /**
     * 消息不能读取
     */
    MESSAGE_NOT_READ("407", "消息不能读取"),
    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED("405", "不支持当前请求方法"),
    /**
     * 404
     */
    NOT_FOUND("404", "没找到请求"),
    /**
     * 系统异常
     */
    ERROR("9999", "系统异常");

    private final String code;
    private final String value;

    CodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
