package myOwnLibrary;

import java.util.List;

import common.base.SpringContextUtil;

import myOwnLibrary.cache.Cache;
import myOwnLibrary.cache.CacheManager;
import myOwnLibrary.taglib.OptionColl;
import tallyBook.dao.DaoUtil;

/**
 * ���ع�Ʊģ�������һЩ���õ������˵�.
 * @author renjie120 419723443@qq.com
 *
 */
public class CommonStockSelDao {
	private DaoUtil dao;
	/**
	 * �õ���Ʊ���������˵�.
	 * @return
	 */
	public OptionColl getStockTypes(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("stockTypes")==null){ 
			coll = getCommonSelect("DEAL_TYPE","DEAL_TYPE_NAME","STOCK_DEAL_TYPE_T","");
			Cache c = new Cache();
			c.setKey("stockTypes");
			c.setValue(coll);
			CacheManager.putCache("stockTypes",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("stockTypes").getValue();
		} 
		return coll;
	}
	
	/**
	 * �õ�ȫ����Ʊ�������˵�.
	 * @return
	 */
	public OptionColl getStocks(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("stocks")==null){ 
			coll = getCommonSelect("STOCK_SNO","STOCK_NAME","STOCK_DETAIL_T","");
			Cache c = new Cache();
			c.setKey("stocks");
			c.setValue(coll);
			CacheManager.putCache("stocks",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("stocks").getValue();
		} 
		return coll;
	}
	
	/**
	 * ���õĲ�ѯ�����б�ķ���.
	 * @param idColumn
	 * @param nameColumn
	 * @param tableName
	 * @param whereStr
	 * @return
	 */
	public OptionColl getCommonSelect(String idColumn,String nameColumn,String tableName,String whereStr){
		OptionColl coll = null; 
		dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		List list = dao.doGetSqlQueryList("SELECT "+idColumn+", "+nameColumn+" FROM "+tableName+" "+whereStr);
		try {
			coll = new OptionColl(list);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return coll;
	}
	/**
	 * �õ���Ʊ�������������˵�
	 * @return
	 */
	public OptionColl getStockDealTypes(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("stockDealTypes")==null){ 
			coll = getCommonSelect("CONFIG_SNO","CONFIG_NAME","STOCK_CONFIG_T","");
			Cache c = new Cache();
			c.setKey("stockDealTypes");
			c.setValue(coll);
			CacheManager.putCache("stockDealTypes",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("stockDealTypes").getValue();
		} 
		return coll;
	}
	
	/**
	 * �õ������֧���������˵�.
	 * @return
	 */
	public OptionColl getTallyTypeCodes(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("tallyTypes")==null){ 
			coll = getCommonSelect("TYPE_CODE","TALLY_TYPE_DESC","TALLY_TYPE_T","");
			Cache c = new Cache();
			c.setKey("tallyTypes");
			c.setValue(coll);
			CacheManager.putCache("tallyTypes",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("tallyTypes").getValue();
		} 
		return coll;
	}
	
	/**
	 * �õ�ȫ�����ռ�����
	 * @return
	 */
	public OptionColl getDiaryTypeCodes(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("diaryTypeCodes")==null){ 
			coll = getCommonSelect("TYPEID","TYPENAME","diary_type","");
			Cache c = new Cache();
			c.setKey("diaryTypeCodes");
			c.setValue(coll);
			CacheManager.putCache("diaryTypeCodes",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("diaryTypeCodes").getValue();
		} 
		return coll;
	}
	
	/**
	 * �õ����������ͻ���
	 * @return
	 */
	public OptionColl getGongguoTypeCodes(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("gongguoType")==null){ 
			coll = getCommonSelect("id","content","gong_guo_type","");
			Cache c = new Cache();
			c.setKey("gongguoType");
			c.setValue(coll);
			CacheManager.putCache("gongguoType",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("gongguoType").getValue();
		} 
		return coll;
	}
	
	/**
	 * �õ���������������˵�
	 * @return
	 */
	public OptionColl getHappyNums(){
		OptionColl coll = null; 
		//�ӻ�����ȡ�б���������������ȡ�ֵ������ݣ����Կ���ʹ�û��档
		if(CacheManager.getCacheInfo("happyNums")==null){ 
			coll = getCommonSelect("HAPPY_ID","HAPPY_NAME","STOCK_HAPPY_T","");
			Cache c = new Cache();
			c.setKey("happyNums");
			c.setValue(coll);
			CacheManager.putCache("happyNums",c);
		}else{
			coll = (OptionColl)CacheManager.getCacheInfo("happyNums").getValue();
		} 
		return coll;
	}
}
