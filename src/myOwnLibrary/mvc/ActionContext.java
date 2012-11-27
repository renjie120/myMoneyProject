package myOwnLibrary.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.jdbc.DBPoolManager;

/**
 * 用来连接上下文的一个类.
 * @author wblishq
 *
 */
public class ActionContext {
	private static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();

	/**
	 * 取得上下文的内容.
	 * @return
	 */
	public static ActionContext getContext() {
		return (ActionContext) actionContext.get();
	}

	/**
	 * 设置上下文.
	 * @param context
	 */
	public static void setContext(ActionContext context) {
		actionContext.set(context);
	}

	private Map<String, Object> context = null;

	/**
	 * 初始化上下文环境.
	 * @param context
	 */
	public ActionContext(Map<String, Object> context) {
		this.context = context;
	}

	/**
	 * 得到上下文.
	 * @return
	 */
	public Map<String, Object> getContextMap() {
		return context;
	}

	/**
	 * 取得上下文中的值.
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return context.get(key);
	}

	/**
	 * 取得上下文中的HttpServletRequest对象.
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) context.get("HttpServletRequest");
	}

	/**
	 * 取得上下文中的HttpServletResponse.
	 * @return
	 */
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) context.get("HttpServletResponse");
	}

	/**
	 * 取得上下文中的连接池.
	 * @return
	 */
	public DBPoolManager getDbPoolManager() {
		return (DBPoolManager) context.get("poolManager");
	}
}
