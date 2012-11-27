package myOwnLibrary.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * �����������Ĺ�����.
 * @author renjie120 419723443@qq.com
 *
 */
public class UrlClientUtil {
	/**
	 * ʹ��get����ȡ��Զ�̷�����������ִ�н��.
	 * @param urlString
	 * @return
	 */
	public static String getDocumentAt(String urlString){
		StringBuffer document = new StringBuffer();
		try{
			URL url = new URL(urlString);
			//URLConnection ʹ���׽��ִ�����ָ���� URL �ж�ȡ��Ϣ����ֻ�ǽ����� IP ��ַ���������������˽���
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			String line = null;
			while((line = reader.readLine())!=null)
			{
				document.append(line+"\n");
			}
			reader.close();
		}
		catch(MalformedURLException e){
			System.out.println("���������ӵ���ַ��"+urlString);
			e.printStackTrace();
		}
		catch(IOException e){
			System.out.println("���ӵ���ַʱ����IO�쳣��"+urlString);
			e.printStackTrace();
		}
		return document.toString();
	}
}
