package tallyBook.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session; 

import tallyBook.base.HibernateUtil;
 
public class ConstarctReportDao {
	/**
	 * �õ����·ݺ�С�ķ���ά���ϵĽ�Ǯͳ�ơ�
	 * 
	 * @return
	 */
	public List getMoneyWithTypeAndMonth(String year, String type) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf
				.append("select t.money, t.money_type, t.tally_type_desc,  t.month  ");
		buf
				.append("  from money_detail_type_month t      where   t.year=:year  and type_id=:type   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("year", year);
			q.setString("type", type);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}

	/** 
	 * �õ�ĳһ��ȵ�ĳ����ȫ�����·ݺͽ����Ϣ. 
	 * @param year
	 * @param smallType
	 * @return
	 */
	public List getEachMonthByTypeData(String year,  String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select month, money ,year               ");
		buf.append("  from money_all_smalltype_month t ");
		buf.append(" where   money_type = :smallType                ");
		if(!"-1".equals( year))
			buf.append("   and  year = :year         ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			if(!"-1".equals( year))
				q.setString("year", year); 
			q.setString("smallType", smallType);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans; 
	}  
	
	/**
	 * �õ�ȫ���ı�������!
	 * ���µ���ȫ�ķ�������������Դsql��
	 * @return
	 */
	public List getTotalBigType() {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.*, tp.tally_type_desc                                  ");
		buf.append("  from (select yeAR, month, money_type, sum(money), 2 moneytype ");
		buf.append("          from money_detail_big_type_month t                    ");
		buf.append("         where type_id = 2                                      ");
		buf.append("         group by cube(money_type, month, year)                 ");
		buf.append("        union all                                               ");
		buf.append("        select yeAR, month, money_type, sum(money), 1 moneytype ");
		buf.append("          from money_detail_big_type_month t                    ");
		buf.append("         where type_id = 1                                      ");
		buf.append("         group by cube(money_type, month, year)) t,             ");
		buf.append("       tally_type_t tp                                          ");
		buf.append(" where t.money_type = tp.type_code                              "); 
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());  
			ans = q.list();
//			if()
//			Iterator it = 
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;  
	}
	public String getEachMonthSumByType(String year,  String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(money)                                        ");
		buf.append("  from money_all_smalltype_month t                          ");
		buf.append(" where       money_type = :smallType                       ");
		if(!"-1".equals( year))
			buf.append("   and   year = :year                            ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			if(!"-1".equals( year))
				q.setString("year", year); 
			q.setString("smallType", smallType);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans.get(0).toString(); 
	}  
	
	/**
	 * �õ�ĳһ��ȵ�ĳ����ȫ����������Ϣ.
	 * @param year
	 * @param smallType
	 * @return
	 */
	public List getEachMonthByType(String year,  String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select rank() over(order by money desc) rank,               ");
		buf.append("       month,                                               ");
		buf.append("       money,                                               ");
		buf.append("       round(money / count, 2) average,                     ");
		buf.append("       100 * round(t.money / sum(money) over(), 4) percents,");
		buf.append("       t.count    ,year                                          ");
		buf.append("  from money_all_smalltype_month t                          ");
		buf.append(" where       money_type = :smallType                       ");
		if(!"-1".equals( year))
			buf.append("   and   year = :year                            ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			if(!"-1".equals( year))
				q.setString("year", year); 
			q.setString("smallType", smallType);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans; 
	}  
	
	/**
	 * ��ѯȫ���Ĵ����Ľ����Ϣ�б���Ϣ.
	 * @param year ��
	 * @param month ��
	 * @param bigType �����
	 * @return
	 */
	public List reportDetailByTimeAndType(String year, String month,
			String bigType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select to_char(t.money_time,'yyyy-MM-dd') money_time,t.money,t.money_desc,t.tally_type_desc,money_sno ");
		buf.append("from money_detail_report_view t where big_type =:bigtype and year=:year and month =:month ");
		buf.append(" order by year desc, month desc,money_type");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ͳ�ƴ������ܽ��.
	 * @param year ��
	 * @param month ��
	 * @param bigType �����
	 * @return
	 */
	public List reportDetailByTimeAndTypeSum(String year, String month,
			String bigType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(t.money) ");
		buf.append("from money_detail_report_view t where big_type =:bigtype and year=:year and month =:month ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * �õ�С����ȫ�������Ϣ�б�.
	 * @param year ��
	 * @param month ��
	 * @param smallType С���
	 * @return
	 */
	public List reportDetailByTimeAndSmallType(String year, String month,
			String bigType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select to_char(t.money_time,'yyyy-MM-dd') money_time,t.money,t.money_desc,t.tally_type_desc,money_sno ");
		buf.append("from money_detail_report_view t where  year=:year and month =:month ");
		if(!"-1".equals(bigType))
			buf.append(" and money_type =:bigtype ");
		buf.append(" order by year desc, month desc,money_type");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if(!"-1".equals(bigType))
				q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ͳ��С�����ܽ��.
	 * @param year ��
	 * @param month ��
	 * @param smallType С���
	 * @return
	 */
	public List reportDetailByTimeAndSmallTypeSum(String year, String month,
			String bigType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(t.money) ");
		buf.append("from money_detail_report_view t where    year=:year and month =:month ");
		if(!"-1".equals(bigType))
			buf.append(" and money_type =:bigtype ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if(!"-1".equals(bigType))
				q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}

	/**
	 * �õ�top n����������.
	 * @param year
	 * @param month
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public List getTopNReport(String year, String month, String bigType,
			String smallType,int n) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select *                                                                    ");
		buf.append("  from (select t.zonghe,                                                    ");
		buf.append("               t.money_type,                                                ");
		buf.append("               t.tally_type_desc,                                           ");
		buf.append("               rank() over(order by zonghe desc) rank                       ");
		buf.append("          from (select t.money_type,t.tally_type_desc, sum(t.money) zonghe  ");
		if (bigType == null){
			buf.append(" from money_all_smalltype_month t");
			buf.append("                  where type_id = 2                                         ");
		}
		else
			buf.append("  from money_all_smalltype_month t where bigtype =:bigtype"); 
		buf.append("                  and year =:year                                         ");
		buf.append("                  and month =:month                                         ");
		buf.append("                 group by money_type,tally_type_desc) t)                    ");
		buf.append("                where rank<=:number                   "); 
//		System.out.println("���±���"+buf);
//		System.out.println("bigType"+bigType+",year"+year+",month"+bigType+",number"+n);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month);
			q.setInteger("number", n);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ��ȱ������������չʾ����ǰ10��
	 * @param year
	 * @param bigType
	 * @param smallType
	 * @return
	 */ 
	public List getTopNYearReport(String year,  String bigType,
			String smallType,int n) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select *                                                                  ");
		buf.append("  from (select t.zonghe,                                                  ");
		buf.append("               t.count,                                                   ");
		buf.append("               t.money_type,                                              ");
		buf.append("               t.tally_type_desc,                                         ");
		buf.append("               rank() over(order by zonghe desc) rank,                    ");
		buf.append("               round(t.zonghe / t.count, 2) average,                      ");
		buf.append("               100 * round(t.zonghe / sum(zonghe) over(), 4) percents     ");
		buf.append("          from (select t.money_type,                                      ");
		buf.append("                       t.tally_type_desc,                                 ");
		buf.append("                       sum(t.money) over(partition by money_type) zonghe, ");
		buf.append("                       t.count                                            ");
		//����ǲ�ѯĳһ����������
		if(!"-1".equals(year)){
			if (bigType == null){
				buf.append(" from money_all_smalltype_year t");
				buf.append("                  where type_id = 2                                         ");
			}
			else
				buf.append("  from money_all_smalltype_year t where bigtype =:bigtype  and type_id = 2 ");  
		}
		//�����ѯȫ�������
		else{
			buf.append(" from money_all_smalltype t where type_id = 2 ");
		}
		if(!"-1".equals(year)){
			buf.append("                   and year = :year ) t)                                   "); 
			buf.append("                where rank<=:number                   ");  
		}
		else{
			buf.append(" ) t)");
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			if(!"-1".equals(year)){
				q.setString("year", year); 
				q.setInteger("number", n);
			}
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	} 
	
	/**
	 * �����ۺ�.
	 * @param year
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public String getSumNYearReport(String year, String bigType,
			String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(money)                                                                 ");
		if(!"-1".equals(year)) {
			if (bigType == null){
				buf.append(" from money_all_smalltype_year t");
				buf.append("                  where type_id = 2                                         ");
			}
			else
				buf.append("  from money_all_smalltype_year t where bigtype =:bigtype"); 
			buf.append("                 and type_id = 2                                        ");
			buf.append("                   and year = :year                                    "); 
		}
		else{
			buf.append(" from money_all_smalltype  t where type_id = 2  ");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			if(!"-1".equals(year)) 
				q.setString("year", year); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return  ans.get(0).toString();
	}
	
	/**
	 * ���top����.
	 * @param year ���
	 * @param bigType �����
	 * @param smallType С���
	 * @return
	 */
	public List getTopNewNYearReport(String year, String bigType,
			String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select *                                                                  ");
		buf.append("  from (select t.zonghe,                                                  ");
		buf.append("               t.count,                                                   ");
		buf.append("               t.money_type,                                              ");
		buf.append("               t.tally_type_desc,                                         ");
		buf.append("               rank() over(order by zonghe desc) rank,                    ");
		buf.append("               round(t.zonghe / t.count, 2) average,                      ");
		buf.append("               100 * round(t.zonghe / sum(zonghe) over(), 4) percents     ");
		buf.append("          from (select t.money_type,                                      ");
		buf.append("                       t.tally_type_desc,                                 ");
		buf.append("                       sum(t.money) over(partition by money_type) zonghe, ");
		buf.append("                       t.count                                            ");
		if(!"-1".equals(year)) {
			if (bigType == null){
				buf.append(" from money_all_smalltype_year t");
				buf.append("                  where type_id = 2                                         ");
			}
			else
				buf.append("  from money_all_smalltype_year t where bigtype =:bigtype"); 
			buf.append("                 and type_id = 2                                        ");
			buf.append("                   and year = :year ) t)                                   "); 
		}
		else{
			buf.append(" from money_all_smalltype  t where type_id = 2 )t)");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			if(!"-1".equals(year)) 
				q.setString("year", year); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ��������.
	 * @param year
	 * @param month
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public String getSumNReport(String year, String month, String bigType,
			String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(money)                                                                ");
		if (bigType == null){
			buf.append(" from money_all_smalltype_month t");
			buf.append("                  where type_id = 2                                         ");
		}
		else
			buf.append("  from money_all_smalltype_month t where bigtype =:bigtype"); 
		buf.append("                   and year = :year                                      ");
		buf.append("                   and month =:month                                 ");  
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();  
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans.get(0).toString();
	}
	
	/**
	 * �¶��µ�top������
	 * �����ܺͣ�ƽ���������е�ȫ����Ϣ
	 * @param year
	 * @param month
	 * @param bigType
	 * @param smallType
	 * @param n
	 * @return
	 */
	public List getTopNewNReport(String year, String month, String bigType,
			String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select *                                                                 ");
		buf.append("  from (select t.zonghe,                                                 ");
		buf.append("               t.count,                                                  ");
		buf.append("               t.money_type,                                             ");
		buf.append("               t.tally_type_desc,                                        ");
		buf.append("               rank() over(order by zonghe desc) rank,                   ");
		buf.append("               round(t.zonghe/t.count,2) average,                        ");
		buf.append("               100*round(t.zonghe/sum(zonghe) over(),4) percents         ");
		buf.append("          from (select t.money_type,                                     ");
		buf.append("                       t.tally_type_desc,                                ");
		buf.append("                       sum(t.money) over(partition by money_type) zonghe,");
		buf.append("                       t.count                                           ");
		if (bigType == null){
			buf.append(" from money_all_smalltype_month t");
			buf.append("                  where type_id = 2                                         ");
		}
		else
			buf.append("  from money_all_smalltype_month t where bigtype =:bigtype"); 
		buf.append("                   and year = :year                                      ");
		buf.append("                   and month =:month) t)                                  ");  
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
//		System.out.println("bigType"+bigType+",year"+year+",month"+month);
//		
//		System.out.println("��ѯtop������"+buf);
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			q.setString("year", year);
			q.setString("month", month); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	} 

	public List getYearInAndOut() {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select year,                                                ");
		buf.append("       MAX(decode(type_id, 1, money, 0)) inmoney,           ");
		buf.append("       MAX(decode(type_id, 2, money, 0)) outmoney           ");
		buf.append("  from (select sum(t.money) money,                          ");
		buf.append("               to_char(t.money_time, 'yyyy') year,          ");
		buf.append("               type_id                                      ");
		buf.append("          from money_detail_view t                          ");
		buf.append("         group by to_char(t.money_time, 'yyyy'), t.type_id) ");
		buf.append(" group by year                                              ");
		buf.append(" order by year desc                                         ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	public List getAllInAndOut(boolean checkSplitNo) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		if(checkSplitNo){
			buf.append("select sum(decode(type_id, 1, money, 0)) inmoney, ");  
			buf.append("       sum(decode(type_id, 2, money, 0)) outmoney ");
		}else{
			buf.append("select sum(decode(type_id, 1, real_money, 0)) inmoney, ");  
			buf.append("       sum(decode(type_id, 2, real_money, 0)) outmoney "); 
		}
		buf.append("  from money_detail_view t                        ");  
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * �õ�ÿ�µ���֧�ܼ�ͳ������.
	 * @param year
	 * @param month
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public List getInAndOut(boolean checkSplitNo) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select year,            						  ");			
		buf.append("       month,                                     ");
		buf.append("       MAX(decode(type_id, 1, money, 0)) inmoney, ");
		buf.append("       MAX(decode(type_id, 2, money, 0)) outmoney ");
		if(checkSplitNo)
			buf.append("  from (select sum(t.money) money,                ");
		else
			buf.append("  from (select sum(t.real_money) money,                ");
		buf.append("               to_char(t.money_time, 'yyyy') year,");
		buf.append("               to_char(t.money_time, 'MM') month, ");
		buf.append("               type_id                            ");
		buf.append("          from money_detail_view t                ");
		buf.append("         group by to_char(t.money_time, 'yyyy'),  ");
		buf.append("                  to_char(t.money_time, 'MM'),    ");
		buf.append("                  t.type_id)                      ");
		buf.append(" group by year, month                             ");
		buf.append(" order by year desc, month desc                   "); 
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString()); 
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * ��ѯȫ���Ľ������б�.
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @param bigType
	 *            ����
	 * @param smallType
	 *            С��
	 * @return
	 */
	public List getDetailReport(String year, String month, String bigType,
			String smallType,boolean splitNo) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		if(splitNo)
			buf.append("select t.year, t.month, t.tally_type_desc, t.money_type, t.money");
		else
			buf.append("select t.year, t.month, t.tally_type_desc, t.money_type, t.real_money money");
		if (bigType == null)
			buf.append(" from money_detail_big_type_month t");
		else
			buf.append("  from money_all_smalltype_month t where bigtype =:bigtype");
		buf.append(" order by year desc, month desc,money_type  "); 
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * �õ������ʱ���ѯ����������ͳ��.
	 * @param year
	 * @param month
	 * @param bigType
	 * @param smallType
	 * @return
	 */
	public List getDetailReportByType(String year, String month, String bigType,
			String smallType) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.zonghe,                                     ");
		buf.append("       t.money_type,                                 ");
		buf.append("       100 * round(t.zonghe / sum(zonghe) over(), 4) ");
		buf.append("  from (select t.money_type, sum(t.money) zonghe     ");
		if (bigType == null)
			buf.append(" from money_detail_big_type_month t");
		else
			buf.append("  from money_all_smalltype_month t where bigtype =:bigtype");
		buf.append("         group by money_type) t                      ");
		
  
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction(); 
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			if (bigType != null)
				q.setString("bigtype", bigType);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
	
	

	/**
	 * �õ�����Ⱥ�С�ķ���ά���ϵĽ�Ǯͳ�ơ�
	 * 
	 * @type Ҫ����ͳ�ƵĴ���
	 * @return
	 */
	public List getMoneyWithTypeAndYear(String type) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.money, t.money_type, t.tally_type_desc, t.year  ");
		buf.append("  from money_detail_type_year t    where type_id=:type   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("type", type);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}

	/**
	 * �õ����·ݺʹ�ķ���ά���ϵĽ�Ǯͳ�ơ�
	 * 
	 * @return
	 */
	public List getMoneyWithBigTypeAndMonth(String year, String type) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf
				.append("select t.money, t.money_type, t.tally_type_desc,  t.month  ");
		buf
				.append("  from money_detail_big_type_month t      where   t.year=:year  and type_id=:type   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("year", year);
			q.setString("type", type);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}

	/**
	 * �õ�����Ⱥʹ�ķ���ά���ϵĽ�Ǯͳ�ơ�
	 * 
	 * @type Ҫ����ͳ�ƵĴ���
	 * @return
	 */
	public List getMoneyWithBigTypeAndYear(String type) {
		List ans = new ArrayList();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.money, t.money_type, t.tally_type_desc, t.year  ");
		buf
				.append("  from money_detail_big_type_year t    where type_id=:type   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setString("type", type);
			ans = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}
}
