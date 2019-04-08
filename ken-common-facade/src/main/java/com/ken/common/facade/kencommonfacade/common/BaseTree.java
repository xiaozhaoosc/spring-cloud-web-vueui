package com.ken.common.facade.kencommonfacade.common;

import lombok.Data;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 18:39
 */
@Data
public class BaseTree {
    Integer id;
    Integer pId;
    String label;
    String icon;
}
