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
public enum PayType implements IEnum<String> {

//    业务类型：1、用户主动还款；2、系统自动全额扣款；3、用户续期；4：系统自动部分扣款、5、客服后台扣款；6、系统自动本金扣款；7、用户购买VIP保费，8：催收后台发起扣款

    /**
     * 用户主动还款
     */
    CUSTOMER_INITIATIVE("1", "用户主动还款"),

    /**
     * 系统自动全额扣款
     */
    SYSTEM_AUTO_FULL("2", "系统自动全额扣款"),

    /**
     * 用户续期
     */
    USER_RENEW("3", "用户续期"),

    /**
     * 系统自动部分扣款
     */
    SYSTEM_AUTO_PART("4", "系统自动部分扣款"),

    /**
     * 客服后台扣款
     */
    KEFU_BACKEND("5", "客服后台扣款"),

    /**
     * 系统自动本金扣款
     */
    SYSTEM_AUTO_PRINCIPAL_FULL("6", "系统自动本金扣款"),

    /**
     * 用户购买vip保费
     */
    USER_BUY_PREMIUM("7", "用户购买vip保费"),

    /**
     * 催收后台发起扣款
     */
    COLLECTION_BACKEND("8", "催收后台发起扣款"),

    /**
     * 快钱转代扣
     */
    BILL99_TO_WITHHOLD("9", "快钱转代扣"),

    /**
     * 发薪日批扣
     */
    PAY_DAY_BATCH_WITHHOLD("10", "发薪日批扣"),

    /**
     * 逾期多天批扣-快钱
     */
    MORE_OVERDUE_DAYS_BATCH_WITHHOLD("11", "逾期多天批扣"),

    /**
     * 逾期多天批扣-宝付
     */
    MORE_OVERDUE_DAYS_BATCH_WITHHOLD_BF("12", "逾期多天批扣");

//    @EnumValue
    private String value;

    private String desc;

    PayType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, PayType> mappings = new HashMap<>();
    static {
        Arrays.stream(PayType.values()).forEach(payType -> mappings.put(payType.getValue(), payType));
    }
    @JsonCreator
    public static PayType resolve(String payTypeValue) {
        return mappings.get(payTypeValue);
    }
}
