package com.ken.kenuserservice.config;

import com.ken.kenuserservice.interceptor.AuthenticationInterceptor;
import com.ken.kenuserservice.interceptor.HttpServletStackInterceptor;
import com.ken.kenuserservice.interceptor.ParameterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverterFactory(new UniversalEnumConverterFactory());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpServletStackInterceptor());
        registry.addInterceptor(new ParameterInterceptor());
        registry.addInterceptor(authenticationInterceptor);
    }

}
