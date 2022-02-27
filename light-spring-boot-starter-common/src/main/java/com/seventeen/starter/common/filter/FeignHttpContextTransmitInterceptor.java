package com.seventeen.starter.common.filter;

import com.seventeen.starter.common.config.HttpContextTransmitProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @author  seventeen
 */
@Configuration
@Slf4j
public class FeignHttpContextTransmitInterceptor implements RequestInterceptor {

    @Autowired(required = false)
    private HttpContextTransmitProperties properties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (Objects.isNull(properties)){
            return;
        }

        List<String> filter = properties.getFilter();
        if (Objects.isNull(filter) || filter.isEmpty()){
            return;
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (filter.contains(name)){
                        String values = request.getHeader(name);
                        requestTemplate.header(name, values);
                    }
                }
            }
        }
    }
}
