package stock.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import stock.pojo.StockConfigVO;
import stock.pojo.StockHolderOnVO;
import stock.pojo.StockVO;
import tallyBook.base.HibernateUtil;
import tallyBook.dao.DaoUtil;

public class StockDao {
	public static final String BUY = "1";
	public static final String SELL = "0";
	DaoUtil dao = null;
	/**
	 * 保存一个交易信息
	 * 
	 * @param moneyDetail
	 */
	public void doCreate(StockVO stock) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(stock);
		session.getTransaction().commit();
	}

	/**
	 * 得到一个具体的交易信息
	 * 
	 * @param tallyTypeId
	 * @return
	 */
	public StockVO doGet(int stockSno) {
		DaoUtil dao = new DaoUtil();
		StockVO stockVo = new StockVO();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer buf = new StringBuffer();
		buf.append("select t.deal_sno,                            ");
		buf.append("       t.deal_stocksno,                       ");
		buf.append("       t.deal_price,                          ");
		buf.append("       t.deal_num,                            ");
		buf.append("       to_char(t.deal_time, 'yyyy-MM-dd')  dealtime,    ");
		buf.append("       t.deal_stockname,                      ");
		buf.append("       t.deal_fee,                            ");
		buf.append("       t.deal_tax,                            ");
		buf.append("       t.deal_idea,                           ");
		buf.append("       hap.happy_name,                        ");
		buf.append("       t.deal_think_price,                    ");
		buf.append("       dealtype.deal_type_name,               ");
		buf.append("       configt.config_name                    ");
		buf.append("  from stock_deal_t      t,                   ");
		buf.append("       stock_deal_type_t dealtype,            ");
		buf.append("       stock_config_t    configt,             ");
		buf.append("       stock_happy_t     hap                  ");
		buf.append(" where t.deal_type = dealtype.deal_type       ");
		buf.append("   and t.config_type = configt.config_sno     ");
		buf
				.append("   and t.happy_num = hap.happy_id   and deal_sno=:stockid         ");
		SQLQuery query = session.createSQLQuery(buf.toString());
		query.setInteger("stockid", stockSno);
		List ans = query.list();
		session.getTransaction().commit();
		// try {
		// if (ans != null) {
		// Object[] objs = (Object[]) ans.get(0);
		//				 
		// }
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		return stockVo;
	}

	/**
	 * 更新一个交易信息
	 * 
	 * @param stockSno
	 * @param stockVO
	 * @return
	 */
	public StockVO doUpdate(int stockSno, StockVO stockVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StockVO detail = (StockVO) session.get(StockVO.class, stockSno);
		detail = stockVO;
		session.merge(detail);
		session.getTransaction().commit();
		return detail;
	}

	/**
	 * 删除交易信息
	 * 
	 * @param stockSno
	 *            股票交易流水号
	 * @return
	 */
	public void doDeleteStock(int stockSno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StockVO stock = null;
		stock = (StockVO) session.get(StockVO.class, stockSno);
		if (stock != null)
			session.delete(stock);
		session.getTransaction().commit();
	}

	/**
	 * 查询指定的查询sql的结果,返回list.
	 * 
	 * @param queryStr
	 * @return
	 */
	public List doGetAllStocks(String queryStr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List ans = session.createQuery(queryStr).list();
		session.getTransaction().commit();
		return ans;
	}

	/** *******************下面是关于股票持有信息的操作部分********************************* */
	/**
	 * 查询是否已经持有某股票信息 
	 * @param stockSno   股票代码
	 */
	public List doQueryExistedHoldon(String stockCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		SQLQuery query = session
				.createSQLQuery("SELECT HOLD_ON_SNO FROM STOCK_HOLD_ON_T WHERE STOCK_NO=:CODE AND DEAL_TYPE=:TP ");
		query.setString("CODE", stockCode);
		query.setString("TP", BUY);
		List ans = query.list();
		session.getTransaction().commit();
		return ans;
	}
	
	/**
	 * 更新股票持有信息.
	 * @param holdOnId
	 * @param stockHolderOnVO
	 * @return
	 */
	public StockHolderOnVO doUpdateStockHoldOn(int holdOnId,StockHolderOnVO stockHolderOnVO){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StockHolderOnVO detail = (StockHolderOnVO)session.get(StockHolderOnVO.class,holdOnId);
		detail = stockHolderOnVO;
		session.merge(detail);
		session.getTransaction().commit();
		return stockHolderOnVO;
	}	
	
	/**
	 * 保存股票的持有信息.
	 * @param stockHolderOnVO
	 */
	public void doCreate(StockHolderOnVO stockHolderOnVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(stockHolderOnVO);
		session.getTransaction().commit();
	}
	
	/**
	 * 保存新的股票持有信息.
	 * @param stockCode 股票持有信息的流水号，而不是股票代码！ 
	 * @return
	 */
	public StockHolderOnVO getStockHoldOnDetail(int holdOnId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StockHolderOnVO detail = (StockHolderOnVO) session.get(StockHolderOnVO.class, holdOnId);
		session.getTransaction().commit();
		return  detail;
	} 
	
	/****************下面关于股票的配置操作部分******************/
	/**
	 * 保存新的股票持有信息.
	 * @param stockCode 股票持有信息的流水号，而不是股票代码！ 
	 * @return
	 */
	public StockConfigVO getStockConfigDetail(int id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StockConfigVO detail = (StockConfigVO) session.get(StockConfigVO.class, id);
		session.getTransaction().commit();
		return  detail;
	} 
}
