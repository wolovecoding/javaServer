package net.web;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

/**
 * 我自己重写的startElement和characters要好好琢磨琢磨，很妙
 *
 * SAX会把xml文档存到char[] ch数组里 包括回车和换行， 从数组的起始位置开始遍历，
 * 遇到开始标签会调用startElement,遇到结束标签会调用endElement
 * >    < 这两个箭头之间的会调用characters方法
 *
 *
 */
public class WebHandler extends DefaultHandler {
    private HashMap<String,String> url_name;
    private HashMap<String,String> name_class;
    private String k,v;  //因为解析xml的特点，所以要用k,v暂存characters函数中的..
    int tag;//是servlet还是mapping
    int tag2;//相当于同步区，为了让startElement、characters之间进行通信，知道对方状态
    final int SERVLET = 1;
    final int MAPPING = 2;
    final int SERVLET_NAME = 4;
    final int SERVLET_CLASS = 8;
    final int URL_PATTERN = 8;
    @Override
    public void startDocument() throws SAXException {
        System.out.println("开始解析------");
        url_name = new HashMap<>();
        name_class = new HashMap<>();
    }

    /**
     *
     * @param uri 和namespace有关
     * @param localName   相当于namespace里面的id?
     * @param qName    标签名
     * @param attributes   是哪个类的，
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "servlet":
                tag = this.SERVLET;
                break;
            case "servlet-mapping":
                tag = this.MAPPING;
                break;
            case "servlet-name":
                tag2 = this.SERVLET_NAME;
                break;
            case "servlet-class":
                tag2 = this.SERVLET_CLASS;
                break;
            case "url-pattern":
                tag2 = this.URL_PATTERN;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s =new String(ch,start,length);
        switch (tag+tag2) {
            case 5:
                k = s;
                break;
            case 9:
                v = s;
                break;
            case 10:
                k = s;
                break;
            case 6:
                v = s;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("servlet")) {
            name_class.put(k,v);
            tag = 0;
        } else if(qName.equals("servlet-mapping")) {
            url_name.put(k,v);
            tag = 0;
        }
        tag2 = 0;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("解析结束");
    }

    public HashMap<String, String> getUrl_name() {
        return url_name;
    }

    public HashMap<String, String> getName_class() {
        return name_class;
    }
}
