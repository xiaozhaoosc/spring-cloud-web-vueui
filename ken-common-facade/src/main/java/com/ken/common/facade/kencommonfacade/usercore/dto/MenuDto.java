package com.ken.common.facade.kencommonfacade.usercore.dto;

import com.ken.common.facade.kencommonfacade.common.BaseTree;
import lombok.Data;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 18:45
 */
@Data
public class MenuDto extends BaseTree {

    private Integer functionId;

    private String type;

    private Integer seq;
}
