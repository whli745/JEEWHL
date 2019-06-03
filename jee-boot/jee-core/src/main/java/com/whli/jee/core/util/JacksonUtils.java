package com.whli.jee.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;

import java.io.IOException;

/**
 * <p>Jackson工具类</p>
 * @author whli
 * @date 2019/4/18 14:38
 */
public class JacksonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JacksonUtils(){}

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 将Pojo转换为Json字符串
     * @param value json字符串
     * @return
     */
    public static String pojoToJson(Object value){
        String result = "";
        try {
            result = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将Pojo转换为Json字符串，忽略空值
     * @param value json字符串
     * @return
     */
    public static String pojoToJsonIgnoreNull(Object value){
        String result = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            result = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将Json字符串转换为Pojo
     * @param value json字符串
     * @param valueType pojo数据类型
     * @return
     */
    public static <T> T jsonToPojo(String value, Class<T> valueType){
        T pojo = null;
        try {
            pojo = objectMapper.readValue(value,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    /**
     * 将Json字符串转换为Pojo，忽略空值
     * @param value json字符串
     * @param valueType pojo数据类型
     * @return
     */
    public static <T> T jsonToPojoIgnoreNull(String value, Class<T> valueType){
        T pojo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            pojo = mapper.readValue(value,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    /**
     * 将pojo转换为指定的数据类型
     * @param pojo 需要转换的pojo
     * @param toValueType 指定的数据类型
     * @return
     */
    public static <T> T pojoToPojo(Object pojo,Class<T> toValueType){
        return objectMapper.convertValue(pojo,toValueType);
    }

    /**
     * 将Json字符串转换为数组
     * @param value  json字符串
     * @param elementClass  数组类型
     * @return
     */
    public static <T> T jsonToArray(String value,Class<?> elementClass){
        T pojo = null;
        try {
            pojo = objectMapper.readValue(value,constructArrayType(elementClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    /**
     * 将Json字符串转换为Collection
     * @param value json字符串
     * @param collectionClass  collection类型
     * @param elementClass  collection内部元素数据类型
     * @return
     */
    public static <T> T jsonToCollection(String value, Class<?> collectionClass,Class<?> elementClass){
        T pojo = null;
        try {
            pojo = objectMapper.readValue(value,constructCollectionLikeType(collectionClass,elementClass));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pojo;
    }

    /**
     * 指定Collection类型及其内部元素数据类型
     * @param collectionClass Collection类型
     * @param elementClass Collection内部元素数据类型
     * @return
     */
    public static CollectionLikeType constructCollectionLikeType(Class<?> collectionClass,Class<?> elementClass){
        return objectMapper.getTypeFactory().constructCollectionLikeType(collectionClass,elementClass);
    }

    /**
     * 指定数组类型
     * @param elementClass 数组类型
     * @return
     */
    public static ArrayType constructArrayType(Class<?> elementClass){
        return objectMapper.getTypeFactory().constructArrayType(elementClass);
    }
}
