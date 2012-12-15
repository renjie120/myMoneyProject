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
 * ��ϸ����ͳ��.
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
	 * ����ͳ��ÿ���������֧��Ϣ.�õ�ÿ����������ļ��֧��Ϣ.
	 * 
	 * @return
	 */
	public String reportInMonthPage() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getTopNReport(year, month, bigType, smallType, 10);

		List newTopN = dao.getTopNewNReport(year, month, bigType, smallType);

		String sum = dao.getSumNReport(year, month, bigType, smallType);

		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		String ansStr = parseReportXml(ans, year, month);
		request.setAttribute("xml", ansStr);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("newTopN", newTopN);
		request.setAttribute("sum", sum);
		return "reportMonthPage";
	}

	/**
	 * ����ͳ��ÿ���������֧��Ϣ.�õ�ÿ����������ļ��֧��Ϣ.
	 * 
	 * @return
	 */
	public String reportInYearPage() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getTopNYearReport(year, bigType, smallType, 15);

		List newTopN = dao.getTopNewNYearReport(year, bigType, smallType);

		String newTopNSum = dao.getSumNYearReport(year, bigType, smallType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
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
		// ��ѯ�б�
		List data = dao.getEachMonthByTypeData(year, smallType);
		// ��ѯ�б�
		List newTopN = dao.getEachMonthByType(year, smallType);

		String sum = dao.getEachMonthSumByType(year, smallType);

		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
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
		String title = year + "��";
		if ("-1".equals(year)) {
			title = "ȫ��";
		}
		StringBuffer buffer = new StringBuffer("<chart caption='" + title + "["
				+ typeName + "]����'  showValues='1'>");
		Iterator it = dataList.iterator();
		while (it.hasNext()) {
			Object[] set = (Object[]) it.next();
			buffer.append("<set label='" + set[0] + "��' value='" + set[1]
					+ "'/>");
		}
		buffer.append("</chart>");
		return buffer.toString();
	}

	private String parseReportYearXml(List dataList, String year) {
		String title = year + "��֧������";
		if ("-1".equals(year)) {
			title = "ȫ��֧������";
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
		StringBuffer buffer = new StringBuffer("<chart caption='" + year + "��"
				+ month + "��֧������'  showValues='1'>");
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
	 * ������ϸ�ı���չʾ��Ϣ.
	 * 
	 * @return
	 */
	public String reportDetail() {
		ConstarctReportDao dao = new ConstarctReportDao();
		// ����������ͳ��
		List groupByType = dao.getDetailReportByType(year, month, bigType,
				smallType);
		//�Ƿ��ǲ�ֵ������Ĭ���ǿ��ǵģ�
		boolean checkSplitNo = true;
		// ��ѯȫ���Ľ������б�
		if ("no".equals(splitNo))
			checkSplitNo = false;
		List ans = dao.getDetailReport(year, month, bigType, smallType,
				checkSplitNo);
		// ��ѯÿ�µ���֧����
		List inAndOut = dao.getInAndOut(checkSplitNo);
		List allInAndOut = dao.getAllInAndOut(checkSplitNo);
		// ��ѯÿ�����֧ͳ��
		List inAndOut2 = dao.getYearInAndOut();
		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		if (bigType == null)
			parseResustMap(ans, request, inAndOut, inAndOut2, allInAndOut,
					false);
		else
			parseResustMap(ans, request, inAndOut, inAndOut2, allInAndOut, true);
		parseGroupResustMap(groupByType, request);
		// ����ǵ����������Ĳ�ѯ���棬�ʹ���ǰ̨��һ��������ʾ����ʾ�Ķ�����𣬲����ٳ��������ڱ������棡
		if (bigType != null) {
			request.setAttribute("nolinktitle", "true");
			request.setAttribute("bigType", bigType);
		}
		request.setAttribute("splitNo", splitNo);
		return "detailReport";
	}

	/**
	 * ������ϸ�Ĵ������Ϣ��ʱ��ı���.
	 * 
	 * @return
	 */
	public String reportDetailByTimeAndType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.reportDetailByTimeAndType(year, month, bigType);

		List sum = dao.reportDetailByTimeAndTypeSum(year, month, bigType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		reportDetailByTimeAndType(ans, sum, request);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("type", smallType);
		return "details";
	}

	/**
	 * ������ϸ��С�����Ϣ��ʱ��ı���.
	 * 
	 * @return
	 */
	public String reportDetailByTimeAndSmallType() {
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.reportDetailByTimeAndSmallType(year, month, smallType);

		List sum = dao
				.reportDetailByTimeAndSmallTypeSum(year, month, smallType);
		HttpServletRequest request = ServletActionContext.getRequest();
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		reportDetailByTimeAndType(ans, sum, request);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("type", smallType);
		return "details";
	}

	/**
	 * �õ���֧ͳ���ַ�����
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
	 * ������ϸ�ı������ݼ���.
	 * 
	 * @param sourceList
	 * @param request
	 * @param inAndOut
	 * @param inAndOut2
	 * @param inAndOut3
	 * @param includeIn
	 *            ����ٷֱȵ�ʱ�򣬷�ĸ�Ƿ�������룡Ĭ��Ϊtrue��
	 */
	private void parseResustMap(List sourceList, HttpServletRequest request,
			List inAndOut, List inAndOut2, List inAndOut3, Boolean includeIn) {
		CommonSelDao commonSelDao = new CommonSelDao();
		// �õ�ȫ���Ľ�����������˵�.
		List allTallyType = null;
		Map allTallyTypeToMoneyType = null;
		// ���������������Ϊ��ĸ����Ҫ���ݽ�����ͽ���ɸѡ.
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
		// ����������ѭ���ҵ��漰�����ٸ��·ݣ��Լ��õ�����ͳ�ƣ�
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
	 * ���������ͳ���ܶ�.
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
	 * �����ܶ�.
	 * 
	 * @param m
	 * @param year
	 * @param money
	 * @param includeIn
	 *            �Ƿ����������Ϊ��ĸ.
	 * @return
	 */
	private Map sumAllMoney(Map m, String year, String type, String money,
			Boolean includeIn, Map map) {
		String key = year;
		if (!includeIn) {
			// �����֧�����Ͳ������ܶ�--������������
			if ("2".equals(map.get(type))) {
				addToMap(m, year + "out", money);
			} else {
				addToMap(m, year + "in", money);
			}

			// �����֧�����Ͳ������ܶ�--������������
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
	 * �����õ�ÿ�����֧ͳ�ƣ�
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
	 * �����õ�ÿ�µ���֧ͳ��
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
	 * �õ�ȫ������֧���ݣ�
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
		// ����������ѭ���ҵ��漰�����ٸ��·ݣ��Լ��õ�����ͳ�ƣ�
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
	 * ������ϸ�ı���չʾ.
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
		// �����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
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
