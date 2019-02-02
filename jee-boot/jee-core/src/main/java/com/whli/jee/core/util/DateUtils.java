package com.whli.jee.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author whli
 * @since 2018/6/12 16:37
 * @version 1.0
 */
public class DateUtils {
    public static final String DEFAULT = "yyyy-MM-dd";
    public static final String DEFAULT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_SLASH = "yyyy/MM/dd";
    public static final String DEFAULT_SLASH_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    /**
     * 将日期转换为字符串
     * @param date 日期
     * @param format 转换格式
     * @return java.lang.String
     */
    public static String dateToString(Date date,String format){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串转换为日期
     * @param date 日期字符串
     * @param format 转换格式
     * @return java.util.Date
     */
    public static Date stringToDate(String date,String format){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
