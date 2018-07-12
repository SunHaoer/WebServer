package xin.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import xin.comman.ServletContext;
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
			File file = new File(ServletContext.webRoot + request.getUri()); 		// 从配置文件获取网页目录
			
			HttpResponse respose = new HttpResponse(socket.getOutputStream());	// HttpRespose对象
			respose.setProtocol(ServletContext.protocol); 		// 从配置文件中获取协议
			respose.setStatus(200); 		// 状态码
			respose.setContentTyep("text/html"); 	// 文件类型
			respose.setContentLength((int)file.length()); 		// 文件长度
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));		// 读取网页文件并输出
			byte[] bs = new byte[(int)file.length()];
			bis.read(bs);		// 把bis读入数组bs中
			respose.getOutputStream().write(bs);		// 输出数组bs中的内容
			respose.getOutputStream().flush();				
			socket.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

