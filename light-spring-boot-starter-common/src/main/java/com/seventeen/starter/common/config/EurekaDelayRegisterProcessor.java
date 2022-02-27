package com.seventeen.starter.common.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seventeen
 */
public class EurekaDelayRegisterProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> source = new HashMap<>(16);
        source.put("eureka.instance.initial-status", "DOWN");
        MapPropertySource propertiesPropertySource = new MapPropertySource("EurekaDelayRegisterConfig", source);
        environment.getPropertySources().addLast(propertiesPropertySource);
    }

}