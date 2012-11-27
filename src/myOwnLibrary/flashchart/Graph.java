package myOwnLibrary.flashchart;

public class Graph extends Chart{
	public Graph(){
		super();
	}
	
	public FlashChartBase endTag() {
		getStringBuffer().append("</graph>");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append("<graph ");
		return this;
	}
}
