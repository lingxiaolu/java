package com.zwq.util;

import com.zwq.servlet.HttpServlet;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

public class TransferUtil {
    public static Object transfer(String fullName, String absolutePath) {
        try {
            File file = new File(absolutePath);
            InputStream in = new FileInputStream(file);
            byte[] bytes = IOUtils.readFully(in, -1, false);
            String src = new String(bytes);
            in.close();

            System.out.println(src);
            DynamicEngine de = DynamicEngine.getInstance();
            return de.javaCodeToObject(fullName, src.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
