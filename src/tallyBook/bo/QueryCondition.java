package tallyBook.bo;

import myOwnLibrary.util.Util;
import tallyBook.pojo.MoneyDetail;

public class QueryCondition {
	/**
	 * 根据moneyDetail和session信息的查询条件返回sql片段。
	 * @param queryCondition
	 * @param session
	 * @return
	 */
	public static String getMDetailConditon(MoneyDetail queryCondition){
		StringBuffer buf = new StringBuffer();
		if(!Util.isBlank(queryCondition.getMoneyType())){
			buf.append(" AND MONEY_TYPE IN ("+getTypeStr(queryCondition.getMoneyType())+")");
		}
		if(!Util.isBlank(queryCondition.getMoneyDesc())){
			buf.append(" AND MONEY_DESC LIKE '%"+queryCondition.getMoneyDesc()+"%'");
		}
		if(!Util.isBlank(queryCondition.getMinTime())){
			buf.append(" AND MONEY_TIME >=TO_DATE('"+queryCondition.getMinTime()+"','yyyy-MM-dd')");
		}
		if(!Util.isBlank(queryCondition.getInOrOutTypeValue())){
			buf.append(" AND TYPE_ID ='"+queryCondition.getInOrOutTypeValue()+"'");
		}
		if(!Util.isBlank(queryCondition.getMoney())){
			buf.append(" AND MONEY >="+queryCondition.getMoney());
		}
		if(!Util.isBlank(queryCondition.getMaxmoney())){
			buf.append(" AND MONEY <="+queryCondition.getMaxmoney());
		}
		if(queryCondition.getShopCard()!=0){
			buf.append(" AND SHOPCARD ="+queryCondition.getShopCard());
		}
		if(!Util.isBlank(queryCondition.getMaxTime())){
			buf.append(" AND MONEY_TIME <=TO_DATE('"+queryCondition.getMaxTime()+"','yyyy-MM-dd')");
		}
		return buf.toString();
	}
	
	private static String getTypeStr(String oldStr){
		String[] ans = oldStr.split(",");
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<ans.length;i++){
			buf.append("'").append(ans[i]).append("'");
			if(i<ans.length-1){
				buf.append(",");
			}
		}
		return buf.toString();
		
	}
}
