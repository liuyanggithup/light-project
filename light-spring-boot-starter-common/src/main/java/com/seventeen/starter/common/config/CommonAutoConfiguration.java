package com.seventeen.starter.common.config;

import com.seventeen.starter.common.advice.CommonExceptionHandler;
import com.seventeen.starter.common.filter.FeignHttpContextTransmitInterceptor;
import com.seventeen.starter.common.filter.FeignRequestInterceptor;
import com.seventeen.starter.common.hystrix.CommonHystrixConcurrencyStrategy;
import com.seventeen.starter.common.hystrix.HystrixCallableWrapper;
import com.seventeen.starter.common.hystrix.MdcAwareCallableWrapper;
import com.seventeen.starter.common.hystrix.RequestAttributeAwareCallableWrapper;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : seventeen
 */
@Configuration
@AutoConfigureOrder(2)
@EnableConfigurationProperties(AppProperties.class)
@Import({FilterAutoConfiguration.class, FeignRequestInterceptor.class, CommonExceptionHandler.class})
public class CommonAutoConfiguration {

    @Autowired(required = false)
    private List<HystrixCallableWrapper> wrappers = new ArrayList<>();

    @Bean
    @ConditionalOnBean(FeignHttpContextTransmitInterceptor.class)
    public HystrixCallableWrapper requestAttributeAwareCallableWrapper() {
        return new RequestAttributeAwareCallableWrapper();
    }

    @Bean
    public HystrixCallableWrapper mdcAwareCallableWrapper(){
        return new MdcAwareCallableWrapper();
    }

    @Bean
    @ConditionalOnClass(HystrixConcurrencyStrategy.class)
    public CommonHystrixConcurrencyStrategy commonHystrixConcurrencyStrategy() {
        return  new CommonHystrixConcurrencyStrategy(wrappers);
    }


}
