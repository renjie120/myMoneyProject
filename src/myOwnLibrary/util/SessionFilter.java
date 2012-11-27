package myOwnLibrary.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myOwnLibrary.util.Util;

/**
 * ��������֤��½.
 * @author renjie120 419723443@qq.com
 *
 */
public class SessionFilter implements Filter {
	private String failurePage;
	private String loginPage;
	
	private String limitUrlPatterStrs;
	private String loginAction;
	private String helpPage;
	private String nocheckurlstr;
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String requestURI = request.getRequestURI();
		// �õ���Ŀ¼
		String webPath = request.getContextPath();
		boolean doContinue = false;
		boolean doFilter = true;
		// ֻҪ�������������ļ������õ����ӷ�ʽ֮һ���,��Ҫ���е������Ĺ���.������.
		if (limitUrlPatterStrs != null) {
			limitUrlPatterStrs = limitUrlPatterStrs.trim();
			String[] urlTypes = limitUrlPatterStrs.split(";");
			// ����simpleUrlPattern�����ͼ��Ͻ����ж�
			for (int i = 0; i < urlTypes.length; i++) {
				String urlT = urlTypes[i].trim();
				if (urlT.length() < 1)
					continue;
				// �����*.��ͷ��ȥ��*
				if (urlT.startsWith("*.")) {
					urlT = urlT.substring(1);
				}
				// ������ͳ��ȴ���0�������Ը����ͽ�β����֤ͨ��������ѭ��
				if (urlTypes[i].trim().length() > 0
						&& requestURI.endsWith(urlT)) {
					doContinue = true;
					break;
				}
			}
		}
		if (doContinue) {
			// ����Ǹ�Ŀ¼,��¼����,���ߴ������,���ý��й���!
			if (Util.equalsIgnoreCase(requestURI, webPath + "/")
					|| Util.equalsIgnoreCase(requestURI, webPath + failurePage) 
					|| Util.equalsIgnoreCase(requestURI, webPath + loginPage)
					|| Util.equalsIgnoreCase(requestURI, webPath + helpPage)) {
				doFilter = false;
			}
			
			//ֻҪ�ǲ�����session������б��ֱ������session����!Ϊ���ں��ֻ�������л����н��д���!!��Ҫ!!
			String[] nocheckurls = null;
			if (!Util.isEmpty(nocheckurlstr)) {
				nocheckurls = nocheckurlstr.split(";");
				for (int i = 0; i < nocheckurls.length; i++) {
					if (requestURI.indexOf(nocheckurls[i]) != -1) {
						doFilter = false;
						break;
					}
				}
			}
		}
		if (doContinue && doFilter) {
			if (Util.equalsIgnoreCase(requestURI, webPath + "/")) {
				chain.doFilter(request, response);
			}
			else if(session.getAttribute("pass")!= null){
				chain.doFilter(request, response);
			}
			else if(requestURI.indexOf(loginAction)!=-1){
				chain.doFilter(request, response);
			}
			else if(requestURI.indexOf(failurePage)!=-1){
				chain.doFilter(request, response);
			}
			// ���session��û���û�����Ϣ���߲��ǵ�¼��action,��ֱ����ת������ҳ��
			else {
				request.setAttribute("message", "sessionʧЧ����û�е�¼.");
				request.getSession().setAttribute("lastUrl", requestURI);
				int port = request.getServerPort();
				// sessionʧЧҳ�����ת����
				String targetUrl = request.getScheme() + "://"
						+ request.getServerName() + ":" + port
						+ request.getContextPath() + failurePage;
				// ��תҳ��
				response.sendRedirect(targetUrl);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {
		failurePage = config.getInitParameter("failure");
		loginPage = config.getInitParameter("login");
		nocheckurlstr = config.getInitParameter("nocheckurlstr");
		limitUrlPatterStrs = config.getInitParameter("limitUrlPattern");
		loginAction = config.getInitParameter("loginAction");
		helpPage = config.getInitParameter("help");
	}

}
