package tallyBook.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myOwnLibrary.CommonStockSelDao;
import myOwnLibrary.taglib.OptionColl;
import myOwnLibrary.util.DateUtil;
import myOwnLibrary.util.Util;
import myOwnLibrary.util.XlsExport;

import org.apache.struts2.ServletActionContext;

import tallyBook.bo.QueryCondition;
import tallyBook.dao.CommonSelDao;
import tallyBook.dao.DaoUtil;
import tallyBook.dao.MoneyDetailDao;
import tallyBook.pojo.DiaryDetail;
import tallyBook.pojo.GongguoDetail;
import tallyBook.pojo.MoneyDetail;
import tallyBook.pojo.ShopCard;

import common.base.SpringContextUtil;

/**
 * 收支增加，删除，修改.
 * 
 * @author renjie120 419723443@qq.com addDiaryFromPhone
 */
public class GridAction {
	String queryStr = "SELECT V.MONEY_SNO,V.MONEY_TIME,V.MONEY,V.MONEY_DESC,V.TALLY_TYPE_DESC,V.TYPE_ID INOROUT,V.CARD FROM MONEY_DETAIL_VIEW V ";
	String queryCount = "SELECT COUNT(1) FROM MONEY_DETAIL_VIEW ";
	DaoUtil dao = null;
	private String shopCard;
	CommonSelDao selDao = null;
	private String moneySno;
	private String moneyTime;
	private String money;
	private String moneys;
	private String diaries;
	private String gongguo;
	private String maxmoney;
	private String moneySort;
	private String moneyDesc;
	private String message;
	private String minTime;
	private String maxTime;
	private String inOrOutTypeValue;
	private String secondSort;
	private String pass;
	private String month;
	private String year;
	private Integer splitNo;
	private String splitNoStr;
	private String moneyTypeDesc;

	public String getMoneyTypeDesc() {
		return moneyTypeDesc;
	}

