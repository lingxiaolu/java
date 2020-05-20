package com.zwq.util;

import java.io.*;

public class StaticResourceUtil {
    public static String getAbsolutePath(String path){
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\", "/") + path;
    }
    public static void outputStaticResource(InputStream inputStream , OutputStream outputStream) throws IOException {
        int count = 0;
        if (count == 0) {
            count += inputStream.available();
        }
        long resourceSize = count;
        //先输出请求头信息，再输出具体内容
        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());
        //读取内容输出
        long written = 0;//已经读取的内容长度
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];

        while (written < resourceSize) {
            if (written + byteSize > resourceSize){//如果满足说明剩余未读的长度小于1024，那就按照实际长度来
                byteSize = (int) (resourceSize - written); //剩余文件的内容长度
                bytes = new byte[byteSize];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            written += byteSize;
        }
    }

}
