package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.dto.TreeItem;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreFunction;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRole;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRoleFunction;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.mapper.CoreRoleMapper;
import com.ken.kenuserservice.usercore.service.ICoreFunctionService;
import com.ken.kenuserservice.usercore.service.ICoreRoleFunctionService;
import com.ken.kenuserservice.usercore.service.ICoreRoleService;
import com.ken.kenuserservice.utils.AtsAssert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Service
@Slf4j
public class CoreRoleServiceImpl extends ServiceImpl<CoreRoleMapper, CoreRole> implements ICoreRoleService {

    @Autowired
    private ICoreRoleFunctionService iCoreRoleFunctionService;
    @Autowired
    private ICoreFunctionService iCoreFunctionService;
    /**
     * 保存权限
     *
     * @param coreRole
     */
    @Override
    public void saveRoleFunction(CoreRole coreRole) {
//        AtsAssert.notNull(coreRole.getId(), "502", "参数不能为空");
        CoreRoleFunction coreRoleFunction = new CoreRoleFunction();
        coreRoleFunction.setRoleId(coreRole.getId());
        QueryWrapper<CoreRoleFunction> coreRoleFunctionQueryWrapper = new QueryWrapper<>(coreRoleFunction);
        iCoreRoleFunctionService.remove(coreRoleFunctionQueryWrapper);

//        if(!StringUtils.isEmpty(coreRole.getFunctionIds())){
//            List<String> ids = Arrays.asList(coreRole.getFunctionIds().split(","));
//            if(!ids.isEmpty()){
//                List<CoreFunction> coreFunctions = iCoreFunctionService.list(null);
//                coreFunctions.forEach(f ->{
//                    Integer id = f.getId();
//                    f.setId(f.getParentId());
//                    f.setParentId(id);
//                });
//
//                TreeItem treeItem = new TreeItem();
//
//                Set<Integer> sids = new HashSet<>();
//                ids.forEach(i ->{
//                    sids.add(Integer.parseInt(i));
//                });
//
//                sids.forEach(i ->{
//                    treeItem.treeMenuList(coreFunctions,i);
//                });
//
//                List<CoreFunction> functions = treeItem.getChildNode();
//                functions.forEach(f ->{
//                    if(f.getId() != 0){
//                        sids.add(f.getId());
//                    }
//                });
//
//                List<CoreRoleFunction> coreRoleFunctions = new ArrayList<>();
//                sids.forEach(i ->{
//                    CoreRoleFunction roleFunction = new CoreRoleFunction();
//                    roleFunction.setRoleId(coreRole.getId());
//                    roleFunction.setFunctionId(i);
//                    coreRoleFunctions.add(roleFunction);
//                });
//                iCoreRoleFunctionService.saveBatch(coreRoleFunctions);
//            }
//        }
    }

    /**
     * 获取所有节点
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    @Override
    public Set<String> getLastChildNodeIds(Integer id) throws BusinessException {
        AtsAssert.notNull(id, "502", "参数为空");
        CoreRoleFunction queryCoreRoleFunction = new CoreRoleFunction();
        queryCoreRoleFunction.setRoleId(id);
        List<CoreRoleFunction> coreRoleFunctions = iCoreRoleFunctionService.list(new QueryWrapper<>(queryCoreRoleFunction));

        List<CoreFunction> CoreFunctions = new ArrayList<>();
        coreRoleFunctions.forEach(cf -> {
            CoreFunction queryModel = new CoreFunction();
            queryModel.setId(cf.getFunctionId());
            CoreFunction coreFunction = iCoreFunctionService.getOne(new QueryWrapper<>(queryModel));
            CoreFunctions.add(coreFunction);
        });

        TreeItem treeItem = new TreeItem();
        treeItem.treeMenuList(CoreFunctions,0);
        List<CoreFunction> functions = treeItem.getLastChildNode();

        Set<String> lastChildNode = new HashSet<>();
        functions.forEach(function -> {
            lastChildNode.add(function.getId()+"");
        });

        return lastChildNode;
    }
}
