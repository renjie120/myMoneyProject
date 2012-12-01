package tallyBook.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.flashchart.Categories;
import myOwnLibrary.flashchart.Category;
import myOwnLibrary.flashchart.Chart;
import myOwnLibrary.flashchart.Data;
import myOwnLibrary.flashchart.DataSet;
import myOwnLibrary.util.JsonUtil;
import myOwnLibrary.util.Util;

import org.apache.struts2.ServletActionContext;

import tallyBook.dao.ConstarctReportDao;


/**
 * ��֧�Ա�ͳ��.
 * @author renjie120 419723443@qq.com
 *
 */
public class MoneyContrastReport {
	ConstarctReportDao dao = null;
	String year;
	String type;
	String month;
	String bigType;
	String smallType;
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
	 * ����һ����������12���µĸ��������жԱȵķ�������
	 * @param year
	 * @return
	 */
	public String reportMoneyWithTypeAndMonth() {
		year = getYear();
		type = getType();
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getMoneyWithTypeAndMonth(year,type);
		HttpServletResponse response = ServletActionContext.getResponse();
		//�����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		Map parseResult = getResustMap(ans);		
		try{
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(parseResult.get("chartStr").toString()
						+"$$"+parseResult.get("datas").toString()
						+"$$"+parseResult.get("sortType").toString()
						+"$$"+parseResult.get("avgs").toString()
						+"$$"+parseResult.get("sums").toString()
						+"$$"+parseResult.get("types").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String reportMoneyWithBigTypeAndMonth() {
		year = getYear();
		type = getType();
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getMoneyWithBigTypeAndMonth(year,type);
		HttpServletResponse response = ServletActionContext.getResponse();
		//�����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		Map parseResult = getResustMap(ans);		
		String aaa = "";
		try{
			response.setContentType("text/html;charset=GBK");
			aaa = parseResult.get("chartStr").toString() + "$$"
					+ parseResult.get("datas").toString() + "$$"
					+ parseResult.get("sortType").toString()+"$$"
					+parseResult.get("avgs").toString()
					+"$$"+parseResult.get("sums").toString()
					+"$$"+parseResult.get("types").toString();
			response.getWriter().write(aaa); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ������ϸ�ı���չʾ��Ϣ.
	 * @return
	 */
	public String reportDetail() { 
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getDetailReport(year,month,bigType,smallType,true);
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		parseResustMap(ans,request);		 
		return "detailReport";
	}
	
	/**
	 * ������ϸ�ı���չʾ.
	 * @return
	 */
	public String reportDetailInBigType() { 
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getDetailReport(year,month,bigType,smallType,true);
		HttpServletRequest request = ServletActionContext.getRequest();
		//�����ѯ�Ľ�����ϵõ���Ҫ���ַ��������
		parseResustMap(ans,request);		 
		return "detailReport";
	}
	
	
	public String reportMoneyWithBigTypeAndYear() {
		type = getType();
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getMoneyWithBigTypeAndYear(type);
		HttpServletResponse response = ServletActionContext.getResponse();
		Map parseResult = getResustMap(ans);		
		try{
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(parseResult.get("chartStr").toString()
						+"$$"+parseResult.get("datas").toString()
						+"$$"+parseResult.get("sortType").toString()
						+"$$"+parseResult.get("avgs").toString()
						+"$$"+parseResult.get("sums").toString()
						+"$$"+parseResult.get("types").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
		
	/**
	 * ������ϸ�ı������ݼ���.
	 * @param sourceList
	 * @return
	 */
	private void parseResustMap(List sourceList,HttpServletRequest request ){ 
		List allYearAndMonths= new ArrayList(); 
		List allTypes= new ArrayList(); 
		Map timeToMoney= new HashMap(); 
		Map allTypeName= new HashMap(); 
		//����������ѭ���ҵ��漰�����ٸ��·ݣ��Լ��õ�����ͳ�ƣ�
		if(sourceList!=null){
			Iterator it = sourceList.iterator();
			while(it.hasNext()){
				Object[] infos = (Object[])it.next();
				String year = infos[0].toString();
				String month = infos[1].toString();
				String typeName = infos[2].toString();
				String type = infos[3].toString();
				String money = infos[4].toString();
				String tm = year+","+month;
				if(!allYearAndMonths.contains(tm)){
					allYearAndMonths.add(tm);
				}
				if(!allTypes.contains(type)){
					allTypes.add(type);
				}
				allTypeName.put(type,typeName);
				timeToMoney.put(tm+","+type,money);
			} 
		}
		request.setAttribute("allYearAndMonths", allYearAndMonths);
		request.setAttribute("allTypes", allTypes);
		request.setAttribute("allTypeName", allTypeName);
		request.setAttribute("timeToMoney", timeToMoney);
	}
	
	private Map getResustMap(List sourceList){
		Map resultMap = new HashMap();
		String resultData = "";
		String allSortType = "";
		List allTypes = new LinkedList();
		List allTimes = new LinkedList();
		List allAvgs = new LinkedList();
		List allSums = new LinkedList();
		//����������ѭ���ҵ��漰�����ٸ�С��𣬺Ͷ��ٸ���ݣ�
		if(sourceList!=null){
			Iterator it = sourceList.iterator();
			while(it.hasNext()){
				Object[] infos = (Object[])it.next();
				if(!allTypes.contains(infos[1])){
					allTypes.add(infos[1]);
				}
				if(!allTimes.contains(infos[3])){
					allTimes.add(infos[3]);
				}
			}
		}
		
		Chart c = new Chart();
		String[][] result = new String[allTypes.size()][allTimes.size()+1];
		if(sourceList!=null){
			Iterator it = sourceList.iterator();
			while(it.hasNext()){
				Object[] infos = (Object[])it.next();
				if(result[allTypes.indexOf(infos[1])][0]==null)
					result[allTypes.indexOf(infos[1])][0] = infos[2].toString();
				result[allTypes.indexOf(infos[1])][allTimes.indexOf(infos[3])+1] = infos[0].toString();
			}
		}
		for(int i=0,j=result.length;i<j;i++){
			for(int ii=0,jj=result[i].length;ii<jj;ii++){
				if(!Util.notNull(result[i][ii])){
					result[i][ii] = "0";
				}
			}
		}
		//�������ƽ��ֵ
		double[] avgs = new double[allTypes.size()];
		for(int i=0,j=result.length;i<j;i++){
			double sum = 0.0;
			for(int ii=1,jj=result[i].length;ii<jj;ii++){
				if(!Util.notNull(result[i][ii])){
					result[i][ii] = "0";
				}
				sum = Util.add(sum, Double.parseDouble(result[i][ii]));
			}
			avgs[i] = Util.divide(sum, result[i].length, 2);
			allAvgs.add(avgs[i]);
			allSums.add(sum);
		}
		
		Chart chart = new Chart();
		chart.setCaption("�Աȷ���ͼ��").setShowNames(true).setShowVlaues(true).setBaseFont("13");
		for(int i=0,j=allTimes.size();i<j;i++){
			DataSet dataSet = new DataSet().setSeriesName(allTimes.get(i).toString()).setShowValues(true);
			for(int ii=0,jj=allTypes.size();ii<jj;ii++){
				dataSet.addSet(new Data().setValue(result[ii][i+1]));
			}
			chart.addDataSet(dataSet);
		}
		//���ƽ��ֵ������
		DataSet avgDataSet = new DataSet().setSeriesName("ƽ��ֵ").setShowValues(true).setParentYAxis("S");
		for(int i=0;i<avgs.length;i++){
			avgDataSet.addSet(new Data().setValue(Double.toString(avgs[i])));
		}
		chart.addDataSet(avgDataSet);
		Categories categories = new Categories();
		for(int i=0,j=allTypes.size();i<j;i++){
			categories.addCategory(new Category().setLabel(result[i][0]));				
		}
		chart.addCategories(categories);		
		
		String avs = JsonUtil.list2json(allAvgs);
		String sums = JsonUtil.list2json(allSums);
		resultData = JsonUtil.array2json(result);
		allSortType = JsonUtil.list2json(allTimes);
		String typeStr = JsonUtil.list2json(allTypes);
		//ʱ�������ַ���
		resultMap.put("sortType", allSortType);
		//����id��
		resultMap.put("types", typeStr); 
		//�����ַ���
		resultMap.put("datas", resultData);
		//�����ַ���
		resultMap.put("avgs", avs);
		//�����ַ���
		resultMap.put("sums", sums);
		//����ַ���
		resultMap.put("chartStr", chart.toString());
		return resultMap;
	}
	
	/**
	 * ���ؼ���ĸ��������жԱȵķ�������	
	 * @return chart�ַ���
	 */
	public String reportMoneyWithTypeAndYear() {
		type = getType();
		ConstarctReportDao dao = new ConstarctReportDao();
		List ans = dao.getMoneyWithTypeAndYear(type);
		HttpServletResponse response = ServletActionContext.getResponse();
		Map parseResult = getResustMap(ans);		
		try{
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(parseResult.get("chartStr").toString()
						+"$$"+parseResult.get("datas").toString()
						+"$$"+parseResult.get("sortType").toString()
						+"$$"+parseResult.get("avgs").toString()
						+"$$"+parseResult.get("sums").toString()
						+"$$"+parseResult.get("types").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
