package tallyBook.pojo;

public class BasePojo {
	/**
	 * ������
	 */
	private String sortName;
	/**
	 * ����ģʽ
	 */
	private String sortType;
	/**
	 * ��ʼ����
	 */
	private String startCount;
	/**
	 * ��ֹ����
	 */
	private String endCount;
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getStartCount() {
		return startCount;
	}
	public void setStartCount(String startCount) {
		this.startCount = startCount;
	}
	public String getEndCount() {
		return endCount;
	}
	public void setEndCount(String endCount) {
		this.endCount = endCount;
	}
}
