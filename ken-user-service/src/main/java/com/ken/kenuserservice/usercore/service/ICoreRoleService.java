package com.ken.kenuserservice.usercore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreRole;
import com.ken.kenuserservice.exception.BusinessException;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
public interface ICoreRoleService extends IService<CoreRole> {

    /**
     * 保存权限
     * @param coreRole
     */
    void saveRoleFunction(CoreRole coreRole);

    /**
     * 获取所有节点
     * @param id
     * @return
     * @throws BusinessException
     */
    Set<String> getLastChildNodeIds(Integer id) throws BusinessException;

}
