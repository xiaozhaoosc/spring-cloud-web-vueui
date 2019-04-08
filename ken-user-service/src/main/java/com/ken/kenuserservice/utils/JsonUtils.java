package com.ken.kenuserservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ken.common.facade.kencommonfacade.enums.ResultCodeEnum;
import com.ken.kenuserservice.exception.BusinessException;
import com.ken.kenuserservice.exception.SystemException;

import java.text.SimpleDateFormat;

/**
 * Description:
 *
 * @author kenzhao
 * @date 2019/4/8 16:25
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
        //去掉默认的时间戳格式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //空值不序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        OBJECT_MAPPER.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //单引号处理
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * 解析JSON字符串为指定的对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new SystemException(ResultCodeEnum.JSON_PARSE_EXCEPTION.getCode(), e.getMessage());
        }
    }

    /**
     * 解析JSON字符串为指定的泛型对象
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new SystemException(ResultCodeEnum.JSON_PARSE_EXCEPTION.getCode(), e.getMessage());
        }
    }

    /**
     * 把对象转换为JSON字符串
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new SystemException(ResultCodeEnum.JSON_PARSE_EXCEPTION.getCode(), e.getMessage());
        }
    }
}
