package stock.bo;

import java.util.Date;

import myOwnLibrary.util.Util;
import stock.pojo.StockConfigVO;
import stock.pojo.StockHolderOnVO;
import stock.pojo.StockVO;
import stock.dao.StockDao;
/**
 * 关于股票的持有信息的运算类。
 * 包括：开仓（新增股票），清仓（卖出股票），加仓，减仓等
 * @author renjie120 419723443@qq.com
 *
 */
public class StockUtil { 
	public static void main(String[] a){
		StockHolderOnVO stockHolderOnVO = new StockHolderOnVO();
		StockConfigVO buyConfig = new StockConfigVO(1,0.001,0,5);
		StockConfigVO sellConfig = new StockConfigVO(2,0.001,0.001,5);
		 
		StockVO stockVO = new StockVO("test","test",13.18,1200,new Date());
		StockUtil.bindStockConfig(stockVO, buyConfig);
		stockHolderOnVO = StockUtil.addStock(stockHolderOnVO, stockVO); 
		 
		StockVO stockVO2 = new StockVO("test","test",13.97,400,new Date());
		StockUtil.bindStockConfig(stockVO2, sellConfig);
		stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO2); 
		
		StockVO stockVO3 = new StockVO("test","test",14.15,400,new Date());
		StockUtil.bindStockConfig(stockVO3, sellConfig);
		stockHolderOnVO = StockUtil.reduceStock(stockHolderOnVO, stockVO3); 
		
