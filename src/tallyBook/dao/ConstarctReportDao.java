package tallyBook.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session; 

import tallyBook.base.HibernateUtil;
 
public class ConstarctReportDao {
	/**
	 * 得到在月份和小的分类维度上的金钱统计。
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
	 * 得到某一年度的某类别的全部的月份和金额信息. 
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
	 * 得到全部的报表数据!
	 * 最新的最全的分析报表数据来源sql！
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
	 * 得到某一年度的某类别的全部的数据信息.
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
	 * 查询全部的大类别的金额信息列表信息.
	 * @param year 年
	 * @param month 月
	 * @param bigType 大类别
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
	 * 统计大类别的总金额.
	 * @param year 年
	 * @param month 月
	 * @param bigType 大类别
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
	 * 得到小类别的全部金额信息列表.
	 * @param year 年
	 * @param month 月
	 * @param smallType 小类别
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
	 * 统计小类别的总金额.
	 * @param year 年
	 * @param month 月
	 * @param smallType 小类别
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
	 * 得到top n分析的数据.
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
//		System.out.println("分月报表"+buf);
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
	 * 年度报表分析，用于展示排列前10的
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
		//如果是查询某一个具体的年份
		if(!"-1".equals(year)){
			if (bigType == null){
				buf.append(" from money_all_smalltype_year t");
				buf.append("                  where type_id = 2                                         ");
			}
			else
				buf.append("  from money_all_smalltype_year t where bigtype =:bigtype  and type_id = 2 ");  
		}
		//负责查询全部的年份
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
	 * 计算综合.
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
	 * 年度top分析.
	 * @param year 年度
	 * @param bigType 大类别
	 * @param smallType 小类别
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
	 * 计算总数.
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
	 * 月度新的top分析！
	 * 分析总和，平均数，排行等全部信息
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
//		System.out.println("查询top分析："+buf);
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
	 * 得到每月的收支总计统计数据.
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
	 * 查询全部的金额，类型列表.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param bigType
	 *            大类
	 * @param smallType
	 *            小类
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
	 * 得到报表的时候查询根据类别进行统计.
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
	 * 得到在年度和小的分类维度上的金钱统计。
	 * 
	 * @type 要进行统计的大类
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
	 * 得到在月份和大的分类维度上的金钱统计。
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
	 * 得到在年度和大的分类维度上的金钱统计。
	 * 
	 * @type 要进行统计的大类
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
