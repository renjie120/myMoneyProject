package myOwnLibrary.util;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;

/**
 * ���Ե��÷�run�ķ�����ָ������������������
 * ʹ��ClassLoader�ķ�ʽִ��groovy�ķ���.
 * @author Amarsoft
 *
 */
public class GroovyUtil2 {

	private GroovyObject groovyObject;

	public Object getProperty(String name) { 
		return groovyObject.getProperty(name);
	}

	public Object invokeScriptMethod(String scriptName, String methodName,
			Object[] args) {
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		try {
			Class groovyClass = loader.parseClass(new File(scriptName));
			groovyObject = (GroovyObject) groovyClass.newInstance();
			return groovyObject.invokeMethod(methodName, args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		GroovyUtil2 dynamicGroovy = new GroovyUtil2();
		Object[] params = { "ffff","a,s,c" };
		Object result = dynamicGroovy.invokeScriptMethod("src/tallyBook/action/BudgetGroovy.groovy",
				"runtest", params);
		System.out.println(result); 

	}
}