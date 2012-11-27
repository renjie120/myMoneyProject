package myOwnLibrary.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 包含request和response的两个成员变量.
 * @author wblishq
 *
 */
public class BaseAction {
	private HttpServletRequest req;
	private HttpServletResponse res;
	public HttpServletRequest getRequest() {
		return req;
	}
	public void setRequest(HttpServletRequest request) {
		this.req = request;
	}
	public HttpServletResponse getResponse() {
		return res;
	}
	public void setResponse(HttpServletResponse response) {
		this.res = response;
	}
}
