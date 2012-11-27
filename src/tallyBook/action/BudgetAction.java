package tallyBook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.GroovyUtil2;

import org.apache.struts2.ServletActionContext;

public class BudgetAction {
	private String budgetName;
	private String moneyType;
	private String budgetId;
	private String year;
	private String month;
	
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

	public String getBudgetName() {
		return budgetName;
	}

	public void setBudgetName(String budgetName) {
		this.budgetName = budgetName;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	
	public static void main(String[] a){
		List l = new ArrayList();
		l.add(1);
		l.add(1);
		l.add(1);
		List l2 = new ArrayList();
		l2.add(12);
		l2.add(13);
		l2.add(14);  
		System.out.println(l);
	}
	protected String changeStr(String oldStr){
        try{
	        return new String(oldStr.getBytes("ISO-8859-1"));
	        }catch(Exception e){
	                return oldStr;
	        }
	}   
	public String saveBudgetDetail() { 
		HttpServletRequest request = ServletActionContext.getRequest();    
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		budgetName = changeStr(budgetName);
		 Object[] params = { budgetName};  
		Object result = dynamicGroovy.invokeScriptMethod( request.getRealPath("/")+
				"groovy\\BudgetGroovy.groovy",
				"addBudgetDetail", params);
		try {
			response.setContentType("text/html;charset=GBK");  
			response.getWriter().write(result+"");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	} 
	
	public String queryBudgetDetail() { 
		HttpServletRequest request = ServletActionContext.getRequest();   
		System.out.println(request.getContextPath());
		Map m = request.getParameterMap(); 
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		budgetName = changeStr(budgetName);
		Object[] params = { m}; 
		Object result = dynamicGroovy.invokeScriptMethod( request.getRealPath("/")+
				"groovy\\BudgetGroovy.groovy",
				"queryBudgetDetail", params);
		try {
			response.setContentType("text/html;charset=GBK");  
			response.getWriter().write(result+"");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	} 
	
	public String updateBudgetMoneyTypeRel() { 
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		Object[] params = { budgetName,moneyType };
		Object result = dynamicGroovy.invokeScriptMethod(ServletActionContext.getRequest().getRealPath("/")+
				"groovy\\BudgetGroovy.groovy",
				"addBudget", params);
		try {
			response.setContentType("text/html;charset=GBK"); 
			response.getWriter().write(result+"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public String deleteBudgetMoneyTypeRel() { 
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		Object[] params = { budgetId }; 
		Object result = dynamicGroovy.invokeScriptMethod(ServletActionContext.getRequest().getRealPath("/")+
				"groovy\\BudgetGroovy.groovy",
				"deleteBudget", params);
		try {
			response.setContentType("text/html;charset=GBK"); 
			response.getWriter().write(""+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteBudgetDetail() { 
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		Object[] params = { year,month }; 
		Object result = dynamicGroovy.invokeScriptMethod(ServletActionContext.getRequest().getRealPath("/")+
				"groovy\\BudgetGroovy.groovy",
				"deleteBudgetDetail", params);
		try {
			response.setContentType("text/html;charset=GBK"); 
			response.getWriter().write(""+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public String getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(String budgetId) {
		this.budgetId = budgetId;
	} 
}
