package com.seventeen.starter.common.hystrix;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * request 传递
 *
 * @author : seventeen
 */
public class RequestCallable<T> implements Callable<T> {
    private final Callable<T> target;
    private final RequestAttributes requestAttributes;

    public RequestCallable(Callable<T> target, RequestAttributes requestAttributes) {
        this.target = target;
        this.requestAttributes = requestAttributes;
    }

    @Override
    public T call() throws Exception {
        try {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            return target.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }
}
