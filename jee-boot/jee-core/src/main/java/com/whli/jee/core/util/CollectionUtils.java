package com.whli.jee.core.util;

import java.util.Collection;

/**
 * @Desc：<em>自定义Collection集合工具类</em>
 * @Author：whli
 * @Date：2018/5/5 9:56
 */
public class CollectionUtils {

    /**
     * @Desc：<em>判断Collection是否为null或空</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param collection Collection
     * @return boolean
     */
    public static boolean isNullOrEmpty(Collection collection){
        return collection == null || collection.size() == 0;
    }

    /**
     * @Desc：<em>判断Collection是否不为null或空</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param collection Collection
     * @return boolean
     */
    public static boolean isNotNullOrEmpty(Collection collection){

        return !isNullOrEmpty(collection);
    }
}
