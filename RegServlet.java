package net.web;

import java.io.IOException;

public class RegServlet implements Servlet{

    @Override
    public void service(Request request, Respond respond) throws IOException {
        respond.println("<h>注册成功</h>");
        respond.send(200);
    }
}
