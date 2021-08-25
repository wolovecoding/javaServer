package net.web;

import java.io.IOException;

public interface Servlet {
    void service(Request request,Respond respond) throws IOException;
   // void doGet(Request request,Respond respond);
}
