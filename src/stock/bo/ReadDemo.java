package stock.bo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ReadDemo {
	public static void main(String argv[]) throws IOException {
		String _str = "http://hq.sinajs.cn/list=sh600000,sh600016"; 
//		Connection conn = Jsoup.connect(_str);
//		Document doc = conn.get();
//		System.out.println(doc);
//		System.out.println(doc.select("tr.current_price").select("td").get(1).text());
		try {
			URL url = new URL( _str);
			BufferedReader in = new BufferedReader(new InputStreamReader(url
					.openStream(),"gbk"));
			String str;
			while ((str = in.readLine()) != null)
				System.out.println(str.substring(str.indexOf("\"")+1));
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		
		ScriptEngineManager mgr = new ScriptEngineManager(); 
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String js = "function doSwing(t){var f=new Packages.javax.swing.JFrame(t);f.setSize(400,300);f.setVisible(true);}";   
	    try {
			engine.eval(js);
			 // Invocable �ӿ�: ����javaƽ̨���ýű������еĺ����򷽷�   
		    Invocable inv = (Invocable) engine;   
		    // invokeFunction()�еĵ�һ���������Ǳ����õĽű������еĺ������ڶ��������Ǵ��ݸ������ú����Ĳ�����   
		    inv.invokeFunction("doSwing", "Scripting Swing");  
		    
		    String script = "function helloFunction(name) { return 'Hello everybody,' + name;}";   
		    engine.eval(script);    
		    String res = (String) inv.invokeFunction("helloFunction", "Scripting");   
		    System.out.println("res:" + res);   
		    
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	   
	}
}