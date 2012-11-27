package myOwnLibrary.flashchart;

public class Apply extends FlashChartBase implements IBaseTag{

	public FlashChartBase end() {
		getStringBuffer().append("/>");
		return this;
	}

	public FlashChartBase start() {
		getStringBuffer().append("<apply ");
		return this;
	}
	public String toString(){
		start();
		end();
		return getString();
	}
}
