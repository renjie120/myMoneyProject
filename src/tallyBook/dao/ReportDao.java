package tallyBook.dao;

import java.text.ParseException;
import java.util.List;

import myOwnLibrary.util.Util;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import tallyBook.base.HibernateUtil;

public class ReportDao {
	/**
	 * 得到月份里每天的支出统计信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportByMonth(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(money),to_char(money_time,'dd')                         ");
		buf.append("  from money_detail_view t                                                ");
		buf.append(" where t.money_time>= :start and                 ");
		buf.append("        t.money_time< :end and t.type_id = '2'  ");
		buf.append(" group by t.money_time                                                    ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到一年里面的每月的支出统计信息.
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportByYear(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("select sum(money), to_char(t.money_time, 'mm')  ");
		buf.append("  from money_detail_view t           ");
		buf.append(" where t.money_time>= :start and                 ");
		buf.append("        t.money_time< :end and t.type_id = '2'  ");
		buf.append(" group by to_char(t.money_time, 'mm')    ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到收入类别的统计数据信息。
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportInByType(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), BIG_TYPE_DESC                       ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE T.MONEY_TIME >= :start ");
		buf.append("   AND T.MONEY_TIME < :end ");
		buf.append("   AND T.TYPE_ID = '1'                                 ");
		buf.append(" GROUP BY T.BIG_TYPE_DESC                              ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到支出类别的统计数据信息。
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportOutByType(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), BIG_TYPE_DESC          ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE T.MONEY_TIME >= :start ");
		buf.append("   AND T.MONEY_TIME < :end ");
		buf.append("   AND T.TYPE_ID = '2'                                 ");
		buf.append(" GROUP BY T.BIG_TYPE_DESC                            ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到详细的小类别的收入统计信息。反应为环状图。
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportInByTallyType(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), T.TALLY_TYPE_DESC, T.MONEY_TYPE                      ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE T.MONEY_TIME >= :start ");
		buf.append("   AND T.MONEY_TIME < :end ");
		buf.append("   AND T.TYPE_ID = '1'                                 ");
		buf.append(" GROUP BY T.TALLY_TYPE_DESC, MONEY_TYPE ORDER BY T.MONEY_TYPE                 ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到详细的小类别的支出统计信息。反应为环状图。
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportOutByTallyType(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), T.TALLY_TYPE_DESC, T.MONEY_TYPE                      ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE T.MONEY_TIME >= :start ");
		buf.append("   AND T.MONEY_TIME < :end ");
		buf.append("   AND T.TYPE_ID = '2'                                 ");
		buf.append(" GROUP BY T.TALLY_TYPE_DESC, MONEY_TYPE ORDER BY T.MONEY_TYPE                 ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
	
	/**
	 * 得到每月的收入，支出大盘。反应为饼图。
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List getReportInAndOutByMonth(String startTime,String endTime){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), DECODE(T.TYPE_ID,'1','收入','支出')");
		buf.append("  FROM MONEY_DETAIL_VIEW T                            ");
		buf.append(" WHERE T.MONEY_TIME >= :start");
		buf.append("   AND T.MONEY_TIME < :end");
		buf.append(" GROUP BY T.TYPE_ID                                   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		try {
			SQLQuery q = session.createSQLQuery(buf.toString());
			q.setDate("start", Util.strToDate(startTime,"yyyy-MM-dd"));
			q.setDate("end", Util.strToDate(endTime,"yyyy-MM-dd"));
			ans = q.list();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		session.getTransaction().commit();
		return ans;
	}	
}
