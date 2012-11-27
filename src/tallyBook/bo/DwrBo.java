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
 * ʵ��DWR���ߵ���.
 * @author renjie120 419723443@qq.com
 */
public class DwrBo {
	DaoUtil dao = null;
	MoneyDetailDao moneyDetailDao = new MoneyDetailDao();
	CardDao cardDao = new CardDao();

	/**
	 * �õ�ȫ������֧��Ϣ�б�.
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
	 * ������֧��Ϣ.
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
				//���ò�ֺţ���ʶ����һ�鱻��ֵ���Ϣ.
				newM.setSplitNo(bg.intValue());
				if(i>0)
					calender.add(Calendar.MONTH, 1);
				newM.setMoney(avg);
				newM.setMoneyTime(calender.getTime());
				moneyDetailDao.doCreate(newM); 
			}
		}
		return "����ɹ�!";
	}
	
	/**
	 * �������ÿ���Ϣ.
	 * @param card
	 * @return
	 */
	public String saveCard(ShopCard card){
		moneyDetailDao.doCreateCard(card);
		//ɾ�������е����ÿ���Ϣ�����˵�
		CacheManager.clearOnly("allCards");
		return "����ɹ�";
	}
	
	/**
	 * ɾ����Ƭ��Ϣ
	 * @param cardSno
	 * @param newTypeCode
	 * @return
	 */
	public String deleteCard(int cardSno){
		cardDao.deleteCard(cardSno);
		CacheManager.clearOnly("allCards");
		return "ɾ���ɹ�";
	}
	
	/**
	 * �ӷ���������ȡ����
	 * @return
	 */
	public String distillFromServer(){
		String moneys = UrlClientUtil.getDocumentAt("http://myfirstgaejava.appspot.com/testServlet?method=getMoneyDetails");
		if(Util.isBlank(moneys)){
			return "û�п�����ȡ������..";
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
				//����Ҫת�������ַ���Ϊ����!
				mDetail.setMoneyType(Util.notBlank(tallyTypes.findId(moneyType),moneyType));
				mDetail.setMoneyDesc(moneyDesc); 
				mDetail.setShopCard(-1);
				dao.doCreate(mDetail); 
			} 
		} 
		//��ȡ��֮����ø���״̬��Զ�̷�����
		String ans = UrlClientUtil.getDocumentAt("http://myfirstgaejava.appspot.com/testServlet?method=updateStatus");
		return ans;
	}
	
	/**
	 * ���¿�Ƭ��Ϣ
	 * @param mDetail
	 * @return
	 */
	public String updateCard(ShopCard acrd) {
		
		cardDao.doUpdate(acrd.getCardSno(),acrd);
		return "�޸ĳɹ�!";
	}
	
	/**
	 * ������ÿ�
	 * @param mDetail
	 * @return
	 */
	public String backMoney(String cardSno,String money) {
		moneyDetailDao.backMoney(cardSno,money);
		return "����ɹ�!";
	}
	
	/**
	 * ������֧��Ϣ
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
				//���ò�ֺţ���ʶ����һ�鱻��ֵ���Ϣ.
				newM.setSplitNo(bg.intValue()); 
				if(i>0)
					calender.add(Calendar.MONTH, 1);
				newM.setMoney(avg);
				newM.setMoneyTime(calender.getTime());
				moneyDetailDao.doCreate(newM); 
			}
		} 
		return "�޸ĳɹ�!";
	}	 
	
	/**
	 * �������룬����֧���õ���֧��𼯺�.
	 * @param type
	 * @return
	 */
	public List getTallyTypeByMoneyType(String type){
		TallyTypeDao tallyTypeDao = new TallyTypeDao();
		List allTallyTypes = tallyTypeDao.doQueryTallyTypes(type);
		return allTallyTypes;
	}
	
	/**
	 * �޸���ϸ������С��������
	 * @param typeSno
	 * @param typeName
	 * @return
	 */
	public String updateTallyTypeName(int typeSno,String typeName){
		TallyTypeDao dao = new TallyTypeDao();
		dao.doUpdateTallyTypeName(typeSno, typeName);
		CacheManager.clearOnly("tallyTypes");
		return "�޸ĳɹ�";
	}
	
	/**
	 * �޸�С���ĸ��׽ڵ㡣
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
		return "�޸ĳɹ�";
	}
	
	/**
	 * ɾ����ϸ��С���ͬʱ�޸��������֧��¼���µ��������ȥ!
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
	 * ɾ����ϸ��С���֮ǰͳ���Ƿ�����֧��Ϣ�����ŵ��ڸ����
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
	 * ���ݲ�ѯ����ͳ����������,֧���������ܺ�
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
	 * �����µ�����
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
			return "����ʧ�ܣ���̨����.";
		}
		return "����ɹ�";
	} 
}
