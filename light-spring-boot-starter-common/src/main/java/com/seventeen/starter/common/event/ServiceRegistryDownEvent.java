package com.seventeen.starter.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * 注册服务下线事件
 *
 * @author seventeen
 */
public class ServiceRegistryDownEvent extends ApplicationEvent {


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ServiceRegistryDownEvent(Object source) {
        super(source);
    }
}
