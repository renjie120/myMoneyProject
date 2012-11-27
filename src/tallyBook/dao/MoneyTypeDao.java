package tallyBook.dao;

import java.util.List;

import org.hibernate.Session;

import tallyBook.base.BaseDao;
import tallyBook.base.HibernateUtil;
import tallyBook.pojo.MoneyType;

/**
 * ά��������Ϣ.�������뻹��֧��.
 * @author renjie120
 * 419723443@qq.com
 */
public class MoneyTypeDao extends BaseDao{
	/**
	 * ���һ��������Ϣ.
	 * @param moneyType
	 */
	public void doCreate(MoneyType moneyType){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		
		session.save(moneyType);
		session.getTransaction().commit();
	}
	
	/**
	 * �õ�ָ�����Ŷ�Ӧ�������Ϣ.
	 * @param moneyTypeId
	 * @return
	 */
	public MoneyType doGet(String moneyTypeId){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MoneyType moneyType = (MoneyType)session.get(MoneyType.class,moneyTypeId);
		session.getTransaction().commit();
		return moneyType;
	}
	
	/**
	 * �õ�ȫ���Ĵ����ļ���.
	 * @return
	 */
	public List doGetAllMoneyTypes(String queryStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}
}
