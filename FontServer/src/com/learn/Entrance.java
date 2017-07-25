/**
 * Project Name:FontServer
 * File Name:Entrance.java
 * Package Name:com.learn
 * Date:2017年7月2日下午4:34:39
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.learn;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;

/**
 * ClassName:Entrance <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年7月2日 下午4:34:39 <br/>
 * @author   Yang Jx
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class Entrance {

	/**
	 * naiveServer:(启动一个简单服务器). <br/>
	 *
	 * @author Yang Jx
	 * @since JDK 1.8
	 */
	public static void startNaiveServer() {
		Server server = new Server(8080);  //实例化服务器对象，并指定端口
		server.setHandler(new MyHandler());  //为服务器设置处理器
		server.setStopAtShutdown(true);
		
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * startServerWithConnector:(使用连接器). <br/>
	 * 可以配置多端口，但是handler还是只有一个能生效
	 * @author Yang Jx
	 * @since JDK 1.8
	 */
	public static void startServerWithConnector(){
		Server server = new Server();
		server.setHandler(new YourHandler());
		server.setStopAtShutdown(true);
		
		ServerConnector connector_1 = new ServerConnector(server);
		connector_1.setPort(8081);
		connector_1.setHost("localhost");
		connector_1.setIdleTimeout(10000L);
		connector_1.setAcceptQueueSize(10);
		server.addConnector(connector_1);
		
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * startServerWithDifferentContext:(在同一个端口下，根据不同的url，请求不同的handler). <br/>
	 * 非常适用
	 * @author Yang Jx
	 * @since JDK 1.8
	 */
	public static void startServerWithDifferentContext() {
//		Server server = new Server(8083);
		Server server = new Server();
		
		ServerConnector connector = new ServerConnector(server);  //用连接器可以为服务器设置更丰富的属性
		connector.setPort(8085);
		connector.setHost("localhost");
		connector.setIdleTimeout(10000L);
		connector.setAcceptQueueSize(10);
		server.addConnector(connector);
		
		ContextHandler contextHandler_1 = new ContextHandler();
		contextHandler_1.setContextPath("/my");
		contextHandler_1.setHandler(new MyHandler());
		
		ContextHandler contextHandler_2 = new ContextHandler();
		contextHandler_2.setContextPath("/your");
		contextHandler_2.setHandler(new YourHandler());
		
		ContextHandler contextHandler_3 = new ContextHandler();
		contextHandler_3.setContextPath("/getImage");
		contextHandler_3.setHandler(new ImageHandler());
		
		HandlerList handlerList = new HandlerList();
		handlerList.setHandlers(new Handler[]{contextHandler_1, contextHandler_2, contextHandler_3});
		
		server.setHandler(handlerList);
		
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
//		startNaiveServer();
//		startServerWithConnector();
		startServerWithDifferentContext();
	}

}

