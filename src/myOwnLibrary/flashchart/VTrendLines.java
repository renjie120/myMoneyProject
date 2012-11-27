package myOwnLibrary.flashchart;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: VTrendLines
 * @Description: VTrendLines±Í«©
 * @author 419723443@qq.com
 * @date Jan 22, 2010 2:55:43 PM
 * 
 */
public class VTrendLines extends FlashChartBase implements IBaseTag,
		IComplicateTag {
	private List lines;

	public VTrendLines addLine(Line line) {
		if (lines == null)
			lines = new ArrayList();
		lines.add(line);
		return this;
	}

	public FlashChartBase endTag() {
		getStringBuffer().append("</vTrendLines>");
		return this;
	}

	public FlashChartBase end() {
		getStringBuffer().append(">");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append("<vTrendLines ");
		return this;
	}

	public String toString() {
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
