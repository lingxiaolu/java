package com.zwq.servlet;

import com.zwq.pojo.Request;
import com.zwq.pojo.Response;

public interface Servlet {
    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request, Response response) throws Exception;
}
