package com.whli.jee.system.entity;

import com.whli.jee.core.web.entity.BaseEntity;

import java.util.List;

/**
 * 系统日志
 *
 * @author whli
 * @version 1.0
 * @since 1.0
 */
public class SysLog extends BaseEntity{
    private String type;  //操作类型（增加、修改、删除）
    private String tableName;  //被操作表
    private String operationDetail;  //操作明细
    private String requestUri;  //请求URI
    private String ip;  //操作ip
    private String hostName;  //操作人主机

    public SysLog() {
    }

    public void setRequestUri(String value) {
        this.requestUri = value;
    }

    public String getRequestUri() {
        return this.requestUri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOperationDetail() {
        return operationDetail;
    }

    public void setOperationDetail(String operationDetail) {
        this.operationDetail = operationDetail;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
