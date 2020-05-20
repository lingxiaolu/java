package com.zwq.util;

import com.zwq.pojo.Request;
import com.zwq.pojo.Response;
import com.zwq.servlet.HttpServlet;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessorPro extends Thread{
    private Socket socket;
    private Map<String, Object> contextMapper;
    private Map<String, Object> wrapperMapper;


    public RequestProcessorPro(Socket socket, Map<String, Object> contextMapper, Map<String, Object> wrapperMapper) {

        this.socket = socket;
        this.wrapperMapper = wrapperMapper;
        this.contextMapper = contextMapper;

    }

    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();
            //封装request对象和response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream(),contextMapper);
            String url = request.getUrl();
            if (wrapperMapper.get(url) == null) {
                response.outputHtmlPro(request.getUrl());//没找到默认为静态资源
            } else {
                HttpServlet httpServlet = (HttpServlet) wrapperMapper.get(url);
                httpServlet.service(request, response);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
