package com.whli.jee.core.generatorcode;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.util.ClassHelper;
import com.whli.jee.core.util.CollectionUtils;
import com.whli.jee.core.util.StringUtils;
import com.whli.jee.core.util.XMLUtils;
import org.dom4j.Element;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * <b><em>代码一键生成</em></b>
 * @author whli
 * @version 2018/9/2 11:02
 */
public class CodeGenerator {

    /**
     * <b><em>设置代码生成基础参数</em></b>
     * @param basePackage 包名
     * @param namespace 静态html文件所在文件夹
     * @param outRoot  代码生成输出路径
     * @param dataUsername 数据库用户名
     * @param dataPassword 数据库密码
     * @param dataUrl 数据库url
     * @param dataDriver  数据库驱动（默认mysql）
     * @param dataSchema  数据库为Oracle时必须填写
     * @param dataCatalog 数据库为Oracle时必须填写
     * @param tableRemovePrefixes  忽略表前缀，多个以','分隔(ts_,tm_)
     */
    private static void setBasicAttribute(String basePackage,String namespace,String outRoot,String dataUsername,String dataPassword,
            String dataUrl,String dataDriver,String dataSchema,String dataCatalog,String tableRemovePrefixes){
        try {
            URL url = CodeGenerator.class.getClassLoader().getResource("generator.xml");
            File currentFile = new File(url.getFile());
            //将XML转换为Doument文档
            XMLUtils.setDocument(currentFile);
            //获取根节点
            Element rootElement = XMLUtils.getRootElement();
            //设置basePackage
            Element packageElement = XMLUtils.getChildElementByAttribute(rootElement,"key","basepackage");
            if (packageElement != null && StringUtils.isNotNullOrBlank(basePackage)){
                XMLUtils.updateElement(packageElement,basePackage);
            }
            //设置namespace
            Element spaceElement = XMLUtils.getChildElementByAttribute(rootElement,"key","namespace");
            if (spaceElement != null && StringUtils.isNotNullOrBlank(namespace)){
                XMLUtils.updateElement(spaceElement,namespace);
            }
            //设置outRoot
            Element outElement = XMLUtils.getChildElementByAttribute(rootElement,"key","outRoot");
            if (outElement != null && StringUtils.isNotNullOrBlank(outRoot)){
                XMLUtils.updateElement(outElement,outRoot);
            }
            //设置username
            Element userNameElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_username");
            if (userNameElement != null && StringUtils.isNotNullOrBlank(dataUsername)){
                XMLUtils.updateElement(userNameElement,dataUsername);
            }
            //设置password
            Element pwdElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_password");
            if (pwdElement != null && StringUtils.isNotNullOrBlank(dataPassword)){
                XMLUtils.updateElement(pwdElement,dataPassword);
            }
            //设置data_url
            Element urlElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_url");
            if (urlElement != null && StringUtils.isNotNullOrBlank(dataUrl)){
                XMLUtils.updateElement(urlElement,dataUrl);
            }
            //设置data_driver
            Element driverElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_driver");
            if (driverElement != null && StringUtils.isNotNullOrBlank(dataDriver)){
                XMLUtils.updateElement(driverElement,dataDriver);
            }
            //设置data_schema
            Element schemaElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_schema");
            if (schemaElement != null && StringUtils.isNotNullOrBlank(dataSchema)){
                XMLUtils.updateElement(schemaElement,dataSchema);
            }
            //设置data_catalog
            Element catalogElement = XMLUtils.getChildElementByAttribute(rootElement,"key","jdbc_catalog");
            if (catalogElement != null && StringUtils.isNotNullOrBlank(dataCatalog)){
                XMLUtils.updateElement(catalogElement,dataCatalog);
            }
            //设置tableRemovePrefixes
            Element prefixesElement = XMLUtils.getChildElementByAttribute(rootElement,"key","tableRemovePrefixes");
            if (prefixesElement != null && StringUtils.isNotNullOrBlank(tableRemovePrefixes)){
                XMLUtils.updateElement(prefixesElement,tableRemovePrefixes);
            }
            //保存XML文件
            XMLUtils.saveDocument(currentFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <b><em>一键生成代码</em></b>
     * @param all  是否整个数据库生成代码
     * @param ge 单表生成代码
     */
    public static void generatorCode(boolean all,Generator ge){
        try {

            if (StringUtils.isNullOrBlank(ge.getOutRoot())){
                ge.setOutRoot("D:/generator/");
            }

            setBasicAttribute(ge.getBasePackage(),ge.getNamespace(),ge.getOutRoot(),
                    ge.getDataUsername(),ge.getDataPassword(),ge.getDataUrl(),ge.getDataDriver(),ge.getDataSchema(),
                    ge.getDataCatalog(),ge.getTableRemovePrefixes());

            URL url = CodeGenerator.class.getClassLoader().getResource("template");

            GeneratorFacade g = new GeneratorFacade();
            g.getGenerator().addTemplateRootDir(url.getPath());
            if (all){
                g.generateByAllTable();
            }else {
                if (CollectionUtils.isNotNullOrEmpty(ge.getTableNames())){
                    for (String tableName : ge.getTableNames()){
                        g.generateByTable(tableName);
                    }
                }
            }
            //打开所在文件
            Desktop.getDesktop().open(new File(ge.getOutRoot()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
