package com.seventeen.common.thread;

import org.slf4j.MDC;

import java.util.Map;

/**
 * @author seventeen
 */
public class MdcAspect {

    public static Map<String, String> beforeExecution(Map<String, String> newValue) {
        Map<String, String> previous = MDC.getCopyOfContextMap();
        if (newValue == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(newValue);
        }
        return previous;
    }

    public static void afterExecution(Map<String, String> oldValue) {
        if (oldValue == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(oldValue);
        }

    }
    
}
