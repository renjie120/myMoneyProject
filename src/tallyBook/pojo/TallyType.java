package tallyBook.pojo;

/**
 * ����ķ�����Ϣ.
 * @author renjie120
 * 419723443@qq.com
 */
public class TallyType {
	/**
	 * С�����ˮ��
	 */
	private int tallyTypeSno;
	/**
	 * �������
	 */
	private String tallyTypeDesc;
	/**
	 * ���ڵĴ���,��֧����������?
	 */
	private String moneyType;
	/**
	 * �ϼ����ͱ���
	 */
	private String parentCode;
	/**
	 * ���ͱ���
	 */
	private String typeCode;
	/**
	 * �������ܽ�
	 */
	private String sumMoney;
	/**
	 * ������ƽ����
	 */
	private String avgMoney;
	public int getTallyTypeSno() {
		return tallyTypeSno;
	}
	public void setTallyTypeSno(int tallyTypeSno) {
		this.tallyTypeSno = tallyTypeSno;
	}
	public String getTallyTypeDesc() {
		return tallyTypeDesc;
	}
	public void setTallyTypeDesc(String tallyTypeDesc) {
		this.tallyTypeDesc = tallyTypeDesc;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}
	public String getAvgMoney() {
		return avgMoney;
	}
	public void setAvgMoney(String avgMoney) {
		this.avgMoney = avgMoney;
	}	
}
