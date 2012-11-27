package stock.pojo;

import java.util.Date;

public class StockVO {
	public StockVO(){}
	/**
	 * 股票交易初始化函数
	 * @param dealStockCode 股票代码
	 * @param dealStockName 股票名称
	 * @param dealPrice 交易价格
	 * @param dealStockNum 交易股数
	 * @param dealTime 交易时间
	 */
	public StockVO(String dealStockCode,String dealStockName,double dealPrice,int dealStockNum,Date dealTime ){
		this.dealStockCode = dealStockCode;
		this.dealStockName = dealStockName;
		this.dealPrice = dealPrice;
		this.dealStockNum = dealStockNum;
		this.dealTime = dealTime;
	}
	/**
	 *  流水号
	 */
	private int dealSno;
	/**
	 * 股票号
	 */
	private String dealStockCode;
	/**
	 * 交易价格
	 */
	private double dealPrice;
	/**
	 * 股票名称
	 */
	private String dealStockName;
	/**
	 * 交易股数
	 */
	private int dealStockNum;
	/**
	 * 交易手续费
	 */
	private double dealFee;
	/**
	 * 交易时间
	 */
	private Date dealTime;
	/**
	 * 印花税
	 */
	private double dealTax;
	/**
	 * 交易心得
	 */
	private String dealIdea;
	/**
	 * 满意度
	 */
	private int happyNum;
	/**
	 * 希望的成交价格
	 */
	private double dealTinkPrice;
	/**
	 * 交易类型，买入或者卖出
	 */
	private String dealType;
	/**
	 * 交易的使用的费用计算方式.
	 */
	private int configType;  
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("股票:").append(this.getDealStockCode()).append("\n");
		builder.append("交易价格:").append(this.getDealPrice()).append("\n");
		builder.append("股票数目:").append(this.getDealStockNum()).append("\n");
		builder.append("交易手续费:").append(this.getDealFee()).append("\n");
		builder.append("印花税:").append(this.getDealTax()).append("\n");
		builder.append("买入时间:").append(this.getDealTime()).append("\n");
		builder.append("市场价格:").append(this.getDealPrice()).append("\n");
		return builder.toString();
	}
	public String getDealStockCode() {
		return dealStockCode;
	}
	public void setDealStockCode(String dealStockCode) {
		this.dealStockCode = dealStockCode;
	}
	public double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getDealStockName() {
		return dealStockName;
	}
	public void setDealStockName(String dealStockName) {
		this.dealStockName = dealStockName;
	}
	public int getDealStockNum() {
		return dealStockNum;
	}
	public void setDealStockNum(int dealStockNum) {
		this.dealStockNum = dealStockNum;
	}
	public double getDealFee() {
		return dealFee;
	}
	public void setDealFee(double dealFee) {
		this.dealFee = dealFee;
	}  
	public double getDealTax() {
		return dealTax;
	}
	public void setDealTax(double dealTax) {
		this.dealTax = dealTax;
	}
	public String getDealIdea() {
		return dealIdea;
	}
	public void setDealIdea(String dealIdea) {
		this.dealIdea = dealIdea;
	}
	public int getHappyNum() {
		return happyNum;
	}
	public void setHappyNum(int happyNum) {
		this.happyNum = happyNum;
	}
	public double getDealTinkPrice() {
		return dealTinkPrice;
	}
	public void setDealTinkPrice(double dealTinkPrice) {
		this.dealTinkPrice = dealTinkPrice;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public int getConfigType() {
		return configType;
	}
	public void setConfigType(int configType) {
		this.configType = configType;
	}
	public int getDealSno() {
		return dealSno;
	}
	public void setDealSno(int dealSno) {
		this.dealSno = dealSno;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
}
