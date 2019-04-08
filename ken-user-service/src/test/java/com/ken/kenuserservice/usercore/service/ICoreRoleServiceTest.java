package com.ken.kenuserservice.usercore.service;

import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRole;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUserRole;
import com.ken.kenuserservice.KenUserServiceApplicationTests;
import com.ken.kenuserservice.usercore.mapper.CoreRoleMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ICoreRoleServiceTest extends KenUserServiceApplicationTests {

    @Autowired
    ICoreRoleService iCoreRoleService;
    @Autowired
    CoreRoleMapper coreRoleMapper;
    @Autowired
    ICoreUserRoleService iCoreUserRoleService;

    @Test
    public void test(){
//        CoreRole coreRole = new CoreRole();
//        coreRole.setId(1);
//        coreRole.setCode("admin");
//        coreRole.setName("admin");
//        coreRole.setCreateTime(LocalDateTime.now());
//        coreRole.setType("2");

//        List<CoreRole> coreRoleList = Lists.newArrayList();
//        coreRoleList.add(coreRole);
//        iCoreRoleService.saveBatch(coreRoleList);

//        coreRoleMapper.insert(coreRole);
        CoreUserRole coreUserRole = new CoreUserRole();
        coreUserRole.setId(32);
        coreUserRole.setUserId(1);
        coreUserRole.setRoleId(1);
        coreUserRole.setOrgId(10);
        coreUserRole.setCreateTime(LocalDateTime.now());
        coreUserRole.setDeleted(0);
        coreUserRole.setVersion(0);
        coreUserRole.setCTime(LocalDateTime.now());
        coreUserRole.setUTime(LocalDateTime.now());
        iCoreUserRoleService.save(coreUserRole);
    }
}