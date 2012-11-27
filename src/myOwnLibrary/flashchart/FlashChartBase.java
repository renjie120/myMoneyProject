package myOwnLibrary.flashchart;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: FlashChartBase
 * @Description: ��������,�ṩһЩ�����ķ���.���е���ص��඼�м��ɴ���!
 * @author 419723443@qq.com
 * @date Jan 20, 2010 8:23:00 PM
 * 
 */
public class FlashChartBase {
	/** �ַ��� */
	private StringBuffer buf;
	/** �޸ĵ����Լ��� */
	private Set registerAtrributes;

	public FlashChartBase() {
		buf = new StringBuffer();
		registerAtrributes = new HashSet();
	}

	public Set getRegisterAtrributes() {
		return registerAtrributes;
	}

	public FlashChartBase addValue(String v) {
		buf.append("'").append(v.toString()).append("' ");
		return this;
	}

	public FlashChartBase addValue(boolean b) {
		buf.append("'").append(b ? "1" : "0").append("' ");
		return this;
	}

	public FlashChartBase addValue(Object o) {
		buf.append("'").append(o.toString()).append("' ");
		return this;
	}

	public FlashChartBase addValue(int i) {
		buf.append("'").append(i).append("' ");
		return this;
	}

	public FlashChartBase add(Object o) {
		buf.append(o.toString());
		return this;
	}

	public FlashChartBase add(String s) {
		buf.append(s);
		return this;
	}

	public String getString() {
		return buf.toString();
	}

	public StringBuffer getStringBuffer() {
		return buf;
	}
}
