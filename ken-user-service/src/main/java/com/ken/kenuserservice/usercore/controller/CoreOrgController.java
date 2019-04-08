package com.ken.kenuserservice.usercore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ken.common.facade.kencommonfacade.common.BaseController;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.common.facade.kencommonfacade.usercore.dto.OrgDto;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreOrg;
import com.ken.kenuserservice.annotation.Function;
import com.ken.kenuserservice.usercore.service.ICoreOrgService;
import com.ken.kenuserservice.utils.ListToTreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/usercore/core-org")
@Slf4j
public class CoreOrgController extends BaseController {

    @Autowired
    private ICoreOrgService iCoreOrgService;

    @RequestMapping(value = "/list")
    @Function("coreorg.query")
    public ResultDto<JSONArray> getCoreOrglist() {
        List<CoreOrg> CoreOrgs = iCoreOrgService.list(null);
        List<Map<String,Object>> maps = new ArrayList<>();

        ResultDto<JSONArray> ResultDto  =new ResultDto<>();
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        CoreOrgs.forEach(c -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("label",c.getName());
            map.put("icon","");//"icon-quanxian"
            map.put("code",c.getCode());//"icon-quanxian"
            map.put("children",new ArrayList<>());//"icon-quanxian"
            map.put("pId",c.getParentOrgId());
            map.put("type",c.getType());//"icon-quanxian"
            maps.add(map);
        });
        JSONArray result = ListToTreeUtil.listToTree(JSONArray.parseArray(JSON.toJSONString(maps)),"id","pId","children");

        ResultDto.setData(result);

        log.info("查询LIST:"+ JSON.toJSONString(result));
        return ResultDto;
    }

    @RequestMapping(value = "/saveData")
    @Function("coreorg.save")
    public ResultDto<?> saveCoreOrg(OrgDto requestmodel) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            CoreOrg model = new CoreOrg();
            model.setCode(requestmodel.getCode());
            model.setName(requestmodel.getLabel());
            model.setId(requestmodel.getId());
            model.setParentOrgId(requestmodel.getPId());
            model.setType(requestmodel.getType());
            iCoreOrgService.save(model);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @RequestMapping(value = "/delData")
    @Function("coreorg.del")
    public ResultDto<?> delData(Integer id) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            iCoreOrgService.removeById(id);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }
}
