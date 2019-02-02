package com.whli.jee.core.util;

import com.whli.jee.core.cache.RedisConfig;
import com.whli.jee.core.constant.SysConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Desc：使用于web工程的工具类
 * @Author：whli
 * @Date：2018/5/6 16:35
 */
public class WebUtils {

    /**
     * 获取登录用户名
     * @return string
     */
    public static String getLoginName(){
        HttpServletRequest request = getRequest();
        if (request == null){
            return  null;
        }
        String token = (String)request.getHeader(SysConstants.AUTHORIZATION);

        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.hGet(token,SysConstants.LOGIN_NAME);
        }
        return JedisUtils.hGet(token, SysConstants.LOGIN_NAME);
    }

    /**
     * 获取登录用户名
     * @return string
     */
    public static String getLoginName(HttpServletRequest request){
        String token = (String)request.getHeader(SysConstants.AUTHORIZATION);

        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.hGet(token,SysConstants.LOGIN_NAME);
        }
        return JedisUtils.hGet(token, SysConstants.LOGIN_NAME);
    }

    /**
     * 获取登录用户id
     * @return string
     */
    public static String getLoginUserId(){
        HttpServletRequest request = getRequest();
        if (request == null){
            return  null;
        }
        String token = (String)request.getHeader(SysConstants.AUTHORIZATION);

        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.hGet(token,SysConstants.LOGIN_USERID);
        }
        return JedisUtils.hGet(token, SysConstants.LOGIN_USERID);
    }

    /**
     * 获取登录用户id
     * @return string
     */
    public static String getLoginUserId(HttpServletRequest request){
        if (request == null){
            return  null;
        }
        String token = (String)request.getHeader(SysConstants.AUTHORIZATION);
        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.hGet(token,SysConstants.LOGIN_USERID);
        }
        return JedisUtils.hGet(token, SysConstants.LOGIN_USERID);
    }

    /**
     * 获取完整请求url
     * @param request
     * @return string
     */
    public static String getRequestURI(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getRequestURI();
    }

    /**
     * 获取远程用户访问的Ip
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * @Desc 将Json字符串返回前端
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 10:07
     * @Params [response, json]
     * @Return void
     */
    public static void returnJson(HttpServletResponse response, String json){
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * @Desc 由Spring容器获取HttpServletRequest
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 10:07
     * @Return HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }

        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        try {
            if(null != request){
                request.setCharacterEncoding("UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * @Desc 由Spring容器获取HttpServletResponse
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 10:07
     * @Params [response, json]
     * @Return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }

        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();

        if(null != response){
            response.setCharacterEncoding("UTF-8");
        }

        return response;
    }

    public static boolean isAjax(HttpServletRequest request){
        return !StringUtils.isNotNullOrBlank(request.getHeader("x-requested-with"))
                && request.getHeader("x-requested-with").equals("XMLHttpRequest");
    }

    /**
     * 查询Redis中是否存在token
     * @param token
     * @return
     */
    public static boolean isExistsToken(String token){
        if (StringUtils.isNotNullOrBlank(RedisConfig.clusterNodes)){
            return JedisClusterUtils.exists(token);
        }
        return JedisUtils.exists(token);
    }
}
