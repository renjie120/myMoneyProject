package myOwnLibrary.flashchart;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TrendLines
 * @Description: TrendLines±Í«©
 * @author 419723443@qq.com
 * @date Jan 22, 2010 12:31:49 PM
 * 
 */
public class TrendLines extends FlashChartBase implements IBaseTag,
		IComplicateTag {
	private List lines;
	public void addLine(Line line) {
		if (lines == null)
			lines = new ArrayList();
		lines.add(line);
	}
	
	public FlashChartBase endTag() {
		getStringBuffer().append("</trendLines>");
		return this;
	}

	public FlashChartBase end() {
		getStringBuffer().append(">");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append("<trendLines ");
		return this;
	}

	public String toString(){
		start();		
		end();	
		if (lines != null) {
			for (int i = 0, j = lines.size(); i < j; i++) {
				this.add(lines.get(i));
			}
		}
		return this.endTag().getString();
	}
	
}
