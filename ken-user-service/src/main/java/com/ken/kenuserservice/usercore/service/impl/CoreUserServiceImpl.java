package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreFunction;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRoleFunction;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUser;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUserRole;
import com.ken.kenuserservice.config.MyDES;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.mapper.*;
import com.ken.kenuserservice.usercore.service.ICoreUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
public class CoreUserServiceImpl extends ServiceImpl<CoreUserMapper, CoreUser> implements ICoreUserService {

    @Autowired
    private CoreUserMapper coreUserMapper;
    @Autowired
    private CoreRoleMapper coreRoleMapper;
    @Autowired
    private CoreUserRoleMapper coreUserRoleMapper;
    @Autowired
    private CoreRoleFunctionMapper coreRoleFunctionMapper;
    @Autowired
    private CoreFunctionMapper coreFunctionMapper;

    /**
     * 用户登陆接口
     *
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public CoreUser login(String username, String password) throws BusinessException {
        QueryWrapper<CoreUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CoreUser::getCode, username);
        CoreUser coreUser = coreUserMapper.selectOne(queryWrapper);
        if(coreUser == null){
            throw new BusinessException("502","用户不存在");
        }else if(coreUser.getState().equals("0")){
            throw new BusinessException("503","用户已停用");
        }else if(coreUser.getPassword().equals(MyDES.encryptBasedDes(coreUser.getCode()+password))){
            return coreUser;
        }else{
            throw new BusinessException("503","密码错误!");
        }
    }

    /**
     * 用户信息
     *
     * @param coreUser
     * @return
     * @throws BusinessException
     */
    @Override
    public Map<String, Object> info(CoreUser coreUser) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
//        //查询用户角色
        CoreUserRole queryModel = new CoreUserRole();
        queryModel.setUserId(coreUser.getId());
        QueryWrapper queryWrapper = new QueryWrapper<>(queryModel);
        CoreUserRole coreUserRole = coreUserRoleMapper.selectOne(queryWrapper);

        //查询角色功能
        List<CoreRoleFunction> roleFunctions = new ArrayList<>();

        CoreRoleFunction queryCoreRoleFunction = new CoreRoleFunction();
        queryCoreRoleFunction.setRoleId(coreUserRole.getRoleId());
        List<CoreRoleFunction> coreRoleFunctions = coreRoleFunctionMapper.selectList(new QueryWrapper<>(queryCoreRoleFunction));
        roleFunctions.addAll(coreRoleFunctions);

        //功能去重
        List<CoreRoleFunction> qcRoleFunctions = roleFunctions.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<CoreRoleFunction>(Comparator.comparing(CoreRoleFunction::getFunctionId))), ArrayList<CoreRoleFunction>::new));

        List<String> r = new ArrayList<>();
        qcRoleFunctions.forEach(rf -> {
            CoreFunction queryFunction = new CoreFunction();
            queryFunction.setId(rf.getFunctionId());
            CoreFunction coreFunction = coreFunctionMapper.selectOne(new QueryWrapper<>(queryFunction));
            r.add(coreFunction.getCode());
        });
        map.put("roles",r);
        map.put("introduction", coreUser.getName());
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", coreUser.getName());
        return map;
    }
}
