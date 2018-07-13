package core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import common.HttpContext;
import common.ServletContext;
import http.HttpRequest;
import http.HttpResponse;

/**
 * 线程类用于处理客户端的请求和响应
 * @author Administrator
 *
 */

public class ClientHandler implements Runnable{
	private Socket socket;		
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			HttpRequest request = new HttpRequest(socket.getInputStream()); 		// 把socket.getInputStream()作为参数传入
			HttpResponse response = new HttpResponse(socket.getOutputStream());
			File file = new File(ServletContext.webRoot + request.getUri()); 		// 根据request的uri获取资源
			if(file.exists() == false) {		// 页面不存在
				file = new File(ServletContext.webRoot + "/" + ServletContext.notFoundPage);
				response.setStatus(HttpContext.CODE_NOTFOUND);
			} else {
				response.setStatus(HttpContext.CODE_OK);
			}
			// GET /sca HTTP/1.1
			response.setProtocol(ServletContext.protocol);		// 从配置文件中获取协议
			response.setContrntType(getContentTypeFileExt(file));		// 文件类型
			response.setContentLength((int)file.length());		// 文件长度
				
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));		// 读取网页文件
			byte[] bs = new byte[(int)file.length()];
			bis.read(bs);		// 把bis存入bs
			response.getOutputStream().write(bs);		// 输出bs
			bis.close();
			response.getOutputStream().flush();			// 清空
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getContentTypeFileExt(File file) {
		String fileName = file.getName();		// 文件名
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);		// 获取后缀(html)
		String type = ServletContext.map.get(ext);
		return type;
	}
	
}
