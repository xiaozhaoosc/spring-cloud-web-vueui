package com.ken.common.facade.kencommonfacade.usercore.dto;

import com.ken.common.facade.kencommonfacade.common.BaseTree;
import lombok.Data;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 18:39
 */
@Data
public class FunctionDto extends BaseTree {

    private String code;

    private String accessUrl;

    private String Type;
}
