package com.seventeen.starter.xxl;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author seventeen
 */
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private XxlAdminProperties admin;

    private String accessToken;

    private XxlExecutorProperties executor;

    public XxlAdminProperties getAdmin() {
        return admin;
    }

    public void setAdmin(XxlAdminProperties admin) {
        this.admin = admin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public XxlExecutorProperties getExecutor() {
        return executor;
    }

    public void setExecutor(XxlExecutorProperties executor) {
        this.executor = executor;
    }
}
