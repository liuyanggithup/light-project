package com.seventeen.common.thread;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author seventeen
 */
public class WrapperBasic {

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (copyOfContextMap == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(copyOfContextMap);
                }
            }
        };
    }

    public static <T> Callable<T> wrap(Callable<T> task, final Map<String, String> context) {
        return () -> {
            Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return task.call();
            } finally {
                if (copyOfContextMap == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(copyOfContextMap);
                }
            }
        };
    }


}
