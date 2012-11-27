package myOwnLibrary;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public class TestTimeTask extends TimerTask{ 
	private Logger log = Logger.getLogger("qqq"); 
	@Override
	public void run() {
		//System.out.println("当前时间："+System.currentTimeMillis());
	}
}
