package com.seventeen.starter.common.advice;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.seventeen.common.enums.CodeEnum;
import com.seventeen.common.exception.ErrorException;
import com.seventeen.common.exception.WarnException;
import com.seventeen.common.response.ResponseData;
import com.seventeen.common.utils.RequestUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

/**
 * 公共的全局异常处理器
 *
 * @author seventeen
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CommonExceptionHandler {


    public String getRequestInfo(HttpServletRequest request) {
        RequestMsg requestMsg = new RequestMsg();
        Map<String, String[]> param = RequestUtil.getParam(request);
        String url = RequestUtil.getUrl(request);
        requestMsg.setParams(param);
        requestMsg.setUrl(url);
        return JSON.toJSONString(requestMsg);
    }

    /**
     * 参数校验异常
     *
     * @param e exception
     * @return ResponseData
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(HttpServletRequest request, ConstraintViolationException e) {
        String requestInfo = getRequestInfo(request);
        log.warn("{} 参数校验异常 {}", requestInfo, e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            return ResponseData.fail(message);
        }
        return ResponseData.fail(e.getLocalizedMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("缺少请求参数:{}", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return ResponseData.fail(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误:{}", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return ResponseData.fail(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return ResponseData.fail(CodeEnum.PARAM_INVALIDATE.getCode(), "参数不正确");
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(BindException e) {
        log.warn("参数绑定失败:{}", e.getMessage());
        return ResponseData.fail(CodeEnum.PARAM_INVALIDATE.getCode(), "参数不正确");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseData<Object> handleError(NoHandlerFoundException e) {
        log.warn("404没找到请求:{}", e.getMessage());
        return ResponseData.fail(CodeEnum.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> handleError(HttpServletRequest request, HttpMessageNotReadableException e) {
        log.error("消息不能读取:{} request:{}", e.getMessage(), getRequestInfo(request), e);
        return ResponseData.fail(CodeEnum.MESSAGE_NOT_READ);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseData<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.warn("不支持当前请求方法:{}", e.getMessage());
        return ResponseData.fail(CodeEnum.METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<Object> headerParamException(HttpServletRequest request, MissingRequestHeaderException e) {
        log.warn("occur an exception url:{}, 缺少header参数 {}", request.getRequestURL(), e.getHeaderName(), e);
        return ResponseData.fail("缺少header参数");
    }

    @ExceptionHandler(value = ErrorException.class)
    public ResponseData<Object> errorException(ErrorException be) {
        log.error("occur an errorException: ", be);
        return ResponseData.fail(be.getCode(), be.getMsg());
    }

    @ExceptionHandler(value = WarnException.class)
    public ResponseData<Object> warnException(WarnException be) {
        log.warn("occur an warnException: ", be);
        return ResponseData.fail(be.getCode(), be.getMsg());
    }

    @ExceptionHandler(value = ClientAbortException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Object> clientAbortException(ClientAbortException ce) {
        log.warn("occur an clientAbortException: ", ce);
        return ResponseData.fail("断开的连接:Broken pipe");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<Object> handleError(HttpServletRequest request, Throwable e) {
        log.error("{} occur an error: ", getRequestInfo(request), e);
        return ResponseData.fail(CodeEnum.ERROR);
    }

    ResponseData<Object> handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return ResponseData.fail(CodeEnum.PARAM_INVALIDATE.getCode(), message);
    }

    @Data
    @JsonPropertyOrder({"url", "params"})
    public static class RequestMsg {
        private String url;

        private  Map<String, String[]> params;

    }
}