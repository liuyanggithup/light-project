package com.seventeen.common.thread;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author seventeen
 */
public class MdcThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private MdcThreadPoolTaskExecutor() {
        super();
    }

    public static MdcThreadPoolTaskExecutor getInstance() {
        return new MdcThreadPoolTaskExecutor();
    }

    @Override
    public void execute(@NonNull Runnable task) {
        super.execute(WrapperBasic.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    @NonNull
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return super.submit(WrapperBasic.wrap(task, MDC.getCopyOfContextMap()));
    }


}
