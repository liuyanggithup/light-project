package com.seventeen.starter.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.seventeen.common.utils.DateUtil;
import com.seventeen.common.utils.RequestUtil;
import com.seventeen.common.utils.StringUtil;
import com.seventeen.starter.common.config.AppProperties;
import com.seventeen.starter.common.constants.HeaderConstants;
import com.seventeen.starter.common.constants.MdcConstants;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author seventeen
 */
public class HttpTraceLogFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger("ACCESS_LOG");
    private static final Logger LOGGER2 = LoggerFactory.getLogger("com.seventeen");
    private static final Set<String> IGNORE_CONTENT_TYPE = Sets.newHashSet("multipart/form-data", "application/x-www-form-urlencoded");
    private static final List<String> METHODS = Lists.newArrayList("POST", "PUT", "PATCH");

    private AppProperties properties;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    public void setProperties(AppProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @Nonnull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String reqTime = DateUtil.formatDateTime(new Date());
        long startTime = System.currentTimeMillis();

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        String parentApp = mutableRequest.getHeader(HeaderConstants.PARENT_APP);
        try {
            String traceId = request.getHeader(HeaderConstants.TRACE_ID_NAME);
            if (StringUtil.isBlank(traceId)) {
                traceId = buildTraceId();
                mutableRequest.putHeader(HeaderConstants.TRACE_ID_NAME, traceId);
            }
            MDC.put(MdcConstants.TRACE_ID, traceId);
            MDC.put(MdcConstants.PARENT_APP, parentApp);
            filterChain.doFilter(mutableRequest, response);
            status = response.getStatus();
        } finally {

            try {
                String contentType = request.getContentType();
                String path = RequestUtil.getUrl(mutableRequest);
                String method = mutableRequest.getMethod();
                Object param = null;
                try {
                    if (METHODS.contains(method) && StringUtil.isNotEmpty(contentType) && IGNORE_CONTENT_TYPE.stream().noneMatch(contentType::startsWith)) {
                        param = mutableRequest.getReader().lines().collect(Collectors.joining(""));
                    } else {
                        param = RequestUtil.getParam(mutableRequest);
                    }
                } catch (Exception e) {
                    LOGGER2.info("获取param异常,", e);
                }

                HttpTraceLog traceLog = new HttpTraceLog();
                traceLog.setReqTime(reqTime);
                traceLog.setTraceId(mutableRequest.getHeader(HeaderConstants.TRACE_ID_NAME));
                traceLog.setParentApp(parentApp);
                traceLog.setPath(path);
                traceLog.setMethod(method);
                traceLog.setParam(param);
                traceLog.setAdminUserId(mutableRequest.getHeader(HeaderConstants.ADMIN_USER_ID));
                String adminUserName = mutableRequest.getHeader(HeaderConstants.ADMIN_USER_NAME);
                if (StringUtil.isNotBlank(adminUserName)) {
                    adminUserName = URLDecoder.decode(adminUserName, "UTF-8");
                    traceLog.setAdminUserName(adminUserName);
                }
                long latency = System.currentTimeMillis() - startTime;
                traceLog.setRt(latency);
                traceLog.setStatus(status);
                LOGGER.info(JSON.toJSONString(traceLog));
            } catch (Exception e) {
                LOGGER2.error("trace log filter finally fail,", e);
            }

            MDC.clear();
        }
    }

    private String buildTraceId() {
        String appId = properties.getId();
        return String.format("%s-%d%d", appId, Math.abs(Objects.hashCode(StringUtil.getUuid())), ThreadLocalRandom.current().nextInt(1000));
    }

    @Data
    private static class HttpTraceLog {
        /**
         * 访问时间
         */
        @JSONField(ordinal = 30)
        private String reqTime;
        /**
         * 耗时
         */
        @JSONField(ordinal = 40)
        private Long rt;
        /**
         * 路径
         */
        @JSONField(ordinal = 50)
        private String path;
        /**
         * 请求方法
         */
        @JSONField(ordinal = 60)
        private String method;
        /**
         * 状态码
         */
        @JSONField(ordinal = 70)
        private Integer status;
        /**
         * 请求参数
         */
        @JSONField(ordinal = 80)
        private Object param;
        /**
         * traceId
         */
        @JSONField(ordinal = 20)
        private String traceId;
        /**
         * 调用方
         */
        @JSONField(ordinal = 10)
        private String parentApp;

        /**
         * adminUserId
         */
        @JSONField(ordinal = 8)
        private String adminUserName;

        /**
         * adminUserId
         */
        @JSONField(ordinal = 6)
        private String adminUserId;

    }
}