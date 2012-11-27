package tallyBook.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import myOwnLibrary.util.Util;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import common.base.SpringContextUtil;

import tallyBook.base.BaseDao;
import tallyBook.base.HibernateUtil;
import tallyBook.bo.ExtTreeNode;
import tallyBook.pojo.TallyType;

/**
 * 维护具体的小类别分类信息.
 * @author renjie120
 * 419723443@qq.com
 */
public class TallyTypeDao extends BaseDao{
	/**
	 * 创建一个小类别的对象保持到数据库.
	 * @param tallyType
	 * @throws SQLException 
	 */
	public void doCreate(TallyType tallyType) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Connection con = session.connection(); 
		//调用存储过程的意图是，自动生成typeCode和typeSno。然后再插入数据。
		String procedure = "{call insertTayllyType(?,?,?) }"; 
		CallableStatement cstmt = con.prepareCall(procedure); 
		cstmt.setString(1, tallyType.getTallyTypeDesc());
		cstmt.setString(2, tallyType.getParentCode());
		cstmt.setString(3, tallyType.getMoneyType());
		cstmt.executeUpdate();
		session.getTransaction().commit();
	}
	
	/**
	 * 得到一个具体的小类的信息.
	 * @param tallyTypeId
	 * @return
	 */
	public TallyType doGet(int tallyTypeId){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		TallyType tallyType = (TallyType)session.get(TallyType.class,tallyTypeId);
		session.getTransaction().commit();
		return tallyType;
	}	
	
	/**
	 * 查询指定的查询sql的结果,返回list.
	 * @param queryStr
	 * @return
	 */
	public List doGetAllTallyTypes(String queryStr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 查询全部的消费类型.
	 * @param queryStr
	 * @return
	 */
	public List doGetAllShopCards(String queryStr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 得到全部的类型数据，以及统计金额。用来形成表格树.
	 * @return
	 */
	public List doGetAllTallyTypeMoney(){
		DaoUtil daoUtil = null;
		daoUtil = (DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		StringBuffer buf = new StringBuffer();
		buf.append("select ty.tally_type_desc,																							");
		buf.append("       ty.tally_type_sno,                                               ");
		buf.append("       ty.type_code,                                                    ");
		buf.append("       ty.parent_code,                                                  ");
		buf.append("       ee.money,                                                        ");
		buf.append("       to_char(ee.av, '999999.00') avg,                                 ");
		buf.append("       ty.money_type                                                    ");
		buf.append("  from tally_type_t ty                                                  ");
		buf.append("  left join (select sum(t.money) money, avg(t.money) av, t.money_type   ");
		buf.append("               from money_detail_view t                                    ");
		buf.append("              group by t.money_type) ee on ty.type_code = ee.money_type ");
		buf.append("                                                                        ");
		List infoType = daoUtil.doGetSqlQueryList(buf.toString());
		return infoType;
	}
	/**
	 * 查询指定收入或者支出的小类别
	 * @param type
	 * @return
	 */
	public List doQueryTallyTypes(String type){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = null ;
		Query query = session.createQuery("from tallyBook.pojo.TallyType where moneyType = :type");
		query.setString("type", type);
		ans = query.list();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 查询全部的小类别列表
	 * @return
	 */
	public List doQueryTallyTypes(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = null ;
		Query query = session.createQuery("from tallyBook.pojo.TallyType");
		ans = query.list();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 在ext表格中根据父节点得到子节点的类型.
	 * @param parentCode
	 * @return
	 */
	public ArrayList doQueryTallyTypesByParentCode(String parentCode){
		StringBuffer sqlbd = new StringBuffer();
		
		sqlbd.append("select t.TYPE_CODE,                                    ");
		sqlbd.append("       t.TALLY_TYPE_DESC                                ");
		sqlbd.append("       from tally_type_t t                              ");
		if (Util.isEmpty(parentCode)||"null".equals(parentCode)) {
			sqlbd.append("  where   t.PARENT_CODE is null       ");
		} else {
			sqlbd.append("  where   t.PARENT_CODE=:parentCode       ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//先修改以前的记录到新的类型下面去		
		SQLQuery query = session.createSQLQuery(sqlbd.toString());
		if (!Util.isEmpty(parentCode)&&!"null".equals(parentCode)) { 
			query.setString("parentCode", parentCode);
		}
		
		List ans = query.list();
		session.getTransaction().commit();  
		ArrayList ansList = new ArrayList();
		if(ans==null){
			return ansList;
		}
		else{
			System.out.println("查询出来子节点："+ansList.size());
		}
		Iterator it = ans.iterator();
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			String id = (String)o[0];
            String name = (String)o[1];
            ExtTreeNode type = new ExtTreeNode();
            type.setId(id);
            type.setText(name);
            ansList.add(type);
		}
		return ansList;
	}

	/**
	 * 判断类型节点是不是有孩子节点.
	 * @param nodeId
	 * @return 有孩子节点就返回true，否则返回false.
	 */
	public boolean hasChildType(String nodeId) {
		String ans = "";
		String sql = "select count(1) from tally_type_t t where t.PARENT_CODE = :nodeId";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//先修改以前的记录到新的类型下面去		
		SQLQuery query = session.createSQLQuery(sql);
		query.setString("nodeId", nodeId);
		List list = query.list();
		ans = list.get(0).toString();
		session.getTransaction().commit();  
		if("0".equals(ans)){
			return false;
		}else
			return true;
	}
	
	/**
	 * 更新一个类型名称
	 * @param tallyTypeId 要修改的类型id
	 * @param tallyTypeName 新的类型名称
	 * @return
	 */
	public TallyType doUpdateTallyTypeName(int tallyTypeId,String tallyTypeName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		TallyType oldType = (TallyType)session.get(TallyType.class,tallyTypeId);
		oldType.setTallyTypeDesc(tallyTypeName);
		session.getTransaction().commit();
		return oldType;
	}
	
	/**
	 * 根据父亲节点所有的子节点类型.
	 * @param tallyTypeId
	 * @param parentCode
	 * @return
	 */
	public TallyType updateTallyTypeParentCode(int tallyTypeId,String parentCode){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		TallyType oldType = (TallyType)session.get(TallyType.class,tallyTypeId);
		oldType.setParentCode(parentCode);
		session.getTransaction().commit();
		return oldType;
	}
	
	/**
	 * 删除旧的小类别，同时把下面的收支信息保存到新的类型中去.
	 * @param tallyTypeId 要删除的小类别
	 * @param newTallyTypeCode 要保存的新的类别代码
	 * @return
	 */
	public int deleteTally(int tallyTypeId,String newTallyTypeCode){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		TallyType oldType = (TallyType)session.get(TallyType.class,tallyTypeId);
		SQLQuery query = null;
		//如果新的类型code非空，就更新收支信息到新的类型编码
		if(Util.notNull(newTallyTypeCode)){
			//先修改以前的记录到新的类型下面去		
			query = session.createSQLQuery("update money_detail_t set money_type = :newType where money_type = :oldType");
			query.setString("newType", newTallyTypeCode);
			query.setString("oldType", oldType.getTypeCode());
			ans = query.executeUpdate();
		}
		//否则就删除对应的收支信息
		else{
			query = session.createSQLQuery("delete from money_detail_t where money_type = :oldType");
			query.setString("oldType", oldType.getTypeCode());
			ans = query.executeUpdate();
		}
		//然后再删除类型数据
		session.delete(oldType);
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 删除小类别之前查询有多少收支信息等于这个类别。
	 * @param tallyTypeId
	 * @return
	 */
	public int beforeDeleteTally(String typeCode){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//先修改以前的记录到新的类型下面去		
		SQLQuery query = session.createSQLQuery("select * from money_detail_t where money_type = :oldType");
		query.setString("oldType", typeCode);
		ans = query.list().size();
		session.getTransaction().commit();
		return ans;
	}
	
	//用于设置一个层次的类型code的字符串！也就是A，B，C依次来，最多一层有26个节点。。。
	static String allCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	/**
	 * 添加一个类型之前，根据算法查询应该设置的code。
	 * 查询其父节点有多少个孩子，然后再在后添加一个新的code作为新类型的code！
	 * @param parentCode
	 * @return
	 */
	public String getNewTallyCode(TallyType type){
		int ans = 0;		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//先修改以前的记录到新的类型下面去		
		SQLQuery query = session.createSQLQuery("select * from money_detail_t where parent_code = :parent");
		query.setString("parent", type.getParentCode());
		ans = query.list().size();
		session.getTransaction().commit();
		return type.getParentCode()+TallyTypeDao.allCode.charAt(ans);
	}
}
