package com.ken.kenuserservice.usercore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ken.common.facade.kencommonfacade.usercore.entity.CoreDict;
import com.ken.kenuserservice.usercore.mapper.CoreDictMapper;
import com.ken.kenuserservice.usercore.service.ICoreDictService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author kenzhao
 * @since 2019-04-08
 */
@Service
public class CoreDictServiceImpl extends ServiceImpl<CoreDictMapper, CoreDict> implements ICoreDictService {

}
