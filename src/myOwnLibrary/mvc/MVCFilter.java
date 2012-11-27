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
 * mvc��Ҫ�Ĺ�����.
 * @author wblishq
 *
 */
public class MVCFilter implements Filter{
	private FilterConfig filterConfig;
	/**
	 * action���д���ĺ�׺�ַ���,Ĭ��Ϊaction
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
		//���action�����ǲ��õ�actionName!methodName�ķ�ʽ�ͽ������⴦��.
		if(actionName.indexOf("!")!=-1){
			newActionName = actionName.substring(0,actionName.indexOf("!"));
			method = actionName.substring(actionName.indexOf("!")+1,actionName.length());
			actionName = newActionName;
		}		
		String fileName = request.getRealPath("")+"\\WEB-INF\\classes\\struts.xml";
		if(Util.isEmpty(actionName)){
			chain.doFilter(request, response);
		}else{
			//���������ļ���ȡaction�Ĳ�������
			ActionConfig _class = factory.getActionConfig(actionName,fileName,null);
			if(!Util.isEmpty(method))
				_class.setMethod(method);			
			//�õ�request�еĲ�������
			Map<String,String[]> params = request.getParameterMap();
			//����request�еĲ����������action������.
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
			//ִ��action�еľ����method����,����������浽request��attribute��.
			try {
				invokeAction(_class,request);
			} catch (MVCException e) {
				e.printStackTrace();
				request.setAttribute("message", consoleException(e,_class));
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", consoleException(e,_class));
			}
			//������ת��ַ��ת�����ҳ��.
			if(_class.isAjax()){
		        response.setContentType("text/html;charset=GBK");
		        response.getWriter().print(_class.getAjaxAns());	
			}else{
				servletContext.getRequestDispatcher(_class.getResultUrl()).forward(request, response);
			}
		}
	}
	
	/**
	 * �����쳣�ķ���.
	 * @param e
	 * @param _class
	 * @return Ҫ�����ǰ̨�Ĵ����ַ���.
	 */
	private String consoleException(Exception e,ActionConfig _class){
		String ans = "";
		StringBuffer buf = new StringBuffer();
		StackTraceElement[] srcs = null;
		try{
			srcs = e.getCause().getStackTrace();
			buf.append("�쳣������:<br>");
			buf.append(e.getCause().getMessage());
			buf.append("<br>");
			for(StackTraceElement src : srcs){
				if(src.getClassName().indexOf("neusoft")!=-1){
					buf.append(""+src.getClassName()+"�ĵ�"+src.getLineNumber()+"��!<br>");
				}
				if(src.getClassName().indexOf(_class.getClassName())!=-1)
					break;
			}
			ans = buf.toString();
		}
		//������쳣�ǿ����������e.getCause().getStackTrace();�����쳣,ԭ�����Ҳ���execute����ʱ�ᱨ��.��ʱֱ�Ӵ�ӡe.getMessage()����!
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
	 * ִ��action������Ķ�Ӧ��method����֮��,����ص�����ֵд��request,������action�ķ�Ӧurl��ַ.
	 * @param clas action��
	 * @param request request����
	 * @throws MVCException 
	 */
	private void invokeAction(ActionConfig clas,HttpServletRequest request) throws Exception{
		// ִ��action�е�method����.
		try {
			factory.invoke(clas);
			// �õ���action���ж�����get����������ֵ,׼������ֵ�ŵ�request��ȥ.
			List<Field> allFields = Util.allAttrWithGetMethod(clas.getAction());
			Iterator<Field> it = allFields.iterator();
			while (it.hasNext()) {
				Field field = it.next();
				String fieldName = field.getName();
				String upperFirstLetter = fieldName.substring(0, 1)
						.toUpperCase();
				// ��ú����Զ�Ӧ��getXXX()����������
				String getMethodName = "get" + upperFirstLetter
						+ fieldName.substring(1);
				// ����request�ı���ֵ.
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
