package com.zwq.util;

/**
 *  http协议⼯具类，主要是提供响应头信息，这⾥我们只提供200和404的情况
 */
public class HttpProtocolUtil {
    /**
     * 为响应码200提供请求头信息
     * @param length
     * @return
     */
    public static String getHttpHeader200(Long length){
        return "HTTP/1.1 200 OK \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + length + "\n" +
                "\r\n";
    }

    /**
     * 为响应码404提供请求头信息
     * @return
     */
    public static String getHttpHeader404(){
        String str404 = "<h1>404 not found</h1>";
        return "HTTP/1.1 404 NOT Found \n" +
                "Content-Type: text/html \n" +
                "Content-Length: " + str404.getBytes().length + " \n" +
                "\r\n" + str404;
    }
}
