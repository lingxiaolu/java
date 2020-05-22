package com.zwq.servlet;

import com.zwq.pojo.Request;
import com.zwq.pojo.Response;
import com.zwq.util.HttpProtocolUtil;

import java.io.IOException;
import java.io.Serializable;

public class MyServlet extends HttpServlet implements Serializable {
    @Override
    public void doGet(Request request, Response response) {

        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String content = "<h1>MyServlet-demo3 get</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200((long) content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>MyServlet-demo3 post</h1>";
        try {
            response.output((HttpProtocolUtil.getHttpHeader200((long) content.getBytes().length) + content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() throws Exception {

    }
}
