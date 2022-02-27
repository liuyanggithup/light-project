package com.seventeen.common.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.seventeen.common.exception.MsgConverterException;
import lombok.extern.slf4j.Slf4j;

/**
 * MQ 消息转换实体对象
 *
 * @author seventeen
 */
@Slf4j
public class MsgConverter {

    public static <T> T getMsgData(byte[] body, TypeReference<T> typeReference) {
        String msg = new String(body);
        try {
            return JSON.parseObject(msg, typeReference);
        } catch (Exception e) {
            log.error("消息转换失败，msg {} error {}", msg, e.getMessage(), e);
            throw new MsgConverterException(e.getMessage());
        }
    }

    public static <T> T getMsgData(byte[] body, Class<T> clazz) {
        String msg = new String(body);
        try {
            return JSON.parseObject(msg, clazz);
        } catch (Exception e) {
            log.error("消息转换失败，msg {} error {}", msg, e.getMessage(), e);
            throw new MsgConverterException(e.getMessage());
        }

    }

}