	public void setMoneyTypeDesc(String moneyTypeDesc) {
		this.moneyTypeDesc = moneyTypeDesc;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getInOrOutTypeValue() {
		return inOrOutTypeValue;
	}

	public void setInOrOutTypeValue(String inOrOutTypeValue) {
		this.inOrOutTypeValue = inOrOutTypeValue;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DaoUtil getDao() {
		return dao;
	}

	public void setDao(DaoUtil dao) {
		this.dao = dao;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getMoneySort() {
		return moneySort;
	}

	public void setMoneySort(String moneySort) {
		this.moneySort = moneySort;
	}

	public String getMoneyDesc() {
		return moneyDesc;
	}

	public void setMoneyDesc(String moneyDesc) {
		this.moneyDesc = moneyDesc;
	}

	public static void main(String[] args){
		System.out.println();
	} 
	
	private static Date getDate(String dateStr, String formateStr) {
		SimpleDateFormat formatter2 = new SimpleDateFormat(formateStr);
		Date date = new Date();
		try {
			date = formatter2.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 综合查询跳转到重新查询界面.
	 * 
	 * @return
	 */
	public String reQuery() {
		setMinTime(getMinTime());
		setMaxTime(getMaxTime());
		setMonth(getMonth());
		setYear(getYear());
		setMoney(getMoney());
		setMaxmoney(getMaxmoney());
		setInOrOutTypeValue(getInOrOutTypeValue());
		setShopCard(getShopCard());
		setMoneySort(getMoneySort());
		setMoneyDesc(getMoneyDesc());
		return "list";
	}

	/**
	 * 重新查询，同时清除session
	 * 
	 * @return
	 */
	public String reSetQuery() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		setMinTime(getMinTime());
		setMaxTime(getMaxTime());
		setMoney(getMoney());
		setShopCard(getShopCard());
		setMaxmoney(getMaxmoney());
		setInOrOutTypeValue(getInOrOutTypeValue());
		setMoneySort(getMoneySort());
		setMoneyDesc(getMoneyDesc());
		return "list";
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

 
	public String exportExcel() {
		dao =(DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response =ServletActionContext.getResponse();
		MoneyDetail condition = getQueryCondition();
		StringBuffer buf = new StringBuffer();
		buf.append(" where 1=1 ");
		buf.append(QueryCondition.getMDetailConditon(condition)); 
	 
		SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
		String a1 = dateformat1.format(new Date());
		response.setContentType("Application/excel");
		String fileNameString = toUtf8String("明细" + a1
				+ ".xls");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileNameString);

		XlsExport e = new XlsExport(); 
		// 添加标题
		e.createRow(0);
		 
		int jj=0;
		e.setCell(jj++,"序号");
		e.setCell(jj++,"时间");
		e.setCell(jj++,"金额");
		e.setCell(jj++,"类别"); 
		e.setCell(jj++,"消费卡");
		e.setCell(jj++,"收支");
		String sortname = request.getParameter("sortname");
		sortname = "undefined".equals(sortname) ? "" : sortname;
		String sortorder = request.getParameter("sortorder");
		sortorder = "undefined".equals(sortorder) ? "" : sortorder;
		List details = dao.doQueryList(getSortStr(queryStr
				+ buf.toString() + " order by money_time  desc,MONEY",
				sortname, sortorder));
		Iterator it = details.iterator();
		List realDetails = new ArrayList();
		int i=0;
		while (it.hasNext()) {
			e.createRow(i + 1);
			int ii=0;
			Object[] objs = (Object[]) it.next(); 
			e.setCell(ii++, objs[0].toString());
			e.setCell(ii++, objs[1].toString());
			e.setCell(ii++, objs[2].toString());
			e.setCell(ii++, objs[3].toString());
			e.setCell(ii++, objs[4].toString());
			e.setCell(ii++, objs[5].toString());
			e.setCell(ii++, objs[5].toString().equals("1") ? "+" : "—");  
		}
		e.exportXls(response);
		return null;
	}
	
	/**
	 * 统计总的收入，支出信息
	 * 
	 * @return
	 */
	public String getSumMoney() {
		dao =(DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		MoneyDetail queryCondition = new MoneyDetail();
		queryCondition.setMinTime(getMinTime());
		if (!Util.isBlank(getShopCard()) && !"-1".equals(getShopCard()))
			queryCondition.setShopCard(Integer.parseInt(getShopCard()));
		queryCondition.setMaxTime(getMaxTime());
		queryCondition.setMoney(Double.parseDouble(Util.notBlankStr(getMoney(),
				"0")));
		queryCondition.setMaxmoney(getMaxmoney());
		queryCondition.setMoneyType(getMoneySort());
		queryCondition.setInOrOutTypeValue(getInOrOutTypeValue());
		queryCondition.setMoneyDesc(getMoneyDesc());

		StringBuffer buf = new StringBuffer();
		buf
				.append("select decode(sum(money),null,0,sum(money)) from money_detail_view t where t.type_id = '1' ");
		buf.append(QueryCondition.getMDetailConditon(queryCondition));
		double in = dao.queryDouble(buf.toString());

		StringBuffer buf2 = new StringBuffer();
		buf2
				.append("select decode(sum(money),null,0,sum(money)) from money_detail_view t where t.type_id = '2' ");
		buf2.append(QueryCondition.getMDetailConditon(queryCondition));
		double out =  dao.queryDouble(buf2.toString());

		try {
			response.setContentType("text/html;charset=GBK");
			String ans = "" + in + "$" + out + "$" + Util.subtract(in, out);
			response.getWriter().write(ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回信用卡相关信息.
	 * 
	 * @return
	 */
	public String getShopCardInfos() {
		dao=(DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		HttpServletRequest request = ServletActionContext.getRequest();
		String queryStr = "select card_desc,card_deadline,card_no,retainMoney,Allmoney,moneycount,card_url,card_sno,card_money from shopcard_view ";
		List realDetails = new ArrayList();
		List details = dao.doGetSqlQueryList(queryStr);
		Iterator it = details.iterator();
		while (it.hasNext()) {
			Object[] objs = (Object[]) it.next();
			ShopCard detail = new ShopCard();
			detail.setCardDesc(objs[0].toString());
			detail.setCardDeadLine(Integer.parseInt(objs[1].toString()));
			detail.setCardNo(objs[2].toString());
			detail.setRetainMoney(Double.parseDouble(Util.notBlankStr(objs[3],
					"0")));
			detail.setSumMoney(Double.parseDouble(Util
					.notBlankStr(objs[4], "0")));
			detail.setTimes(Integer.parseInt(objs[5].toString()));
			detail.setCardUrl(objs[6].toString());
			detail.setCardSno(Integer.parseInt(objs[7].toString()));
			detail.setCardMoney(Double.valueOf(Util.notBlankStr(objs[8], "0")));
			realDetails.add(detail);
		}
		request.setAttribute("shopcardlist", realDetails);
		return "shopcard";
	}

	/**
	 * 得到要显示的表格的起始行和终止行还有当前页数。
	 * 
	 * @param request
	 * @param total
	 * @return
	 */
	private int[] getStartAndEnd(HttpServletRequest request, int total) {
		int page = 1;
		if (request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));
		int rp = 10;
		if (request.getParameter("rp") != null)
			rp = Integer.parseInt(request.getParameter("rp"));
		int start = (page - 1) * rp + 1;
		int end = (page * rp + 1) > total + 1 ? total + 1 : page * rp + 1;
		return new int[] { start, end, page };
	}

	private MoneyDetail getQueryCondition(){
		MoneyDetail queryCondition = new MoneyDetail();
		queryCondition.setMinTime(getMinTime());
		if (!Util.isBlank(getShopCard()) && !"-1".equals(getShopCard()))
			queryCondition.setShopCard(Integer.parseInt(getShopCard()));
		queryCondition.setMaxTime(getMaxTime());
		queryCondition.setMoney(Double.parseDouble(Util.notBlankStr(getMoney(),
				"0")));
		queryCondition.setMaxmoney(getMaxmoney());
		queryCondition.setMoneyType(getMoneySort());
		queryCondition.setInOrOutTypeValue(getInOrOutTypeValue());
		queryCondition.setMoneyDesc(getMoneyDesc());
		return queryCondition;
	}
	 
	/**
	 * 查询消费细节的表格.
	 * 
	 * @return
	 */
	public String initMoneyDetailGrid() {
		dao =(DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		
		MoneyDetail queryCondition = getQueryCondition();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse(); 
		int[] startAndEndAndPage = new int[3];
	    StringBuffer buf = new StringBuffer();
		buf.append(" where 1=1 ");
		buf.append(QueryCondition.getMDetailConditon(queryCondition));
		String sortname = request.getParameter("sortname");
		sortname = "undefined".equals(sortname) ? "" : sortname;
		String sortorder = request.getParameter("sortorder");
		sortorder = "undefined".equals(sortorder) ? "" : sortorder;
		int count = 0;
		try {
			count = dao.queryInt(
					queryCount + buf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		startAndEndAndPage = getStartAndEnd(request, count);
		List details = dao.doQueryPagingSqlList(getSortStr(queryStr
				+ buf.toString() + " order by money_time  desc,MONEY",
				sortname, sortorder), startAndEndAndPage[0],
				startAndEndAndPage[1]);
		Iterator it = details.iterator();
		List realDetails = new ArrayList();
		while (it.hasNext()) {
			Object[] objs = (Object[]) it.next();
			MoneyDetail detail = new MoneyDetail();
			detail.setMoneySno(Integer.parseInt(objs[0].toString()));
			detail.setMoneyTime((Date) objs[1]);
			detail.setMoney(Double.parseDouble(objs[2].toString()));
			detail.setMoneyDesc(Util.notBlankStr(objs[3], ""));
			detail.setMoneyType(Util.notBlankStr(objs[4], ""));
			detail.setInorout(objs[5].toString().equals("1") ? "+" : "—");
			detail.setShopCardStr(Util.notBlankStr(objs[6], ""));
			realDetails.add(detail);
		}
		String ans = Util.getGridJsonStr(realDetails, count,
				startAndEndAndPage[2]);
		try {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String initExtMoneyDetailGrid() {
		dao =(DaoUtil)SpringContextUtil.getBean("daoUtil");
		MoneyDetail queryCondition = new MoneyDetail();
		queryCondition.setMinTime(getMinTime());
		if (!Util.isBlank(getShopCard()) && !"-1".equals(getShopCard()))
			queryCondition.setShopCard(Integer.parseInt(getShopCard()));
		queryCondition.setMaxTime(getMaxTime());
		queryCondition.setMoney(Double.parseDouble(Util.notBlankStr(getMoney(),
				"0")));
		queryCondition.setMaxmoney(getMaxmoney());
		queryCondition.setMoneyType(getMoneySort());
		queryCondition.setInOrOutTypeValue(getInOrOutTypeValue());
		queryCondition.setMoneyDesc(getMoneyDesc());

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		int[] startAndEndAndPage = new int[3];
		String queryStr = "SELECT V.MONEY_SNO,V.MONEY_TIME,V.MONEY,V.MONEY_DESC,V.TALLY_TYPE_DESC,V.TYPE_ID INOROUT,V.CARD FROM MONEY_DETAIL_VIEW V ";
		String queryCount = "SELECT COUNT(1) FROM MONEY_DETAIL_VIEW ";
		StringBuffer buf = new StringBuffer();
		buf.append(" where 1=1 ");
		buf.append(QueryCondition.getMDetailConditon(queryCondition));
		String sortname = request.getParameter("sortname");
		sortname = "undefined".equals(sortname) ? "" : sortname;
		String sortorder = request.getParameter("sortorder");
		sortorder = "undefined".equals(sortorder) ? "" : sortorder;
		int count = 0;
		try {
			count =  dao.queryInt(queryCount + buf.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		startAndEndAndPage = Util.getStartAndEnd(Integer.parseInt(request
				.getParameter("start")), Integer.parseInt(request
				.getParameter("limit")), count);

		List details = dao.doQueryPagingSqlList(getSortStr(queryStr
				+ buf.toString() + " order by money_time  desc,MONEY",
				sortname, sortorder), startAndEndAndPage[0] + 1, Integer
				.parseInt(request.getParameter("limit")));
		Iterator it = details.iterator();
		List realDetails = new ArrayList();
		while (it.hasNext()) {
			Object[] objs = (Object[]) it.next();
			MoneyDetail detail = new MoneyDetail();
			detail.setMoneySno(Integer.parseInt(objs[0].toString()));
			detail.setMoneyTime((Date) objs[1]);
			detail.setMoney(Double.parseDouble(objs[2].toString()));
			detail.setMoneyDesc(Util.notBlankStr(objs[3], ""));
			detail.setMoneyType(Util.notBlankStr(objs[4], ""));
			detail.setInorout(objs[5].toString().equals("1") ? "+" : "—");
			detail.setShopCardStr(Util.notBlankStr(objs[6], ""));
			realDetails.add(detail);
		}
		String ans = Util.getExtGridJsonStr(realDetails, count,
				startAndEndAndPage[2]);
		try {
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(ans);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加排序之后的新的sql语句. 
	 * @param oldsql
	 * @param sortName
	 * @param orderType
	 * @return
	 */
	private String getSortStr(String oldsql, String sortName, String orderType) {
		StringBuilder sql = new StringBuilder();
		if (!Util.isBlank(sortName))
			sql.append("select * from (").append(oldsql).append(") ORDER BY ")
					.append(sortName).append(" ").append(orderType);
		else
			return oldsql;
		return sql.toString();
	}

	/**
	 * 得到详细信息. 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public String beforeUpdateMoneyDetail() {
		moneySno = getMoneySno();
		MoneyDetailDao tempdao = new MoneyDetailDao();
		MoneyDetail moneyDetail; 
		try {
			moneyDetail = tempdao.doGet(Integer.parseInt(moneySno));
			// 收支描述
			setMoneyDesc(moneyDetail.getMoneyDesc());
			// 设置卡类型
			setShopCard(Integer.toString(moneyDetail.getShopCard()));
			// 收支金额
			setMoney(new Double(moneyDetail.getMoney()).toString());
			// 收支类型
			setMoneySort(moneyDetail.getMoneyType());
			// 流水号
			setMoneySno(new Integer(moneyDetail.getMoneySno()).toString());
			// 时间
			setMoneyTime(Util.dateToStr(moneyDetail.getMoneyTime(),
					"yyyy-MM-dd"));
			setMoneyTypeDesc(moneyDetail.getMoneyTypeDesc()); 
			setSplitNo(moneyDetail.getSplitNo());
			setSplitNoStr(moneyDetail.getSplitNoStr());
			setMessage("修改信息");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "info";
	}

	/**
	 * 删除信息.
	 * 
	 * @return
	 */
	public String deleteMoney() {
		int ans = 0;
		HttpServletResponse response = ServletActionContext.getResponse();
		MoneyDetailDao tempdao = new MoneyDetailDao();
		moneySno = getMoneySno();
		moneySno = moneySno.replace("#", ",");
		String[] snos = moneySno.split(",");
		for (int i = 0; i < snos.length; i++) {
			ans += tempdao.doDelete(Integer.parseInt(snos[i]));
		}
		response.setContentType("text/html;charset=GBK");
		try {
			if (ans > 0) {
				response.getWriter().write("删除" + ans + "条记录成功!");
			} else {
				response.getWriter().write("删除" + ans + "条记录失败!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提取日记信息到数据库
	 * 
	 * @return
	 */
	public String addDiaryFromPhone() {
		String[] everDiary = diaries.split("\\$;");
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonStockSelDao seldao = new CommonStockSelDao();
		OptionColl diaryTypes = seldao.getDiaryTypeCodes();
		MoneyDetailDao dao = new MoneyDetailDao();
		for (String aDiary : everDiary) {
			String[] details = aDiary.split("\\$,");
			String content = details[0];
			String time = details[1];
			String diarytype = details[2];
			if (!Util.isBlank(time) && !"null".equals(time)
					&& !"".equals(content)) {
				DiaryDetail diary = new DiaryDetail();
				diary.setTime(DateUtil.getDate(time, "yyyy-MM-dd"));
				diary.setContent(content);
				// 下面要转换类型字符串为编码!
				diary.setDiaryType(Util.notBlank(diaryTypes.findId(diarytype),
						diarytype));
				dao.doCreate(diary);
			}
		}
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write("保存" + everDiary.length + "条记录成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 提取功过信息到数据库
	 * 
	 * @return
	 */
	public String addGongguoFromPhone() {
		String[] everGongguo = gongguo.split("\\$;");
		HttpServletResponse response = ServletActionContext.getResponse();
		CommonStockSelDao seldao = new CommonStockSelDao();
		OptionColl gongguoType = seldao.getGongguoTypeCodes();
		MoneyDetailDao dao = new MoneyDetailDao();
		try {
			for (String aGongguo : everGongguo) {
				String[] details = aGongguo.split("\\$,");
				String content = details[0];
				String time = details[1];
				if (!Util.isBlank(time) && !"null".equals(time)
						&& !"".equals(content) && !"可多选".equals(content)
						&& !"未选择".equals(content)) {
					String[] temp = content.split(",");
					// 可能有多选的情形的数据,全部添加进去
					for (int _i = 0; _i < temp.length; _i++) {
						GongguoDetail gongguo = new GongguoDetail();
						gongguo.setTime(DateUtil.getDate(time, "yyyy-MM-dd"));
						gongguo.setContent(Util.changeToNotNull(gongguoType
								.findId(temp[_i]), temp[_i]));
						dao.doCreate(gongguo);
					}
				}
			}
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write("保存" + everGongguo.length + "条记录成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 直接从手机上面传递来的moneys字符串进行金额的保存到本地数据库.
	 * 
	 * @return
	 */
	public String datadistillFromPhone() {
		String[] everMoney = moneys.split("\\$;");
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		CommonStockSelDao seldao = new CommonStockSelDao();
		OptionColl tallyTypes = seldao.getTallyTypeCodes();
		MoneyDetailDao dao = new MoneyDetailDao();
		for (String aMoney : everMoney) {
			String[] details = aMoney.split("\\$,");
			String time = details[0];
			String money = details[1];
			String moneyType = details[2];
			String moneyDesc = details[3];
			if (!Util.isBlank(time) && !"null".equals(time)
					&& !"".equals(money)) {
				MoneyDetail mDetail = new MoneyDetail();
				mDetail.setMoneyTime(DateUtil.getDate(time, "yyyy-MM-dd"));
				mDetail.setMoney(Double.parseDouble(money));
				if (Util.isBlank(tallyTypes.findId(moneyType))) {
					System.out.println(moneyType);
					if (Util.isBlank(tallyTypes.findId(moneyDesc))) {
						System.out.println(moneyDesc);
					}
				}
				// 下面要转换类型字符串为编码!
				mDetail.setMoneyType(Util.notBlank(tallyTypes.findId(moneyType
						.trim()), tallyTypes.findId(moneyDesc.trim())));
				mDetail.setMoneyDesc(moneyDesc);
				// 设置账本类型
				mDetail.setBooktype("1");

				mDetail.setShopCard(-1);
				dao.doCreate(mDetail);
			}
		}
		response.setContentType("text/html;charset=GBK");
		try {
			response.getWriter().write("保存" + everMoney.length + "条记录成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getMoneySno() {
		return moneySno;
	}

	public void setMoneySno(String moneySno) {
		this.moneySno = moneySno;
	}

	public String getMoneyTime() {
		return moneyTime;
	}

	public void setMoneyTime(String moneyTime) {
		this.moneyTime = moneyTime;
	}

	public String getMaxmoney() {
		return maxmoney;
	}

	public void setMaxmoney(String maxmoney) {
		this.maxmoney = maxmoney;
	}

	public String getSecondSort() {
		return secondSort;
	}

	public void setSecondSort(String secondSort) {
		this.secondSort = secondSort;
	}

	public String getShopCard() {
		return shopCard;
	}

	public void setShopCard(String shopCard) {
		this.shopCard = shopCard;
	}

	public String getMoneys() {
		return moneys;
	}

	public void setMoneys(String moneys) {
		this.moneys = moneys;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDiaries() {
		return diaries;
	}

	public void setDiaries(String diaries) {
		this.diaries = diaries;
	}

	public String getGongguo() {
		return gongguo;
	}

	public void setGongguo(String gongguo) {
		this.gongguo = gongguo;
	}

	public Integer getSplitNo() {
		return splitNo;
	}

	public void setSplitNo(Integer splitNo) {
		this.splitNo = splitNo;
	}

	public String getSplitNoStr() {
		return splitNoStr;
	}

	public void setSplitNoStr(String splitNoStr) {
		this.splitNoStr = splitNoStr;
	}
}
