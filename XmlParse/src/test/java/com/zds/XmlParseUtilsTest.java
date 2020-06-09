package com.zds;

import org.junit.Test;

/**
 * Description: please add the description
 * Author: zhongds
 * Date : 2020/6/3 23:02
 */
public class XmlParseUtilsTest {
    @Test
    public void parseByDom4j() throws Exception {
        XmlParseUtils.parseByDom4j();
    }

    @Test
    public void parseByJDom() throws Exception {
        XmlParseUtils.parseByJDom();
    }

    @Test
    public void parseBySax() throws Exception {
        XmlParseUtils.parseBySax();
    }

    @Test
    public void parseByDom() throws Exception {
        XmlParseUtils.parseByDom();
    }

}