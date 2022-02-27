package com.seventeen.lightregistry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 属性定位器
 *
 * @author seventeen
 */
@Component
public class ConfigLocator {

    @Value("${eureka.instance.hostname}")
    private String hostname;

    public static ConfigLocator getConfigLocator() {
        return SpringContextHolder.getBean(ConfigLocator.class);
    }

    public String getHostname() {
        return hostname;
    }
}
