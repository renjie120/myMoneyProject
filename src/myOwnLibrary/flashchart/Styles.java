package myOwnLibrary.flashchart;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: Styles 
* @Description: Styles��ǩ
* @author 419723443@qq.com
* @date Jan 22, 2010 2:15:40 PM 
*  
*/
public class Styles extends FlashChartBase implements IBaseTag, IComplicateTag {
	private List definitions;
	private List applications;
	/**
	 * ����ӱ�ǩDefinition
	 * @param definition
	 */
	public void addDefinition(Definition definition) {
		if (definitions == null)
			definitions = new ArrayList();
		definitions.add(definition);
	}
	/**
	 * ����ӱ�ǩApplication
	 * @param application
	 */
	public void addApplication(Application application) {
		if (applications == null)
			applications = new ArrayList();
		applications.add(application);
	}
	public FlashChartBase endTag() {
		getStringBuffer().append("</styles>");
		return this;
	}

	public FlashChartBase end() {
		getStringBuffer().append(">");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append("<styles ");
		return this;
	}

	public String toString(){
		start();
		end();
		if (definitions != null) {
			for (int i = 0, j = definitions.size(); i < j; i++) {
				this.add(definitions.get(i));
			}
		}
		return this.endTag().getString();
	}
}
