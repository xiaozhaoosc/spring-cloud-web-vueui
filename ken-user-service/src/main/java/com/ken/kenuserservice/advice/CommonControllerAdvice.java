package com.ken.kenuserservice.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 16:16
 */
@ControllerAdvice
@Slf4j
public class CommonControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDto Exception(Exception exception) {
        log.error("Exception:::", exception);
        return ResultDto.format("503",exception.getMessage());
    }
}
