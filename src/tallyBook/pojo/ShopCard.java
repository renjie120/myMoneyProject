package tallyBook.pojo;

/**
 * ����;��.
 * @author renjie120 419723443@qq.com
 *
 */
public class ShopCard {
	/**
	 * ��ˮ��
	 */
	private Integer cardSno;
	/**
	 * ���ÿ�����
	 */
	private String cardDesc;
	/**
	 * ���ڻ�����
	 */
	private Integer cardDeadLine;
	/**
	 * ���ÿ�����
	 */
	private String cardNo;
	/**
	 * ���ÿ����ѽ��
	 */
	private double sumMoney;
	/**
	 * ���ÿ�ʣ����
	 */
	private Double retainMoney;
	/**
	 * ���ÿ��д����
	 */
	private Double cardMoney;
	/**
	 * ��վ����
	 */
	private String cardUrl;
	/**
	 * �ۼ����Ѵ���
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
