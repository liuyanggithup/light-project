package com.seventeen.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.seventeen.common.enums.CodeEnum;
import com.seventeen.common.exception.WarnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Api响应写入工具类
 * @author seventeen
 * @date 2018/10/30
 */
public class ResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(ResponseUtil.class);

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * 将对象转换为JSON的格式返回
     */
    public static void writeJsonResponse(Object responseObj, HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(responseObj,
                    SerializerFeature.DisableCircularReferenceDetect));
        } catch (Exception e) {
            log.error("unknown exception: ", e);
            throw new WarnException(CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getValue());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

}
