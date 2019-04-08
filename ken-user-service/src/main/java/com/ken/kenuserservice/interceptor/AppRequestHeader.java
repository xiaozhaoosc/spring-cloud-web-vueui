package com.ken.kenuserservice.interceptor;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 15:53
 */
@Data
public class AppRequestHeader implements Serializable {
    private static final long serialVersionUID = -1707014735102282241L;
    /**
     * 头信息
     */
    private Map<String, String> header;

    /**
     * APP Id 由后台系统提供
     */
    private String appId;
    /**
     * 请求流水号
     */
    private String serialNumber;
    /**
     * 请求时间	时间戳
     */
    private long requestTime;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 系统类型	手机系统类型:ios或Android
     */
    private String os;
    /**
     * 软件版本号	app版本号
     */
    private String appVersion;
    /**
     * 应用渠道
     */
    private String market;
    /**
     * 签名
     */
    private String sign;
    /**
     * 客户编号
     */
    private String customerId;
    /**
     * 访问令牌	登录后后台返回
     */
    private String accessToken;
    /**
     * 系统版本号	当前手机系统的版本号
     */
    private String osVersion;
    /**
     * 手机型号	当前手机型号，例:oppo r9或nokia e71
     */
    private String phoneModels;

    /**
     * app平台（	1:闪贷、2:速贷、3:应急现金、4:惠花花、5:秒购、6:分多多）
     */
    private Integer platform;

    /**
     * app平台（	1:闪贷、2:速贷、3:应急现金、4:惠花花、5:秒购、6:分多多）
     */
    private String appName;

    /**
     *  请求的真是Ip
     */
    private String xRealIp;

    /**
     * 请求手机号
     */
    private String mobile;

    private String requestId;

    private String xToken;
}
