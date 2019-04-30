package com.whli.jee.core.util;

import com.whli.jee.core.cache.RedisConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc Redis集群客户端操作工具类
 * @Author whli
 * @Version 1.0
 * @Date 2018/6/2 12:51
 */
public class JedisClusterUtils {
    private static JedisCluster jedisCluster;
    //成功返回标识
    private static final String SUCCESS_STATUS_OK = "OK";
    private static final Long SUCCESS_STATUS_LONG = 1L;

    private JedisClusterUtils(){}

    /**
     * @Desc 获取单例JedisPool
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:14
     * @Params []
     * @Return redis.clients.jedis.JedisPool
     */
    public static JedisCluster getJedisCluster(){
        if (null == jedisCluster){
            synchronized (JedisClusterUtils.class){
                if (null == jedisCluster){
                    String [] serverArray=RedisConfig.clusterNodes.split(",");
                    Set<HostAndPort> nodes=new HashSet<HostAndPort>();
                    for (String ipPort:serverArray){
                        String [] ipPortPair=ipPort.split(":");
                        nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));

                    }
                    return  new JedisCluster(nodes,RedisConfig.timeout);
                }
            }
        }
        return jedisCluster;
    }

    /**
     * @Desc 存储key-value样式的数据
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:27
     * @Params [key 键, value 值]
     * @Return void
     */
    public static boolean set(String key,String value){
        JedisCluster jedis = null;
        String statusCode = null;
        try {
            jedis = getJedisCluster();
            statusCode = jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SUCCESS_STATUS_OK.equals(statusCode)){
            return true;
        }
        return false;
    }

    /**
     * @Desc 根据key获取redis里key-value样式的值
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:32
     * @Params [key]
     * @Return java.lang.String
     */
    public static String get(String key){
        JedisCluster jedis = null;
        String value = null;
        try{
            jedis = getJedisCluster();
            value = jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    /**
     * @Desc 判断key是否存在
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 9:59
     * @Params [key]
     * @Return boolean
     */
    public static boolean exists(String... key){
        JedisCluster jedis = null;
        Long statusCode = null;
        try{
            jedis = getJedisCluster();
            statusCode = jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (SUCCESS_STATUS_LONG.compareTo(statusCode) == 0){
            return true;
        }
        return false;
    }

    /**
     * @Desc 存储key:{filed:value}样式的数据
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:32
     * @Params [key, filed, value]
     * @Return void
     */
    public static boolean hSet(String key,String field,String value){
        JedisCluster jedis = null;
        Long statusCode = null;

        try {
            jedis = getJedisCluster();
            statusCode = jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SUCCESS_STATUS_LONG.compareTo(statusCode) == 0){
            return true;
        }
        return false;
    }

    /**
     * @Desc 根据key,filed获取redis里key:{field:value}样式的值
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:35
     * @Params [key, filed]
     * @Return java.lang.String
     */
    public static String hGet(String key,String field){
        JedisCluster jedis = null;
        String value = null;
        try {
            jedis = getJedisCluster();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * @Desc 根据删除值
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 23:40
     * @Params [key]
     * @Return void
     */
    public static boolean delete(String... key){
        JedisCluster jedis = null;
        Long statusCode = null;
        try {
            jedis = getJedisCluster();
            statusCode = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SUCCESS_STATUS_LONG.compareTo(statusCode) == 0){
            return true;
        }
        return false;
    }

    /**
     * @Desc 设置key过期时间
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:49
     * @Params [key, seconds]
     * @Return boolean
     */
    public static boolean expireDefault(String key){
        return expire(key,RedisConfig.defaultExpire);
    }

    /**
     * @Desc 设置key过期时间
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/3 0:49
     * @Params [key, seconds]
     * @Return boolean
     */
    public static boolean expire(String key,int seconds){
        JedisCluster jedis =null;
        Long statusCode = null;
        try {
            if (seconds < 0){
                return false;
            }
            jedis = getJedisCluster();
            statusCode = jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SUCCESS_STATUS_LONG.compareTo(statusCode) == 0){
            return true;
        }
        return false;
    }
}
