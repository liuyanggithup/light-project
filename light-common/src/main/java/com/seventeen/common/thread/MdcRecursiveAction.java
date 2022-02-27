package com.seventeen.common.thread;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.RecursiveAction;

/**
 * @author seventeen
 */
public abstract class MdcRecursiveAction extends RecursiveAction {

    private final Map<String, String> newContext;

    protected MdcRecursiveAction() {
        this.newContext = MDC.getCopyOfContextMap();
    }

    /**
     * mdc compute
     *
     * @return
     */
    protected abstract void mdcCompute();

    /**
     * compute
     *
     * @return
     */
    @Override
    public void compute() {
        Map<String, String> oldContext = MdcAspect.beforeExecution(newContext);
        try {
            mdcCompute();
        } finally {
            MdcAspect.afterExecution(oldContext);
        }
    }

}
