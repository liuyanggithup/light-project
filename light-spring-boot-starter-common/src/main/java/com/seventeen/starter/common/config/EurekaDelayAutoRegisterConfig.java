package com.seventeen.starter.common.config;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author seventeen
 */
public class EurekaDelayAutoRegisterConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Resource
    private ConfigurableEnvironment environment;

    @Resource
    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Resource
    private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        Map<String, Object> source = new HashMap<>(16);
        source.put("eureka.instance.initial-status", "UP");
        MapPropertySource propertiesPropertySource = new MapPropertySource("EurekaDelayRegisterConfig", source);
        environment.getPropertySources().addLast(propertiesPropertySource);

        eurekaInstanceConfigBean.setInitialStatus(InstanceInfo.InstanceStatus.UP);
        eurekaAutoServiceRegistration.stop();
        eurekaAutoServiceRegistration.start();
    }


}
