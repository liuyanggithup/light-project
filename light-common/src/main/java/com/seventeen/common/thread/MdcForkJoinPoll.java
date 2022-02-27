package com.seventeen.common.thread;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 支持MDC参数传递的ForkJoin线程池类
 *
 * @author seventeen
 */
public class MdcForkJoinPoll extends ForkJoinPool {


    private MdcForkJoinPoll() {
        super();
    }

    private MdcForkJoinPoll(int parallelism) {
        super(parallelism);
    }

    public static MdcForkJoinPoll getInstance(int parallelism) {
        return new MdcForkJoinPoll(parallelism);
    }

    @Override
    public <T> ForkJoinTask<T> submit(Callable<T> task) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> ForkJoinTask<T> submit(Runnable task, T result) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public ForkJoinTask<?> submit(Runnable task) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()));
    }

    private <T> Callable<T> wrap(Callable<T> task, Map<String, String> newContext) {
        return () -> {
            Map<String, String> oldContext = MdcAspect.beforeExecution(newContext);
            try {
                return task.call();
            } finally {
                MdcAspect.afterExecution(oldContext);
            }
        };
    }

    private Runnable wrap(Runnable task, Map<String, String> newContext) {
        return () -> {
            Map<String, String> oldContext = MdcAspect.beforeExecution(newContext);
            try {
                task.run();
            } finally {
                MdcAspect.afterExecution(oldContext);
            }
        };
    }

    @Override
    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task) {
        return super.submit(wrap(task, MDC.getCopyOfContextMap()));
    }

    private <T> ForkJoinTask<T> wrap(ForkJoinTask<T> task, Map<String, String> newContext) {
        return new ForkJoinTask<T>() {
            private final AtomicReference<T> override = new AtomicReference<>();

            @Override
            public T getRawResult() {
                T result = override.get();
                if (result != null) {
                    return result;
                }
                return task.getRawResult();
            }

            @Override
            protected void setRawResult(T value) {
                override.set(value);
            }

            @Override
            protected boolean exec() {
                Map<String, String> oldContext = MdcAspect.beforeExecution(newContext);
                try {
                    task.invoke();
                    return true;
                } finally {
                    MdcAspect.afterExecution(oldContext);
                }
            }
        };
    }


    @Override
    public void execute(Runnable task) {
        super.execute(wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public void execute(ForkJoinTask<?> task) {
        super.execute(wrap(task, MDC.getCopyOfContextMap()));
    }

}
