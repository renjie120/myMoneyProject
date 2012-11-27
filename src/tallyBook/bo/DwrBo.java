package tallyBook.bo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import myOwnLibrary.CommonStockSelDao;
import myOwnLibrary.cache.CacheManager;
import myOwnLibrary.taglib.OptionColl;
import myOwnLibrary.util.Cdd2;
import myOwnLibrary.util.DateUtil;
import myOwnLibrary.util.UrlClientUtil;
import myOwnLibrary.util.Util;
import tallyBook.action.MoneyDetailsReport;
import tallyBook.dao.CardDao;
import tallyBook.dao.DaoUtil;
import tallyBook.dao.MoneyDetailDao;
import tallyBook.dao.TallyTypeDao;
import tallyBook.pojo.MoneyDetail;
import tallyBook.pojo.ShopCard;
import tallyBook.pojo.TallyType;

import common.base.SpringContextUtil;

/**
 * 实验DWR工具的类.
 * @author renjie120 419723443@qq.com
 */
public class DwrBo {
	DaoUtil dao = null;
	MoneyDetailDao moneyDetailDao = new MoneyDetailDao();
	CardDao cardDao = new CardDao();

	/**
	 * 得到全部的收支信息列表.
	 * @param test
	 * @return
	 */
	public List getMoneyDetails(String test) {
		List moneyDetails = moneyDetailDao.doGetAllMoneyDetails(
				"from tallyBook.pojo.MoneyDetail order by moneySno desc");
		return moneyDetails;
	}

	public String checkPass(String pass){
		Cdd2 cdd2 = new Cdd2();
		return null;
	}
	
	public String setPass(String pass){
		return null;
	}
	
	public List getDetailsByTimeAndBigSort(String year,String month,String bigType){
		StringBuffer buf = new StringBuffer();
		dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		String[] startAndEndTimes = MoneyDetailsReport.getStartAndEndTime(year,month);
		buf.append("select to_char(t.money_time,'yyyy-MM-dd'),to_char(t.money,'99999.00'),decode(t.money_desc,null,' ',t.money_desc)	");
		buf.append("	  from money_detail_view t                              ");
		buf.append("	  where t.money_time >= to_date('"+startAndEndTimes[0]+"', 'yyyy-mm-dd') ");
		buf.append("	    and t.money_time < to_date('"+startAndEndTimes[1]+"', 'yyyy-mm-dd')  ");
		buf.append("	    and t.big_type = '"+bigType+"'                                ");
		buf.append("	    order by t.money_time                               ");
		List ansList = dao.doGetSqlQueryList(buf.toString());
		return ansList;
	
	}
	
	/**
	 * 保存收支信息.
	 * @param mDetail
	 * @return
	 */
	public String saveMoneyDetail(MoneyDetail mDetail) {
		if(mDetail.getSplitMonths()<0)
			moneyDetailDao.doCreate(mDetail);
		else{
			Calendar calender = Calendar.getInstance();
	        calender.setTime(mDetail.getMoneyTime()); 
			double m = mDetail.getMoney();
			double avg = Util.divide(m, mDetail.getSplitMonths(), 2);
			dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
			List r = dao.doGetSqlQueryList("select SPLIT_NO_SEQ.Nextval from dual");
			Object[] objs = (Object[])r.get(0);
			BigDecimal bg = (BigDecimal)(objs[0]); 
			for(int i=0;i<mDetail.getSplitMonths();i++){
				MoneyDetail newM = mDetail; 
				Date d = mDetail.getMoneyTime();
				//设置拆分号！标识这是一组被拆分的信息.
				newM.setSplitNo(bg.intValue());
				if(i>0)
					calender.add(Calendar.MONTH, 1);
				newM.setMoney(avg);
				newM.setMoneyTime(calender.getTime());
				moneyDetailDao.doCreate(newM); 
			}
		}
		return "保存成功!";
	}
	
