package com.seventeen.starter.common.filter;

import com.seventeen.starter.common.config.AppProperties;
import com.seventeen.starter.common.constants.HeaderConstants;
import com.seventeen.starter.common.constants.MdcConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author seventeen
 */
@Configuration
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    @Resource
    private AppProperties appProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String appId = appProperties.getId();
        requestTemplate.header(HeaderConstants.PARENT_APP, appId);
        String traceId = MDC.get(MdcConstants.TRACE_ID);
        requestTemplate.header(HeaderConstants.TRACE_ID_NAME,traceId);
    }
}