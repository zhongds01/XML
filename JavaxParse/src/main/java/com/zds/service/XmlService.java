package com.zds.service;

import org.dom4j.Attribute;
import org.dom4j.io.SAXReader;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/5/22 22:24
 */
public class XmlService {

    /**
     * dom方式解析xml文件
     */
    public static void parseByDom() {

        File file = new File("D:\\GitHub\\XML\\JavaxParse\\src\\main\\resources\\UserInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            // Document document = documentBuilder.parse(file);
            Document document = documentBuilder.parse(file);
            // 获取xml文档的根节点
            Element documentElement = document.getDocumentElement();
            System.out.println("xml文档的根节点为 ： " + documentElement.getChildNodes());
            // 获取xml文档的指定节点
            String documentURI = document.getDocumentURI();
            System.out.println("xml文档的路径为 ： " + documentURI);
            // 获取xml文档的指定标签下的所有子节点
            NodeList nodeList = document.getElementsByTagName("user");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node item = nodeList.item(i);
                NodeList childNodes;
                if (item.hasChildNodes()) {
                    childNodes = item.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            System.out.print(childNodes.item(j).getNodeName() + ":");
                            System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                        }
                    }
                }
            }
            /*for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element)nodeList.item(i);
                NodeList childNodes;
                if (element.hasChildNodes()) {
                    childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        // Node.ELEMENT_NODE表示
                        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
                            System.out.print(childNodes.item(j).getNodeName() + ":");
                            System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                        }

                    }
                }
            }*/

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseBySax() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        File file = new File("D:\\GitHub\\XML\\JavaxParse\\src\\main\\resources\\UserInfo.xml");
        saxParser.parse(file, new SaxHandler());
    }

    /**
     * 继承DefaultHandler类，实现2start、2end、1character方法
     * sax解析会按照xml文档的顺序逐个解析，不管是什么节点
     * users->user->name->age->phone->address->createDate->user->user->name->age->phone->address->createDate->user->users
     */
    static class SaxHandler extends DefaultHandler {

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            String value = new String(ch, start, length).trim();
            if (!value.equals("")) {
                System.out.println(value);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (qName.equals("user") || qName.equals("users")) {
                System.out.println("====" + qName + "遍历结束" + "====");
            }
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("user") || qName.equals("users")) {
                System.out.println("====" + qName + "遍历开始" + "====");
            } else {
                System.out.print("节点名称： " + qName + "===节点值：");
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            System.out.println("==========sax解析xml结束...==========");
        }


        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            System.out.println("==========sax解析xml开始...==========");
        }
    }

    static void parseByDom4J() throws Exception {
        // 1、创建xml解析对象
        SAXReader reader = new SAXReader();
        // 2、解析指定文件
        org.dom4j.Document document = reader.read(new File("D:\\GitHub\\XML\\JavaxParse\\src\\main\\resources\\UserInfo.xml"));
        // 3、获取根节点
        org.dom4j.Element rootElement = document.getRootElement();
        System.out.println("根节点名称为：" + rootElement.getName());
        // 4、获取根节点下指定节点信息
        System.out.println("++++获取根节点下指定user节点的name信息开始++++");
        List<org.dom4j.Element> user = rootElement.elements("user");
        for (org.dom4j.Element element : user) {
            org.dom4j.Element nameValue = element.element("name");
            System.out.println("指定获取user节点下的name值" + nameValue.getStringValue());
        }
        System.out.println("----获取根节点下指定user节点的name信息结束----");
        System.out.println("++++迭代根节点下指定user节点的信息开始++++");
        // 5、迭代根节点下指定节点信息
        Iterator users = rootElement.elementIterator("user");
        while (users.hasNext()) {
            // 获取user节点
            org.dom4j.Element userChild = (org.dom4j.Element) users.next();
            Iterator iterator = userChild.elementIterator();
            while (iterator.hasNext()){
                // 获取user节点下的所有子节点
                org.dom4j.Element userChildInfo =  (org.dom4j.Element) iterator.next();
                System.out.println("节点名："+userChildInfo.getName()+"---节点值："+userChildInfo.getStringValue());
            }
        }
        System.out.println("----迭代根节点下指定user节点的信息结束----");
        System.out.println("++++迭代根节点下所有节点的信息开始++++");
        // 6、迭代方式获取根节点下所有节点信息
        Iterator iterator = rootElement.elementIterator();
        System.out.println("----开始获取xml节点内容----");
        while (iterator.hasNext()) {
            org.dom4j.Element element = (org.dom4j.Element) iterator.next();
            List<Attribute> attributes = element.attributes();
            for (Attribute attribute : attributes) {
                System.out.println(attribute.getName()+attribute.getValue()+attribute.getText());
            }
            System.out.println("遍历子节点");
            Iterator iterator2 = element.elementIterator();
            while (iterator2.hasNext()) {
                org.dom4j.Element stuChild = (org.dom4j.Element) iterator2.next();
                System.out.println("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
        System.out.println("----结束获取xml节点内容----");
        System.out.println("----迭代根节点下所有节点的信息结束----");
    }

    static void parseByJDom() {
        // 1、创建saxbuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        // 2、创建输入流
        try {
            InputStream inputStream = new FileInputStream(new File("D:\\GitHub\\XML\\JavaxParse\\src\\main\\resources\\UserInfo.xml"));
            // 3、将输入流加载到sax解析对象中
            org.jdom2.Document document = saxBuilder.build(inputStream);
            // 4、获取根节点
            org.jdom2.Element rootElement = document.getRootElement();
            System.out.println("根节点名称为："+rootElement.getName());
            // 5、获取根节点下的指定节点
            List<org.jdom2.Element> user = rootElement.getChildren("user");
            // 6、遍历子节点user信息
            for (org.jdom2.Element element : user) {
                List<org.jdom2.Element> children = element.getChildren();
                // 7、开始遍历子节点的各个标签属性
                for (org.jdom2.Element child : children) {
                    System.out.println("节点名："+child.getName()+"---节点值："+child.getValue());
                }
            }
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        // parseByDom();
        // parseBySax();
        // parseByDom4J();
        parseByJDom();
    }
}
