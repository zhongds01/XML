package com.zds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.DocumentException;
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
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Description: xml解析几种常用方式
 * Author: zhongds
 * Date : 2020/6/3 22:49
 */
class XmlParseUtils {

    private static final Logger logger = LogManager.getLogger(XmlParseUtils.class);


    static File file = new File("D:\\GitHub\\XML\\XmlParse\\src\\main\\resources\\userInfo.xml");
    /**
     * dom解析
     * 优点：方便读取
     * 缺点：一次性加载整个文档，消耗内存
     */
    static void parseByDom() {

        logger.info("通过dom解析xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element rootElement = document.getDocumentElement();
            System.out.println("======根节点" + rootElement.getNodeName() + "开始遍历======");
            NodeList childNodes = rootElement.getChildNodes();
            // byNode(childNodes);
            byElement(childNodes);
            System.out.println("======根节点" + rootElement.getNodeName() + "结束遍历======");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            logger.error("failed to parse xml ...", e);
        }

    }

    /**
     * SAX解析
     */
    static void parseBySax() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
            // FileInputStream inputStream = new FileInputStream(new File("D:\\GitHub\\XML\\XmlParse\\src\\main\\resources\\userInfo.xml"));
            saxParser.parse(file, new HandlerXml());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * jdom解析
     *
     */
    static void parseByJDom(){
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            org.jdom2.Document build = saxBuilder.build(file);
            org.jdom2.Element rootElement = build.getRootElement();
//            List<org.jdom2.Element> children = rootElement.getChildren();
            List<org.jdom2.Element> children = rootElement.getChildren("user");
            for (org.jdom2.Element child : children) {
                List<org.jdom2.Element> stuElements = child.getChildren();
                for (org.jdom2.Element stuElement : stuElements) {
                    System.out.println("名称："+stuElement.getName()+"---值："+stuElement.getValue());
                }
            }
        } catch (JDOMException |IOException e) {
            logger.error("failed to parse xml ...",e);
        }
    }

    /**
     * dom4j解析
     */
    static void parseByDom4j(){
        SAXReader saxReader = new SAXReader();
        try {
            org.dom4j.Document read = saxReader.read(file);
            org.dom4j.Element rootElement = read.getRootElement();
            String name = rootElement.getName();
            System.out.println("xml根节点名称："+name);
            Iterator iterator = rootElement.elementIterator("user");
            while (iterator.hasNext()){
                org.dom4j.Element element = (org.dom4j.Element)iterator.next();
                Iterator iteratorUsers = element.elementIterator();
                while (iteratorUsers.hasNext()){
                    org.dom4j.Element user = (org.dom4j.Element)iteratorUsers.next();
                    System.out.println("名称："+user.getName()+"---值："+user.getStringValue());
                }
            }
        } catch (DocumentException e) {
            logger.error("Failed to parse xml ...");
            if (logger.isDebugEnabled()){
                logger.debug("Failed to parse xml ...");
            }
        }
    }


    private static void byNode(NodeList childNodes) {
        for (int i = 0; i < childNodes.getLength(); i++) {
            NodeList nodeList = childNodes.item(i).getChildNodes();
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("++++开始遍历" + childNodes.item(i).getNodeName() + "节点信息++++");
            }
            for (int j = 0; j < nodeList.getLength(); j++) {
                // Element element = (Element) nodeList.item(j);
                Node item = nodeList.item(j);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println("---节点名称：" + item.getNodeName() + "  ---节点值：" + item.getFirstChild().getNodeValue());
                }
            }
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("++++遍历" + childNodes.item(i).getNodeName() + "节点信息结束++++");
            }
        }
    }

    // 将Node强转为Element时，注意先判断node是否为ELEMENT_NODE类型
    private static void byElement(NodeList childNodes) {
        for (int i = 0; i < childNodes.getLength(); i++) {
//            NodeList childNodes1 = childNodes.item(i).getChildNodes();
//            Node item = childNodes.item(i);
//            if (item.getNodeType() == Node.ELEMENT_NODE){
//                System.out.println("++++开始遍历" + item.getNodeName() + "节点信息++++");
//            }
//            for (int j = 0; j < childNodes1.getLength(); j++) {
//                if (childNodes1.item(j).getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) childNodes1.item(j);
//                    System.out.println("---节点名称：" + element.getNodeName() + "  ---节点值：" + element.getFirstChild().getNodeValue());
//                }
//            }
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element elementList = (Element) childNodes.item(i);
                System.out.println("++++开始遍历" + childNodes.item(i).getNodeName() + "节点信息++++");
                for (int j = 0; j < elementList.getChildNodes().getLength(); j++) {
                    if (elementList.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) elementList.getChildNodes().item(j);
                        System.out.println("---节点名称：" + element.getNodeName() + "  ---节点值：" + element.getFirstChild().getNodeValue());
                    }
                }
            }
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println("++++遍历" + childNodes.item(i).getNodeName() + "节点信息结束++++");
            }
        }
    }

    static class HandlerXml extends DefaultHandler {
        /**
         * 开始解析xml文件
         *
         * @throws SAXException
         */
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            System.out.println("start parse xml root...");
        }

        /**
         * 结束解析xml文件
         *
         * @throws SAXException
         */
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            System.out.println("end parse xml root...");
        }

        /**
         * 开始解析xml元素
         *
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            // System.out.println("start parse xml element");
            if (!"".equals(qName)){
                System.out.println("start..."+qName);
            }
        }

        /**
         * 结束解析xml元素
         *
         * @param uri
         * @param localName
         * @param qName
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (!"".equals(qName)){
                System.out.println("end..."+qName);
            }
            // System.out.println("end parse xml element");
        }

        /**
         * 处理每个element元素过程
         *
         * @param ch
         * @param start
         * @param length
         * @throws SAXException
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            String s = new String(ch, start, length).trim();
            if (!"".equals(s)) {
                System.out.println(s);
            }

        }
    }
}
