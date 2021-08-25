package net.web;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 对http协议的响应信息进行封装
 * 自动填充请求头
 */
public class Respond {

    private Socket socket;
    private BufferedWriter bw;
    //请求头
    private StringBuilder headInfo;
    //请求内容
    private StringBuilder content;
    //长度   是字节长度
    private int len;
    //常用常量
    private final char BLANK = ' ';
    private final String CRLF ="\r\n";
    //状态码
   // private int code;

    private Respond(){
        content = new StringBuilder();
        headInfo = new StringBuilder();
    }
    //连接时用
    public Respond(Socket socket){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //构建头信息
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        switch (code){
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("Not Found").append(CRLF);
                break;
            case 505:
                headInfo.append("Server Error").append(CRLF);
                break;
        }
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("yicheng Server/0.1;charset=GBK").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }

    //头信息
    public Respond print(String info){
        content.append(info);
        len += info.getBytes().length;
        return this;
    }
    public Respond println(String info){
        content.append(info).append(CRLF);
        len += info.getBytes().length;
        return this;
    }
    public void send(int code) throws IOException {
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
        bw.close();
    }
}
