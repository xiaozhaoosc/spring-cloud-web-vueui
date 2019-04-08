package com.ken.kenuserservice.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.ken.kenuserservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *请求栈
 * @author kenzhao
 * @date 2019/4/8 15:52
 */
@Slf4j
public enum HttpServletStack {

    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 请求线程锁
     */
    private final static ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 响应线程锁
     */
    private final static ThreadLocal<HttpServletResponse> RESPONSE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 指定的APP请求头
     */
    private final static ThreadLocal<AppRequestHeader> REQUEST_HEADER = new ThreadLocal<>();

    /**
     * 请求参数
     */
    private final static ThreadLocal<Object> REQUEST_BODY = new ThreadLocal<Object>();

    /**
     * 响应json
     */
    private final static ThreadLocal<Object> RESPONSE_BODY = new ThreadLocal<Object>();

    /**
     * 处理器
     */
    private final static ThreadLocal<Class<?>> CONTROLLER_TYPE = new ThreadLocal<Class<?>>();

    /**
     * 处理方法
     */
    private final static ThreadLocal<Method> METHOD = new ThreadLocal<Method>();

    /**
     * 执行时间
     */
    private final static ThreadLocal<LocalDateTime> START_TIME = new ThreadLocal<>();

    /**
     * 新增请求
     * @param request
     */
    public final void putRequest(HttpServletRequest request) {
        REQUEST_THREAD_LOCAL.set(request);
    }

    /**
     * 新增响应
     * @param response
     */
    public final void putResponse(HttpServletResponse response) {
        RESPONSE_THREAD_LOCAL.set(response);
    }

    /**
     * 移除请求
     */
    public final void removeRequest() {
        REQUEST_THREAD_LOCAL.remove();
    }

    /**
     * 移除响应
     */
    public final void removeResponse() {
        RESPONSE_THREAD_LOCAL.remove();
    }

    /**
     * 获取当前线程里面的APP请求头
     * @return
     */
    public final AppRequestHeader getAppRequestHeader(){
        return REQUEST_HEADER.get();
    }

    /**
     * 获取当前线程请求
     * @return
     */
    public final HttpServletRequest getRequest() {
        return REQUEST_THREAD_LOCAL.get();
    }

    /**
     * 获取当前线程响应
     * @return
     */
    public final HttpServletResponse getResponse() {
        return RESPONSE_THREAD_LOCAL.get();
    }

    /**
     * 获取当前线程会话
     * @param create 是否创建新的会话
     * @return
     */
    public final HttpSession getSession(boolean create) {
        return this.getRequest().getSession(create);
    }

    /**
     * 获取当前线程会话
     * @return
     */
    public final HttpSession getSession() {
        return this.getSession(true);
    }

    /**
     * 往会话中添加属性
     * @param value 属性值
     */
    public final void putSessionAttr(String name, Object value) {
        HttpSession session = this.getSession();
        session.setAttribute(name, value);
    }

    /**
     * 移除会话中的属性
     */
    public final void removeSessionAttr(String name) {
        HttpSession session = this.getSession();
        session.removeAttribute(name);
    }

    /**
     * 获取会话中的属性
     * @param <T> 属性类型
     * @return 属性值
     */
    public final <T> T getSessionAttr(String name) {
        HttpSession session = this.getSession();
        return (T) session.getAttribute(name);
    }

    /**
     * 获取APP请求头
     * @return
     */
    public final AppRequestHeader getRequestHeader() {
        return REQUEST_HEADER.get();
    }

    /**
     * 新增APP请求头
     * @param request
     */
    public final void putRequestHeader(HttpServletRequest request) {
        REQUEST_HEADER.set(RequestUtils.parseRequestHeader(request));
    }

    /**
     * 移除请求APP
     */
    public final void removeRequestHeader() {
        REQUEST_HEADER.remove();
    }

    /**
     * 获取请求参数
     * @param <T>
     * @return
     */
    public final <T> T getRequestBody() {
        return (T) REQUEST_BODY.get();
    }

    /**
     * 设置请求参数
     * @param requestBody
     */
    public final void putRequestBody(Object requestBody) {
        REQUEST_BODY.set(requestBody);
    }


    /**
     * 移除执行时间
     */
    public final void removeStartTime() {
        START_TIME.remove();
    }

    /**
     * 获取执行时间
     * @param <T>
     * @return
     */
    public final <T> T getStartTime() {
        return (T) START_TIME.get();
    }

    /**
     * 设置执行时间
     * @param localDateTime
     */
    public final void putStartTime(LocalDateTime localDateTime) {
        START_TIME.set(localDateTime);
    }

    /**
     * 移除请求参数
     */
    public final void removeRequestBody() {
        REQUEST_BODY.remove();
    }

