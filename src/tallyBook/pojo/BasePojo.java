package tallyBook.pojo;

public class BasePojo {
	/**
	 * 排序列
	 */
	private String sortName;
	/**
	 * 排序模式
	 */
	private String sortType;
	/**
	 * 起始行数
	 */
	private String startCount;
	/**
	 * 终止行数
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
