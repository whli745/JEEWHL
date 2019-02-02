package com.whli.jee.core.util;


import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具
 * Created by whli on 2016/12/5.
 */
public class PwdUtils {

    /**
     * @Desc：<em>SHA-1加密</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param pwd 密码, salt 加密盐
     * @return java.lang.String
     */
    public static String sha1Encode(String pwd,String salt) {
        MessageDigest sh1 = getMessageDigest("SHA1");

        String pwdTemp = pwd+salt;
        String hex = "";

        try {
            byte[] result = sh1.digest(pwdTemp.getBytes("UTF-8"));
            hex = byteArrayToHexString(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hex;
    }

    /**
     * @Desc：<em>MD5加密</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param pwd 密码, salt 加密盐
     * @return java.lang.String
     */
    public static String md5Encode(String pwd,String salt){
        MessageDigest md5 = getMessageDigest("MD5");

        String pwdTemp = pwd+salt;
        String hex = "";

        try {
            byte[] result = md5.digest(pwdTemp.getBytes("UTF-8"));
            hex = byteArrayToHexString(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hex;
    }

    /**
     * @Desc：<em>获取加密工具类</em>
     * @Author：whli
     * @Date：2018/5/5
     * @param algorithm 加密方式
     * @return java.security.MessageDigest
     */
    private static MessageDigest getMessageDigest(String algorithm){

        MessageDigest message = null;

        try {
            message =  MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        String temp = DatatypeConverter.printHexBinary(b);
        return temp;
    }

}