	/**
	 * 保存信用卡信息.
	 * @param card
	 * @return
	 */
	public String saveCard(ShopCard card){
		moneyDetailDao.doCreateCard(card);
		//删除缓存中的信用卡信息下拉菜单
		CacheManager.clearOnly("allCards");
		return "保存成功";
	}
	
	/**
	 * 删除卡片信息
	 * @param cardSno
	 * @param newTypeCode
	 * @return
	 */
	public String deleteCard(int cardSno){
		cardDao.deleteCard(cardSno);
		CacheManager.clearOnly("allCards");
		return "删除成功";
	}
	
	/**
	 * 从服务器端提取数据
	 * @return
	 */
	public String distillFromServer(){
		String moneys = UrlClientUtil.getDocumentAt("http://myfirstgaejava.appspot.com/testServlet?method=getMoneyDetails");
		if(Util.isBlank(moneys)){
			return "没有可以提取的数据..";
		}
		String[] everMoney = moneys.split("\\$;");
		CommonStockSelDao seldao = new CommonStockSelDao();
		OptionColl tallyTypes = seldao.getTallyTypeCodes();
		MoneyDetailDao dao = new MoneyDetailDao();
		String result = "";
		for(String aMoney:everMoney){
			String[] details = aMoney.split("\\$,");
			String time = details[0];
			String money = details[1];
			String moneyType = details[2];
			String moneyDesc = details[3]; 
			if(!Util.isBlank(time)&&!"null".equals(time)){
				MoneyDetail mDetail = new MoneyDetail();
				mDetail.setMoneyTime(DateUtil.getDate(time, "yyyy-MM-dd"));
				mDetail.setMoney(Double.parseDouble(money));
				//下面要转换类型字符串为编码!
				mDetail.setMoneyType(Util.notBlank(tallyTypes.findId(moneyType),moneyType));
				mDetail.setMoneyDesc(moneyDesc); 
				mDetail.setShopCard(-1);
				dao.doCreate(mDetail); 
			} 
		} 
		//提取完之后调用更新状态的远程方法！
		String ans = UrlClientUtil.getDocumentAt("http://myfirstgaejava.appspot.com/testServlet?method=updateStatus");
		return ans;
	}
	
	/**
	 * 更新卡片信息
	 * @param mDetail
	 * @return
	 */
	public String updateCard(ShopCard acrd) {
		
		cardDao.doUpdate(acrd.getCardSno(),acrd);
		return "修改成功!";
	}
	
	/**
	 * 还款到信用卡
	 * @param mDetail
	 * @return
	 */
	public String backMoney(String cardSno,String money) {
		moneyDetailDao.backMoney(cardSno,money);
		return "还款成功!";
	}
	
	/**
	 * 更新收支信息
	 * @param mDetail
	 * @return
	 */
	public String updateMoneyDetail(MoneyDetail mDetail) {
		if(mDetail.getSplitMonths()<0)
			moneyDetailDao.doUpdate(mDetail.getMoneySno(),mDetail);
		else{ 
			Calendar calender = Calendar.getInstance();
	        calender.setTime(mDetail.getMoneyTime()); 
	        moneyDetailDao.doDelete(mDetail.getMoneySno());
			double m = mDetail.getMoney();
			double avg = Util.divide(m, mDetail.getSplitMonths(), 2);
			dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
			List r = dao.doGetSqlQueryList("select SPLIT_NO_SEQ.Nextval from dual");
			Object[] objs = (Object[])r.get(0);
			BigDecimal bg = (BigDecimal)(objs[0]); 
			for(int i=0;i<mDetail.getSplitMonths();i++){
				MoneyDetail newM = mDetail;  
				//设置拆分号！标识这是一组被拆分的信息.
				newM.setSplitNo(bg.intValue()); 
				if(i>0)
					calender.add(Calendar.MONTH, 1);
				newM.setMoney(avg);
				newM.setMoneyTime(calender.getTime());
				moneyDetailDao.doCreate(newM); 
			}
		} 
		return "修改成功!";
	}	 
	
