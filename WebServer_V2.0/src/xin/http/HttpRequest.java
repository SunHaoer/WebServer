package xin.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用于封装Http请求
 * @author Administrator
 *
 */

public class HttpRequest {
	// 声明请求参数
	private String method;		// 请求方式
	private String uri;			// 请求资源路径
	private String protocol;	// 遵循的协议
	
	public HttpRequest(InputStream in) {		// 初始化参数
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();		// 获取请求行信息
			if(line != null && line.length() > 0) {
				// [GET./index.html,HTTP/1.1]
				String[] datas = line.split(" ");	
				method = datas[0];		// 封装请求方式
				uri = datas[1];			// 封装请求路径
				protocol = datas[2];		// 封装协议名
				
				if(uri.equals("/")) {		// 设置默认主页
					uri = "/index.html";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
}
