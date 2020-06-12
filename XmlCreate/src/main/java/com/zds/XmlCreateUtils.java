package com.zds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/6/10 21:37
 */
public class XmlCreateUtils {

    private static final Logger logger = LoggerFactory.getLogger(XmlCreateUtils.class);

    static void createXmlByDom() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            // 不显示standalone="no"
            document.setXmlStandalone(true);
            // 创建组成xml文档的各个元素
            Element root = document.createElement("root");
            Element user = document.createElement("user");
            Element name = document.createElement("name");
            Element age = document.createElement("age");
            // 为元素设置属性节点
            user.setAttribute("id", "1");
            // 设置文本节点
            name.setTextContent("Tom");
            // name.setNodeValue("xxx");
            age.setTextContent("23");
            // 设置各个元素从属关系
            document.appendChild(root);
            root.appendChild(user);
            user.appendChild(name);
            user.appendChild(age);
            // 构建转换工厂对象获取转换对象
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // 解决换行问题
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");
            // 生成xml文件
            transformer.transform(new DOMSource(document), new StreamResult(new File("src/main/resources/userDom.xml")));
            logger.info("create userDim.xml successfully...");
        } catch (ParserConfigurationException | TransformerException e) {
            logger.error("Failed to create userDom.xml ... ",e);
        }
    }

    /**
     * sax方式个人感觉不怎么好用
     */
    static void createXmlBySax() {
        // 创建sax转换类的工厂
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            // 获取转换对象
            TransformerHandler transformerHandler = factory.newTransformerHandler();
            Transformer transformer = transformerHandler.getTransformer();

            // xml设置
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "");

            // 创建File对象
            File file = new File("src/main/resources/userSax.xml");

            Result result = new StreamResult(new FileOutputStream(file));
            transformerHandler.setResult(result);
            transformerHandler.startDocument();
            AttributesImpl attributes = new AttributesImpl();
            transformerHandler.startElement("", "", "root", attributes);
            attributes.clear();
            // 创建name
            transformerHandler.startElement("", "", "user", attributes);
            attributes.clear();

            transformerHandler.startElement("", "", "name", attributes);
            attributes.clear();
            attributes.addAttribute("", "", "id", "", "1");
            transformerHandler.characters("tomSax".toCharArray(), 0, 6);
            transformerHandler.endElement("", "", "name");

            attributes.clear();
            transformerHandler.startElement("", "", "age", attributes);
            attributes.clear();
            transformerHandler.characters("23".toCharArray(), 0, 2);
            transformerHandler.endElement("", "", "age");

            transformerHandler.endElement("", "", "user");

            transformerHandler.endElement("", "", "root");
            transformerHandler.endDocument();
            logger.info("create userSax.xml successfully ...");
        } catch (IOException | SAXException | TransformerConfigurationException e) {
            logger.error("failed to create userSax.xml ...");
        }
    }
    static void createXmlByJDom(){

    }

    static void createXmlByDom4j(){

    }
}
