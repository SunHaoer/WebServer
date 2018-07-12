package xin.core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
			server = new ServerSocket(8080);		// 初始化端口
			threadPool = Executors.newFixedThreadPool(100);		// 初始化线程池
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 创建start方法，用于接受客户端请求
	public void start() {
		
		try {
			while(true) {
				Socket socket = server.accept();	// 接收请求
				/*  // 不符合http协议响应格式 
				OutputStream outputStream = socket.getOutputStream();		// 处理业务
				outputStream.write("hello World".getBytes());
				outputStream.flush();
				*/
				/*  // 已提取到ClicentHandler类
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println("Http/1.1 200 OK"); 		// 拼接符合http协议的数据格式    	// 200网页就绪
				String data = "Hello World";
				ps.println("Content-Length" + data.length()); 		// 响应头
				ps.println("Content-Type:text/html"); 		// 要响应的数据格式
				ps.println(); 		// 空白行
				ps.write(data.getBytes()); 		// 响应实体
				ps.flush();
				socket.close();
				*/
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
