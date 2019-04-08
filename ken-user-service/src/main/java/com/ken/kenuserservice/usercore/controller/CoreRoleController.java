package com.ken.kenuserservice.usercore.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ken.common.facade.kencommonfacade.common.BaseController;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRole;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUser;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUserRole;
import com.ken.kenuserservice.annotation.Function;
import com.ken.kenuserservice.usercore.service.ICoreRoleService;
import com.ken.kenuserservice.usercore.service.ICoreUserRoleService;
import com.ken.kenuserservice.usercore.service.ICoreUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@RestController
@RequestMapping("/usercore/core-role")
@Slf4j
public class CoreRoleController extends BaseController {

    @Autowired
    private ICoreRoleService iCoreRoleService;
    @Autowired
    private ICoreUserService iCoreUserService;
    @Autowired
    private ICoreUserRoleService iCoreUserRoleService;

    @RequestMapping(value = "/list")
    @Function("corerole.query")
    public ResultDto<List<CoreRole>> getCoreRolelist(CoreRole coreRole) {

//        coreRole.setName(StringUtils.isEmpty(coreRole.getName()) ? null:coreRole.getName());
//        IPage<CoreRole> coreRoleIPage = new Page<>(coreRole.getPageIndex(), coreRole.getPageSize());
//        coreRoleIPage = iCoreRoleService.page(coreRoleIPage,new QueryWrapper<>(coreRole));

        ResultDto<List<CoreRole>> resultDto  =new ResultDto<>();
        resultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
//        resultDto.setData(coreRoleIPage.getRecords());
//        resultDto.setTotal(coreRoleIPage.getTotal());
        log.info("查询LIST:"+ JSON.toJSONString(resultDto));
        return resultDto;
    }

    @RequestMapping(value = "/allList")
    public ResultDto<List<CoreRole>> getAllRolelist() {
        List<CoreRole> CoreRoles = iCoreRoleService.list(null);

        ResultDto<List<CoreRole>> ResultDto  =new ResultDto<>();
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        ResultDto.setData(CoreRoles);
        log.info("查询LIST:"+JSON.toJSONString(ResultDto));
        return ResultDto;
    }

    @RequestMapping(value = "/userAllList")
    public ResultDto<List<Integer>> getUserAllList(CoreUser coreUser) {
        ResultDto<List<Integer>> ResultDto  =new ResultDto<>();

        QueryWrapper<CoreUserRole> queryWrapper = new QueryWrapper<CoreUserRole>();
        queryWrapper.lambda().eq(CoreUserRole::getUserId,coreUser.getId());
        List<CoreUserRole> coreUserList = iCoreUserRoleService.list(queryWrapper);
        List<Integer> coreRoleIds = Lists.newArrayList();
        if (coreUserList != null && coreUserList.size() > 0) {
            coreUserList.forEach(coreUserRole -> {
                coreRoleIds.add(coreUserRole.getRoleId());
            });
        }
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        ResultDto.setData(coreRoleIds);
        log.info("查询LIST:"+JSON.toJSONString(ResultDto));
        return ResultDto;
    }

    @RequestMapping(value = "/saveData")
    @Function("corerole.save")
    public ResultDto<?> saveCoreRole(CoreRole coreRole) {
        ResultDto<?> ResultDto =new ResultDto<>();
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());


        if(coreRole.getId() != null){
            CoreRole queryCoreRole = new CoreRole();
            queryCoreRole.setId(coreRole.getId());
            CoreRole coreRoleServiceOne = iCoreRoleService.getOne(new QueryWrapper<>(queryCoreRole));
            if(coreRoleServiceOne != null){
                iCoreRoleService.updateById(coreRole);
            }
        }else{
            coreRole.setCreateTime(LocalDateTime.now());
//            coreRole.setVersion(0);
//            coreRole.setDelegate("0");
            iCoreRoleService.save(coreRole);
        }
        return ResultDto;
    }

    @RequestMapping(value = "/saveRoleFunction")
    @Function("corerole.saveRoleFunction")
    public ResultDto<?> saveRoleFunction(CoreRole coreRole) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            iCoreRoleService.saveRoleFunction(coreRole);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @RequestMapping(value = "/getLastChildNodeIds")
    public ResultDto<Set<String>> getLastChildNodeIds(Integer id) {
        ResultDto<Set<String>> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            Set<String> ids = iCoreRoleService.getLastChildNodeIds(id);
            ResultDto.setData(ids);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }
}
