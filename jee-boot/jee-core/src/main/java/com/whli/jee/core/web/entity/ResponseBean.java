package com.whli.jee.core.web.entity;

import java.util.HashMap;

/**
 * <em>返回结果JavaBean</em>
 * Created by whli on 2018/1/18.
 */
public class ResponseBean extends HashMap{
    private String code;
    private String message;
    private Integer count;
    private Object data;

    public String getCode() {
        return code;
    }

    public ResponseBean setCode(String code) {
        put("code",code);
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseBean setMessage(String message) {
        put("message",message);
        this.message = message;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public ResponseBean setCount(Integer count) {
        put("count",count);
        this.count = count;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseBean setData(Object data) {
        put("data",data);
        this.data = data;
        return this;
    }
}
