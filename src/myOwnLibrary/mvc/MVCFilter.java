package myOwnLibrary.mvc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * mvc主要的过滤器.
 * @author wblishq
 *
 */
public class MVCFilter implements Filter{
	private FilterConfig filterConfig;
	/**
	 * action进行处理的后缀字符串,默认为action
	 */
	private String fiexdUrl = "action";
	protected Log log = LogFactory.getLog(this.getClass().getName()); 
	private ActionFactory factory = new ActionFactory();
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		//request.getParameter("username");
		HttpServletResponse response = (HttpServletResponse) res;
		ServletContext servletContext = filterConfig.getServletContext();
		String actionName = Util.parseServletPath(request
				.getServletPath(),fiexdUrl);		
		String method = "";
		String newActionName = "";
		//如果action名字是采用的actionName!methodName的方式就进行特殊处理.
		if(actionName.indexOf("!")!=-1){
			newActionName = actionName.substring(0,actionName.indexOf("!"));
			method = actionName.substring(actionName.indexOf("!")+1,actionName.length());
			actionName = newActionName;
		}		
		String fileName = request.getRealPath("")+"\\WEB-INF\\classes\\struts.xml";
		if(Util.isEmpty(actionName)){
			chain.doFilter(request, response);
		}else{
			//根据配置文件读取action的参数配置
			ActionConfig _class = factory.getActionConfig(actionName,fileName,null);
			if(!Util.isEmpty(method))
				_class.setMethod(method);			
			//得到request中的参数配置
			Map<String,String[]> params = request.getParameterMap();
			//设置request中的参数到具体的action对象中.
			factory.setActionObject(_class, params);
			
			if(_class.getAction() instanceof IRequest){
				IRequest iRequest = (IRequest)(_class.getAction());
				iRequest.setServletRequest(request);
			}
			if(_class.getAction() instanceof IResponse){
				IResponse iResponse = (IResponse)(_class.getAction());
				iResponse.setServletResponse(response);
			}
			if(_class.getAction() instanceof BaseAction){
				Util.setPro(_class.getAction(), "request", request);
				Util.setPro(_class.getAction(), "response", response);
			}
			Map context = new HashMap();
			context.put("HttpServletRequest", request);
			context.put("HttpServletResponse", response);
			ActionContext actionContext = new ActionContext(context);
			ActionContext.setContext(actionContext);
			//执行action中的具体的method方法,并将结果保存到request的attribute中.
			try {
				invokeAction(_class,request);
			} catch (MVCException e) {
				e.printStackTrace();
				request.setAttribute("message", consoleException(e,_class));
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", consoleException(e,_class));
			}
			//根据跳转地址跳转到结果页面.
			if(_class.isAjax()){
		        response.setContentType("text/html;charset=GBK");
		        response.getWriter().print(_class.getAjaxAns());	
			}else{
				servletContext.getRequestDispatcher(_class.getResultUrl()).forward(request, response);
			}
		}
	}
	
	/**
	 * 处理异常的方法.
	 * @param e
	 * @param _class
	 * @return 要输出到前台的错误字符串.
	 */
	private String consoleException(Exception e,ActionConfig _class){
		String ans = "";
		StringBuffer buf = new StringBuffer();
		StackTraceElement[] srcs = null;
		try{
			srcs = e.getCause().getStackTrace();
			buf.append("异常出现于:<br>");
			buf.append(e.getCause().getMessage());
			buf.append("<br>");
			for(StackTraceElement src : srcs){
				if(src.getClassName().indexOf("neusoft")!=-1){
					buf.append(""+src.getClassName()+"的第"+src.getLineNumber()+"行!<br>");
				}
				if(src.getClassName().indexOf(_class.getClassName())!=-1)
					break;
			}
			ans = buf.toString();
		}
		//下面的异常是可能在上面的e.getCause().getStackTrace();出现异常,原因在找不到execute方法时会报错.此时直接打印e.getMessage()即可!
		catch(Exception ee){
			ans =  e.getMessage();
		}finally{
			if(_class.isAjax()){
				_class.setAjaxAns(e.getMessage());	
			}
			else{
				_class.setResultUrl("/mss/common/error.jsp");
			}
		}
		return ans;
	}
	/**
	 * 执行action类里面的对应的method方法之后,把相关的属性值写入request,并设置action的反应url地址.
	 * @param clas action类
	 * @param request request变量
	 * @throws MVCException 
	 */
	private void invokeAction(ActionConfig clas,HttpServletRequest request) throws Exception{
		// 执行action中的method方法.
		try {
			factory.invoke(clas);
			// 得到在action类中定义了get方法的属性值,准备将其值放到request中去.
			List<Field> allFields = Util.allAttrWithGetMethod(clas.getAction());
			Iterator<Field> it = allFields.iterator();
			while (it.hasNext()) {
				Field field = it.next();
				String fieldName = field.getName();
				String upperFirstLetter = fieldName.substring(0, 1)
						.toUpperCase();
				// 获得和属性对应的getXXX()方法的名字
				String getMethodName = "get" + upperFirstLetter
						+ fieldName.substring(1);
				// 设置request的变量值.
				request.setAttribute(fieldName, Util.invoke(clas.getAction(),
						getMethodName));
			}
		} 	
		catch (MVCException e) {
			throw e;
		}catch (Exception e) {
			throw e;
		}
	}
	
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;		
		if(!Util.isEmpty(filterConfig.getInitParameter("fixedUrl")))
			fiexdUrl = filterConfig.getInitParameter("fixedUrl");
	}
}
