package tallyBook.base;

import java.util.List;

import myOwnLibrary.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 * 基础的dao类.
 * 
 * @author renjie120 419723443@qq.com
 */
public class BaseDao {
	protected Log log = LogFactory.getLog("TALLYBook");

	/**
	 * 查询指定的查询sql的结果,返回list.
	 * 
	 * @param queryStr
	 * @return
	 */
	public List doQueryList(String queryStr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}

	public static String[][] getStartAndEndTime(String y, String m) {
		int year = Integer.parseInt(y);
		if (m.indexOf(",") == -1) {
			String[][] result = new String[1][] ;
			result[0] = getStartAndEndTimeOneMonth(y, m);
			return result;
		}
		// 否则说明有多个月份要进行处理.
		else {
			String[] months = m.split(",");
			String[][] result = new String[months.length][] ;
			int i = 0;
			for (String mm : months) {
				result[i++] = getStartAndEndTimeOneMonth(y, mm);
			}
			return result;
		}

	}

	public static String[] getStartAndEndTimeOneMonth(String y, String m) {
		int year = Integer.parseInt(y);
		int month = Integer.parseInt(m);
		int startMonth = month;
		String startTime = "";
		// month=-1就说明是查询年度的统计信息。
		if (month == -1) {
			startTime = DateUtil.getDateStr(year, 1, 1, "yyyy-MM-dd");
		} else {
			startTime = DateUtil.getDateStr(year, startMonth, 1, "yyyy-MM-dd");
		}
		int endYear = year;
		int endMonth = month + 1;
		String endTime = "";
		// month=-1就说明是查询年度的统计信息。
		if (month == -1) {
			endYear = year + 1;
			endTime = DateUtil.getDateStr(endYear, 1, 1, "yyyy-MM-dd");
		} else {
			endTime = DateUtil.getDateStr(endYear, endMonth, 1, "yyyy-MM-dd");
		}
		return new String[] { startTime, endTime };
	}

	public static void main(String[] a) {
		String[][] res = getStartAndEndTime("2012", "1");
		for (int i = 0, j = res.length; i < j; i++){
			String[] ss = res[i];
			for (int ii = 0, jj = ss.length; ii < jj; ii++){
				System.out.println(ss[ii]);
			}
		}
	}
}
