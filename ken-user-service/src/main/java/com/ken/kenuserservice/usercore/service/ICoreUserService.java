package com.ken.kenuserservice.usercore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreUser;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.usercore.mapper.CoreUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
public interface ICoreUserService extends IService<CoreUser> {

    /**
     * 用户登陆接口
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    CoreUser login(String username, String password) throws BusinessException;


    /**
     * 用户信息
     * @param coreUser
     * @return
     * @throws BusinessException
     */
    Map<String, Object> info(CoreUser coreUser) throws BusinessException;
}
