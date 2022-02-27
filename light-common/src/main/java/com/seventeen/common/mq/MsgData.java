package com.seventeen.common.mq;

import com.seventeen.common.utils.StringUtil;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * MQ消息体结构
 *
 * @author seventeen
 */
@Data
public class MsgData<T> {
    /**
     * 追踪ID
     */
    private String traceId;
    /**
     * 发送方应用名
     */
    private String sendAppName;
    /**
     * 具体消息内容
     */
    private T data;

    /**
     * 私有化构造器
     */
    private MsgData() {
    }

    /**
     * 唯一创建构造器,会自动加入追踪ID
     *
     * @param sendAppName 发送方应用名 使用spring.application.name或app.id
     * @param data        消息体内容不能为空
     */
    public MsgData(String sendAppName, T data) {
        Assert.hasText(sendAppName, "发送方应用名必填");
        Assert.notNull(data, "消息体内容不能为空");
        String traceId = MDC.get("traceId");
        this.traceId = StringUtil.isBlank(traceId) ? sendAppName +
                UUID.randomUUID().toString() : traceId;
        this.data = data;
        this.sendAppName = sendAppName;
    }
}
