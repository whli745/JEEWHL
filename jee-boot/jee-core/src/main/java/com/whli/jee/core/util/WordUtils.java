package com.whli.jee.core.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Created by whli on 2017/11/22.
 */
public class WordUtils {
    /**
     * @Desc：使用velocity模板生成word文件
     * @Author：whli
     * @Date：2014-1-22下午05:33:42
     * @param dataMap word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：test.ftl
     * @param servletContext web服务器上下文
     * @param fileName 生成的文件名称，例如：test.doc
     */
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public static void createWord(Map dataMap, String templateName, Object servletContext, String fileName){
        try {
            //创建配置实例
            Configuration configuration = new Configuration();

            //设置编码
            configuration.setDefaultEncoding("UTF-8");

            //ftl模板文件统一放至 com.lun.template 包下面
            configuration.setServletContextForTemplateLoading(servletContext,"/static/template");

            //获取模板
            Template template = configuration.getTemplate(templateName);

            //输出文件
            File outFile = new File("C:/temp"+ File.separator+fileName);

            //如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }

            //将模板和数据模型合并生成文件
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));


            //生成文件
            template.process(dataMap, out);

            //关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
