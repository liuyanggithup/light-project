package com.seventeen.starter.common.hystrix;

import java.util.concurrent.Callable;

/**
 * @author seventeen
 */
public interface HystrixCallableWrapper {
 
    /**
     * 包装Callable实例
     *
     * @param callable 待包装实例
     * @param <T>      返回类型
     * @return 包装后的实例
     */
    <T> Callable<T> wrap(Callable<T> callable);
 
}