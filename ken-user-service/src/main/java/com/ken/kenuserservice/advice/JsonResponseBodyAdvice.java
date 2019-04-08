package com.ken.kenuserservice.advice;

import com.alibaba.druid.support.json.JSONUtils;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.kenuserservice.interceptor.HttpServletStack;
import com.ken.kenuserservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 16:14
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
@Slf4j
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<ResultDto> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getMethod().getReturnType().isAssignableFrom(ResultDto.class);
    }

    @Override
    public ResultDto beforeBodyWrite(ResultDto body, MethodParameter returnType, MediaType selectedContentType,
                                     Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                     ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletStack.INSTANCE.putResponseBody(body);
        if(log.isDebugEnabled()) {
            log.debug("Controller的{} method的返回结果为：{}", returnType.getMethod().getName(), JsonUtils.toJSONString(body));
        }
        return body;
    }
}
