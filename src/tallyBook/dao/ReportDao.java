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
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportByMonth(String[][] times) {
		return getReportByMonth(times, null, null);
	}

	public List getReportByMonth(String[][] times, String lxtp, String tps) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY),TO_CHAR(MONEY_TIME,'DD')  ");
		buf.append("  FROM MONEY_DETAIL_VIEW T");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("   AND T.TYPE_ID = '2'  ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf.append(" group by t.money_time ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();
		session.getTransaction().commit();
		return ans;
	}

	public List getReportByYear(String[][] times, String lxtp, String tps) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), TO_CHAR(T.MONEY_TIME, 'MM')  ");
		buf.append("  FROM MONEY_DETAIL_VIEW T           ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("     AND T.TYPE_ID = '2'  ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf.append(" GROUP BY TO_CHAR(T.MONEY_TIME, 'MM')    ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}

		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	public List getReportInByTallyType(String[][] times, String lxtp, String tps) {
		StringBuffer buf = new StringBuffer();
		buf
				.append("SELECT SUM(MONEY), T.TALLY_TYPE_DESC, T.MONEY_TYPE                      ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("   AND T.TYPE_ID = '1'                                 ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf
				.append(" GROUP BY T.TALLY_TYPE_DESC, MONEY_TYPE ORDER BY T.MONEY_TYPE                 ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	public List getReportInByType(String[][] times, String lxtp, String tps) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), BIG_TYPE_DESC                       ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("   AND T.TYPE_ID = '1'                                 ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf.append(" GROUP BY T.BIG_TYPE_DESC                              ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	public List getReportOutByType(String[][] times, String lxtp, String tps) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), BIG_TYPE_DESC          ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("   AND T.TYPE_ID = '2'                                 ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf.append(" GROUP BY T.BIG_TYPE_DESC                            ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	public List getReportInAndOutByMonth(String[][] times, String lxtp,
			String tps) {
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT SUM(MONEY), DECODE(T.TYPE_ID,'1','收入','支出')");
		buf.append("  FROM MONEY_DETAIL_VIEW T                            ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf.append(" GROUP BY T.TYPE_ID                                   ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	public List getReportOutByTallyType(String[][] times, String lxtp,
			String tps) {
		StringBuffer buf = new StringBuffer();
		buf
				.append("SELECT SUM(MONEY), T.TALLY_TYPE_DESC, T.MONEY_TYPE                      ");
		buf.append("  FROM MONEY_DETAIL_VIEW T                             ");
		buf.append(" WHERE  ");
		buf.append(addQueryByTimes(times));
		buf.append("   AND T.TYPE_ID = '2'                                 ");
		if (lxtp != null)
			buf.append(addQuery(lxtp, tps));
		buf
				.append(" GROUP BY T.TALLY_TYPE_DESC, MONEY_TYPE ORDER BY T.MONEY_TYPE                 ");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List ans = null;
		session.beginTransaction();
		SQLQuery q = session.createSQLQuery(buf.toString());
		int i = 0;
		for (int m = 0, n = times.length; m < n; m++) {
			String[] temp = times[m];
			q.setString(i++, temp[0]);
			q.setString(i++, temp[1]);
		}
		if (lxtp != null) {
			String[] tp = tps.split(",");
			for (String t : tp) {
				q.setString(i++, t);
			}
		}
		ans = q.list();

		session.getTransaction().commit();
		return ans;
	}

	private String addQueryByTimes(String[][] times) {
		StringBuffer buf = new StringBuffer(" (");
		for (int i = 0, j = times.length; i < j; i++) { 
			buf.append(" (T.MONEY_TIME >= TO_DATE( ?,'yyyy-MM-dd') AND ");
			buf.append(" T.MONEY_TIME < TO_DATE( ?,'yyyy-MM-dd')) ");
			if (i < j - 1)
				buf.append(" OR ");
		}
		buf.append(") ");
		return buf.toString();
	}

	private String addQuery(String lxtp, String tps) {
		String[] tp = tps.split(",");
		StringBuffer buf = new StringBuffer("");
		System.out.println(tps + ",,," + lxtp);
		if (tp.length > 0) {
			if ("fst".equals(lxtp)) {
				buf.append(" AND BIG_TYPE IN  (");
				for (String s : tp) {
					buf.append("?,");
				}
				buf = buf.deleteCharAt(buf.lastIndexOf(","));
				buf.append(") ");
			} else {
				buf.append(" AND MONEY_TYPE IN  (");
				for (String s : tp) {
					buf.append("?,");
				}
				buf = buf.deleteCharAt(buf.lastIndexOf(","));
				buf.append(") ");
			}
		}
		return buf.toString();
	}

	/**
	 * 得到一年里面的每月的支出统计信息.
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportByYear(String[][] times) {
		return getReportByYear(times, null, null);
	}

	/**
	 * 得到收入类别的统计数据信息。
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportInByType(String[][] times) {
		return getReportInByType(times, null, null);
	}

	/**
	 * 得到支出类别的统计数据信息。
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportOutByType(String[][] times) {
		return getReportOutByType(times, null, null);
	}

	/**
	 * 得到详细的小类别的收入统计信息。反应为环状图。
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportInByTallyType(String[][] times) {
		return getReportInByTallyType(times, null, null);
	}

	/**
	 * 得到详细的小类别的支出统计信息。反应为环状图。
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportOutByTallyType(String[][] times) {
		return getReportOutByTallyType(times, null, null);
	}

	/**
	 * 得到每月的收入，支出大盘。反应为饼图。
	 * 
	 * @param startTime
	 * @param times
	 * @return
	 */
	public List getReportInAndOutByMonth(String[][] times) {
		return getReportInAndOutByMonth(times, null, null);
	}
}
