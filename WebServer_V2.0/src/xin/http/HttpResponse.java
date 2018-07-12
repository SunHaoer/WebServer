package xin.http;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 封装响应信息
 * @author Administrator
 *
 */

public class HttpResponse {
	private String protocol;		// 响应遵循的协议
	private int status;				// 响应的状态码
	private String contentTyep;		// 响应的数据类型
	private int contentLength;		// 响应的数据长度
	private OutputStream outputStream;
	
	public HttpResponse(OutputStream outputstream) {		// 封装
		this.outputStream = outputstream;
	}
	
	private boolean isSend;
	public OutputStream getOutputStream() {		// 只发送一次响应头
		if(isSend == false) {			// 保证响应头只被发送一次
			PrintStream ps = new PrintStream(outputStream);
			ps.println(protocol + " " + status + " " + "OK"); 		// 拼接状态行
			ps.println("Content-Type:" + contentTyep); 			// 拼接响应头，数据类型
			ps.println("Content-Length:" + contentLength); 		// 拼接响应头，数据长度
			ps.println(); 			// 空白行	
			isSend = true;
		}
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getContentTyep() {
		return contentTyep;
	}
	
	public void setContentTyep(String contentTyep) {
		this.contentTyep = contentTyep;
	}
	
	public int getContentLength() {
		return contentLength;
	}
	
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	
}
