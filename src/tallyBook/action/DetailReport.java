package tallyBook.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myOwnLibrary.util.Util;

import org.apache.struts2.ServletActionContext;

import tallyBook.dao.CommonSelDao;
import tallyBook.dao.ConstarctReportDao;
import tallyBook.pojo.TallyType;

/**
 * 详细报表统计.
 * 
 * @author renjie120 419723443@qq.com
 * 
 */
public class DetailReport {
	ConstarctReportDao dao = null;
	String year;
	String type;
	String month;
	String bigType;
	String smallType;
	String splitNo;

	public ConstarctReportDao getDao() {
		return dao;
	}

	public void setDao(ConstarctReportDao dao) {
		this.dao = dao;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	public String getSmallType() {
		return smallType;
	}

	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 按月统计每月里面的收支信息.得到每月里面的最多的几项开支信息.
	 * 
	 * @return
	 */
	public String reportInMonthPage() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getTopNReport(year, month, bigType, smallType, 10);

		List newTopN = dao.getTopNewNReport(year, month, bigType, smallType);

		String sum = dao.getSumNReport(year, month, bigType, smallType);

		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		String ansStr = parseReportXml(ans, year, month);
		request.setAttribute("xml", ansStr);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("newTopN", newTopN);
		request.setAttribute("sum", sum);
		return "reportMonthPage";
	}

	/**
	 * 按年统计每月里面的收支信息.得到每月里面的最多的几项开支信息.
	 * 
	 * @return
	 */
	public String reportInYearPage() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getTopNYearReport(year, bigType, smallType, 15);

		List newTopN = dao.getTopNewNYearReport(year, bigType, smallType);

		String newTopNSum = dao.getSumNYearReport(year, bigType, smallType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		String ansStr = parseReportYearXml(ans, year);
		request.setAttribute("xml", ansStr);
		request.setAttribute("sum", newTopNSum);
		request.setAttribute("newTopN", newTopN);
		request.setAttribute("year", year);
		return "reportYearPage";
	}

	public String getTotalBigType() {
		return "";
	}

	public String getEachMonthByType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		String typeName = bigType;
		// 查询列表
		List data = dao.getEachMonthByTypeData(year, smallType);
		// 查询列表
		List newTopN = dao.getEachMonthByType(year, smallType);

