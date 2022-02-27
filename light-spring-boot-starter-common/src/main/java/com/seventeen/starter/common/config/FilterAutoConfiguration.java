package com.seventeen.starter.common.config;

import com.seventeen.starter.common.filter.HttpTraceLogFilter;
import com.seventeen.starter.common.filter.ServiceRegistryFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : seventeen
 */
@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class FilterAutoConfiguration implements ApplicationEventPublisherAware {


    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    @ConditionalOnClass(HttpTraceLogFilter.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public FilterRegistrationBean<HttpTraceLogFilter> httpTraceLogFilterRegistration(AppProperties appProperties) {
        FilterRegistrationBean<HttpTraceLogFilter> registration = new FilterRegistrationBean<>();
        HttpTraceLogFilter httpTraceLogFilter = new HttpTraceLogFilter();
        httpTraceLogFilter.setProperties(appProperties);
        registration.setFilter(httpTraceLogFilter);
        registration.setOrder(1);
        return registration;
    }


    @Bean
    @ConditionalOnClass(ServiceRegistryFilter.class)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public FilterRegistrationBean<ServiceRegistryFilter> serviceRegistryFilterRegistration() {
        FilterRegistrationBean<ServiceRegistryFilter> registration = new FilterRegistrationBean<>();
        ServiceRegistryFilter serviceRegistryFilter = new ServiceRegistryFilter();
        serviceRegistryFilter.setApplicationEventPublisher(applicationEventPublisher);
        registration.setFilter(serviceRegistryFilter);
        registration.addUrlPatterns("/actuator/service-registry");
        registration.setOrder(1);
        return registration;

    }

}
