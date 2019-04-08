package com.ken.kenuserservice.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Description : 请求栈拦截器
 *
 * @author kenzhao
 * @date 2019/4/8 15:51
 */
public class HttpServletStackInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpServletStack.INSTANCE.putStartTime(LocalDateTime.now());
        HttpServletStack.INSTANCE.putRequestHeader(request);
        HttpServletStack.INSTANCE.putRequest(request);
        HttpServletStack.INSTANCE.putResponse(response);
        if (handler != null && (handler instanceof HandlerMethod)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> controllerType = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            HttpServletStack.INSTANCE.putControllerType(controllerType);
            HttpServletStack.INSTANCE.putMethod(method);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpServletStack.INSTANCE.removeStartTime();
        HttpServletStack.INSTANCE.removeRequestHeader();
        HttpServletStack.INSTANCE.removeRequest();
        HttpServletStack.INSTANCE.removeResponse();
        HttpServletStack.INSTANCE.removeRequestBody();
        HttpServletStack.INSTANCE.removeResponseBody();
        HttpServletStack.INSTANCE.removeControllerType();
        HttpServletStack.INSTANCE.removeMethod();
    }
}
