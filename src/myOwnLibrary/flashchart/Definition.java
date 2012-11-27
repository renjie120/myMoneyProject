package myOwnLibrary.flashchart;

import java.util.List;

/** 
* @ClassName: Definition 
* @Description: Definition±êÇ©
* @author 419723443@qq.com
* @date Jan 22, 2010 2:27:46 PM 
*  
*/
public class Definition extends FlashChartBase implements IBaseTag, IComplicateTag{
	private List styles;
	public FlashChartBase endTag() {
		getStringBuffer().append(">");
		return this;
	}

	public FlashChartBase end() {
		getStringBuffer().append(">");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append(">");
		return this;
	}
	public String toString(){
		start();
		end();
		if (styles != null) {
			for (int i = 0, j = styles.size(); i < j; i++) {
				this.add(styles.get(i));
			}
		}
		return this.endTag().getString();
	}
}
