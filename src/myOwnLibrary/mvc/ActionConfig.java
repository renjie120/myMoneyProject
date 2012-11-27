package myOwnLibrary.mvc;

import org.w3c.dom.NodeList;

/**
 * action配置文件的实体类.
 * @author wblishq
 *
 */
public class ActionConfig {
	private String name;
	private String method;
	private String resultUrl;
	private String className;
	private NodeList results;
	private boolean isAjax;
	private String ajaxAns;
	private Object action;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public NodeList getResults() {
		return results;
	}

	public void setResults(NodeList results) {
		this.results = results;
	}

	public boolean isAjax() {
		return isAjax;
	}

	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}

	public String getAjaxAns() {
		return ajaxAns;
	}

	public void setAjaxAns(String ajaxAns) {
		this.ajaxAns = ajaxAns;
	}
}
