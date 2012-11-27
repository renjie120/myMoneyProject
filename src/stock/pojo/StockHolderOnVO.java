package stock.pojo;

import java.util.Date;

import myOwnLibrary.util.Util;

/**
 * 股票持有实体类.
 * 用来进行当前收益的统计！
 * @author renjie120 419723443@qq.com
 *
 */
public class StockHolderOnVO {
	/**
	 *  流水号
	 */
	private int holdOnSno;
	/**
	 * 股票号
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 成本价格
	 */
	private double costPrice; 
	/**
	 * 交易股数
	 */
	private int stockNum;
	/**
	 * 交易手续费
	 */
	private double dealFee;
	/**
	 * 交易时间
	 */
	private Date fitstBuyTime;
	/**
	 * 卖出时间
	 */
	private Date sellTime;
	/**
	 * 印花税
	 */
	private double dealTax;   
	/**
	 * 卖出价格或者市场价格
	 */
	private double realPrice;
	/**
	 * 收益百分比
	 */
	private double profit;   
	/**
	 * 收益金额
	 */
	private double profitMoney;
	/**
	 * 成本金额
	 */
	private double costMoney;
	/**
	 * 市值或者卖出价
	 */
	private double marketMoney;
	/**
	 * 交易类型，买入或者卖出
	 */
	private String dealType; 
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("股票:").append(this.getStockCode()).append("\n");
		builder.append("股票名称:").append(this.getStockName()).append("\n");
		builder.append("交易价格:").append(this.getCostPrice()).append("\n");
		builder.append("股票数目:").append(this.getStockNum()).append("\n");
		builder.append("交易手续费:").append(this.getDealFee()).append("\n");
		builder.append("印花税:").append(this.getDealTax()).append("\n");
		builder.append("买入时间:").append(Util.dateToStr(this.getFitstBuyTime(),"yyyy-MM-dd")).append("\n");
		if(this.getSellTime()!=null)
			builder.append("卖出时间:").append(Util.dateToStr(this.getSellTime(),"yyyy-MM-dd")).append("\n");
		builder.append("市场价格:").append(this.getRealPrice()).append("\n");
		builder.append("收益金额:").append(this.getProfitMoney()).append("\n");
		builder.append("收益率:").append(this.getProfit()).append("\n");
		return builder.toString();
	}
	public int getHoldOnSno() {
		return holdOnSno;
	}
	public void setHoldOnSno(int holdOnSno) {
		this.holdOnSno = holdOnSno;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	} 
	public double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public double getDealFee() {
		return dealFee;
	}
	public void setDealFee(double dealFee) {
		this.dealFee = dealFee;
	}
	public Date getFitstBuyTime() {
		return fitstBuyTime;
	}
	public void setFitstBuyTime(Date fitstBuyTime) {
		this.fitstBuyTime = fitstBuyTime;
	}
	public double getDealTax() {
		return dealTax;
	}
	public void setDealTax(double dealTax) {
		this.dealTax = dealTax;
	}
	public double getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(double realPrice) {
		this.realPrice = realPrice;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public double getProfitMoney() {
		return profitMoney;
	}
	public void setProfitMoney(double profitMoney) {
		this.profitMoney = profitMoney;
	}
	/**
	 * 返回保本价格
	 * @return
	 */
	public double getCostMoney() {
		return costMoney;
	}
	/**
	 * 设置保本价格.
	 * @param costMoney
	 */
	public void setCostMoney(double costMoney) {
		this.costMoney = costMoney;
	}
	public double getMarketMoney() {
		return marketMoney;
	}
	public void setMarketMoney(double marketMoney) {
		this.marketMoney = marketMoney;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	} 
}
