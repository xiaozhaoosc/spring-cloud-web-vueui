package com.ken.common.facade.kencommonfacade.common;

import lombok.Data;

/**
 * Description: 通用接口返回
 *
 * @author kenzhao
 * @date 2019/4/2 18:36
 */
@Data
public class ResultBean<T> {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private T data;
}
