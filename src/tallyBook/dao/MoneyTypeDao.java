package tallyBook.dao;

import java.util.List;

import org.hibernate.Session;

import tallyBook.base.BaseDao;
import tallyBook.base.HibernateUtil;
import tallyBook.pojo.MoneyType;

/**
 * 维护大类信息.即是收入还是支出.
 * @author renjie120
 * 419723443@qq.com
 */
public class MoneyTypeDao extends BaseDao{
	/**
	 * 添加一个大类信息.
	 * @param moneyType
	 */
	public void doCreate(MoneyType moneyType){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();		
		session.save(moneyType);
		session.getTransaction().commit();
	}
	
	/**
	 * 得到指定类别号对应的类别信息.
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
	 * 得到全部的大类别的集合.
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
