package tallyBook.dao;

import java.util.List;

import myOwnLibrary.util.Util;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import tallyBook.base.HibernateUtil;
import tallyBook.pojo.DiaryDetail;
import tallyBook.pojo.GongguoDetail;
import tallyBook.pojo.MoneyDetail;
import tallyBook.pojo.ShopCard;

import common.base.SpringContextUtil;

/**
 * 添加详细的记录信息.
 * @author renjie120
 * 419723443@qq.com
 */
public class MoneyDetailDao implements IMoneyDetailDao{
	private String BEANNAME = "moneyDetailDao";
	private SessionFactory sessionFactory;
	/**
	 * 保存一个消费信息.
	 * @param moneyDetail
	 */
	public void doCreate(MoneyDetail moneyDetail){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(moneyDetail);
		session.getTransaction().commit();
	}
	
	public void doCreate(GongguoDetail gongguoDetail){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(gongguoDetail);
		session.getTransaction().commit();
	}
	
	public void doCreate(DiaryDetail diaryDetail){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(diaryDetail);
		session.getTransaction().commit();
	}
	
	/**
	 * 创建一个卡片信息
	 * @param moneyDetail
	 */
	public void doCreateCard(ShopCard card){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(card);
		session.getTransaction().commit();
	}
		
	/**
	 * 信用卡还钱
	 * @param cardSno 卡号
	 * @param money 还的金额
	 */
	public ShopCard backMoney(String cardSno,String money){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		ShopCard detail = (ShopCard)session.get(ShopCard.class,Integer.parseInt(cardSno));
		detail.setCardMoney(Util.add(detail.getCardMoney().doubleValue(),Double.parseDouble(money)));
		session.merge(detail);
		session.getTransaction().commit();
		return detail;
	}
		
	/**
	 * 得到一个具体的消费详细信息
	 * @param tallyTypeId
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	public MoneyDetail doGet(int moneyDetailId) throws  Exception{
		DaoUtil dao =(DaoUtil)SpringContextUtil.getBean("daoUtil"); 
		MoneyDetail moneyDetail = new MoneyDetail();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.money_sno,      				");
		buf.append("       to_char(t.money_time,'yyyy-MM-dd'), ");
		buf.append("       t.money,                           ");
		buf.append("       t.money_type,                      ");
		buf.append("       t.money_desc,                      ");
		buf.append("       ty.tally_type_desc,decode(t.shopcard,null,-1,t.shopcard) ,t.splitNo                ");
		buf.append("  from money_detail_t t, tally_type_t ty  ");
		buf.append(" where t.money_type = ty.type_code   and    t.money_sno = :detailId "); 
		SQLQuery query = session.createSQLQuery(buf.toString());
		query.setInteger("detailId", moneyDetailId);
		List ans = query.list();
		session.getTransaction().commit();
		try {
			if (ans != null) {
				Object[] objs = (Object[]) ans.get(0);
				moneyDetail.setMoneySno(Integer.parseInt(objs[0].toString()));
				moneyDetail.setMoneyTime(Util.strToDate(objs[1].toString(),
						"yyyy-MM-dd"));
				moneyDetail.setMoney(Double.parseDouble(objs[2].toString()));
				moneyDetail.setMoneyType(objs[3].toString());
				moneyDetail.setMoneyDesc(Util.notBlankStr(objs[4]));
				moneyDetail.setMoneyTypeDesc(objs[5].toString());
				moneyDetail.setShopCard(Integer.parseInt(objs[6].toString()));
				if(objs[7]!=null){ 
					moneyDetail.setSplitNo(Integer.parseInt(objs[7].toString()));
					moneyDetail.setSplitNoStr(dao.queryDouble("select sum(money) from money_detail_t where splitno =  "+objs[7])+""); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moneyDetail;
	}	
	
	/**
	 * 更新一个消费信息
	 * @param moneyDetailId
	 * @param moneyDetail
	 * @return
	 */
	public MoneyDetail doUpdate(int moneyDetailId,MoneyDetail moneyDetail){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		MoneyDetail detail = (MoneyDetail)session.get(MoneyDetail.class,moneyDetailId);
		detail = moneyDetail;
		session.merge(detail);
		session.getTransaction().commit();
		return moneyDetail;
	}	
	
	/**
	 * 删除收支信息
	 * @param moneyinfosno 收支信息的主键
	 * @return
	 */
	public int doDelete(int moneyinfosno){
		int ans = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery query = null;
		query = session.createSQLQuery("delete from money_detail_t where money_sno = :sno");
		query.setInteger("sno", moneyinfosno);
		ans = query.executeUpdate();
		session.getTransaction().commit();
		return ans;
	}
	/**
	 * 查询指定的查询sql的结果,返回list.
	 * @param queryStr
	 * @return
	 */
	public List doGetAllMoneyDetails(String queryStr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
}
