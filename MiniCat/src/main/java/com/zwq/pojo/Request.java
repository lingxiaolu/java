package com.zwq.pojo;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private String method;
    private String url;
    private InputStream inputStream;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Request() {
    }


    /**
     *构造器，输⼊流传⼊
     * @param inputStream
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        int count = 0;
        if (count == 0){
            count += inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);
        String firstLineStr = inputStr.split("\\n")[0];
        String[] split = firstLineStr.split(" ");
        System.out.println(split);
        this.method = split[0];
        this.url = split[1];
        System.out.println("method =====>" + method);
        System.out.println("url ======>" + url);

    }

}
