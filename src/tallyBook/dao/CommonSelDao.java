package tallyBook.dao;

import java.util.Iterator;
import java.util.List;

import myOwnLibrary.cache.Cache;
import myOwnLibrary.cache.CacheManager;
import myOwnLibrary.taglib.OptionColl;
import tallyBook.bo.ExtTreeColl;
import tallyBook.bo.ExtTreeNode;

import common.base.SpringContextUtil;

/**
 * 查询下拉菜单的工具类。
 * @author renjie120 419723443@qq.com
 *
 */
public class CommonSelDao {
	private DaoUtil dao;
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
	 * 得到全部的账本
	 * @return
	 */
	public OptionColl getAllBookTypes(){
		OptionColl bookTypes = null;  
		if (CacheManager.getCacheInfo("bookTypes") == null) { 
			bookTypes = getCommonSelect("BOOK_TYPE", "BOOK_TYPE_NAME", "money_book_type_t", "");
			Cache c = new Cache();
			c.setKey("bookTypes");
			c.setValue(bookTypes);
			CacheManager.putCache("bookTypes", c);
		} else {
			bookTypes = (OptionColl) CacheManager.getCacheInfo("bookTypes")
					.getValue();
		}
		return bookTypes;
	} 
	
	/**
	 * @return 全部的金额类型下拉菜单。
	 */
	public List getAllTallyTypes(){
		List allTallyTypes = null;
		TallyTypeDao tallyTypeDao = new TallyTypeDao();
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("tallyTypes")==null){
			allTallyTypes = tallyTypeDao.doGetAllTallyTypes(
				"from tallyBook.pojo.TallyType");
			Cache c = new Cache();
			c.setKey("tallyTypes");
			c.setValue(allTallyTypes);
			CacheManager.putCache("tallyTypes",c);
		}else{
			allTallyTypes = (List)(CacheManager.getCacheInfo("tallyTypes").getValue());
		}
		return allTallyTypes;
	}
	
	/**
	 * ext树得到集合
	 * @return
	 */
	public ExtTreeColl getAllTallyTypeTreeColl(){
		List allTallyTypes = null;
		ExtTreeColl result = new ExtTreeColl(); 
		 dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		TallyTypeDao tallyTypeDao = new TallyTypeDao();
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("extTallyTypes")==null){
			allTallyTypes = dao.doGetSqlQueryList("select TALLY_TYPE_SNO,TALLY_TYPE_DESC,PARENT_CODE,TYPE_CODE,money_type from tally_type_t");
			Iterator it = allTallyTypes.iterator(); 
			while(it.hasNext()){ 
				Object[] o = (Object[])it.next();
				String id = (String)o[3];
                String name = (String)o[1];
                String parent = (String)o[2];
                 
                ExtTreeNode tp = new ExtTreeNode();
                tp.setId(id);
                tp.setText(name);
                tp.setParent(parent);
                 
                result.addExtTreeVO(tp);
			}
			result.setLeaves();
			Cache c = new Cache();
			c.setKey("extTallyTypes");
			c.setValue(result);
			CacheManager.putCache("extTallyTypes",c); 
		}else{
			result = (ExtTreeColl)CacheManager.getCacheInfo("extTallyTypes").getValue();
		}
		return result;
	}

	/**
	 * @return 全部的消费卡类途径下拉菜单。
	 */
	public List getAllCards(){
		List allCards = null;
		TallyTypeDao tallyTypeDao = new TallyTypeDao();
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("allCards")==null){
			allCards = tallyTypeDao.doGetAllShopCards(
				"from tallyBook.pojo.ShopCard");
			Cache c = new Cache();
			c.setKey("allCards");
			c.setValue(allCards);
			CacheManager.putCache("allCards",c);
		}else{
			allCards = (List)CacheManager.getCacheInfo("allCards").getValue();
		}
		return allCards;
	}
	
	/**
	 * @return 年份下拉菜单
	 */
	public List getAllYears(){
		List years = null;
		DaoUtil dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("years")==null){
			
			years = dao.doGetSqlQueryList("select * from year_t");
			Cache c = new Cache();
			c.setKey("years");
			c.setValue(years);
			CacheManager.putCache("years",c);
		}else{
			years = (List)CacheManager.getCacheInfo("years").getValue();
		}
		return years;
	}
	
	/**
	 * @return 月份下拉菜单.
	 */
	public List getAllMonths(){
		List months = null;
		DaoUtil dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("months")==null){
			
			months = dao.doGetSqlQueryList("select * from month_t");
			Cache c = new Cache();
			c.setKey("months");
			c.setValue(months);
			CacheManager.putCache("months",c);
		}else{
			months = (List)CacheManager.getCacheInfo("months").getValue();
		}
		return months;
	}
	
	public OptionColl getAllYearColl() {
		OptionColl years = null; 
		// 从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if (CacheManager.getCacheInfo("yearColl") == null) { 
			years = getCommonSelect("ID", "VALUE", "YEAR_T", "");
			Cache c = new Cache();
			c.setKey("yearColl");
			c.setValue(years);
			CacheManager.putCache("yearColl", c);
		} else {
			years = (OptionColl) CacheManager.getCacheInfo("yearColl")
					.getValue();
		}
		return years;
	}
	
	/**
	 * @return 月份下拉菜单.
	 */
	public OptionColl getAllMonthColl(){
		OptionColl months = null;
		DaoUtil dao = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		//从缓存中取列表，对于这里类似是取字典表的数据，所以可以使用缓存。
		if(CacheManager.getCacheInfo("monthColl")==null){
			
			months =  getCommonSelect("ID", "VALUE", "MONTH_T", "");
			Cache c = new Cache();
			c.setKey("monthColl");
			c.setValue(months);
			CacheManager.putCache("monthColl",c);
		}else{
			months = (OptionColl)CacheManager.getCacheInfo("monthColl").getValue();
		}
		return months;
	}
}
