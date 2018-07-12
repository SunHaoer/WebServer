package xin.comman;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 封装服务器相关参数
 * 读取xml数据(DOM4J)
 * @author Administrator
 *
 */

public class ServletContext {		// 供整个项目使用
	public static int port; 		// 端口号
	public static int maxThread;		// 线程池大小
	public static String protocol;		// 遵循协议
	public static String webRoot;		// 资源根目录
	
	private static void init() { 	// 解析xml数据并给参数复制
		try {
			SAXReader reader = new SAXReader();		// 加载web.xml文件
			File file = new File("config/web.xml");			
			Document doc = reader.read(file);		
			
			Element rootElement = doc.getRootElement(); 	// 开始读取文件
			Element serviceEle = rootElement.element("service");
			Element connEle = serviceEle.element("connector");
			port = Integer.valueOf(connEle.attributeValue("port"));		// 获取属性值
			maxThread = Integer.valueOf(connEle.attributeValue("maxThread"));
			protocol = connEle.attributeValue("protocol");
			webRoot = serviceEle.elementText("webRoot");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}	
	
	static {		// 初始化参数值
		init();		// 解析xml数据并给参数复制
	}
}
