package com.seventeen.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.MDC;

import java.util.concurrent.*;

/**
 * 支持MDC参数传递的线程池
 *
 * @author seventeen
 */
public class MdcThreadPoolExecutor extends ThreadPoolExecutor {

    private MdcThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    public static ThreadPoolExecutor newThreadPoll(int corePoolSize,
                                                   int maximumPoolSize,
                                                   long keepAliveTime,
                                                   TimeUnit unit,
                                                   BlockingQueue<Runnable> workQueue,
                                                   ThreadFactory threadFactory,
                                                   RejectedExecutionHandler handler) {

        return new MdcThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue, threadFactory, handler);
    }


    public static ThreadPoolExecutor newThreadPoll(int corePoolSize,
                                                   int maximumPoolSize,
                                                   int blockQueueNum,
                                                   String threadPoolName) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadPoolName + "-%d").build();
        return new MdcThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(blockQueueNum), threadFactory, new AbortPolicy());
    }


    public static ThreadPoolExecutor newThreadPoll(int corePoolSize,
                                                   int maximumPoolSize,
                                                   String threadPoolName) {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(threadPoolName + "-%d").build();
        return new MdcThreadPoolExecutor(corePoolSize, maximumPoolSize, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), threadFactory, new AbortPolicy());
    }

    @Override
    public void execute(Runnable command) {
        super.execute(WrapperBasic.wrap(command, MDC.getCopyOfContextMap()));
    }
}