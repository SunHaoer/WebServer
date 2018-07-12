package xin.common;

/**
 * 封装http相关的参数信息
 * @author Administrator
 *
 */

public class HttpContext {
	public static final int CODE_OK = 200;		// 状态码，就绪
	public static final int CODE_NOTFOUND = 404;		// 找不到资源
	public static final int CODE_ERROR = 500;		// 服务器有误
	
	public static final String DESC_OK = "OK";		// 状态码描述信息
	public static final String DESC_NOTFOUND = "NOTFOUND";
	public static final String DESC_ERROR = "ERROR";	
}
