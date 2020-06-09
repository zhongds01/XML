package com.zds.service;

import org.junit.Test;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/6/3 22:40
 */
public class XmlServiceTest {
    @Test
    public void parseByDom() throws Exception {
        XmlService.parseByDom();
    }

    @Test
    public void parseBySax() throws Exception {
        XmlService.parseBySax();
    }

    @Test
    public void parseByDom4J() throws Exception {
        XmlService.parseByDom4J();
    }

    @Test
    public void parseByJDom() throws Exception {
        XmlService.parseByJDom();
    }

}