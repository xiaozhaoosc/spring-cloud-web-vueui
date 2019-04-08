package com.ken.common.facade.kencommonfacade.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 结果码枚举类
 * @author kenzhao
 * @date 2019/4/8 13:46
 */
public enum ResultCodeEnum {
    SUCCESS("200", "请求成功"),
    ILLEGAL_IP("1", "非法IP"), ILLEGAL_TOKEN("2", "登陆生效，请重新登陆"), ILLEGAL_REQUEST("3", "请求不合法"), ILLEGAL_PARAM("4", "参数不正确"),
    NO_RESULT("5", "没有结果"), SYSTEM_EXCEPTION("6", "服务繁忙，请稍后重试"), RPC_EXCEPTION("7", "RPC远程调用异常"),
    JSON_PARSE_EXCEPTION("8", "JSON解析异常"), GET_LOCK_EXCEPTION("9", "获取锁失败");


    /**
     * 结果代码
     */
    private String code;
    /**
     * 结果描述
     */
    private String description;

    ResultCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private static Map<String, String> mappings = new HashMap<>();

    static {
        Arrays.stream(ResultCodeEnum.values())
                .forEach(resultCodeEnum ->
                        mappings.put(resultCodeEnum.getCode(), resultCodeEnum.getDescription())
                );
    }

    public boolean isMatch(int code) {
        return mappings.containsKey(code);
    }
}
