package com.ken.kenuserservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * Description:反射工具类
 *
 * @author kenzhao
 * @date 2019/4/8 15:58
 */
@Slf4j
public class ReflectionUtils {

    private static String longType = "java.lang.Long";
    private static String longTypeSimp = "long";
    private static String integerType = "java.lang.Integer";
    private static String integerTypeSimp = "Integer";

    /**
     * 通过反射设置属性的值
     * @param instance
     * @param name
     * @param value
     */
    public static void setPropertyValue(Object instance,
                                        String name,
                                        String value) {

        try {
            Class<?> clazz = instance.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            String typeName = fieldType.getCanonicalName();
            if(longType.equals(typeName) || longTypeSimp.equals(typeName)) {
                field.set(instance, Long.parseLong(value));
            }if(integerType.equals(typeName) || integerTypeSimp.equals(typeName)) {
                field.set(instance, Integer.parseInt(value));
            } else {
                field.set(instance, value);
            }
        } catch (NoSuchFieldException e) {
            log.debug("设置HTTP请求头的属性{}，值{}失败：不存在该属性", name, value);
        } catch (Exception e) {
            log.debug("设置HTTP请求头的属性{}，值{}失败：{}", name, value, e.getMessage());
        }
    }
}
