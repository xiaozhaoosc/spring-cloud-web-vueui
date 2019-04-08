package com.ken.common.facade.kencommonfacade.common;

import lombok.Builder;
import lombok.Data;
import org.omg.CORBA.SystemException;

/**
 * Description: web视图页面返回
 *
 * @author kenzhao
 * @date 2019/4/2 18:36
 */
@Data
public class ResultDto<T> {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private T data;
    private Long total;

    public static ResultDto format(String code,String message) {
        return new Builder().code(code).message(message).build();
    }

    public static ResultDto format(SystemException systemException) {
        return new Builder().code("500").message(systemException.getMessage()).build();
    }


    public static class Builder {
        private ResultDto resultDto;

        public Builder() {
            this.resultDto = new ResultDto();
        }

        public Builder code(String code) {
            this.resultDto.setCode(code);
            return this;
        }

        public Builder message(String message) {
            this.resultDto.setMessage(message);
            return this;
        }

        public Builder data(Object data) {
            this.resultDto.setData(data);
            return this;
        }

        public ResultDto build(){
            return resultDto;
        }
    }
}
