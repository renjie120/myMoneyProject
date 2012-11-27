package myOwnLibrary.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseAction {
	protected final Log log = LogFactory.getLog(getClass()); 
	
	protected void writeLog(String str){
		log.info(str);
	}
	
	protected void error(String str){
		log.error(str);
	}
	
	/**
	 * �����ַ�����ǰ̨���,������ajax�ĵ���
	 * @param response
	 * @param str
	 */
	protected void writeToPage(HttpServletResponse response ,String str){
		try {
			response.setContentType("text/html;charset=GBK"); 
			response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
