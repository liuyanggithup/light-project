package com.seventeen.starter.common.filter;

import com.seventeen.common.thread.MdcThreadPoolExecutor;
import com.seventeen.starter.common.event.ServiceRegistryDownEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拦截service-registry端点调用
 *
 * @author seventeen
 */
@Slf4j
public class ServiceRegistryFilter extends OncePerRequestFilter {


    private static final String STATUS_DOWN = "DOWN";
    private static final String STATUS_PARAM = "status";
    private static final ThreadPoolExecutor EXECUTOR = MdcThreadPoolExecutor.newThreadPoll(2, 2, "ServiceRegistryFilterPool");
    private ApplicationEventPublisher applicationEventPublisher;

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String status = request.getParameter(STATUS_PARAM);
        if (STATUS_DOWN.equalsIgnoreCase(status)) {
            EXECUTOR.execute(() -> applicationEventPublisher.publishEvent(new ServiceRegistryDownEvent(STATUS_DOWN)));
            log.info("触发service registry down事件");
        }

        filterChain.doFilter(request, response);
    }
}