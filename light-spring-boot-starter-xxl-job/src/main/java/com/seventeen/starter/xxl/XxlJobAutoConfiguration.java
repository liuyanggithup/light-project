package com.seventeen.starter.xxl;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;

/**
 * @author seventeen
 */
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnClass({XxlJobSpringExecutor.class})
@ConditionalOnProperty(prefix = "xxl.job", value = {"admin.addresses", "executor.appname"})
@Import({BeanScanConfig.class})
public class XxlJobAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(XxlJobAutoConfiguration.class);


    @Bean(initMethod = "start", destroyMethod = "destroy")
    @ConditionalOnMissingBean(XxlJobSpringExecutor.class)
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties) {

        Assert.notNull(xxlJobProperties.getAdmin(), "[xxl.job.admin.] must not be null");
        Assert.hasText(xxlJobProperties.getAdmin().getAddresses(), "[xxl.job.admin.addresses] must not be null");
        Assert.notNull(xxlJobProperties.getExecutor(), "[xxl.job.executor.] must not be null");
        Assert.hasText(xxlJobProperties.getExecutor().getAppname(), "[xxl.job.executor.appname] must not be null");
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        xxlJobSpringExecutor.setAppName(xxlJobProperties.getExecutor().getAppname());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogpath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogretentiondays());

        return xxlJobSpringExecutor;
    }


}
