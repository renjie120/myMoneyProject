package tallyBook.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myOwnLibrary.util.Util;

import org.apache.struts2.ServletActionContext;

import tallyBook.dao.DaoUtil;

import common.base.SpringContextUtil;

/**
 * 股票交易信息表格树.
 */
public class StockDetailAction {
	private DaoUtil dao = null;

	public String getTypeTree() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		dao = (DaoUtil) SpringContextUtil.getBean("daoUtil");
		List ansList = dao
				.doGetSqlQueryList("select t.hold_on_sno,  t.stock_no,  t.stock_price,   "
						+ "t.deal_num, t.deal_fee,        t.deal_tax,     t.deal_type,    t.stock_real_price,   "
						+ "t.profit, t.profit_money   from stock_hold_on_t2 t");
		List<StockTreeNode> ans = new ArrayList<StockTreeNode>();
		try {
			if (ansList != null) {
				Iterator it = ansList.iterator();
				while (it.hasNext()) {
					Object[] objs = (Object[]) it.next();
					StockTreeNode stockHoldOn = new StockTreeNode();
					stockHoldOn.setId("" + objs[0]);
					stockHoldOn.setStockNo("" + objs[1]);
					stockHoldOn.setStockPrice("" + objs[2]);
					stockHoldOn.setNum("" + objs[3]);
					stockHoldOn.setFee("" + objs[4]);
					stockHoldOn.setTax("" + objs[5]);
					stockHoldOn.setProfit("" + objs[8]);
					stockHoldOn.setProfitSum("" + objs[9]);
					stockHoldOn.setType("" + objs[6]);
					stockHoldOn.setIsLeaf("1");
					ans.add(stockHoldOn);
				}
			}
			String jsonStr = "{total:" + ansList.size() + ",page:1,data:["
					+ list2json(ans) + "]}";
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTypeChildTree() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("pId");

		try {
			String jsonStr = "[" + list2json(getDetailByHoldOn(id)) + "]";
			response.setContentType("text/html;charset=GBK");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询关联的交易详情.
	 * 
	 * @param holdOn
	 * @return
	 */
	public List getDetailByHoldOn(String holdOn) {
		dao = (DaoUtil) SpringContextUtil.getBean("daoUtil");
		List ansList = dao
				.doGetSqlQueryList("select hold.hold_on_sno, t.deal_stocksno stock_no, t.deal_price stock_price, "
						+ "t.deal_num, 0 deal_fee, 0 deal_tax, t.deal_type, t.deal_price stock_real_price, 0 profit, "
						+ "0 profit_money from stock_deal_t2 t, stock_hold_on_t2 hold where t.group_num = "
						+ "hold.hold_on_sno and hold.hold_on_sno = " + holdOn);
		List<StockTreeNode> ans = new ArrayList<StockTreeNode>();
		if (ansList != null) {
			Iterator it = ansList.iterator();
			while (it.hasNext()) {
				Object[] objs = (Object[]) it.next();
				StockTreeNode stockHoldOn = new StockTreeNode();
				stockHoldOn.setId("" + objs[0]);
				stockHoldOn.setPId(holdOn);
				stockHoldOn.setStockNo("" + objs[1]);
				stockHoldOn.setStockPrice("" + objs[2]);
				stockHoldOn.setNum("" + objs[3]);
				stockHoldOn.setFee("" + objs[4]);
				stockHoldOn.setTax("" + objs[5]);
				stockHoldOn.setProfit("" + objs[8]);
				stockHoldOn.setProfitSum("" + objs[9]);
				stockHoldOn.setIsLeaf("0");
				stockHoldOn.setType("" + objs[6]);
				ans.add(stockHoldOn);
			}
		}
		return ans;
	}

	public String list2json(List<StockTreeNode> nodes) {
		StringBuffer buf = new StringBuffer();
		if (nodes != null && nodes.size() > 0) {
			Iterator<StockTreeNode> it = nodes.iterator();
			while (it.hasNext()) {
				StockTreeNode node = it.next();
				buf.append("{").append(Util.getJsonFromObject(node)).append(
						"},");
			}
		}
		if (buf.length() > 0)
			buf = buf.deleteCharAt(buf.length() - 1);
		return buf.toString();
	}

}
