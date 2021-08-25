package net.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Request {

    private StringBuilder reqInfo;
    private String url;
    private String method;
    private HashMap<String,String> params;
    private final String CRLF ="\r\n";

    public Request(InputStream in) throws IOException {
        reqInfo = new StringBuilder();
        byte[] bytes = new byte[1024];
        int len;
        BufferedInputStream bfin = new BufferedInputStream(in);
        while ((len=bfin.read(bytes))!=-1||len<bytes.length){
            if (len==bytes.length){
                reqInfo.append(new String(bytes,0,len));
            }else if (len<bytes.length&&len!=-1){
                reqInfo.append(new String(bytes,0,len));
                //这个关了的话，accept也会关，及http连接断开
                //bfin.close();
                break;
            }
        }
        parseReq();
    }
    public Request(Socket socket) throws IOException {
        this(socket.getInputStream());
    }

    private void parseReq(){
        params = new HashMap<String, String>();

        method = this.reqInfo.substring(0,this.reqInfo.indexOf("/")).trim();

        int urihd = this.reqInfo.indexOf("/");
        int uried = this.reqInfo.indexOf(" HTTP");
        //得到url，后面还有一步处理
        url = this.reqInfo.substring(urihd,uried);

        if (url.contains("?")){ //解析出url后的参数
            uried = url.indexOf("?");
            String[] splits = url.substring(uried + 1).split("&");
            int i;
            for (String s : splits) {
                i = s.indexOf("=");
                //防止是中文的话乱码，url解码后再存
                params.put(decode(s.substring(0,i)),decode(s.substring(i+1)));
            }
            url = url.substring(0,uried);
        }
        //解析post请求
        if (method.equals("POST")){//post请求格式， 这里只实现了form
            String formData = this.reqInfo.substring(this.reqInfo.lastIndexOf(CRLF)).trim();
            String[] splits = formData.split("&");
            int i;
            for (String s : splits) {
                i = s.indexOf("=");
                params.put(s.substring(0,i),s.substring(i+1));
            }
        }
    }

    private String decode(String value)  {
        try {
            return URLDecoder.decode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public StringBuilder getReqInfo() {
        return reqInfo;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public HashMap<String, String> getParams() {
        return params;
    }
    public void printParams(){
        if (params==null) System.out.println("没有参数");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey()+" = "+entry.getValue());
        }
    }
}