		String sum = dao.getEachMonthSumByType(year, smallType);

		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		String ansStr = parseEachMonthByType(data, year, typeName);
		request.setAttribute("xml", ansStr);
		request.setAttribute("sum", sum);
		request.setAttribute("smallType", smallType);
		request.setAttribute("newTopN", newTopN);
		request.setAttribute("year", year);
		return "monthRankReport";
	}

	private String parseEachMonthByType(List dataList, String year,
			String typeName) {
		String title = year + "年";
		if ("-1".equals(year)) {
			title = "全部";
		}
		StringBuffer buffer = new StringBuffer("<chart caption='" + title + "["
				+ typeName + "]分析'  showValues='1'>");
		Iterator it = dataList.iterator();
		while (it.hasNext()) {
			Object[] set = (Object[]) it.next();
			buffer.append("<set label='" + set[0] + "月' value='" + set[1]
					+ "'/>");
		}
		buffer.append("</chart>");
		return buffer.toString();
	}

	private String parseReportYearXml(List dataList, String year) {
		String title = year + "年支出分析";
		if ("-1".equals(year)) {
			title = "全部支出分析";
		}
		StringBuffer buffer = new StringBuffer("<chart caption='" + title
				+ "'  showValues='1'>");
		Iterator it = dataList.iterator();
		while (it.hasNext()) {
			Object[] set = (Object[]) it.next();
			buffer.append("<set label='" + set[3] + "' value='" + set[0]
					+ "'/>");
		}
		buffer.append("</chart>");
		return buffer.toString();
	}

	private String parseReportXml(List dataList, String year, String month) {
		StringBuffer buffer = new StringBuffer("<chart caption='" + year + "年"
				+ month + "月支出分析'  showValues='1'>");
		Iterator it = dataList.iterator();
		while (it.hasNext()) {
			Object[] set = (Object[]) it.next();
			buffer.append("<set label='" + set[2] + "' value='" + set[0]
					+ "'/>");
		}
		buffer.append("</chart>");
		return buffer.toString();
	}

	/**
	 * 返回详细的报表展示信息.
	 * 
	 * @return
	 */
	public String reportDetail() {
		ConstarctReportDao dao = new ConstarctReportDao();
		// 根据类别进行统计
		List groupByType = dao.getDetailReportByType(year, month, bigType,
				smallType);
		//是否考虑拆分的情况，默认是考虑的！
		boolean checkSplitNo = true;
		// 查询全部的金额，类型列表
		if ("no".equals(splitNo))
			checkSplitNo = false;
		List ans = dao.getDetailReport(year, month, bigType, smallType,
				checkSplitNo);
		// 查询每月的收支总数
		List inAndOut = dao.getInAndOut(checkSplitNo);
		List allInAndOut = dao.getAllInAndOut(checkSplitNo);
		// 查询每年的收支统计
		List inAndOut2 = dao.getYearInAndOut();
		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		if (bigType == null)
			parseResustMap(ans, request, inAndOut, inAndOut2, allInAndOut,
					false);
		else
			parseResustMap(ans, request, inAndOut, inAndOut2, allInAndOut, true);
		parseGroupResustMap(groupByType, request);
		// 如果是点击大类别进入的查询界面，就传到前台有一个参数表示是显示的二级类别，不用再出现连接在标题里面！
		if (bigType != null) {
			request.setAttribute("nolinktitle", "true");
			request.setAttribute("bigType", bigType);
		}
		request.setAttribute("splitNo", splitNo);
		return "detailReport";
	}

	/**
	 * 返回详细的大类别信息和时间的报表.
	 * 
	 * @return
	 */
	public String reportDetailByTimeAndType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.reportDetailByTimeAndType(year, month, bigType);

		List sum = dao.reportDetailByTimeAndTypeSum(year, month, bigType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		reportDetailByTimeAndType(ans, sum, request);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("type", smallType);
		return "details";
	}

	/**
	 * 返回详细的小类别信息和时间的报表.
	 * 
	 * @return
	 */
	public String reportDetailByTimeAndSmallType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.reportDetailByTimeAndSmallType(year, month, smallType);

		List sum = dao
				.reportDetailByTimeAndSmallTypeSum(year, month, smallType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// 处理查询的结果集合得到需要的字符串结果。
		reportDetailByTimeAndType(ans, sum, request);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("type", smallType);
		return "details";
	}

	/**
	 * 得到收支统计字符串。
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	private String getStr(String in, String out) {
		double dIn = Double.parseDouble(in);
		double dOut = Double.parseDouble(out);
		String mv = "-";
		if (dIn != 0) {
			mv = "" + Util.multiply(Util.divide(dOut, dIn, 4), 100);
		} 
		return "<font color='green' tag='1'>" + in
				+ "</font>&nbsp;<font color='black' tag='2'>" + out
				+ "</font>&nbsp;<font color='red' tag='3'>"
				+ Util.subtract(dIn, dOut)
				+ "</font>&nbsp;<font color='black' tag='4'>"
				+ mv+ "%</font>";

//		return "<font color='red' tag='3'>" + Util.subtract(dIn, dOut)
//				+ "</font>&nbsp;<font color='black' tag='4'>" + mv + "%</font>";
	}

	/**
	 * 分析详细的报表数据集合.
	 * 
	 * @param sourceList
	 * @param request
	 * @param inAndOut
	 * @param inAndOut2
	 * @param inAndOut3
	 * @param includeIn
	 *            计算百分比的时候，分母是否计入收入！默认为true。
	 */
	private void parseResustMap(List sourceList, HttpServletRequest request,
			List inAndOut, List inAndOut2, List inAndOut3, Boolean includeIn) {
		CommonSelDao commonSelDao = new CommonSelDao();
		// 得到全部的金额类型下拉菜单.
		List allTallyType = null;
		Map allTallyTypeToMoneyType = null;
		// 如果不计算收入作为分母，就要根据金额类型进行筛选.
		if (!includeIn) {
			allTallyType = commonSelDao.getAllTallyTypes();
			allTallyTypeToMoneyType = new HashMap();
			for (int i = 0; i < allTallyType.size(); i++) {
				TallyType _temp = (TallyType) allTallyType.get(i);
				allTallyTypeToMoneyType.put(_temp.getTypeCode(), _temp
						.getMoneyType());
			}
		}

		List allYearAndMonths = new ArrayList();
		List allTypes = new ArrayList();
		Map timeToMoney = new HashMap();
		Map timeToInAndOut = new HashMap();
		Map yearToInAndOut = new HashMap();
		Map yearAndTypeToMoney = new HashMap();
		Map sumAllMoney = new HashMap();
		Map allTypeName = new HashMap();
		// 下面进行逐个循环找到涉及到多少个月份，以及得到金额的统计！
		if (sourceList != null) {
			Iterator it = sourceList.iterator();
			while (it.hasNext()) {
				Object[] infos = (Object[]) it.next();
				String year = infos[0].toString();
				String month = infos[1].toString();
				String typeName = infos[2].toString();
				String type = infos[3].toString();
				String money = infos[4].toString();
				String tm = year + "," + month;
				if (!allYearAndMonths.contains(tm)) {
					allYearAndMonths.add(tm);
				}
				if (!allTypes.contains(type)) {
					allTypes.add(type);
				}
				allTypeName.put(type, typeName);
				timeToMoney.put(tm + "," + type, money);
				yearAndTypeToMoney = yearAndTypeToMoney(yearAndTypeToMoney,
						year, type, money);
				sumAllMoney = sumAllMoney(sumAllMoney, year, type, money,
						includeIn, allTallyTypeToMoneyType);
			}
		}

		timeToInAndOut = getTimeToInAndOut(inAndOut);
		yearToInAndOut = getYearToInAndOut(inAndOut2);
		String[] ans = getAllInAndOut(inAndOut3);

		request.setAttribute("allInAndOut", getStr(ans[0], ans[1]));
		request.setAttribute("yearAndTypeToMoney", yearAndTypeToMoney);
		request.setAttribute("yearToMoney", sumAllMoney);
		request.setAttribute("timeToInAndOut", timeToInAndOut);
		request.setAttribute("yearToInAndOut", yearToInAndOut);
		request.setAttribute("allYearAndMonths", allYearAndMonths);
		request.setAttribute("allTypes", allTypes);
		request.setAttribute("allTypeName", allTypeName);
		request.setAttribute("timeToMoney", timeToMoney);
	}

	private void parseResustMap(List sourceList, HttpServletRequest request,
			List inAndOut, List inAndOut2, List inAndOut3) {
		parseResustMap(sourceList, request, inAndOut, inAndOut2, inAndOut3,
				true);
	}

	/**
	 * 按年和类型统计总额.
	 * 
	 * @param year
	 * @param type
	 * @param money
	 * @return
	 */
	private Map yearAndTypeToMoney(Map m, String year, String type, String money) {
		String key = year + "," + type;
		if (m.containsKey(key)) {
			double d = (Double) m.get(key);
			m.put(key, Util.add(d, Double.parseDouble(money)));
		} else {
			m.put(key, Double.parseDouble(money));
		}
		return m;
	}

	/**
	 * 计算总额.
	 * 
	 * @param m
	 * @param year
	 * @param money
	 * @param includeIn
	 *            是否计算收入作为分母.
	 * @return
	 */
	private Map sumAllMoney(Map m, String year, String type, String money,
			Boolean includeIn, Map map) {
		String key = year;
		if (!includeIn) {
			// 如果是支出，就不计入总额--仅仅计算收入
			if ("2".equals(map.get(type))) {
				addToMap(m, year + "out", money);
			} else {
				addToMap(m, year + "in", money);
			}

			// 如果是支出，就不计入总额--仅仅计算收入
			if ("2".equals(map.get(type))) {
				return m;
			}
		}
		addToMap(m, key, money);
		return m;
	}

	private void addToMap(Map m, String key, String money) {
		if (m.containsKey(key)) {
			double d = (Double) m.get(key);
			m.put(key, Util.add(d, Double.parseDouble(money)));
		} else {
			m.put(key, Double.parseDouble(money));
		}
	}

	/**
	 * 分析得到每年的收支统计！
	 * 
	 * @param inAndOut2
	 * @return
	 */
	public Map getYearToInAndOut(List inAndOut2) {
		Map yearToInAndOut = new HashMap();
		if (inAndOut2 != null) {
			Iterator it = inAndOut2.iterator();
			while (it.hasNext()) {
				Object[] infos = (Object[]) it.next();
				String year = infos[0].toString();
				String in = infos[1].toString();
				String out = infos[2].toString();
				yearToInAndOut.put(year, getStr(in, out));
			}
		}
		return yearToInAndOut;
	}

	/**
	 * 分析得到每月的收支统计
	 * 
	 * @param inAndOut
	 * @return
	 */
	public Map getTimeToInAndOut(List inAndOut) {
		Map timeToInAndOut = new HashMap();
		if (inAndOut != null) {
			Iterator it = inAndOut.iterator();
			while (it.hasNext()) {
				Object[] infos = (Object[]) it.next();
				String year = infos[0].toString();
				String month = infos[1].toString();
				String in = infos[2].toString();
				String out = infos[3].toString();
				String tm = year + "," + month;
				timeToInAndOut.put(year + "," + month, getStr(in, out));
			}
		}
		return timeToInAndOut;
	}

	/**
	 * 得到全部的收支数据！
	 * 
	 * @param inAndOut3
	 * @return
	 */
	public String[] getAllInAndOut(List inAndOut3) {
		String[] ans = new String[3];
		if (inAndOut3 != null) {
			Object[] infos = (Object[]) inAndOut3.get(0);
			String in = infos[0].toString();
			String out = infos[1].toString();
			ans[0] = in;
			ans[1] = out;
			ans[2] = Util.subtract(Double.parseDouble(in), Double
					.parseDouble(out))
					+ "";
		}
		return ans;
	}

	private void parseGroupResustMap(List sourceList, HttpServletRequest request) {
		Map typeToMoney = new HashMap();
		Map typeToPer = new HashMap();
		// 下面进行逐个循环找到涉及到多少个月份，以及得到金额的统计！
		if (sourceList != null) {
			Iterator it = sourceList.iterator();
			while (it.hasNext()) {
				Object[] infos = (Object[]) it.next();
				String type = infos[1].toString();
				String money = infos[0].toString();
				String percents = infos[2].toString();
				typeToMoney.put(type, money);
				typeToPer.put(type, percents);
			}
		}
		request.setAttribute("typeToMoney", typeToMoney);
		request.setAttribute("typeToPer", typeToPer);
	}

	private void reportDetailByTimeAndType(List sourceList, List sum,
			HttpServletRequest request) {
		request.setAttribute("details", sourceList);
		request.setAttribute("sum", sum);
	}

	/**
	 * 返回详细的报表展示.
	 * 
	 * @return
	 */
	public String reportDetailInBigType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getDetailReport(year, month, bigType, smallType, true);
		HttpServletRequest request = ServletActionContext.getRequest();
		List inAndOut = dao.getInAndOut(true);
		List inAndOut2 = dao.getYearInAndOut();
		List inAndOut3 = dao.getAllInAndOut(true);
		// 处理查询的结果集合得到需要的字符串结果。
		parseResustMap(ans, request, inAndOut, inAndOut2, inAndOut3);
		return "detailReport";
	}

	public String getSplitNo() {
		return splitNo;
	}

	public void setSplitNo(String splitNo) {
		this.splitNo = splitNo;
	}

}
