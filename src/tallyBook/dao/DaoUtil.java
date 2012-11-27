package tallyBook.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import tallyBook.base.HibernateUtil;
/**
 * ���ݿ�Ĳ�ѯͨ�ù�����.
 * @author renjie120 419723443@qq.com
 *
 */
public class DaoUtil {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
 
	/**
	 * ִ��һ��sql��� 
	 * @param sql
	 * @throws Exception 
	 */
	public void exeSql(String sql) { 
		this.jdbcTemplate.execute(sql);
	}
	
	public int queryInt(String sql) { 
		return this.jdbcTemplate.queryForInt(sql);
	}  
	
	public double queryDouble(String sql)  { 
		List l  = this.jdbcTemplate.queryForList(sql);
		if(l!=null){
			ListOrderedMap m = (ListOrderedMap)l.get(0);
			return Double.parseDouble(""+m.getValue(0));
		}
		return 0;
	}  
	
	public Object queryObject(String sql,Class requiredType)  { 
		return this.jdbcTemplate.queryForObject(sql, requiredType);
	} 
	
	public Object queryObject(String sql,Object[] args,Class requiredType)   { 
		return this.jdbcTemplate.queryForObject(sql, args, requiredType);
	} 

	public Map query(String sql) {
		final List result = new ArrayList();
		Map ans = new HashMap();
		// �õ�����е�������Ϣ.
		RowCountCallbackHandler rcch = new RowCountCallbackHandler();
		jdbcTemplate.query(sql, rcch);
		List columnNames = new ArrayList();
		// �õ�����
		for (String colName : rcch.getColumnNames()) {
			columnNames.add(colName);
		}
		ans.put("columnNames", columnNames);
		
		final List columnTypeNames =new ArrayList();    
		final int columnCount = columnNames.size();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				//����ǵ�һ�У��õ��е�Ԫ����,�����е�����.
				if(rs.isFirst()){
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					List columnTypes = new ArrayList();
					for (int i = 1; i <= columnCount; i++) { 
						columnTypes.add(rsmd.getColumnTypeName(i));
					}
					columnTypeNames.addAll(columnTypes);
				}
				Object[] ans = new Object[columnCount];
				for (int i = 0; i < columnCount; i++)
					ans[i] = rs.getObject(i + 1);
				result.add(ans);
			}
		});
		ans.put("colType", columnTypeNames);
		ans.put("data", result);
		return ans;
	} 
	/**
	 * ʹ��ԭʼ��sql�����в�ѯ
	 * @param queryStr
	 * @return
	 */
	public List doGetSqlQueryList(String queryStr){
		List l =  this.jdbcTemplate.queryForList(queryStr); 
		List r = new ArrayList();
		if(l!=null){
			Iterator it = l.iterator();
			while(it.hasNext()){
				ListOrderedMap m = (ListOrderedMap)it.next();
				Object[] o = new Object[m.size()];
				for(int i=0,j=m.size();i<j;i++){
					o[i] = m.getValue(i);
				}
				r.add(o);
			}
		}
		return r;
	}	
	
	/**
	 * ִ��ԭʼ�����ӣ�ɾ�������²�����
	 * @param queryStr
	 * @return
	 */
	public int doUpdateSqlQuery(String queryStr){
		this.jdbcTemplate.execute(queryStr);
		return 1;
	}	
	
	/**
	 * ִ��HQL��sql��䡣
	 * @param queryStr
	 * @return
	 */
	public int doUpdateQuery(String queryStr){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ans = session.createQuery(queryStr).executeUpdate();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ��ҳ��ѯָ������֮�������. 
	 * @param queryStr ��ѯ�ַ���
	 * @param start ��ʼ��
	 * @param end ��ֹ��
	 * @return
	 */
	public List doQueryList(String queryStr,int start,int end){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery(queryStr);
		q.setFirstResult(start);
		q.setMaxResults(end);
		List l = q.list(); 
		session.getTransaction().commit();
		return l;
	}
	
	/**
	 * ��ѯ����������ȫ���б�����.
	 * @param queryStr
	 * @return
	 */
	public List doQueryList(String queryStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createQuery(queryStr); 
		List l = q.list(); 
		session.getTransaction().commit();
		return l;
	}
	
	/**
	 * ʹ��ԭʼ��sql�����з�ҳ.
	 * @param queryStr
	 * @param start
	 * @param end
	 * @return
	 */
	public List doQueryPagingSqlList(String queryStr,int start,int end){ 
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query q = session.createSQLQuery(queryStr);
		q.setFirstResult(start-1);
		q.setMaxResults(end);
		List l = q.list(); 
		session.getTransaction().commit();
		return l;
	}
}
