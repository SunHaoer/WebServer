package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import common.ServletContext;

/**
 * 核心类用于启动服务器
 * @author Administrator
 *
 */

public class WebServer {
	private ServerSocket server;		// ServerSocket类代表服务器
	private Executor threadPool;		// 线程池
	
	public WebServer() {		// 构造函数初始化
		try {
			server = new ServerSocket(ServletContext.port);		// 初始化端口号
			threadPool = Executors.newFixedThreadPool(ServletContext.maxThread);		// 初始化线程池，最大容量100
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {		// start函数用于接收客户端请求
		while(true) {		// 不停接收
			try {
				Socket socket = server.accept();	// 监听客户端的请求
				threadPool.execute(new ClientHandler(socket));
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}

	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}

}
