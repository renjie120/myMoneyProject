package myOwnLibrary.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.jdbc.DBPoolManager;

/**
 * �������������ĵ�һ����.
 * @author wblishq
 *
 */
public class ActionContext {
	private static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();

	/**
	 * ȡ�������ĵ�����.
	 * @return
	 */
	public static ActionContext getContext() {
		return (ActionContext) actionContext.get();
	}

	/**
	 * ����������.
	 * @param context
	 */
	public static void setContext(ActionContext context) {
		actionContext.set(context);
	}

	private Map<String, Object> context = null;

	/**
	 * ��ʼ�������Ļ���.
	 * @param context
	 */
	public ActionContext(Map<String, Object> context) {
		this.context = context;
	}

	/**
	 * �õ�������.
	 * @return
	 */
	public Map<String, Object> getContextMap() {
		return context;
	}

	/**
	 * ȡ���������е�ֵ.
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return context.get(key);
	}

	/**
	 * ȡ���������е�HttpServletRequest����.
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) context.get("HttpServletRequest");
	}

	/**
	 * ȡ���������е�HttpServletResponse.
	 * @return
	 */
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) context.get("HttpServletResponse");
	}

	/**
	 * ȡ���������е����ӳ�.
	 * @return
	 */
	public DBPoolManager getDbPoolManager() {
		return (DBPoolManager) context.get("poolManager");
	}
}
