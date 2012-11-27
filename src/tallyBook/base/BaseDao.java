package tallyBook.base;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 * ������dao��.
 * @author renjie120
 * 419723443@qq.com
 */
public class BaseDao {
	protected Log log = LogFactory.getLog("TALLYBook");

	/**
	 * ��ѯָ���Ĳ�ѯsql�Ľ��,����list.
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
}
