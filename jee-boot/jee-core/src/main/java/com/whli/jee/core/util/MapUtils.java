package com.whli.jee.core.util;

import java.util.Map;

/**
 * Created by whli on 2018/4/7.
 */
public class MapUtils {

    /**
     * 判断Map是否为Null或空
     * @param map
     * @return
     */
    public static boolean isNullOrEmpty(Map map){
        if(map == null || map.isEmpty()){
            return true;
        }
        return map == null || map.size() == 0;
    }

    /**
     * 判断Map不为Null及空
     * @param map
     * @return
     */
    public static boolean isNotNullOrEmpty(Map map){
        return !isNullOrEmpty(map);
    }
}
