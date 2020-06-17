package com.zds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/6/10 21:37
 */
public class XmlCreateUtils {

    private static final Logger logger = LogManager.getLogger(XmlCreateUtils.class);

    /**
     * 通过dom创建xml
     * 原生方式，通过document统一创建xml元素
     */
    static void createXmlByDom() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            logger.info("create userDom.xml begins ...");
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
            logger.info("create userDom.xml successfully...");
        } catch (ParserConfigurationException | TransformerException e) {
            logger.error("Failed to create userDom.xml ... ", e);
        }
    }

    /**
     * sax方式个人感觉不怎么好用
     */
    static void createXmlBySax() {
        // 创建sax转换类的工厂
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        try {
            logger.info("begin to create userSax.xml ... ");
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

    /**
     * 通过jDom创建xml文件
     * 1、生成根元素节点
     * 2、由根元素创建document对象
     * 3、需要事先定义好每个元素，再对各个元素设置父子结构
     */
    static void createXmlByJDom() {
        logger.info("begin to create userJDom.xml ... ");
        // 1、创建根元素节点
        org.jdom2.Element rootElement = new org.jdom2.Element("root");
        // 2、为根节点创建属性
        rootElement.setAttribute("name", "root");
        // 3、生成Document对象
        org.jdom2.Document document = new org.jdom2.Document(rootElement);
        // 4、创建其他元素节点
        org.jdom2.Element element = new org.jdom2.Element("users");
        org.jdom2.Element elementChild1 = new org.jdom2.Element("user");
        org.jdom2.Element elementChild2 = new org.jdom2.Element("user");
        // 5、设置元素节点信息
        elementChild1.setAttribute("id", "1");
        elementChild2.setAttribute("id", "2");
        org.jdom2.Element name = new org.jdom2.Element("name");
        org.jdom2.Element age = new org.jdom2.Element("age");
        org.jdom2.Element name1 = new org.jdom2.Element("name");
        org.jdom2.Element age1 = new org.jdom2.Element("age");
        name.setText("TomJDom");
        age.setText("23");
        name1.setText("RoseJDom");
        age1.setText("24");
        List<org.jdom2.Element> elements = new ArrayList<org.jdom2.Element>(Arrays.asList(name, age));
        List<org.jdom2.Element> elements1 = new ArrayList<org.jdom2.Element>(Arrays.asList(name1, age1));
        elementChild1.addContent(elements);
        // FIXME: 2020/6/15 已经定义的Element元素（name，age）不可以复用，但重复定义Element会造成内存浪费，后续寻找可替代方案
//        elements.get(0).setText("RoseJDom");
//        elements.get(1).setText("24");
        elementChild2.addContent(elements1);
        // 6、设置各个元素节点的从属关系
        rootElement.addContent(element);
        element.addContent(elementChild1);
        element.addContent(elementChild2);
        // 7、设置生成xml文档格式
        Format format = Format.getCompactFormat();
        format.setEncoding("UTF-8");
        // 设置换行缩进二个空格
        format.setIndent("  ");
        // 8、创建XMLOutputter对象
        XMLOutputter xmlOutputter = new XMLOutputter(format);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("src/main/resources/userJDom.xml"));
            xmlOutputter.output(document, fileOutputStream);
            logger.info("create userJDom.xml successfully ... ");
        } catch (IOException e) {
            logger.error("Failed to create userJDom.xml ... ");
        }

    }

    /**
     * 通过dom4j创建xml
     * 1、创建document对象
     * 2、document对象创建根元素
     * 3、根元素创建其他元素
     * 4、各级元素可以通过父元素直接创建，不像dom，所有元素都由document创建
     * 5、也不像jDom，先创建好各个元素后，再设置各元素父子节点关系
     * 6、通过XmlWriter输出流将document写入xml文档
     */
    static void createXmlByDom4j() {
        logger.info("begin to create userDom4j.xml ...");
        // 1、创建dom对象
        org.dom4j.Document document = DocumentHelper.createDocument();
        // 2、创建元素节点
        org.dom4j.Element root = document.addElement("root");
        org.dom4j.Element users = root.addElement("users");
        org.dom4j.Element user = users.addElement("user");
        org.dom4j.Element name = user.addElement("name");
        org.dom4j.Element age = user.addElement("age");
        // 给元素设置属性信息
        user.addAttribute("id", "1");
        // 设置值
        name.setText("tomDom4j");
        age.setText("23\\t\\n\\r<");
        // 设置生成xml格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setNewLineAfterDeclaration(false);
        // 生成xml
        File file = new File("src/main/resources/userDom4j.xml");
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new XMLWriter(new FileOutputStream(file), format);
            // 设置不转义特殊字符，会把<输出到xml元素节点内容中
            xmlWriter.setEscapeText(true);
            xmlWriter.write(document);
            xmlWriter.close();
            logger.info("create userDom4j.xml successfully...");
        } catch (IOException e) {
            logger.error("Failed to create userDom4j.xml ...");
        }
    }
}
