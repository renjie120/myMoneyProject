package tallyBook.pojo;

/**
 * 具体的分类信息.
 * @author renjie120
 * 419723443@qq.com
 */
public class TallyType {
	/**
	 * 小类别流水号
	 */
	private int tallyTypeSno;
	/**
	 * 类别描述
	 */
	private String tallyTypeDesc;
	/**
	 * 属于的大类,是支出还是收入?
	 */
	private String moneyType;
	/**
	 * 上级类型编码
	 */
	private String parentCode;
	/**
	 * 类型编码
	 */
	private String typeCode;
	/**
	 * 该类型总金额。
	 */
	private String sumMoney;
	/**
	 * 该类型平均金额。
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
