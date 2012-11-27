package tallyBook.pojo;

import java.util.Date;
 

public class DiaryDetail {
	private String content;
	private Date time;
	private String diaryType;
	private int diarySno;
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
	public String getDiaryType() {
		return diaryType;
	}
	public void setDiaryType(String diaryType) {
		this.diaryType = diaryType;
	}
	public int getDiarySno() {
		return diarySno;
	}
	public void setDiarySno(int diarySno) {
		this.diarySno = diarySno;
	}
}
