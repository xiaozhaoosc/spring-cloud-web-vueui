package com.ken.kenuserservice.usercore.controller;

import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.kenuserservice.annotation.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/var")
@Slf4j
public class SystemVarController {

    @RequestMapping("/{type}")
    @Function
    public ResultDto type(@PathVariable("type") String type) {
        log.info(type);
         return ResultDto.format(ResultCodeEnum.NO_RESULT.getCode(), ResultCodeEnum.NO_RESULT.getDescription());
    }
}
