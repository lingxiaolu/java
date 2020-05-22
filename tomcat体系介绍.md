### tomcat体系介绍

tomcat是由两大组件组成的一个http服务器

* 组件一：连接器组件Coyote

  * Coyote是tomcat内连接器组件的名字，其主要作用：客户端通过Coyote组件与服务器建立连接、发送请求并接收响应。Coyote又是由一下组件构成的：

    （1）EndPoint:EndPoint是Coyote通信端点，是具体Socket接收和发送处理器，**是对传输层的抽象**，因此EndPoint实现了TCP/IP协议。

    （2）Processor：Processor是Coyote协议处理接口，Processor实现了HTTP协议，Processor接收来自EndPoint的Socket，读取字节流解析成Tomcat Request和Response对象，并通过Adapter将其提交到容器处理，**Processor是对应用层协议的抽象**

    （3）ProtocolHandler：Coyote协议接口，通过EndPoint和Processor实现针对具体协议的处理能力，Tomcat按照协议和I/O提供了6个实现类：AjpNioProtocol ，AjpAprProtocol， AjpNio2Protocol ， Http11NioProtocol ，Http11Nio2Protocol ，Http11AprProtocol

    （4）Adapter：由于协议不同，客户端发送过来的请求信息也不尽相同，因此Tomcat定义了字节的Request类来封装这些请求信息。ProtocolHandler接口负责解析请求并生成Tomcat Request类，但是这个类不是标准的ServletRequest，不能用Tomcat Request作为参数来调用容器。因此运用适配器模式，连接器调用的是CoyoteAdapter的service方法，传入的是Tomcat Request对象，CoyoteAdapter负责将Tomcat Request转为ServletRequest，再调用容器。

    Coyote工作流程图解

    ![image-20200522223832695](C:\Users\zwq\AppData\Roaming\Typora\typora-user-images\image-20200522223832695.png)

    

* 组件二：容器Container（Catalina）

  Tomcat启动的时候会初始化Catalina实例，该实例通过加载server.xml完成其他实例的创建，创建并管理一个Server，Server创建并管理多个服务，每个服务又可以又多个Connector和Container。

  * 一个Catalina实例下可以有一个Server实例（容器），一个Server实例（容器）下可以有多个Service实例（容器），每个Service实例下可以有多个Connector实例和一个Container实例

    * Catalina：负责解析Tomcat的配置文件server.xml，以此来创建服务器Server组件并进行管理

    * Server：服务器表示整个Catalina Servlet容器以及其他组件，负责组装并启动Servlet引擎、Tomcat连接器。Server通过实现Lifecycle接口，提供了一种优雅的启动和关闭整个系统的方式。

    * Service：Service是Server内部的组件，一个Server包含多个Service，它将若干个Connector组件绑定到一个Container

    * Container：容器，负责处理用户的servlet请求，并返回对象给web用户的模块

      * Engine：表示整个Catalina的Servlet引擎，用来管理多个虚拟站点，一个Service最多只能有一个Engine，但是一个引擎可以包含多个Host

      * Host：代表一个虚拟主机，或者说一个站点，可以给Tomcat配置多个虚拟主机地址，而一个虚拟主机下可以包含多个Context

      * Context：表示一个web应用程序，一个web应用可包含多个Wrapper

      * Wrapper：表示一个Servlet，Wrapper作为容器中的最底层，不能包含子容器

        上面组件的配置体现在**conf/server.xml**中

## MiniCat 访问路径

**http:localhost:8080/demo1/MyServlet**   **http:localhost:8080/demo1/index.html**

**http:localhost:8080/demo2/MyServlet**  **http:localhost:8080/demo2/index.html**

**http:localhost:8080/demo3/MyServlet**  **http:localhost:8080/demo3/index.html**

文件放置**C:/Users/zwq/webapps**下