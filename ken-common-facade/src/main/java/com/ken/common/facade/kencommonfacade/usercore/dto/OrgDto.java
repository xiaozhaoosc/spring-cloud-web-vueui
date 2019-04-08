package com.ken.common.facade.kencommonfacade.usercore.dto;

import com.ken.common.facade.kencommonfacade.common.BaseTree;
import lombok.Data;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 18:48
 */
@Data
public class OrgDto extends BaseTree {
    private String type;
    private String code;
}
