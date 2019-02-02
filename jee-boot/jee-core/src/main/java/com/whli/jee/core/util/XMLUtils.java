package com.whli.jee.core.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <b><em>dom4j操作XML文件</em></b>
 *
 * @author whli
 * @version 2018/9/2 11:24
 */
public class XMLUtils {
    private static Document document;
    private static SAXReader reader = new SAXReader();

    static {
        try {
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public XMLUtils(){

    }

    /**
     * 设定全局Document
     * @param file
     */
    public static void setDocument(File file){
        if (file != null){
            try {
                document = reader.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设定全局Document
     * @param is
     */
    public static void setDocument(InputStream is){
        if (is != null){
            try {
                document = reader.read(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取根据节点
     * @return
     */
    public static Element getRootElement(){
        Element root = document.getRootElement();
        return root;
    }

    /**
     * 获取某个节点下的所有子节点
     * @param element
     * @return
     */
    public static List<Element> getChildElements(Element element){
        List<Element> childElements = element.elements();
        if (CollectionUtils.isNotNullOrEmpty(childElements)){
            return childElements;
        }
        return null;
    }

    /**
     * 查询指定节点的指定名称子节点
     * @param parentElement
     * @param nodeName
     * @return
     */
    public static Element getChildElementByName(Element parentElement,String nodeName){
        List<Element> childElements = getChildElements(parentElement);
        if (CollectionUtils.isNotNullOrEmpty(childElements)){
            for (Element childElement : childElements){
                if (nodeName.equals(childElement.getName())){
                    return childElement;
                }
                getChildElementByName(childElement,nodeName);
            }
        }
        return null;
    }

    /**
     * 获取指定名称的字节点值
     * @param parentElement
     * @param nodeName
     * @return
     */
    public static String getChildElementValueByName(Element parentElement,String nodeName){
        Element childElement = getChildElementByName(parentElement,nodeName);
        if (childElement != null){
            return childElement.getTextTrim();
        }
        return null;
    }

    /**
     * <em>根据属性查询指定节点下的子节点</em>
     * @param root
     * @return
     */
    public static Element getChildElementByAttribute(Element root,String attributeKey){
       return getChildElementByAttribute(root,attributeKey,null);
    }

    /**
     * <em>根据属性值查询指定节点下的子节点</em>
     * @param root
     * @param attributeValue
     * @return
     */
    public static Element getChildElementByAttribute(Element root,String attributeKey,String attributeValue){
        List<Element> childrenElements = getChildElements(root);
        if (CollectionUtils.isNotNullOrEmpty(childrenElements)){
            for (Element childrenElement : childrenElements){
                List<DefaultAttribute> attributes = childrenElement.attributes();
                if (CollectionUtils.isNotNullOrEmpty(attributes)){
                    for (int i = 0;i < attributes.size();i++){
                        DefaultAttribute attribute = attributes.get(i);
                        if (StringUtils.isNotNullOrBlank(attributeKey) && StringUtils.isNotNullOrBlank(attributeValue)){
                            if (attributeKey.equals(attribute.getName()) && attributeValue.equals(attribute.getValue())){
                                return childrenElement;
                            }
                        }else if (StringUtils.isNotNullOrBlank(attributeKey)){
                            if (attributeKey.equals(attribute.getName())){
                                return childrenElement;
                            }
                        }else if (StringUtils.isNotNullOrBlank(attributeValue)){
                            if (attributeValue.equals(attribute.getName())){
                                return childrenElement;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 更改节点的值
     * @param root
     * @param elementText
     */
    public static void updateElement(Element root,String elementText){
        root.setText(elementText);
    }

    /**
     * 修改指定节点的指定属性值
     * @param root
     * @param attributeKey
     * @param attributeValue
     */
    public static void updateAttribute(Element root,String attributeKey,String attributeValue){
        root.attributeValue(attributeKey,attributeValue);
    }

    /**
     * 增加节点
     * @param parentElement
     * @param nodeName
     */
    public static void addChildElement(Element parentElement,String nodeName){
        addChildElement(parentElement,nodeName,null);
    }


    /**
     * 增加节点
     * @param parentElement
     * @param nodeName
     * @param nodeText
     */
    public static void addChildElement(Element parentElement,String nodeName,String nodeText){
        addChildElement(parentElement,nodeName,nodeText,null);
    }


    /**
     * 增加节点
     * @param parentElement
     * @param nodeName
     * @param nodeText
     * @param attributes
     */
    public static void addChildElement(Element parentElement,String nodeName,String nodeText,Map<String,String> attributes){
        if (StringUtils.isNotNullOrBlank(nodeName)){
            Element childElement = parentElement.addElement(nodeName);
            if (StringUtils.isNotNullOrBlank(nodeText)){
                childElement.setText(nodeText);
            }
            if (MapUtils.isNotNullOrEmpty(attributes)){
                Iterator<Map.Entry<String,String>> attribute = attributes.entrySet().iterator();
                while (attribute.hasNext()){
                    Map.Entry<String,String> entry = attribute.next();
                    updateAttribute(childElement,entry.getKey(),entry.getValue());
                }
            }
        }
    }

    /**
     * 保存XML文件
     * @param xmlFile
     */
    public static void saveDocument(File xmlFile){
        try {
            Writer osWrite=new OutputStreamWriter(new FileOutputStream(xmlFile));//创建输出流
            OutputFormat format = OutputFormat.createPrettyPrint();  //获取输出的指定格式
            format.setEncoding("UTF-8");//设置编码 ，确保解析的xml为UTF-8格式
            XMLWriter writer = new XMLWriter(osWrite,format);//XMLWriter 指定输出文件以及格式
            writer.write(document);//把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
