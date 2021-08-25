package net.web;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;

/**
 * 没用了，放到UrlToServlet里了
 */
public class ParseXML extends DefaultHandler {
    ParseXML(){
        //1.获取解析工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2.从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();
            //3.编写处理器
            WebHandler pHandler = new WebHandler();
            //getResourceAsStream("net/web/web.xml")不能用点net.web.web.xml  用点就相当于是包了？？
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("net/web/web.xml"),pHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


