package xin.http;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import xin.common.HttpContext;

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
	private Map<Integer, String> DescMap = new HashMap<Integer, String>();		// 响应描述信息
	private OutputStream outputStream;		// 响应头
	
	public HttpResponse(OutputStream outputstream) {		// 封装
		DescMap.put(HttpContext.CODE_OK, HttpContext.DESC_OK);
		DescMap.put(HttpContext.CODE_ERROR, HttpContext.DESC_ERROR);
		DescMap.put(HttpContext.CODE_NOTFOUND, HttpContext.DESC_NOTFOUND);
		this.outputStream = outputstream;
	}
	
	private boolean isSend;
	public OutputStream getOutputStream() {		// 只发送一次响应头
		if(isSend == false) {			// 保证响应头只被发送一次
			PrintStream ps = new PrintStream(outputStream);
			ps.println(protocol + " " + status + " " + DescMap.get(status)); 		// 拼接状态行
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
