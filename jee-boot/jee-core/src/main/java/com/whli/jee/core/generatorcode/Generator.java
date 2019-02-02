package com.whli.jee.core.generatorcode;

import java.util.List;

/**
 * <em>代码生成基本类</em>
 * @author whli
 * @version 2018/9/4 9:26
 */
public class Generator {
    /** 包名 */
    private String basePackage;
    /** 静态html文件所在文件夹 */
    private String namespace;
    /** 代码生成输出路径 */
    private String outRoot;
    /** 数据库用户名 */
    private String dataUsername;
    /** 数据库密码 */
    private String dataPassword;
    /** 数据库url */
    private String dataUrl;
    /** 数据库驱动（默认mysql） */
    private String dataDriver;
    /** 包名 */
    private String dataSchema;
    private String dataCatalog;
    /** 忽略表前缀，多个以','分隔(ts_,tm_) */
    private String tableRemovePrefixes;
    /** 需要一键生成代码的表名 */
    private List<String> tableNames;

    public Generator() {
    }

    public Generator(String basePackage, String namespace, String outRoot, String dataUsername, String dataPassword,
                     String dataUrl, String dataDriver, String dataSchema, String dataCatalog,
                     String tableRemovePrefixes, List<String> tableNames) {
        this.basePackage = basePackage;
        this.namespace = namespace;
        this.outRoot = outRoot;
        this.dataUsername = dataUsername;
        this.dataPassword = dataPassword;
        this.dataUrl = dataUrl;
        this.dataDriver = dataDriver;
        this.dataSchema = dataSchema;
        this.dataCatalog = dataCatalog;
        this.tableRemovePrefixes = tableRemovePrefixes;
        this.tableNames = tableNames;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getOutRoot() {
        return outRoot;
    }

    public void setOutRoot(String outRoot) {
        this.outRoot = outRoot;
    }

    public String getDataUsername() {
        return dataUsername;
    }

    public void setDataUsername(String dataUsername) {
        this.dataUsername = dataUsername;
    }

    public String getDataPassword() {
        return dataPassword;
    }

    public void setDataPassword(String dataPassword) {
        this.dataPassword = dataPassword;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataDriver() {
        return dataDriver;
    }

    public void setDataDriver(String dataDriver) {
        this.dataDriver = dataDriver;
    }

    public String getDataSchema() {
        return dataSchema;
    }

    public void setDataSchema(String dataSchema) {
        this.dataSchema = dataSchema;
    }

    public String getDataCatalog() {
        return dataCatalog;
    }

    public void setDataCatalog(String dataCatalog) {
        this.dataCatalog = dataCatalog;
    }

    public String getTableRemovePrefixes() {
        return tableRemovePrefixes;
    }

    public void setTableRemovePrefixes(String tableRemovePrefixes) {
        this.tableRemovePrefixes = tableRemovePrefixes;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }
}
