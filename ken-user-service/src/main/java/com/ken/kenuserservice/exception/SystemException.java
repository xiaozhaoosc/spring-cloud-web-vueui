package com.ken.kenuserservice.exception;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;

/**
 * Description:
 *系统异常，这是非受检异常
 * @author kenzhao
 * @date 2019/4/8 16:27
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = -181248499771720338L;
    private String code;

    public SystemException(){
        super();
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(ResultCodeEnum resultCode) {
        super(resultCode.getDescription());
        this.code = resultCode.getCode();
    }

    public SystemException(IEnum<String> codeEnum) {
        this.code = codeEnum.getValue();
    }

    public SystemException(ResultCodeEnum resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public SystemException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public SystemException(ResultCodeEnum resultCode, Throwable cause) {
        super(resultCode.getDescription(), cause);
        this.code = resultCode.getCode();
    }

    public SystemException(String code, String message, Throwable cause) {
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
        if (!(o instanceof SystemException)) {
            return false;
        }
        SystemException that = (SystemException) o;
        return getCode() == that.getCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
