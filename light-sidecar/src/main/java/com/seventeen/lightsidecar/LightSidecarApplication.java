package com.seventeen.lightsidecar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;
import org.springframework.cloud.netflix.sidecar.SidecarProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author seventeen
 */
@EnableDiscoveryClient
@Configuration
@EnableSidecar
@SpringBootApplication
public class LightSidecarApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightSidecarApplication.class, args);
    }

    @Primary
    @Bean
    public SidecarProperties sidecarProperties(InetUtils inetUtils, ServerProperties serverProperties) {
        SidecarProperties sidecarProperties = new SidecarProperties();
        String hostAddress = inetUtils.findFirstNonLoopbackAddress().getHostAddress();
        sidecarProperties.setIpAddress(hostAddress);
        sidecarProperties.setHostname(hostAddress);
        String healthUrl = "http://" + hostAddress + ":" + serverProperties.getPort() + "/actuator/health";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(healthUrl).build();
        sidecarProperties.setHealthUri(uriComponents.toUri());
        return sidecarProperties;
    }

}
