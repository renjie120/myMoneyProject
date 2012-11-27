package tallyBook.base;	

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 用于产生一个hibernate会话的工具类.
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