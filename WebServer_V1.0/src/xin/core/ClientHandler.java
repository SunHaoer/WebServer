package xin.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = reader.readLine();		// 获取地址的第一行信息
			System.out.println(line);
			if(line != null && line.length() > 0) {
				String uri = line.split(" ")[1];		// 获取请求行里的第二块内容(请求的uri)
				if(uri.equals("/")) {			// 设置index.html为默认首页
					uri = "/index.html";
				}
				System.out.println(uri);		// 输出访问地址
				
				/* 
				 * Http协议响应格式
				 * Http/1.1 200 OK 
				 * Content-type:test/html
				 * Content-Length:文件的数据长度
				 * 空白行
				 * 响应实体
				 */
				PrintStream ps = new PrintStream(socket.getOutputStream());
				ps.println("Http/1.1 200 OK");		// 拼接状态行
				ps.println("Content-type:text/html"); 		// 拼接响应头，指定输出格式
				//String data = "Hello World";
				//ps.println("Content-Length:" + data.length());		// 拼接响应头，指定响应数据长度
				File file = new File("WebContent" + uri);		// 网页文件file
				ps.println("Content-Length:" + file.length());		// 拼接响应头，指定响应数据长度
				ps.println(); 		// 空白行
				
				//ps.write(data.getBytes()); 		// 输出响应实体
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));		// 读取网页文件并输出
				byte[] bs = new byte[(int)file.length()];
				bis.read(bs);		// 把bis读入数组bs中
				ps.write(bs);		// 输出数组bs中的内容
				ps.flush();				
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

