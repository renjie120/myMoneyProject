package tallyBook.pojo;


/**
 * 收支大类别.
 * @author renjie120
 * 419723443@qq.com
 */
public class MoneyType {
	/**
	 * 流水号 
	 */
	private String typeId;
	/**
	 * 描述
	 */
	private String typeDesc;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
}
