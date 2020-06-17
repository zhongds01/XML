package com.zds;

import org.junit.Test;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/6/10 21:43
 */
public class XmlCreateUtilsTest {
    @Test
    public void createXmlByJDom() throws Exception {
        XmlCreateUtils.createXmlByJDom();
    }

    @Test
    public void createXmlByDom4j() throws Exception {
        XmlCreateUtils.createXmlByDom4j();
    }

    @Test
    public void createXmlBySax() throws Exception {
        XmlCreateUtils.createXmlBySax();
    }

    @Test
    public void createXmlByDom() throws Exception {
        XmlCreateUtils.createXmlByDom();
    }

}