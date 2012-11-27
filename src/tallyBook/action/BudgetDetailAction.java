package tallyBook.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.GroovyUtil2;

import org.apache.struts2.ServletActionContext;

public class BudgetDetailAction {
	private String year;
	private String month;
	private String budgetType;
	private String money;
	private String detailId;
	 
	protected String changeStr(String oldStr){
        try{
        return new String(oldStr.getBytes("ISO-8859-1"));
        }catch(Exception e){
                return oldStr;
        }
}
	public String saveBudgetMoneyTypeRel() { 
		HttpServletRequest request = ServletActionContext.getRequest(); 
		HttpServletResponse response = ServletActionContext.getResponse();
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		 Object[] params = { year,month,budgetType,money }; 
		Object result = dynamicGroovy.invokeScriptMethod( "F:\\newMoney\\newHibernateMoney\\groovy\\BudgetGroovy.groovy",
				"addBudgetDetail", params);
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
		 Object[] params = { year,month,budgetType,money }; 
		Object result = dynamicGroovy.invokeScriptMethod("F:\\newMoney\\newHibernateMoney\\groovy\\BudgetGroovy.groovy",
				"addBudgetDetail", params);
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
		Object[] params = { detailId }; 
		Object result = dynamicGroovy.invokeScriptMethod("F:\\newMoney\\newHibernateMoney\\groovy\\BudgetGroovy.groovy",
				"deleteBudgetDetail", params);
		try {
			response.setContentType("text/html;charset=GBK"); 
			response.getWriter().write(""+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
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
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	} 
}
