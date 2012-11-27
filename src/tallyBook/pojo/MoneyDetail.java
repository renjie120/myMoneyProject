package tallyBook.pojo;

import java.util.Date;


/**
 * 记录收入支出的详细信息.
 * @author renjie120
 * 419723443@qq.com
 */
public class MoneyDetail extends BasePojo{
	/**
	 * 记录流水号
	 */
	private int moneySno;
	/**
	 * 账本类型.
	 */
	private String booktype;
	private int splitMonths;
	/**
	 * 查询记录的最大金额限制
	 */
	private String maxmoney;
	/**
	 * 查询金额的最小时间限制
	 */
	private String minTime;
	/**
	 * 查询金额的最大时间限制
	 */
	private String maxTime;
	/**
	 * 消费卡
	 */
	private int shopCard;
	/**
	 * 拆分流水号.
	 */
	private Integer splitNo;
	private String splitNoStr;
	/**
	 * 消费卡信息
	 */
	private String shopCardStr;
	/**
	 * 收入或者支出
	 */
	private String inOrOutTypeValue;
	private int rowNum;
	/**
	 * 记录时间
	 */
	private Date moneyTime;
	/**
	 * 金额
	 */
	private double money;
	/**
	 * 类别
	 */
	private String moneyType;
	/**
	 * 类别描述
	 */
	private String moneyTypeDesc;
	/**
	 * 记录描述
	 */
	private String moneyDesc;
	/**
	 * 收入或者支出
	 */
	private String inorout;
	public String getInorout() {
		return inorout;
	}
	public void setInorout(String inorout) {
		this.inorout = inorout;
	}
	public int getMoneySno() {
		return moneySno;
	}
	public void setMoneySno(int moneySno) {
		this.moneySno = moneySno;
	}
	public Date getMoneyTime() {
		return moneyTime;
	}
	public void setMoneyTime(Date moneyTime) {
		this.moneyTime = moneyTime;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getMoneyDesc() {
		return moneyDesc;
	}
	public void setMoneyDesc(String moneyDesc) {
		this.moneyDesc = moneyDesc;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getMaxmoney() {
		return maxmoney;
	}
	public void setMaxmoney(String maxmoney) {
		this.maxmoney = maxmoney;
	}
	public String getMinTime() {
		return minTime;
	}
	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}
	public String getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}
	public String getInOrOutTypeValue() {
		return inOrOutTypeValue;
	}
	public void setInOrOutTypeValue(String inOrOutTypeValue) {
		this.inOrOutTypeValue = inOrOutTypeValue;
	}
	public String getMoneyTypeDesc() {
		return moneyTypeDesc;
	}
	public void setMoneyTypeDesc(String moneyTypeDesc) {
		this.moneyTypeDesc = moneyTypeDesc;
	}
	public int getShopCard() {
		return shopCard;
	}
	public void setShopCard(int shopCard) {
		this.shopCard = shopCard;
	}
	public String getShopCardStr() {
		return shopCardStr;
	}
	public void setShopCardStr(String shopCardStr) {
		this.shopCardStr = shopCardStr;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public int getSplitMonths() {
		return splitMonths;
	}
	public void setSplitMonths(int splitMonths) {
		this.splitMonths = splitMonths;
	}
	public Integer getSplitNo() {
		return splitNo;
	}
	public void setSplitNo(Integer splitNo) {
		this.splitNo = splitNo;
	}
	public String getSplitNoStr() {
		return splitNoStr;
	}
	public void setSplitNoStr(String splitNoStr) {
		this.splitNoStr = splitNoStr;
	}
}
