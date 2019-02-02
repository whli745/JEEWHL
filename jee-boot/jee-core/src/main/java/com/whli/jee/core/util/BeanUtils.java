package com.whli.jee.core.util;

import java.util.UUID;

/**
 * @Desc：<em>JavaBean工具类</em>
 * @Author：whli
 * @Date：2018/5/5 9:35
 */
public class BeanUtils {

    /**
     * @Desc：<em>获取UUID</em>
     * @Author：whli
     * @Date：2018/5/5
     * @return java.lang.String
     */
    public static String getUUID(){
        String uuid =UUID.randomUUID().toString();
        return uuid.replace("-","").toUpperCase();
    }

    /**
     * @Desc：<em>判断对象是否为Null</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param z Object
     * @return boolean
     */
    public static boolean isNull(Object z){
        return z == null;
    }

    /**
     * @Desc：<em>判断对象是否不为Null</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param z Object
     * @return boolean
     */
    public static boolean isNotNull(Object z){
        return !isNull(z);
    }
}
