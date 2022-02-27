package com.seventeen.starter.common.config;

import com.seventeen.starter.common.filter.FeignHttpContextTransmitInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author  seventeen
 */
@Configuration
@AutoConfigureOrder(1)
@ConditionalOnProperty(prefix = "http.context.transmit", name = "enable", havingValue = "true" )
@EnableConfigurationProperties({HttpContextTransmitProperties.class})
@Import(FeignHttpContextTransmitInterceptor.class)
public class HttpContextTransmitConfiguration {
}
