package com.ken.kenuserservice.usercore.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.GsonBuilder;
import com.ken.common.facade.kencommonfacade.common.BaseController;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUser;
import com.ken.kenuserservice.annotation.Function;
import com.ken.kenuserservice.config.RedisConfig;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.service.ICoreUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@RestController
@RequestMapping("/usercore/core-user")
@Slf4j
public class CoreUserController extends BaseController {

    @Autowired
    private ICoreUserService iCoreUserService;

    @RequestMapping(value = "/list")
    @Function("coreuser.query")
    public ResultDto<List<CoreUser>> getCoreUserlist(CoreUser coreUser) {

        coreUser.setCode(StringUtils.isEmpty(coreUser.getCode()) ? null:coreUser.getCode());
        QueryWrapper<CoreUser> queryWrapper = new QueryWrapper<>(coreUser);
        IPage<CoreUser> coreUserIPage = new Page<>(coreUser.getPageIndex(), coreUser.getPageSize());
        coreUserIPage = iCoreUserService.page(coreUserIPage,queryWrapper);

        ResultDto<List<CoreUser>> resultDto  =new ResultDto<>();
        resultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        resultDto.setData(coreUserIPage.getRecords());
        resultDto.setTotal(coreUserIPage.getTotal());
        log.info("查询LIST:"+ GsonHttpMessageConverter.DEFAULT_CHARSET.toString());
        return resultDto;
    }

    @RequestMapping(value = "/saveData")
    @Function("coreuser.save")
    public ResultDto<?> saveCoreUser(CoreUser CoreUser) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            iCoreUserService.save(CoreUser);
        } catch (Exception e) {
            log.error(e.getMessage());
            ResultDto.setCode("503");
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @RequestMapping(value = "/login")
    public ResultDto<Map<String,Object>> login(HttpServletRequest request, String username,String password) {
        ResultDto<Map<String,Object>> ResultDto =new ResultDto<>();
        try {
            CoreUser coreUser = iCoreUserService.login(username,password);
            request.getSession(true).setAttribute("user", coreUser);
            Map<String, Object> map = new HashMap<>();
            map.put("introduction", coreUser.getName());
            map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            map.put("name", coreUser.getName());
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            ResultDto.setData(map);
        } catch (BusinessException e) {
            ResultDto.setCode(e.getCode());
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @RequestMapping(value = "/info")
    @Function
    public ResultDto<Map<String,Object>> info(HttpServletRequest request) {
        ResultDto<Map<String,Object>> ResultDto =new ResultDto<>();
        try {
            CoreUser coreUser = (CoreUser) request.getSession().getAttribute("user");
            if (coreUser == null) {
                coreUser = iCoreUserService.login("admin","123456");
            }
            Map<String, Object> map = iCoreUserService.info(coreUser);
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            ResultDto.setData(map);
        } catch (BusinessException e) {
            ResultDto.setCode(e.getCode());
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @RequestMapping(value = "/logout")
    public ResultDto<Map<String,Object>> out(String token) {
        ResultDto<Map<String,Object>> ResultDto =new ResultDto<>();
        try {
            redisTemplate.delete(token);
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }
}
