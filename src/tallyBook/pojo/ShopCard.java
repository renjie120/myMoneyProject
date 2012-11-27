package tallyBook.pojo;

/**
 * 消费途径.
 * @author renjie120 419723443@qq.com
 *
 */
public class ShopCard {
	/**
	 * 流水号
	 */
	private Integer cardSno;
	/**
	 * 信用卡描述
	 */
	private String cardDesc;
	/**
	 * 到期还款日
	 */
	private Integer cardDeadLine;
	/**
	 * 信用卡卡号
	 */
	private String cardNo;
	/**
	 * 信用卡消费金额
	 */
	private double sumMoney;
	/**
	 * 信用卡剩余金额
	 */
	private Double retainMoney;
	/**
	 * 信用卡中存款金额
	 */
	private Double cardMoney;
	/**
	 * 网站链接
	 */
	private String cardUrl;
	/**
	 * 累计消费次数
	 */
	private int times;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public double getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(double sumMoney) {
		this.sumMoney = sumMoney;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public Integer getCardSno() {
		return cardSno;
	}
	public void setCardSno(Integer cardSno) {
		this.cardSno = cardSno;
	}
	public String getCardDesc() {
		return cardDesc;
	}
	public void setCardDesc(String cardDesc) {
		this.cardDesc = cardDesc;
	}
	public Integer getCardDeadLine() {
		return cardDeadLine;
	}
	public void setCardDeadLine(Integer cardDeadLine) {
		this.cardDeadLine = cardDeadLine;
	}
	public Double getRetainMoney() {
		return retainMoney;
	}
	public void setRetainMoney(Double retainMoney) {
		this.retainMoney = retainMoney;
	}
	public Double getCardMoney() {
		return cardMoney;
	}
	public void setCardMoney(Double cardMoney) {
		this.cardMoney = cardMoney;
	}
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}	
}