	/**
	 * 根据收入，或者支出得到分支类别集合.
	 * @param type
	 * @return
	 */
	public List getTallyTypeByMoneyType(String type){
		TallyTypeDao tallyTypeDao = new TallyTypeDao();
		List allTallyTypes = tallyTypeDao.doQueryTallyTypes(type);
		return allTallyTypes;
	}
	
	/**
	 * 修改详细的收入小类别的名字
	 * @param typeSno
	 * @param typeName
	 * @return
	 */
	public String updateTallyTypeName(int typeSno,String typeName){
		TallyTypeDao dao = new TallyTypeDao();
		dao.doUpdateTallyTypeName(typeSno, typeName);
		CacheManager.clearOnly("tallyTypes");
		return "修改成功";
	}
	
	/**
	 * 修改小类别的父亲节点。
	 * @param typeSno
	 * @param parentCode
	 * @return
	 */
	public String updateTallyTypeParentCode(int typeSno,String parentCode){
		TallyTypeDao dao = new TallyTypeDao();
		if("-1".equals(parentCode)){
			parentCode = null;
		}
		dao.updateTallyTypeParentCode(typeSno, parentCode);
		CacheManager.clearOnly("tallyTypes");
		return "修改成功";
	}
	
	/**
	 * 删除详细的小类别，同时修改下面的收支记录到新的类别下面去!
	 * @param typeSno
	 * @param typeName
	 * @return
	 */
	public int deleteTally(int typeSno,String newTypeCode){
		TallyTypeDao dao = new TallyTypeDao();
		int ans = dao.deleteTally(typeSno, newTypeCode);
		CacheManager.clearOnly("tallyTypes");
		return ans;
	}
	
	/**
	 * 删除详细的小类别之前统计是否有收支信息在类别号等于该类别。
	 * @param typeSno
	 * @param newTypeCode
	 * @return
	 */
	public int beforeDeleteTally(String typeCode){
		TallyTypeDao dao = new TallyTypeDao();
		int ans = dao.beforeDeleteTally(typeCode);
		return ans;
	}
	
	/**
	 * 根据查询条件统计收入总数,支出总数和总和
	 * @param queryCondition
	 * @return
	 */
	public String getSumMoney(MoneyDetail queryCondition){
		dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT DECODE(SUM(MONEY),NULL,0,SUM(MONEY)) FROM MONEY_DETAIL_VIEW T WHERE T.TYPE_ID = '1' ");
		buf.append(QueryCondition.getMDetailConditon(queryCondition));
		double in = Double.parseDouble(dao.doGetSqlQueryList(buf.toString()).get(0).toString());	
		StringBuffer buf2 = new StringBuffer();
		buf2.append("SELECT DECODE(SUM(MONEY),NULL,0,SUM(MONEY)) FROM MONEY_DETAIL_VIEW T WHERE T.TYPE_ID = '2' ");
		buf2.append(QueryCondition.getMDetailConditon(queryCondition));
		double out = Double.parseDouble(dao.doGetSqlQueryList(buf2.toString()).get(0).toString());		
		return 	in+","+out+","+Util.subtract(in, out);
	}
	
	/**
	 * 保存新的类型
	 * @param type
	 * @return
	 */
	public String saveTallyType(String parentCode,String tallyTypeDesc,String moneyType){
		TallyType type = new TallyType();
		if(parentCode.equals("-1"))
			parentCode = "";
		type.setParentCode(parentCode);
		type.setTallyTypeDesc(tallyTypeDesc);
		type.setMoneyType(moneyType);
		TallyTypeDao dao = new TallyTypeDao();
		try {
			dao.doCreate(type);
			CacheManager.clearOnly("tallyTypes");
		} catch (SQLException e) {
			return "保存失败，后台出错.";
		}
		return "保存成功";
	} 
}
