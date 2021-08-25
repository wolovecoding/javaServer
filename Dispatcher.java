package net.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Dispatcher implements Runnable{

    private Socket accept;
    private Respond respond;
    private Request request;
    BufferedInputStream bfin;
    Servlet servlet;
    Dispatcher(Socket accept) throws IOException {
        this.accept = accept;
        respond = new Respond(accept);
        request = new Request(accept);
    }
    @Override
    public void run() {
        try {
            bfin = new BufferedInputStream(accept.getInputStream());
            servlet = UrlToServlet.getServlet(request.getUrl());
            if (servlet!=null) {
                servlet.service(request,respond);
            }else {
                InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("net/web/NotFound.html");
                respond.println(new String(inputStream.readAllBytes()));
                respond.send(404);
                inputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                respond.send(500);
                accept.close();
                bfin.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
