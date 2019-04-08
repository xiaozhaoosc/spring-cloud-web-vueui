package com.ken.kenuserservice.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    public static final String[] ALLOW_HEADS = {"X-Auth-Token", "content-type", "content-disposition"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String origin = request.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.isNotBlank(origin)) {
            response.addHeader("Access-Control-Allow-Origin",origin);
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.addHeader("Access-Control-Max-Age","100");
            response.addHeader("Access-Control-Allow-Headers", StringUtils.join(ALLOW_HEADS, ","));
            response.addHeader("Access-Control-Expose-Headers", StringUtils.join(ALLOW_HEADS, ","));
            response.addHeader("Access-Control-Allow-Credentials","true");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
