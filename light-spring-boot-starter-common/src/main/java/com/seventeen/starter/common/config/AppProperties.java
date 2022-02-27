package com.seventeen.starter.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author seventeen
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
