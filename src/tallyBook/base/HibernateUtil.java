package tallyBook.base;	

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * ���ڲ���һ��hibernate�Ự�Ĺ�����.
 * @author renjie120
 * 419723443@qq.com
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		}
		return sessionFactory;
	}

	private HibernateUtil() {
	}
}