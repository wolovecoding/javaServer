package net.web;

import java.io.IOException;

public class LoginServlet implements Servlet{
    @Override
    public void service(Request request, Respond respond) throws IOException {
        String name = request.getParams().get("name");
        String passwd = request.getParams().get("passwd");
        if (name!=null&&passwd!=null&&name.equals("wang")&&passwd.equals("123456")){
            respond.println("登录成功");
        }else respond.println("用户名或密码错误");
        respond.send(200);

    }
}
