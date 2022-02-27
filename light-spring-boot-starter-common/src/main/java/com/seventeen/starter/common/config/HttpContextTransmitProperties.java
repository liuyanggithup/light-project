package com.seventeen.starter.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Http 上下文传递配置
 * @author  seventeen
 */
@ConfigurationProperties(prefix = "http.context.transmit")
public class HttpContextTransmitProperties {
    private List<String> filter;

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }
}
