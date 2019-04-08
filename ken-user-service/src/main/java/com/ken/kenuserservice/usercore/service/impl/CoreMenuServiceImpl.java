package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreMenu;
import com.ken.kenuserservice.usercore.mapper.CoreMenuMapper;
import com.ken.kenuserservice.usercore.service.ICoreMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Service
public class CoreMenuServiceImpl extends ServiceImpl<CoreMenuMapper, CoreMenu> implements ICoreMenuService {

}
