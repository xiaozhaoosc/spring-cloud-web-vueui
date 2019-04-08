package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreOrg;
import com.ken.kenuserservice.usercore.mapper.CoreOrgMapper;
import com.ken.kenuserservice.usercore.service.ICoreOrgService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Service
public class CoreOrgServiceImpl extends ServiceImpl<CoreOrgMapper, CoreOrg> implements ICoreOrgService {

}
