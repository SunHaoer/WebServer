package xin.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import xin.comman.ServletContext;

/**
 * 核心类，用来启动服务器
 * @author Administrator
 *
 */

public class WebServer {
	private ServerSocket server;		// 声明一个ServerSocket, 代表服务器端
	private Executor threadPool;		// 声明线程池
	public WebServer() {		// 在构造函数中初始化
		try {
			server = new ServerSocket(ServletContext.port);		// 获取端口号
			threadPool = Executors.newFixedThreadPool(ServletContext.maxThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 创建start方法，用于接受客户端请求
	public void start() {
		try {
			while(true) {
				Socket socket = server.accept();	// 接收请求
				threadPool.execute(new ClientHandler(socket)); 		// 利用线程池改造start方法
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
		
	}
}
