package com.seventeen.starter.common.hystrix;

import com.google.common.collect.Maps;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * MDC传递
 *
 * @author : seventeen
 */
public class MdcAwareCallable<T> implements Callable<T> {
    private final Callable<T> delegate;

    private final Map<String, String> contextMap;

    public MdcAwareCallable(Callable<T> callable, Map<String, String> contextMap) {
        this.delegate = callable;
        this.contextMap = contextMap != null ? contextMap : Maps.newHashMap();
    }

    @Override
    public T call() throws Exception {
        try {
            MDC.setContextMap(contextMap);
            return delegate.call();
        } finally {
            MDC.clear();
        }
    }
}