    /**
     * 获取响应json
     * @param <T>
     * @return
     */
    public final <T> T getResponseBody() {
        return (T) RESPONSE_BODY.get();
    }

    /**
     * 设置响应json
     * @param responseBody
     */
    public final void putResponseBody(Object responseBody) {
        RESPONSE_BODY.set(responseBody);
    }

    /**
     * 移除响应json
     */
    public final void removeResponseBody() {
        RESPONSE_BODY.remove();
    }

    public final Class<?> getControllerType() {
        return CONTROLLER_TYPE.get();
    }

    public final void putControllerType(Class<?> controllerType) {
        CONTROLLER_TYPE.set(controllerType);
    }

    public final void removeControllerType() {
        CONTROLLER_TYPE.remove();
    }

    public final Method getMethod() {
        return METHOD.get();
    }

    public final void putMethod(Method method) {
        METHOD.set(method);
    }

    public final void removeMethod() {
        METHOD.remove();
    }

    /**
     * 是ajax请求
     * @return
     */
    public final boolean isAjaxRequest() {
        HttpServletRequest request = getRequest();
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
            return true;
        }
        String contentType = request.getContentType();
        if ("application/json".equalsIgnoreCase(contentType)) {
            return true;
        }
        return false;
    }

    /**
     * 获取请求信息
     * @return
     */
    public final Map<Object, Object> getRequestInfo() {
        HttpServletRequest request = getRequest();
        Map<Object, Object> info = new HashMap<Object, Object>();
        Map<Object, Object> heads = new HashMap<Object, Object>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value = request.getHeader(name);
            heads.put(name, value);
        }

        Map<Object, Object> method = new HashMap<Object, Object>();
        Method m = HttpServletStack.INSTANCE.getMethod();
        if (m != null) {
            method.put("name", m.getName());
            method.put("parameterTypes", m.getParameterTypes());
        }

        Map<Object, Object> mapping = new HashMap<Object, Object>();
        mapping.put("id", request.getRemoteAddr());
        mapping.put("controllerType", HttpServletStack.INSTANCE.getControllerType());
        mapping.put("method", method);

        HttpSession httpSession = request.getSession(false);
        info.put("session", getSessionInfo(httpSession));
        info.put("mapping", mapping);
        info.put("isAjax", isAjaxRequest());
        info.put("api", request.getRequestURI());
        info.put("method", request.getMethod());
        info.put("authType", request.getAuthType());
        info.put("heads", heads);
        info.put("params", request.getParameterMap());
        info.put("requestBody", getRequestBody());
        info.put("responseBody", getResponseBody());
        return info;
    }

    public final void getRequestInfo(StringBuilder sb) {
        HttpServletRequest request = getRequest();
        Map<Object, Object> info = new HashMap<>();
        Map<Object, Object> heads = new HashMap<>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String value = request.getHeader(name);
            heads.put(name, value);
        }
        info.put("ip", RequestUtils.getRemoteAddr(request));
        info.put("session", getSessionInfo(request.getSession(false)));
        info.put("isAjax", isAjaxRequest());
        info.put("api", request.getRequestURI());
        info.put("method", request.getMethod());
        info.put("authType", request.getAuthType());
        info.put("heads", heads);

        sb.append("heads:").append(JsonUtils.toJSONString(info));
        sb.append("\n");
        sb.append("params:").append(JsonUtils.toJSONString(request.getParameterMap()));
        sb.append("\n");
        sb.append("requestBody:").append(JsonUtils.toJSONString(getRequestBody()));
        sb.append("\n");
        sb.append("responseBody:").append(JsonUtils.toJSONString(getResponseBody()));
    }

    private Map<Object, Object> getSessionInfo(HttpSession httpSession){
        if (httpSession == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<Object, Object> session = new HashMap<>();
        Map<Object, Object> sessionAttr = new HashMap<>();
        session.put("attributes", sessionAttr);
        session.put("id", httpSession.getId());
        session.put("createTime", dateFormat.format(new Date(httpSession.getCreationTime())));
        session.put("lastAccessedTime", dateFormat.format(new Date(httpSession.getLastAccessedTime())));
        session.put("maxInactiveInterval", httpSession.getMaxInactiveInterval());
        Enumeration<String> sessionAttrNames = httpSession.getAttributeNames();
        while (sessionAttrNames.hasMoreElements()) {
            String name = sessionAttrNames.nextElement();
            Object value = httpSession.getAttribute(name);
            sessionAttr.put(name, value);
        }
        return session;
    }

    public final String getRequestInfoJson(Map<Object, Object> info) {
        return JsonUtils.toJSONString(info);
    }

    public final String getRequestInfoJson() {
        Map<Object, Object> info = getRequestInfo();
        return getRequestInfoJson(info);
    }
}
