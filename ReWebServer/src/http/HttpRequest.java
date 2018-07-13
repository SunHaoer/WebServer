package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 封装http请求
 * @author Administrator
 *
 */

public class HttpRequest {
	private String method;		// 请求方式
	private String uri;			// 请求资源路径
	private String protocol;	// 遵循的协议
	
	public HttpRequest(InputStream in) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = reader.readLine();	// 获取请求行信息
			if(line != null && line.length() > 0) {			// 请求行有内容
				// [GET./index.html,HTTP/1.1]
				String[] datas = line.split(" ");
				method = datas[0];		// 请求方式GET
				uri = datas[1]; 		// 请求路径/index.html
				protocol = datas[2];	// 协议名HTTP/1.1
				
				if(uri.equals("/")) {	// 设置index.html为默认主页
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
