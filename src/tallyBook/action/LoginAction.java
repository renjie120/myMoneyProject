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
 * 登陆系统
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
	// 是否游客访问，是的话就只可以看到支出，不可以看到收入！
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
			// 如果不是游客访问，就说明可以看到收入信息
			if (traveler == null) {
				commonDao.doUpdateSqlQuery("update config set canseein = '1'");
				ConstarctReportDao dao2 = new ConstarctReportDao();
				// 全部的收支
				List allInAndOut = dao2.getAllInAndOut(true);
				// 查询每年的收支统计
				List inAndOut2 = dao2.getYearInAndOut();

				DetailReport report = new DetailReport();
				Map yearMap = report.getYearToInAndOut(inAndOut2);

				String[] ans = report.getAllInAndOut(allInAndOut);

				// 设置session里面是否是游客访问！
				request.getSession().setAttribute("traveler", "false");
				request.getSession().setAttribute("yearMap", yearMap);
				request.getSession().setAttribute("in", ans[0]);
				request.getSession().setAttribute("out", ans[1]);
				request.getSession().setAttribute("all", ans[2]);
			}
			// 否则不可以看到收入信息
			else {
				commonDao.doUpdateSqlQuery("update config set canseein = '3'");
				request.getSession().setAttribute("traveler", "true");
			}
			// 设置账本的类型
			commonDao.doUpdateSqlQuery("update config set booktype = '"
					+ bookType + "'");
			request.getSession().setAttribute("pass", "ok");
			// 将账本类型放到缓存中
			if (CacheManager.getCacheInfo("bookType") == null) {
				Cache c = new Cache();
				c.setKey("bookType");
				c.setValue(bookType);
				CacheManager.putCache("bookType", c);
			}
			// 设置账本的类型到session中.
			request.getSession().setAttribute("booktype", bookType);
			return "success";
		} else {
			request.setAttribute("message", "密码错误");
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
			// 清除缓存中的下拉菜单列表
			CacheManager.clearAll();
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write("成功退出系统.");
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
			response.getWriter().write("密码修改成功");
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
				response.getWriter().write("密码错误重新输入！");
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