		StockVO stockVO4 = new StockVO("test","test",14.20,400,new Date());
		StockUtil.bindStockConfig(stockVO4, sellConfig);
		System.out.println(StockUtil.reduceStock(stockHolderOnVO, stockVO4));
	}
	
	
	/**
	 * 设置费率
	 * @param stockVO
	 * @param config
	 */
	public static void bindStockConfig(StockVO stockVO,StockConfigVO config){
		double buyPrice = stockVO.getDealPrice();
		int buyNum = stockVO.getDealStockNum(); 
		double marketMoney =  Util.multiply(buyPrice, buyNum); 
		double stockFee= Util.multiply(marketMoney, config.getStockFee());
		stockFee = stockFee>config.getMinFee()?stockFee:config.getMinFee();
		double taxFee = Util.multiply(marketMoney, config.getTaxFee());
		//设置交易手续费
		stockVO.setDealFee(stockFee);
		//设置印花税
		stockVO.setDealTax(taxFee);
	}
	
	/**
	 * 买入股票
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	public static StockHolderOnVO addStock(StockHolderOnVO stockHolderOnVO,StockVO stockVO){  
		double buyPrice = stockVO.getDealPrice();
		int buyNum = stockVO.getDealStockNum();
		//收益金额
		double profitMoney;
		//收益率
		double profit;
		//买入纯价值
		double marketMoney =  Util.multiply(buyPrice, buyNum); 
		//统计手续费
		stockHolderOnVO.setDealFee(getDealFee(stockHolderOnVO,stockVO));
		//统计印花税
		stockHolderOnVO.setDealTax(getDealTax(stockHolderOnVO,stockVO)); 
		//计算持有股数
		stockHolderOnVO.setStockNum((int)Util.add(stockHolderOnVO.getStockNum(), stockVO.getDealStockNum()));
		//计算买入的股票全部成本
		stockHolderOnVO.setCostMoney(Util.add(Util.add(Util.add(stockHolderOnVO.getCostMoney(), marketMoney),stockVO.getDealFee()),stockVO.getDealTax()));
		//计算买入的平均价格
		stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockHolderOnVO.getStockNum() ,4));
		//计算市场价格
		stockHolderOnVO.setRealPrice(stockVO.getDealPrice());
		//计算市场价值
		stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockHolderOnVO.getStockNum()));
		//如果持有股票信息中的买入时间为空,说明是新建仓的股票!要设置第一次买入时间!
		if(stockHolderOnVO.getFitstBuyTime()==null){
			//设置建仓时间,股票号码,股票名称
			stockHolderOnVO.setFitstBuyTime(stockVO.getDealTime());
			stockHolderOnVO.setDealType(StockDao.BUY);
			stockHolderOnVO.setStockCode(stockVO.getDealStockCode());
			stockHolderOnVO.setStockName(stockVO.getDealStockName());
			//建仓的收益金额就是损失的手续费和印花税!!
			profitMoney = 0.0-stockHolderOnVO.getDealFee()-stockHolderOnVO.getDealTax(); 
		}
		else{
			profitMoney = Util.subtract(stockHolderOnVO.getMarketMoney(),stockHolderOnVO.getCostMoney()); 
		}
		profit = Util.divide(profitMoney, stockHolderOnVO.getCostMoney(),4);		
		stockHolderOnVO.setProfit(profit); 
		stockHolderOnVO.setProfitMoney(profitMoney);
		return stockHolderOnVO;
	}
	 
	/**
	 * 卖出股票
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	public static StockHolderOnVO reduceStock(StockHolderOnVO stockHolderOnVO,StockVO stockVO){  
		double sellPrice = stockVO.getDealPrice();
		int sellNum = stockVO.getDealStockNum();
		//卖出价值
		double marketMoney =  Util.multiply(sellNum, sellPrice); 
		//统计手续费		
		stockHolderOnVO.setDealFee(getDealFee(stockHolderOnVO,stockVO));
		//统计印花税
		stockHolderOnVO.setDealTax(getDealTax(stockHolderOnVO,stockVO)); 
		//计算股数
		stockHolderOnVO.setStockNum((int)Util.subtract(stockHolderOnVO.getStockNum(), stockVO.getDealStockNum()));
		//收益金额: 持仓市场价值-持仓的股票成本
		double profitMoney;
		//收益率
		double profit;
		//如果剩余股票数目为0,说明已经全部卖出去了.设置卖出时间, 
		if(stockHolderOnVO.getStockNum()==0){
			stockHolderOnVO.setSellTime(stockVO.getDealTime()); 
			stockHolderOnVO.setDealType(StockDao.SELL);
			//清仓情况下,股票的成本:   已经持有的成本+手续费+印花税
			stockHolderOnVO.setCostMoney(Util.add(Util.add(stockHolderOnVO.getCostMoney(), stockVO.getDealFee()),stockVO.getDealTax()));
			//计算持有股票的平均价格: 持仓股票的成本/卖出的股数
			stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockVO.getDealStockNum() ,4));
			//计算持仓股票的市场价值: 市场价格*卖出的股数
			stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockVO.getDealStockNum()));
		}
		else{
			//计算持仓的股票的成本:   已经持有的成本-卖出的市场价值+手续费+印花税
			stockHolderOnVO.setCostMoney(Util.add(Util.add(Util.subtract(stockHolderOnVO.getCostMoney(), marketMoney),stockVO.getDealFee()),stockVO.getDealTax()));
			//计算持有股票的平均价格: 持仓股票的成本/持有股数
			stockHolderOnVO.setCostPrice(Util.divide(stockHolderOnVO.getCostMoney(),stockHolderOnVO.getStockNum() ,4));
			//计算持仓股票的市场价值: 市场价格*持有股数
			stockHolderOnVO.setMarketMoney(Util.multiply(stockVO.getDealPrice(), stockHolderOnVO.getStockNum()));
		}
		profitMoney =Util.subtract(stockHolderOnVO.getMarketMoney(),stockHolderOnVO.getCostMoney());
		profit = Util.divide(profitMoney, stockHolderOnVO.getCostMoney(),4);
		stockHolderOnVO.setSellTime(stockVO.getDealTime()); 
		stockHolderOnVO.setProfit(profit);  
		stockHolderOnVO.setProfitMoney(profitMoney);
		stockHolderOnVO.setRealPrice(stockVO.getDealPrice()); 
		return stockHolderOnVO;
	}  
	
	/**
	 * 计算交易的手续费
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	private static double getDealFee(StockHolderOnVO stockHolderOnVO,StockVO stockVO){
		return Util.add(stockHolderOnVO.getDealFee(), stockVO.getDealFee());
	}
	
	/**
	 * 计算交易的印花税
	 * @param stockHolderOnVO
	 * @param stockVO
	 * @return
	 */
	private static double getDealTax(StockHolderOnVO stockHolderOnVO,StockVO stockVO){
		return Util.add(stockHolderOnVO.getDealTax(), stockVO.getDealTax());
	}  
}
