package com.ken.kenuserservice.exception;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;

/**
 * Description:
 * 业务层异常，调用方必须捕获
 * @author kenzhao
 * @date 2019/4/8 13:47
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -181248499771720338L;
    private String code;

    public BusinessException() {
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultCodeEnum resultCode) {
        super(resultCode.getDescription());
        this.code = resultCode.getCode();
    }

    public BusinessException(IEnum<String> businessCodeEnum) {
        super(businessCodeEnum.getValue());
        this.code = businessCodeEnum.getValue();
    }

    public BusinessException(ResultCodeEnum resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public BusinessException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BusinessException(ResultCodeEnum resultCode, Throwable cause) {
        super(resultCode.getDescription(), cause);
        this.code = resultCode.getCode();
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessException)) {
            return false;
        }
        BusinessException that = (BusinessException) o;
        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
