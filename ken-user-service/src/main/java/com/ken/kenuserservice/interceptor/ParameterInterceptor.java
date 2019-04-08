package com.ken.kenuserservice.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *请求参数拦截器
 * @author kenzhao
 * @date 2019/4/8 15:27
 */
@Slf4j
public class ParameterInterceptor extends HandlerInterceptorAdapter{
    public ParameterInterceptor() {
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(HttpServletStack.INSTANCE.getRequestInfoJson());
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

    /**
     * 是ajax请求
     * @return
     */
    public final boolean isAjaxRequest(HttpServletRequest request) {
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
}
