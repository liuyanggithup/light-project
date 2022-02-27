package com.seventeen.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author seventeen
 */
public class RequestUtil {

    public static Map<String, String[]> getParam(HttpServletRequest request) {
        return request.getParameterMap();
    }

    public static String getUrl(HttpServletRequest request) {
        return request.getRequestURI();
    }

}
