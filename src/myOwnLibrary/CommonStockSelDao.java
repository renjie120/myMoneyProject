package myOwnLibrary;

import java.util.List;

import common.base.SpringContextUtil;

import myOwnLibrary.cache.Cache;
import myOwnLibrary.cache.CacheManager;
import myOwnLibrary.taglib.OptionColl;
import tallyBook.dao.DaoUtil;

/**
 * 返回股票模块里面的一些常用的下拉菜单.
 * @author renjie120 419723443@qq.com
 *
 */
public class CommonStockSelDao {
	private DaoUtil dao;
	/**
	 * 得到股票类型下拉菜单.
	 * @return
	 */
	public OptionColl getStockTypes(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 得到全部股票的下拉菜单.
	 * @return
	 */
	public OptionColl getStocks(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 公用的查询下拉列表的方法.
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
	 * 得到股票交易类型下拉菜单
	 * @return
	 */
	public OptionColl getStockDealTypes(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 得到金额收支类型下拉菜单.
	 * @return
	 */
	public OptionColl getTallyTypeCodes(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 得到全部的日记类型
	 * @return
	 */
	public OptionColl getDiaryTypeCodes(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 得到功过的类型缓存
	 * @return
	 */
	public OptionColl getGongguoTypeCodes(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
	 * 得到交易满意度下拉菜单
	 * @return
	 */
	public OptionColl getHappyNums(){
		OptionColl coll = null; 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
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
