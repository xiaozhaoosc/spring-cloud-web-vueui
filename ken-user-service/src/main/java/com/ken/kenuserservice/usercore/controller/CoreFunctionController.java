package com.ken.kenuserservice.usercore.controller;


import com.alibaba.fastjson.JSON;
import com.ken.common.facade.kencommonfacade.common.BaseController;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.common.facade.kencommonfacade.usercore.dto.FunctionDto;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreFunction;
import com.ken.kenuserservice.annotation.Function;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.service.ICoreFunctionService;
import com.ken.kenuserservice.utils.ListToTreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
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
@RequestMapping("/usercore/core-function")
@Slf4j
public class CoreFunctionController extends BaseController {


    @Autowired
    private ICoreFunctionService iCoreFunctionService;

    @RequestMapping(value = "/list")
    @Function("corefunction.query")
    public ResultDto<JSONArray> getCoreFunctionlist() {
        List<CoreFunction> CoreFunctions = iCoreFunctionService.list(null);
        List<Map<String,Object>> maps = new ArrayList<>();

        ResultDto<JSONArray> ResultDto  =new ResultDto<>();
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        CoreFunctions.forEach(c -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId()+"");
            map.put("label",c.getName());
            map.put("icon","");//"icon-quanxian"
            map.put("code",c.getCode());//"icon-quanxian"
            map.put("children",new ArrayList<>());//"icon-quanxian"
            map.put("pId",c.getParentId());
            map.put("type",c.getType());//"icon-quanxian"
            map.put("accessUrl",c.getAccessUrl());//"icon-quanxian"
            maps.add(map);
        });
        JSONArray result = ListToTreeUtil.listToTree(JSONArray.parseArray(JSON.toJSONString(maps)),"id","pId","children");

        ResultDto.setData(result);

        log.info("查询LIST:"+JSON.toJSONString(result));
        return ResultDto;
    }

    @RequestMapping(value = "/saveData")
    @Function("corefunction.save")
    public ResultDto<?> saveCoreFunction(FunctionDto requestmodel) {
        ResultDto<?> resultDto =new ResultDto<>();
        resultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        resultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        CoreFunction model = new CoreFunction();
        model.setCode(requestmodel.getCode());
        model.setName(requestmodel.getLabel());
        model.setId(requestmodel.getId());
        model.setParentId(requestmodel.getPId());
        model.setType(requestmodel.getType());
        model.setAccessUrl(requestmodel.getAccessUrl());
        iCoreFunctionService.save(model);
        return resultDto;
    }

    @RequestMapping(value = "/delData")
    @Function("corefunction.del")
    public ResultDto<?> delData(Integer id) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            iCoreFunctionService.removeById(id);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }
}
