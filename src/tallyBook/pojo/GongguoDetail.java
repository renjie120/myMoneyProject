package tallyBook.pojo;

import java.util.Date;

 /**
  * 功过类型.
  * @author renjie120 419723443@qq.com
  *
  */
public class GongguoDetail {
	private String sno;
	private String content; 
	private Date time;
	private String desc;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}  
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	} 
}
