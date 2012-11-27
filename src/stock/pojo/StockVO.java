package stock.pojo;

import java.util.Date;

public class StockVO {
	public StockVO(){}
	/**
	 * ��Ʊ���׳�ʼ������
	 * @param dealStockCode ��Ʊ����
	 * @param dealStockName ��Ʊ����
	 * @param dealPrice ���׼۸�
	 * @param dealStockNum ���׹���
	 * @param dealTime ����ʱ��
	 */
	public StockVO(String dealStockCode,String dealStockName,double dealPrice,int dealStockNum,Date dealTime ){
		this.dealStockCode = dealStockCode;
		this.dealStockName = dealStockName;
		this.dealPrice = dealPrice;
		this.dealStockNum = dealStockNum;
		this.dealTime = dealTime;
	}
	/**
	 *  ��ˮ��
	 */
	private int dealSno;
	/**
	 * ��Ʊ��
	 */
	private String dealStockCode;
	/**
	 * ���׼۸�
	 */
	private double dealPrice;
	/**
	 * ��Ʊ����
	 */
	private String dealStockName;
	/**
	 * ���׹���
	 */
	private int dealStockNum;
	/**
	 * ����������
	 */
	private double dealFee;
	/**
	 * ����ʱ��
	 */
	private Date dealTime;
	/**
	 * ӡ��˰
	 */
	private double dealTax;
	/**
	 * �����ĵ�
	 */
	private String dealIdea;
	/**
	 * �����
	 */
	private int happyNum;
	/**
	 * ϣ���ĳɽ��۸�
	 */
	private double dealTinkPrice;
	/**
	 * �������ͣ������������
	 */
	private String dealType;
	/**
	 * ���׵�ʹ�õķ��ü��㷽ʽ.
	 */
	private int configType;  
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("��Ʊ:").append(this.getDealStockCode()).append("\n");
		builder.append("���׼۸�:").append(this.getDealPrice()).append("\n");
		builder.append("��Ʊ��Ŀ:").append(this.getDealStockNum()).append("\n");
		builder.append("����������:").append(this.getDealFee()).append("\n");
		builder.append("ӡ��˰:").append(this.getDealTax()).append("\n");
		builder.append("����ʱ��:").append(this.getDealTime()).append("\n");
		builder.append("�г��۸�:").append(this.getDealPrice()).append("\n");
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
