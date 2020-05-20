package com.zwq.server;

import com.zwq.servlet.HttpServlet;
import com.zwq.util.RequestProcessor;
import com.zwq.util.RequestProcessorPro;
import com.zwq.util.TransferUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Bootstrap {
    /**定义socket监听的端⼝号*/
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static void main(String[] args)  {
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Map<String, Object> contextMapper = new HashMap<>();
    private Map<String, Object> wrapperMapper = new HashMap<>();
    //3.0 版本的servletMap
    /**/private Map<String, HttpServlet> servletMap = new HashMap<>();

    public void loadServlet() {
        /*InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document read = saxReader.read(resourceAsStream);
            Element rootElement = read.getRootElement();
            List<Element> list = rootElement.selectNodes("//servlet");
            for (Element element : list) {
                //<servlet-name>MyServlet</servlet-name>
                Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameElement.getStringValue();
                //<servlet-class>com.zwq.servlet.MyServlet</servlet-class>
                Element servletClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassElement.getStringValue();
                //<url-pattern>/myServlet</url-pattern>
                Element urlPattern = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String servletMapping = urlPattern.selectSingleNode("url-pattern").getStringValue();
                servletMap.put(servletMapping, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            Element hostElement = (Element) rootElement.selectSingleNode("/Server/Service/Engine/Host");
            String appBase = hostElement.attributeValue("appBase");
            File file = new File(appBase);
            loadFile(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
    public void loadFile(File file) {
        try {
            File[] files = file.listFiles();
            String context = null;
            String wrapper = null;
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    loadFile(file1);
                } else {
                    String name = file1.getName();
                    //获取后缀名
                    String suffix = name.substring(name.lastIndexOf("."), name.length());
                    if (".html".equalsIgnoreCase(suffix)) {
                        FileInputStream fileInputStream = new FileInputStream(file1);
                        context = file1.getParent();
                        String substring = context.substring(context.lastIndexOf("\\")+1, context.length());
                        contextMapper.put(substring, fileInputStream);
                    } else if (".java".equalsIgnoreCase(suffix)) {
                        String fullName = "com.zwq.servlet.MyServlet";

                        String absolutePath = file1.getAbsolutePath();
                        String transAbsolutePath = absolutePath.replaceAll("\\\\", "/");
                        HttpServlet httpServlet = (HttpServlet) TransferUtil.transfer(fullName, transAbsolutePath);
                        String fileName = transAbsolutePath.substring(transAbsolutePath.lastIndexOf("/") +1, transAbsolutePath.length());
                        String fileNameNoSuffix = fileName.substring(0, fileName.lastIndexOf("."));
                        context = file1.getParent();
                        File file2 = new File(context);
                        String parent = file2.getParent();
                        String substring = context.substring(parent.lastIndexOf("\\")+1, parent.length());
                        wrapperMapper.put("/"+substring+"/"+fileNameNoSuffix, httpServlet);


                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public void start() throws Exception {

        loadServlet();

        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);


//        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("============》MiniCat start on port:"+port);

        *//**
     * 1.0 版本浏览器请求http://localhost:8080,返回⼀个固定的字符串到⻚⾯"Hello Minicat!
     *//*

     *//*while (true){
            //获取socket
            Socket socket = serverSocket.accept();
            //通过socket获取输出流
            String data = "hello Minicat";
            String httpHeader200 = HttpProtocolUtil.getHttpHeader200((long) data.getBytes().length) + data;
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(httpHeader200.getBytes());
            socket.close();
        }*//*

     *//**
     * 2.0 封装Request和Response对象，返回html静态资源⽂件
     *//*

     *//*while(true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            //封装request对象和response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            response.outputHtml(request.getUrl());
            socket.close();
            break;
        }*//*
     *//**
     * 3.0 可以请求动态资源（Servlet）
     *//*
     *//*while (true){
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            //封装request对象和response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            if (servletMap.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());//没找到默认为静态资源
            } else {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }
            socket.close();
            serverSocket.close();
        }*//*
        // 多线程改造 (不使用线程池)
        *//*while (true){
            ServerSocket serverSocket = new ServerSocket(port);

            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            requestProcessor.start();
            serverSocket.close();
        }*//*
        // 使用线程池
        while (true){
            ServerSocket serverSocket = new ServerSocket(port);

            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            threadPoolExecutor.execute(requestProcessor);
            serverSocket.close();
        }

    }*/
    public void start() throws Exception {

        loadServlet();

        // 定义一个线程池
        int corePoolSize = 10;
        int maximumPoolSize =50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);


        System.out.println("============》MiniCat start on port:"+port);


        // 使用线程池
        while (true){
            ServerSocket serverSocket = new ServerSocket(port);

            Socket socket = serverSocket.accept();
            RequestProcessorPro requestProcessor = new RequestProcessorPro(socket, contextMapper, wrapperMapper);
            threadPoolExecutor.execute(requestProcessor);
            serverSocket.close();
        }

    }

}
