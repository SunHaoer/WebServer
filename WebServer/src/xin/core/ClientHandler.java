package xin.core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 线程类，用于处理客户端请求和响应
 * @author Administrator
 *
 */

public class ClientHandler implements Runnable {
	
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {		// 重写run方法，响应代码提取
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			ps.println("Http/1.1 200 OK");		// 拼接状态行
			ps.println("Content-type:text/html"); 		// 拼接响应头，指定输出格式
			String data = "Hello World";
			ps.println("Content-Length:" + data.length());		// 拼接响应头，指定响应数据长度
			ps.println(); 		// 空白行
			ps.write(data.getBytes()); 		// 输出响应实体
			ps.flush();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

