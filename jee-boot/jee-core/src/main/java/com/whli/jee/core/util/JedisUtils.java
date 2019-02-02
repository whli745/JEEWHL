package com.whli.jee.core.util;

import com.whli.jee.core.cache.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Desc Redis客户端操作工具Jedis类
 * @Author whli
 * @Version 1.0
 * @Date 2018/6/2 12:51
 */
public class JedisUtils {
    private static JedisPool jedisPool;
    //成功返回标识
    private static final String SUCCESS_STATUS_OK = "OK";
    private static final Long SUCCESS_STATUS_LONG = 1L;


    /**
     * @Desc 获取单例JedisPool
     * @Author whli
     * @Version 1.0
     * @Date 2018/6/2 14:14
     * @Params []
     * @Return redis.clients.jedis.JedisPool
     */
    public static JedisPool getJedisPool(){
        if (null == jedisPool){
            synchronized (JedisUtils.class){
                if (null == jedisPool){
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxIdle(RedisConfig.maxIdle);
                    config.setMaxWaitMillis(RedisConfig.maxWaitMillis);
                    config.setMaxTotal(RedisConfig.total);
                    jedisPool = new JedisPool(config,RedisConfig.host,RedisConfig.port);
                }
            }
        }
        return jedisPool;
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
        Jedis jedis = null;
        String statusCode = null;
        try {
            jedis = getJedisPool().getResource();
            statusCode = jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                jedis.close();
            }
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
        Jedis jedis = null;
        String value = null;
        try{
            jedis = getJedisPool().getResource();
            value = jedis.get(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
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
        Jedis jedis = null;
        Long statusCode = null;
        try{
            jedis = getJedisPool().getResource();
            statusCode = jedis.exists(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
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
        Jedis jedis = null;
        Long statusCode = null;

        try {
            jedis = getJedisPool().getResource();
            statusCode = jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
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
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedisPool().getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
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
        Jedis jedis = null;
        Long statusCode = null;
        try {
            jedis =getJedisPool().getResource();
            statusCode = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
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
        Jedis jedis =null;
        Long statusCode = null;
        try {
            if (seconds < 0){
                return false;
            }
            jedis =getJedisPool().getResource();
            statusCode = jedis.expire(key,seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        if (SUCCESS_STATUS_LONG.compareTo(statusCode) == 0){
            return true;
        }
        return false;
    }
}
