package com.whli.jee.core.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义数组工具类
 * Created by whli on 2018/4/7.
 */
public class ArrayUtils {

    /**
     * 判断数组为Null或长度为0
     * @param objects
     * @return
     */
    public static boolean isNullOrEmpty(Object[] objects){
        return objects == null || objects.length == 0;
    }

    /**
     * 判断数组不为Null或长度不为0
     * @param objects
     * @return
     */
    public static boolean isNotNullOrEmpty(Object[] objects){
        return !isNullOrEmpty(objects);
    }

    /**
     * 将数组转换为list
     * @param objects
     * @return
     */
    public static List transToList(Object[] objects){
        return Arrays.asList(objects);
    }

    /**
     * 将数组转换为set
     * @param objects
     * @return
     */
    public static Set transToSet(Object[] objects){
        return new HashSet(transToList(objects));
    }
}
