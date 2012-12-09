package tallyBook.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myOwnLibrary.cache.Cache;
import myOwnLibrary.cache.CacheManager;

import org.apache.struts2.ServletActionContext;

import tallyBook.dao.ConstarctReportDao;
import tallyBook.dao.DaoUtil;
import tallyBook.dao.LoginDao;

import common.base.SpringContextUtil;

/**
 * ��½ϵͳ
 * 
 * @author renjie120 419723443@qq.com *
 */
public class LoginAction {
	private String pass;
	private LoginDao dao;
	private String newPass;
	private String bookType;
	private static DaoUtil commonDao = (DaoUtil) SpringContextUtil
			.getBean("daoUtil");
	// �Ƿ��οͷ��ʣ��ǵĻ���ֻ���Կ���֧���������Կ������룡
	private String traveler;

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String login() {
		dao = new LoginDao();
		traveler = getTraveler();
		bookType = getBookType();
		pass = getPass();
		HttpServletRequest request = ServletActionContext.getRequest();
		System.out.println(request.getParameter("pass"));
		if (dao != null && dao.doCheckPass(pass)) {
			// ��������οͷ��ʣ���˵�����Կ���������Ϣ
			if (traveler == null) {
				commonDao.doUpdateSqlQuery("update config set canseein = '1'");
				ConstarctReportDao dao2 = new ConstarctReportDao();
				// ȫ������֧
				List allInAndOut = dao2.getAllInAndOut(true);
				// ��ѯÿ�����֧ͳ��
				List inAndOut2 = dao2.getYearInAndOut();

				DetailReport report = new DetailReport();
				Map yearMap = report.getYearToInAndOut(inAndOut2);

				String[] ans = report.getAllInAndOut(allInAndOut);

				// ����session�����Ƿ����οͷ��ʣ�
				request.getSession().setAttribute("traveler", "false");
				request.getSession().setAttribute("yearMap", yearMap);
				request.getSession().setAttribute("in", ans[0]);
				request.getSession().setAttribute("out", ans[1]);
				request.getSession().setAttribute("all", ans[2]);
			}
			// ���򲻿��Կ���������Ϣ
			else {
				commonDao.doUpdateSqlQuery("update config set canseein = '3'");
				request.getSession().setAttribute("traveler", "true");
			}
			// �����˱�������
			commonDao.doUpdateSqlQuery("update config set booktype = '"
					+ bookType + "'");
			request.getSession().setAttribute("pass", "ok");
			// ���˱����ͷŵ�������
			if (CacheManager.getCacheInfo("bookType") == null) {
				Cache c = new Cache();
				c.setKey("bookType");
				c.setValue(bookType);
				CacheManager.putCache("bookType", c);
			}
			// �����˱������͵�session��.
			request.getSession().setAttribute("booktype", bookType);
			return "success";
		} else {
			request.setAttribute("message", "�������");
			return "failure";
		}
	}

	public String logout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("pass");
		session.removeAttribute("traveler");
		session.invalidate();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			// ��������е������˵��б�
			CacheManager.clearAll();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write("�ɹ��˳�ϵͳ.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String repassword() {
		dao = new LoginDao();
		pass = getPass();
		HttpServletResponse response = ServletActionContext.getResponse();
		dao.doUpdatePass(pass);
		try {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write("�����޸ĳɹ�");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}  

	public String beforeUpdate() {
		dao = new LoginDao();
		pass = getPass();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType("text/html;charset=GBK");
			if (!dao.doCheckPass(pass))
				response.getWriter().write("��������������룡");
			else
				response.getWriter().write("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTraveler() {
		return traveler;
	}

	public void setTraveler(String traveler) {
		this.traveler = traveler;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
}
