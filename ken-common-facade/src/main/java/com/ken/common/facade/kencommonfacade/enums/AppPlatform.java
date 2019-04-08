package com.ken.common.facade.kencommonfacade.enums;


//import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * app平台
 */
public enum AppPlatform implements IEnum<Integer> {
    /**
     * 闪贷
     */
    SHAN_DAI(1, "闪贷"),
    /**
     * 小牛互娱
     */
    XIAO_NIU_HU_YU(2, "小牛互娱"),
    /**
     * 应急现金
     */
    YING_JI_XIAN_JIN(3, "应急现金"),
    /**
     * 惠花花
     */
    HUI_HUA_HUA(4, "惠花花"),
    /**
     * 秒购
     */
    MIAO_GOU(5, "秒购"),
    /**
     * 秒购
     */
    FEN_DUODUO(6, "分多多");

//    @EnumValue
    private Integer value;

    private String desc;

    AppPlatform(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

//    @JsonValue
//    @Override
    public Integer getValue() {
        return value;
    }


//    @JsonValue
    public String getDesc() {
        return desc;
    }


    private static Map<Integer, AppPlatform> mappings = new HashMap<>();
    static {
        Arrays.stream(AppPlatform.values()).forEach(appPlatform -> mappings.put(appPlatform.getValue(), appPlatform));
    }


    @JsonCreator
    public static AppPlatform resolve(Integer appPlatformValue) {
        return mappings.get(appPlatformValue);
    }
}
