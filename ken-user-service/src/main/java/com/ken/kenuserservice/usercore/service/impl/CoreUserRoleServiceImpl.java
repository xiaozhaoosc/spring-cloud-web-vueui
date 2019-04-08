package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUserRole;
import com.ken.kenuserservice.usercore.mapper.CoreUserRoleMapper;
import com.ken.kenuserservice.usercore.service.ICoreUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Service
public class CoreUserRoleServiceImpl extends ServiceImpl<CoreUserRoleMapper, CoreUserRole> implements ICoreUserRoleService {

}
