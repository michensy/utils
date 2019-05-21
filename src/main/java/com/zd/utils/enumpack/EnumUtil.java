package com.zd.utils.enumpack;

/**
 * @author Zidong
 * @date 2019/5/21 4:00 PM
 */
public class EnumUtil {
    /**
     * 获取value返回枚举对象
     *
     * @param value
     * @param clazz
     */
    public static <T extends EnumMessage> T getEnumObject(Object value, Class<T> clazz) {
        return (T) Constant.ENUM_MAP.get(clazz).get(value);
    }

    public static void main(String[] args) {
        System.out.println(EnumUtil.getEnumObject("W1", SexEnum.class));
    }
}
