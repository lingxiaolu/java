package com.zwq.pojo;

import com.zwq.util.HttpProtocolUtil;
import com.zwq.util.StaticResourceUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private OutputStream outputStream;
    private Map<String, Object> contextWrapper = new HashMap<>();

    public Response() {
    }

    public Response(OutputStream outputStream, Map<String, Object> contextWrapper) {
        this.outputStream = outputStream;
        this.contextWrapper = contextWrapper;
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void outputHtmlPro(String path) throws IOException {
        String[] split = path.split("/");
        String staticResource = split[1];
        if (contextWrapper.get(staticResource) != null) {
            StaticResourceUtil.outputStaticResource((FileInputStream) contextWrapper.get(staticResource), outputStream);
        } else {
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }

    public void outputHtml(String path) throws IOException {
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);
        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }

    public void output(String httpHeader404) throws IOException {
        outputStream.write(httpHeader404.getBytes());
    }

}
