package net.web;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UrlToServlet {

    public static Servlet getServlet(String url) throws ClassNotFoundException {
        Servlet servlet;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2.从解析工厂获取解析器
            SAXParser parser = factory.newSAXParser();
            //3.编写处理器
            WebHandler pHandler = new WebHandler();
            //getResourceAsStream("net/web/web.xml")不能用点net.web.web.xml  用点就相当于是包了？？
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("net/web/web.xml"),pHandler);
            String name = pHandler.getUrl_name().get(url);
            String servletClass = pHandler.getName_class().get(name);
            if (servletClass!=null){
                System.out.println("没有执行");
                Class<?> aClass = Class.forName(servletClass);
                if (aClass!=null){
                    Constructor<?> constructor = aClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Object o = constructor.newInstance();
                    return (Servlet)o;
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
