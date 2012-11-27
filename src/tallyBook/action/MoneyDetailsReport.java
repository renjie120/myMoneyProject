package tallyBook.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.DateUtil;
import myOwnLibrary.util.Util;

import org.apache.struts2.ServletActionContext;
 
import tallyBook.dao.ReportDao;
import flashchart.Chart;
import flashchart.Data;
import flashchart.Line;
import flashchart.TrendLines;

/**
 * ����ͳ��.
 * @author lsq
 *
 */
public class MoneyDetailsReport { 
	ReportDao reportDao = null;
	private String year;
	private String month;
	private String minTime;
	private String maxTime;
	private String[] types;
	private String type;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * �õ�ÿ�µ�ÿ��Ŀ�֧����ͼ
	 * @return
	 */
	public String reportByMonth(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		int year = Integer.parseInt(getYear());
		int month = Integer.parseInt(getMonth());
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());
		List ansList = null;
		if(month!=-1)
			ansList = reportDao.getReportByMonth(startAndEndTimes[0],startAndEndTimes[1]);
		else
			ansList = reportDao.getReportByYear(startAndEndTimes[0],startAndEndTimes[1]);
		
		Chart chart = new Chart().setCaption(year+"��"+month+"�µĿ�֧").setXAxisName("��").setYAxisName("���").setShowVlaues(true);
		if(month==-1){
			chart.setXAxisName("�·�").setCaption(year+"�꿪֧");
		}
		double count = 0;
		double sum = 0.0;
		double avg = 0.0;
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				chart.addSet(new Data().setLabel(objs[1].toString()).setValue(objs[0].toString()));
				count++;
				sum = Util.add(sum,Double.parseDouble(objs[0].toString()));
			}	
			if(count==0) count = 1;
			avg = Util.divide(sum, count, 2);
			TrendLines lines = new TrendLines();
			lines.addLine(new Line().setStartValue(avg).setColor("009933")
					 .setValueOnRight(false).setDisplayValue("ƽ��:"+avg));
			chart.addTrendLines(lines);
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �õ��������ĸ���ͳ�����ݡ���ӦΪ��״ͼ��
	 * @return
	 */
	public String reportInByType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());		
		List ansList = reportDao.getReportInByType(startAndEndTimes[0],startAndEndTimes[1]);
		Chart chart = new Chart().setPalette(4).setCaption(startAndEndTimes[0]+"��"+startAndEndTimes[1]+"������").setBaseFontSize(12);
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				chart.addSet(new Data().setLabel(objs[1].toString()).setValue(objs[0].toString()));
			}			
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �õ���ϸ��С��������ͳ����Ϣ����ӦΪ��״ͼ��
	 * @return
	 */
	public String reportInByTallyType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());
		List ansList = reportDao.getReportInByTallyType(startAndEndTimes[0],startAndEndTimes[1]);
		Chart chart = new Chart().setPalette(4).setCaption(startAndEndTimes[0]+"��"+startAndEndTimes[1]+"�ľ����������").setBaseFontSize(12);
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				String value = objs[0].toString();
				String bigType =  objs[2].toString();
				//ע������ĵ�����ʹ�õ��ַ����ǣ�"%26apos;"
				chart.addSet(new Data().setLabel(objs[1].toString())
						.setValue(value));
			}			
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����ǰ̨��������ݺ��·ݵõ�Ҫ���в�ѯ����ʼʱ��ͽ���ʱ�䡣
	 * @param y
	 * @param m
	 * @return ��Сʱ������ʱ���һ�����顣Ӧ����ǰ�պ󿪿ռ�ıȽϺã�
	 */
	public static String[] getStartAndEndTime(String y,String m){
		int year = Integer.parseInt(y);
		int month = Integer.parseInt(m);
		int startMonth = month;
		String startTime = "";
		//month=-1��˵���ǲ�ѯ��ȵ�ͳ����Ϣ��
		if(month==-1){	
			startTime = DateUtil.getDateStr(year, 1, 1, "yyyy-MM-dd");	
		}
		else{
			startTime = DateUtil.getDateStr(year, startMonth, 1, "yyyy-MM-dd");		
		}
		int endYear = year;
		int endMonth = month+1;
		String endTime = "";
		//month=-1��˵���ǲ�ѯ��ȵ�ͳ����Ϣ��
		if(month==-1)	{
			endYear = year+1;
			endTime = DateUtil.getDateStr(endYear, 1, 1, "yyyy-MM-dd");				
		}else{
			endTime = DateUtil.getDateStr(endYear, endMonth, 1, "yyyy-MM-dd");	
		}
		return new String[]{startTime,endTime};
	}
	
	/**
	 * �õ�֧������ͳ����Ϣ����ӦΪ��ͼ��
	 * @return
	 */
	public String reportOutByType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());
		List ansList = reportDao.getReportOutByType(startAndEndTimes[0],startAndEndTimes[1]);
		Chart chart = new Chart().setPalette(4).setCaption(startAndEndTimes[0]+"��"+startAndEndTimes[1]+"�Ŀ�֧").setBaseFontSize(12);
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				String value = objs[0].toString();
				//ע������ĵ�����ʹ�õ��ַ����ǣ�"%26apos;".setLink("javascript:showDetailsByType(%26apos;"+bigType+"%26apos;);")
				chart.addSet(new Data().setLabel(objs[1].toString()).setValue(value));
			}			
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �õ���ϸ��С����֧��ͳ����Ϣ����ӦΪ��״ͼ��
	 * @return
	 */
	public String reportOutByTallyType(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());
		List ansList = reportDao.getReportOutByTallyType(startAndEndTimes[0],startAndEndTimes[1]);
		Chart chart = new Chart().setPalette(4).setCaption(startAndEndTimes[0]+"��"+startAndEndTimes[1]+"�ľ������֧");
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				String value = objs[0].toString();
				String bigType =  objs[2].toString();
				//ע������ĵ�����ʹ�õ��ַ����ǣ�"%26apos;"
				chart.addSet(new Data().setLabel(objs[1].toString())
						.setValue(value));
			}			
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �õ�ÿ�µ����룬֧�����̡���ӦΪ��ͼ��
	 * @return
	 */
	public String reportInAndOutByMonth(){
		HttpServletResponse response = ServletActionContext.getResponse();
		reportDao = new ReportDao();
		String[] startAndEndTimes = getStartAndEndTime(getYear(),getMonth());		
		List ansList = reportDao.getReportInAndOutByMonth(startAndEndTimes[0],startAndEndTimes[1]);
		Chart chart = new Chart().setPalette(4);
		try {
			Iterator it = ansList.iterator();
			while(it.hasNext()){
				Object[] objs = (Object[])it.next();
				chart.addSet(new Data().setLabel(objs[1].toString()).setValue(objs[0].toString()));
			}			
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(chart.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
}
