package myOwnLibrary.util;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.File;

/**
 * 调用默认的run方法，只可以指定参数。
 * 使用shell执行脚本的方式调用groovy的方法!
 * @author Amarsoft
 *
 */
public class GroovyUtil { 
	private Binding binding = new Binding();

	public Object getProperty(String name) {
		return binding.getProperty(name);
	}

	public void setParameters(String[] paramNames, Object[] paramValues) {
		int len = paramNames.length;
		if (len != paramValues.length) {
			System.out.println("parameters not match!");
		}

		for (int i = 0; i < len; i++) {
			binding.setProperty(paramNames[i], paramValues[i]);
		}
	}

	public Object runScript(String scriptName) {
		GroovyShell shell = new GroovyShell(binding);
		try {
			return shell.evaluate(new File(scriptName));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		GroovyUtil embedGroovy = new GroovyUtil();
		String[] paramNames = { "name" ,"moneyType"};
		Object[] paramValues = { "testname","1,2,3" };
		embedGroovy.setParameters(paramNames, paramValues);
		Object result = embedGroovy.runScript("src/tallyBook/action/BudgetGroovy.groovy");
		System.out.println(result);
		System.out.println(embedGroovy.getProperty("name"));
		System.out.println(embedGroovy.getProperty("moneyType"));
	}
}