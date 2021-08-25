package net.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class main {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            Socket accept = server.accept();
            /*这些可用多线程处理Dispatcher
            BufferedInputStream bfin = new BufferedInputStream(accept.getInputStream());
            Request request = new Request(accept);
            Servlet servlet = UrlToServlet.getServlet(request.getUrl());
            servlet.service(request,new Respond(accept));*/
            new Thread(new Dispatcher(accept)).start();
        }

    }
}
