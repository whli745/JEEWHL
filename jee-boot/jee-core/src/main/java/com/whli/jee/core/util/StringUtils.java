package com.whli.jee.core.util;

/**
 * @Desc：<em>字符串工具类</em>
 * @Author：whli
 * @Date：2018/5/5 9:27
 */
public class StringUtils {

    /**
     * @Desc：<em>判断字符串是否为null或空串或空格符</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param input String
     * @return boolean
     */
    public static boolean isNullOrBlank(String input){
        return input == null || input.trim().length() == 0;
    }

    /**
     * @Desc：<em>判断字符串是否不为null或空串</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param input String
     * @return boolean
     */
    public static boolean isNotNullOrBlank(String input){
        return !isNullOrBlank(input);
    }
}
