package com.whli.jee.core.web.entity;

import java.util.HashMap;

/**
 * <em>返回结果JavaBean</em>
 * Created by whli on 2018/1/18.
 */
public class ResponseBean extends HashMap{
    private Boolean succeed;
    private Integer count;
    private String code;
    private String message;
    private Object results;

    public ResponseBean(){

    }

    private ResponseBean(Boolean succeed){
        this(succeed,null);
    }

    private ResponseBean(Boolean succeed,String code){
        this(succeed,code,null);
    }

    private ResponseBean(Boolean succeed,String code,String message){
        this(succeed,code,message,null);
    }

    private ResponseBean(String code){
        this(code,null);
    }

    private ResponseBean(String code,String message){
        this(code,message,null);
    }

    private ResponseBean(String code,Object results){
        this(code,null,results);
    }

    public ResponseBean(String code,String message,Object results){
        this(null,code,message,results);
    }

    public ResponseBean(Boolean succeed,String code,String message,Object results){
        this.setSucceed(succeed);
        this.setCode(code);
        this.setMessage(message);
        this.setResults(results);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        put("code",code);
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        put("message",message);
        this.message = message;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        put("results",results);
        this.results = results;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public void setSucceed(Boolean succeed) {
        put("succeed",succeed);
        this.succeed = succeed;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        put("count",count);
        this.count = count;
    }
}
