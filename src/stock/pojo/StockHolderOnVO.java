package stock.pojo;

import java.util.Date;

import myOwnLibrary.util.Util;

/**
 * ��Ʊ����ʵ����.
 * �������е�ǰ�����ͳ�ƣ�
 * @author renjie120 419723443@qq.com
 *
 */
public class StockHolderOnVO {
	/**
	 *  ��ˮ��
	 */
	private int holdOnSno;
	/**
	 * ��Ʊ��
	 */
	private String stockCode;
	/**
	 * ��Ʊ����
	 */
	private String stockName;
	/**
	 * �ɱ��۸�
	 */
	private double costPrice; 
	/**
	 * ���׹���
	 */
	private int stockNum;
	/**
	 * ����������
	 */
	private double dealFee;
	/**
	 * ����ʱ��
	 */
	private Date fitstBuyTime;
	/**
	 * ����ʱ��
	 */
	private Date sellTime;
	/**
	 * ӡ��˰
	 */
	private double dealTax;   
	/**
	 * �����۸�����г��۸�
	 */
	private double realPrice;
	/**
	 * ����ٷֱ�
	 */
	private double profit;   
	/**
	 * ������
	 */
	private double profitMoney;
	/**
	 * �ɱ����
	 */
	private double costMoney;
	/**
	 * ��ֵ����������
	 */
	private double marketMoney;
	/**
	 * �������ͣ������������
	 */
	private String dealType; 
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("��Ʊ:").append(this.getStockCode()).append("\n");
		builder.append("��Ʊ����:").append(this.getStockName()).append("\n");
		builder.append("���׼۸�:").append(this.getCostPrice()).append("\n");
		builder.append("��Ʊ��Ŀ:").append(this.getStockNum()).append("\n");
		builder.append("����������:").append(this.getDealFee()).append("\n");
		builder.append("ӡ��˰:").append(this.getDealTax()).append("\n");
		builder.append("����ʱ��:").append(Util.dateToStr(this.getFitstBuyTime(),"yyyy-MM-dd")).append("\n");
		if(this.getSellTime()!=null)
			builder.append("����ʱ��:").append(Util.dateToStr(this.getSellTime(),"yyyy-MM-dd")).append("\n");
		builder.append("�г��۸�:").append(this.getRealPrice()).append("\n");
		builder.append("������:").append(this.getProfitMoney()).append("\n");
		builder.append("������:").append(this.getProfit()).append("\n");
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
	 * ���ر����۸�
	 * @return
	 */
	public double getCostMoney() {
		return costMoney;
	}
	/**
	 * ���ñ����۸�.
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
