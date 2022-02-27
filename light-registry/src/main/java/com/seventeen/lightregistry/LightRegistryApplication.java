package com.seventeen.lightregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 主程序启动入口
 * @author seventeen
 */
@SpringBootApplication
@EnableEurekaServer
public class LightRegistryApplication {

	private final static Logger log = LoggerFactory.getLogger(LightRegistryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LightRegistryApplication.class, args);
		log.info("\n************************************" +
				"\n************************************" +
				"\n************************************" +
				"\n\n注册中心启动成功！节点位置: {}", ConfigLocator.getConfigLocator().getHostname()+
				"\n\n************************************" +
				"\n************************************");
	}
}
