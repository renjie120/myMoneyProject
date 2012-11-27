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
 * ά�������С��������Ϣ.
 * @author renjie120
 * 419723443@qq.com
 */
public class TallyTypeDao extends BaseDao{
	/**
	 * ����һ��С���Ķ��󱣳ֵ����ݿ�.
	 * @param tallyType
	 * @throws SQLException 
	 */
	public void doCreate(TallyType tallyType) throws SQLException{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Connection con = session.connection(); 
		//���ô洢���̵���ͼ�ǣ��Զ�����typeCode��typeSno��Ȼ���ٲ������ݡ�
		String procedure = "{call insertTayllyType(?,?,?) }"; 
		CallableStatement cstmt = con.prepareCall(procedure); 
		cstmt.setString(1, tallyType.getTallyTypeDesc());
		cstmt.setString(2, tallyType.getParentCode());
		cstmt.setString(3, tallyType.getMoneyType());
		cstmt.executeUpdate();
		session.getTransaction().commit();
	}
	
	/**
	 * �õ�һ�������С�����Ϣ.
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
	 * ��ѯָ���Ĳ�ѯsql�Ľ��,����list.
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
	 * ��ѯȫ������������.
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
	 * �õ�ȫ�����������ݣ��Լ�ͳ�ƽ������γɱ����.
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
	 * ��ѯָ���������֧����С���
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
	 * ��ѯȫ����С����б�
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
	 * ��ext����и��ݸ��ڵ�õ��ӽڵ������.
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
		//���޸���ǰ�ļ�¼���µ���������ȥ		
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
			System.out.println("��ѯ�����ӽڵ㣺"+ansList.size());
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
	 * �ж����ͽڵ��ǲ����к��ӽڵ�.
	 * @param nodeId
	 * @return �к��ӽڵ�ͷ���true�����򷵻�false.
	 */
	public boolean hasChildType(String nodeId) {
		String ans = "";
		String sql = "select count(1) from tally_type_t t where t.PARENT_CODE = :nodeId";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//���޸���ǰ�ļ�¼���µ���������ȥ		
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
	 * ����һ����������
	 * @param tallyTypeId Ҫ�޸ĵ�����id
	 * @param tallyTypeName �µ���������
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
	 * ���ݸ��׽ڵ����е��ӽڵ�����.
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
	 * ɾ���ɵ�С���ͬʱ���������֧��Ϣ���浽�µ�������ȥ.
	 * @param tallyTypeId Ҫɾ����С���
	 * @param newTallyTypeCode Ҫ������µ�������
	 * @return
	 */
	public int deleteTally(int tallyTypeId,String newTallyTypeCode){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		TallyType oldType = (TallyType)session.get(TallyType.class,tallyTypeId);
		SQLQuery query = null;
		//����µ�����code�ǿգ��͸�����֧��Ϣ���µ����ͱ���
		if(Util.notNull(newTallyTypeCode)){
			//���޸���ǰ�ļ�¼���µ���������ȥ		
			query = session.createSQLQuery("update money_detail_t set money_type = :newType where money_type = :oldType");
			query.setString("newType", newTallyTypeCode);
			query.setString("oldType", oldType.getTypeCode());
			ans = query.executeUpdate();
		}
		//�����ɾ����Ӧ����֧��Ϣ
		else{
			query = session.createSQLQuery("delete from money_detail_t where money_type = :oldType");
			query.setString("oldType", oldType.getTypeCode());
			ans = query.executeUpdate();
		}
		//Ȼ����ɾ����������
		session.delete(oldType);
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ɾ��С���֮ǰ��ѯ�ж�����֧��Ϣ����������
	 * @param tallyTypeId
	 * @return
	 */
	public int beforeDeleteTally(String typeCode){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//���޸���ǰ�ļ�¼���µ���������ȥ		
		SQLQuery query = session.createSQLQuery("select * from money_detail_t where money_type = :oldType");
		query.setString("oldType", typeCode);
		ans = query.list().size();
		session.getTransaction().commit();
		return ans;
	}
	
	//��������һ����ε�����code���ַ�����Ҳ����A��B��C�����������һ����26���ڵ㡣����
	static String allCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	/**
	 * ���һ������֮ǰ�������㷨��ѯӦ�����õ�code��
	 * ��ѯ�丸�ڵ��ж��ٸ����ӣ�Ȼ�����ں����һ���µ�code��Ϊ�����͵�code��
	 * @param parentCode
	 * @return
	 */
	public String getNewTallyCode(TallyType type){
		int ans = 0;		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//���޸���ǰ�ļ�¼���µ���������ȥ		
		SQLQuery query = session.createSQLQuery("select * from money_detail_t where parent_code = :parent");
		query.setString("parent", type.getParentCode());
		ans = query.list().size();
		session.getTransaction().commit();
		return type.getParentCode()+TallyTypeDao.allCode.charAt(ans);
	}
}
