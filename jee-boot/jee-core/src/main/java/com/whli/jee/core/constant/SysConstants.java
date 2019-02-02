package com.whli.jee.core.constant;

import java.util.Date;

/**
 * @Desc 类作用描述
 * @Author whli
 * @Version 1.0
 * @Date 2018/5/29 16:24
 */
public class SysConstants {
    /**
     * 存放验证密钥token的header
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 登录用户名
     */
    public static final String LOGIN_NAME = "loginName";

    public static final String LOGIN_USERID = "loginUserId";

    /**
     * token过期
     */
    public static final int STATUS_FAIL =1;
    public static final String TOKEN_FAIL_CODE = "-10005";
    public static final String TOKEN_FAIL_MESSAGE ="token过期,请重新登陆";

    public static final Long currentTimeMillis = new Date().getTime();
}
