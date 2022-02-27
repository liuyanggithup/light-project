package com.seventeen.common.thread;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.RecursiveTask;

/**
 * @author seventeen
 */
public abstract class MdcRecursiveTask<V> extends RecursiveTask<V> {

    private final Map<String, String> newContext;

    protected MdcRecursiveTask() {
        this.newContext = MDC.getCopyOfContextMap();
    }

    /**
     * mdc compute
     *
     * @return
     */
    protected abstract V mdcCompute();

    /**
     * compute
     *
     * @return
     */
    @Override
    public V compute() {
        Map<String, String> oldContext = MdcAspect.beforeExecution(newContext);
        try {
            return mdcCompute();
        } finally {
            MdcAspect.afterExecution(oldContext);
        }
    }

    

}
