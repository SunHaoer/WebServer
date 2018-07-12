package xin.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import xin.http.HttpRequest;
import xin.http.HttpResponse;

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
			/* 
			 * Http协议响应格式
			 * Http/1.1 200 OK 
			 * Content-type:test/html
			 * Content-Length:文件的数据长度
			 * 空白行
			 * 响应实体
			 */			
			HttpRequest request = new HttpRequest(socket.getInputStream());		// HTTPRequest对象
			File file = new File("WebContent" + request.getUri());		// 网页文件file	
			HttpResponse respose = new HttpResponse(socket.getOutputStream());	// HttpRespose对象
			respose.setProtocol("HTTP/1.1"); 		// 遵循的协议
			respose.setStatus(200); 		// 状态码
			respose.setContentTyep("text/html"); 	// 文件类型
			respose.setContentLength((int)file.length()); 		// 文件长度
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));		// 读取网页文件并输出
			byte[] bs = new byte[(int)file.length()];
			bis.read(bs);		// 把bis读入数组bs中
			respose.getOutputStream().write(bs);		// 输出数组bs中的内容
			respose.getOutputStream().flush();				
			socket.close();			
			
			/*  // 把响应代码提取到HttpResepose类
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println("Http/1.1 200 OK");		// 拼接状态行
				ps.println("Content-type:text/html"); 		// 拼接响应头，指定输出格式
				
				ps.println("Content-Length:" + file.length());		// 拼接响应头，指定响应数据长度
				ps.println(); 		// 空白行
			*/

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

