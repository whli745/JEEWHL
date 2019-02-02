package com.whli.jee.core.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Desc Redis配置类
 * @Author whli
 * @Version 1.0
 * @Date 2018/5/29 16:15
 */
@Component
public class RedisConfig {
    public static String host;
    public static int port;
    public static int timeout;
    public static int total;
    public static int maxIdle;
    public static long maxWaitMillis;
    public static String password;
    public static int defaultExpire;
    public static String clusterNodes;

    @Value("${spring.redis.host}")
    private  String hostRedis;
    @Value("${spring.redis.port}")
    private  int portRedis;
    @Value("${spring.redis.timeout}")
    private  int timeoutRedis;
    @Value("${spring.redis.pool.max-active}")
    private  int totalRedis;
    @Value("${spring.redis.pool.max-idle}")
    private  int maxIdleRedis;
    @Value("${spring.redis.pool.max-wait}")
    private  long maxWaitMillisRedis;
    @Value("${spring.redis.password}")
    private  String passwordRedis;
    @Value("${spring.redis.defaultExpire}")
    private int defaultExpireRedis;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodesRedis;

    /*
     * @Desc Spring容器加载时调用
     * @Author whli
     * @Version 1.0
     * @Date 2018/5/29 16:16
     * @Params []
     * @Return void
     */
    @PostConstruct
    public void init() {
        host = hostRedis;
        port = portRedis;
        timeout = timeoutRedis;
        total = totalRedis;
        maxIdle = maxIdleRedis;
        maxWaitMillis = maxWaitMillisRedis;
        password = passwordRedis;
        defaultExpire = defaultExpireRedis;
        clusterNodes = clusterNodesRedis;
    }
}
