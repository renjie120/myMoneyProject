package tallyBook.dao;

import org.hibernate.Session;

import tallyBook.base.BaseDao;
import tallyBook.base.HibernateUtil;
import tallyBook.pojo.ShopCard;

/**
 * ���ÿ�ά����dao��
 * @author renjie120 419723443@qq.com
 *
 */
public class CardDao extends BaseDao{
	/**
	 * ɾ����Ƭ��Ϣ
	 * @param cardSno
	 * @return
	 */
	public void deleteCard(int cardSno){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ShopCard card = null;
		card = (ShopCard) session.get(ShopCard.class, cardSno);
		if (card != null)
			session.delete(card);
		session.getTransaction().commit();
	}
	
	/**
	 * ���¿�Ƭ��Ϣ
	 * @param cardSno
	 * @param shopCard
	 * @return
	 */
	public ShopCard doUpdate(int cardSno,ShopCard shopCard){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ShopCard cardVo = (ShopCard)session.get(ShopCard.class,cardSno);
		cardVo = shopCard;
		session.merge(cardVo);
		session.getTransaction().commit();
		return cardVo;
	}	
	
	/**
	 * �õ���Ƭ����Ϣ.
	 * @param cardSno
	 * @return
	 */
	public ShopCard doGetCardInfo(int cardSno){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ShopCard shopCard = (ShopCard)session.get(ShopCard.class,cardSno);
		session.getTransaction().commit();
		return shopCard;
	}
}
