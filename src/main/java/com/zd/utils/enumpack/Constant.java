package com.zd.utils.enumpack;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Zidong
 * @date 2019/5/21 3:58 PM
 */
class Constant {

    /**
     * 枚举类包名集合
     */
    private static List<String> pathList = initPackagePathList();

    /**
     * 枚举接口类全路径
     */
    private final static String ENUM_MESSAGE_PATH = "com.zd.utils.enumpack.EnumMessage";

    /**
     * 枚举类对应的全路径集合
     */
    private static final List<String> ENUM_OBJECT_PATH = PackageUtil.getPackageClasses(pathList);

    /**
     * 存放单个枚举对象 map常量定义
     */
    private static Map<Object, EnumMessage> SINGLE_ENUM_MAP = null;

    /**
     * 所有枚举对象的 map
     */
    static final Map<Class, Map<Object, EnumMessage>> ENUM_MAP = initialEnumMap(true);

    private static List<String> initPackagePathList() {
        List<String> list = new ArrayList<>(1);
        list.add("com.zd.utils.enumpack");
        list.add("com.zd.utils.tools.util.bean");
        return list;
    }

    // 类被加载时，先初始化各个静态变量，再执行 static块。所以不能在这里执行 pathList的 add操作("com.zd.utils.enumpack.EnumMessage")。
    // static {
    // }

    /**
     * 加载所有枚举对象数据
     *
     * @param isFouceCheck 是否强制校验枚举是否实现了EnumMessage接口,若为false则没有实现接口的枚举类也会被加载
     */
    private static Map<Class, Map<Object, EnumMessage>> initialEnumMap(boolean isFouceCheck) {
        Map<Class, Map<Object, EnumMessage>> enumMap = new HashMap<>();
        try {
            for (String classname : ENUM_OBJECT_PATH) {
                Class<?> cls;
                cls = Class.forName(classname);
                Class<?>[] iter = cls.getInterfaces();
                boolean flag = false;
                if (isFouceCheck) {
                    for (Class cz : iter) {
                        if (cz.getName().equals(ENUM_MESSAGE_PATH)) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag == isFouceCheck) {
                    SINGLE_ENUM_MAP = new HashMap<>();
                    initialSingleEnumMap(cls);
                    enumMap.put(cls, SINGLE_ENUM_MAP);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumMap;
    }

    /**
     * 加载每个枚举对象数据
     */
    private static void initialSingleEnumMap(Class<?> cls) throws Exception {
        Method method = cls.getMethod("values");
        EnumMessage[] inter = (EnumMessage[]) method.invoke(null, null);
        for (EnumMessage enumMessage : inter) {
            SINGLE_ENUM_MAP.put(enumMessage.getValue(), enumMessage);
        }
    }
}
