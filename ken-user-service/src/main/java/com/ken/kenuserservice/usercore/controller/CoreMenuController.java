package com.ken.kenuserservice.usercore.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ken.common.facade.kencommonfacade.common.BaseController;
import com.ken.common.facade.kencommonfacade.common.ResultDto;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.common.facade.kencommonfacade.usercore.dto.MenuDto;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreFunction;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreMenu;
import com.ken.kenuserservice.annotation.Function;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.service.ICoreFunctionService;
import com.ken.kenuserservice.usercore.service.ICoreMenuService;
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
 * 菜单表 前端控制器
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@RestController
@RequestMapping("/usercore/core-menu")
@Slf4j
public class CoreMenuController extends BaseController {

    @Autowired
    private ICoreMenuService iCoreMenuService;
    @Autowired
    private ICoreFunctionService iCoreFunctionService;

    @RequestMapping(value = "/list")
    @Function("coremenu.query")
    public ResultDto<JSONArray> getCoreMenulist() {
        List<CoreMenu> CoreMenus = iCoreMenuService.list(null);
        List<Map<String,Object>> maps = new ArrayList<>();

        ResultDto<JSONArray> ResultDto  =new ResultDto<>();
        ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
        ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
        CoreMenus.forEach(c -> {
            Map<String,Object> map = new HashMap<>();
            map.put("id",c.getId());
            map.put("label",c.getName());
            map.put("icon",c.getIcon());//"icon-quanxian"
            map.put("children",new ArrayList<>());//"icon-quanxian"
            map.put("pId",c.getParentMenuId());
            if(null != c.getFunctionId()){
                CoreFunction coreFunction = iCoreFunctionService.getById(c.getFunctionId());
                map.put("functionName",null == coreFunction ? "":coreFunction.getName());
            }
            map.put("type",c.getType());//"icon-quanxian"
            map.put("seq",c.getSeq());//"icon-quanxian"
            maps.add(map);
        });
        JSONArray result = ListToTreeUtil.listToTree(JSONArray.parseArray(JSON.toJSONString(maps)),"id","pId","children");

        ResultDto.setData(result);

        log.info("查询LIST:"+JSON.toJSONString(result));
        return ResultDto;
    }

    @RequestMapping(value = "/saveData")
    @Function("coremenu.save")
    public ResultDto<?> saveCoreMenu(MenuDto menuDto) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            CoreMenu model = new CoreMenu();
            model.setCode(menuDto.getLabel());
            model.setName(menuDto.getLabel());
            model.setFunctionId(menuDto.getFunctionId());
            model.setIcon(menuDto.getIcon());
            model.setId(menuDto.getId());
            model.setParentMenuId(menuDto.getPId());
            model.setSeq(menuDto.getSeq());
            model.setType(menuDto.getType());
            iCoreMenuService.save(model);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }

    @RequestMapping(value = "/delData")
    @Function("coremenu.del")
    public ResultDto<?> delData(Integer id) {
        ResultDto<?> ResultDto =new ResultDto<>();
        try {
            ResultDto.setCode(ResultCodeEnum.SUCCESS.getCode());
            ResultDto.setMessage(ResultCodeEnum.SUCCESS.getDescription());
            iCoreMenuService.removeById(id);
        } catch (Exception e) {
            ResultDto.setMessage(e.getMessage());
        }
        return ResultDto;
    }
}
