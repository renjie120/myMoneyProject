package myOwnLibrary.taglib;

/**
 * �����Զ��������˵���voʵ����.
 * @author renjie120 419723443@qq.com
 *
 */
public class Option {
	private String id;
	private String name;
	private String other;
	public Option(String id,String name){
		this.id = id;
		this.name = name;
	}
	public Option(){}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}
