package myOwnLibrary.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myOwnLibrary.mvc.MVCException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 

/**
 * ������.
 * @author wblishq
 *
 */
public class Util {
	private static DecimalFormat df = new DecimalFormat("#######.####");
	public static String notBlank(Object obj){
		if(obj==null)
			return "";
		return obj.toString();
	}
	
	public final static String CONFIG = "struts.xml";
	private static Properties prop = new Properties();
	protected static Log log = LogFactory.getLog("Util");
	//���������Ҫ��oracle�ű��е�to_date('09-02-2010', 'dd-mm-yyyy')ת��Ϊderby��֧�ֵ�date('2010-09-02')!!
	public static void oracleDateToDerbyDate(String fileName) {
		String[] contents = readFileToStrArr(fileName);
		StringBuffer buf = new StringBuffer();
		Pattern pattern = Pattern.compile("date\\('([\\w-]+)'\\)");
		Matcher matcher = null;
		for (int i = 0; i < contents.length; i++) {
			String oneRow = contents[i];
			if (oneRow.indexOf("to_date") != -1) {
				oneRow = oneRow.replace("to_date", "date");
				oneRow = oneRow.replaceAll(",\\s+'dd-mm-yyyy'", "");
				matcher = pattern.matcher(oneRow);
				String dateStr = oneRow;
				if (matcher.find()) {
					dateStr = matcher.group(1);
					buf.append(oneRow.substring(0, matcher.start(1)));
					String[] dd = dateStr.split("-");
					buf.append(dd[2] + "-" + dd[1] + "-" + dd[0]);
					buf.append(oneRow
							.substring(matcher.end(1), oneRow.length()));
				} else {
					buf.append(dateStr);
				}
			} else {
				buf.append(oneRow);
			}
			buf.append("\t\n");
		}
		writeFile(fileName, buf.toString());
	}
	/**
	 * ���ַ���д���ļ�
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param contant
	 *            Ҫд���ļ����ַ���
	 */
	public static void writeFile(String fileName, String contant) {
		PrintWriter out;
		try {
			File file = new File(fileName);
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			out.print(contant);
			out.close();
		} catch (IOException e) {
			System.out.println("��д�ļ������쳣��");
		} catch (Exception e) {
			System.out.println("�����쳣");
		}
	}

	/**
	 * ������д�뵽�ļ��У�������ÿ��Ԫ��Ϊһ��
	 * 
	 * @param fileName
	 * @param rows
	 */
	public static void writeFile(String fileName, Object[] rows) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			for (int temp = 0; temp < rows.length; temp++) {
				out.println(rows[temp]);
			}
			out.close();
		} catch (IOException e) {
			System.out.println("��д�ļ������쳣��");
		} catch (Exception e) {
			System.out.println("�����쳣");
		}
	}

	/**
	 * ��ָ���ļ��е�������ÿ��ת��Ϊ�ַ�������
	 * 
	 * @param fileName
	 * @return
	 */
	public static String[] readFileToStrArr(String fileName) {
		BufferedReader in;
		ArrayList list = new ArrayList();
		String[] result = null;
		try {
			// �����ļ�����������
			in = new BufferedReader(new FileReader(fileName));
			String s;
			while ((s = in.readLine()) != null) {
				list.add(s);
			}
			result = new String[list.size()];
			Iterator it = list.iterator();
			int index = 0;
			while (it.hasNext()) {
				result[index++] = it.next().toString();
			}
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ����ļ���");
			throw new Exception("�ļ��Ҳ�����");
		} catch (IOException e) {
			System.out.println("�����쳣��");
			throw new Exception("�ļ��Ҳ�����");
		} finally {
			return result;
		}
	}

	/**
	 * �����ַ����ķǿ���ʽ������վͷ���""
	 * @param oldStr
	 * @return
	 */
	public static String notBlank(String oldStr){
		if(isBlank(oldStr)){
			return "";
		}
		return oldStr;
	}
	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean notNull(String str) {
		return str != null && !"".equals(str);
	}
	/**
	 * ���ַ���ת��Ϊ�ǿ��ַ���
	 * 
	 * @param str
	 * @return
	 */
	public static String changeToNotNull(String str) {
		if (!notNull(str)) {
			return "";
		}
		return str;
	}

	/**
	 * ���ַ���ת��Ϊ�ǿ��ַ���
	 * 
	 * @param str
	 * @param val
	 *            Ĭ�ϵ�ֵ
	 * @return
	 */
	public static String changeToNotNull(String str, String val) {
		if (!notNull(str)) {
			return val;
		}
		return str;
	}
	
	/**
	 * �����ַ����ķǿ���ʽ������ǿվͷ���Ĭ�ϵ��ַ�����ʽ
	 * @param oldStr
	 * @param defaultStr
	 * @return
	 */
	public static String notBlank(String oldStr,String defaultStr){
		if(isBlank(oldStr)){
			return defaultStr;
		}
		return oldStr;
	}
	/**
	 * �õ������ļ��еĶ�Ӧ�Ĳ���ֵ.
	 * @param key ������
	 * @param fileName �ļ���ַ
	 * @return
	 */
	public static String getProperty(String key,String fileName) {
		try {
			FileInputStream in = new FileInputStream(fileName);
			prop.load(in);
			in.close();
		} catch (Exception e) {
			System.out.println("Error of create input stream");
		}
		String value = "";
		try {
			value = prop.getProperty(key);
			value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			log.error("Util--getProperty:", e);
		}
		return value;
	}
	
	 
	
	 
	/**
	 * �����б��Ϸ��ط��ϱ�����ݵ�json��,�ֶ�˳���Ǹ��ݷ���java�γ�,�ǲ����.
	 * @param dataList ������ݵļ���
	 * @param total ���ݵ�������
	 * @param page ��ǰ��ҳ��
	 * @return
	 */
	public static String getGridJsonStr(List dataList,long total,int page){
		StringBuffer buf = new StringBuffer();
		if(total==0)
			page = 0;
		buf.append("{page:").append(page).append("\n,total:").append(total).append("\n,rows:[");
		if(dataList!=null&&dataList.size()>0){
			Iterator it = dataList.iterator();
			int rowNum = 1;
			while(it.hasNext()){
				buf.append("{");
				Map allProperties = getAllProperties(it.next());
				Set entrySet = allProperties.entrySet(); 
				Iterator iterator = entrySet.iterator(); 
				while(iterator.hasNext()) 
				{ 
				        Map.Entry entry = (Map.Entry) iterator.next(); 
				        Object value = entry.getValue(); 
				        Object key = entry.getKey(); 
				        buf.append(key).append(":'").append(Util.notBlankStr(value)).append("',");
				}
				buf.deleteCharAt(buf.lastIndexOf(",")).append("},\n");
			}
			buf.deleteCharAt(buf.lastIndexOf(","));
		}else{
			buf.append("{'id':null,'name':null}");	
		}	
		buf.append("]}");		
		return buf.toString();
	}
	
	/**
	 * �õ�ext�ı�������s
	 * @param dataList
	 * @param total
	 * @param page
	 * @return
	 */
	public static String getExtGridJsonStr(List dataList,int total,int page){
		StringBuffer buf = new StringBuffer();
		if(total==0)
			page = 0;
		buf.append("{totalNum:").append(total).append("\n,root:[");
		if(dataList!=null){
			Iterator it = dataList.iterator();
			int rowNum = 1;
			while(it.hasNext()){
				buf.append("{");
				Map allProperties = getAllProperties(it.next());
				Set entrySet = allProperties.entrySet(); 
				Iterator iterator = entrySet.iterator(); 
				while(iterator.hasNext()) 
				{ 
				        Map.Entry entry = (Map.Entry) iterator.next(); 
				        Object value = entry.getValue(); 
				        Object key = entry.getKey(); 
				        buf.append(key).append(":'").append(value).append("',");
				}
				buf.deleteCharAt(buf.lastIndexOf(",")).append("},\n");
			}
			buf.deleteCharAt(buf.lastIndexOf(","));
		}else{
			buf.append("{}");	
		}	
		buf.append("]}");		
		return buf.toString();
	}
	
	/**
	 * �õ���ҳ��ѯ��sql���
	 * @param queryStr ԭʼsql���
	 * @param start ��ʼ��(��СΪ1)
	 * @param end ��ֹ��
	 * @return
	 */
	public static String getRealQuerySql(String queryStr,int start,int end){
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT *																						");
		buf.append("  FROM (SELECT T_T_T.*, ROWNUM ROWCOUNT             ");
		buf.append("          FROM (").append(queryStr).append(") T_T_T  ");
		buf.append("         WHERE ROWNUM < ").append(end).append(")    ");
		buf.append(" WHERE ROWCOUNT >= ").append(start);
		return buf.toString();
	}
	
 
	
	/**
	 * ���γ�ext����ʱ��õ���ҳ�����������Ϣ.
	 * @param start ��ʼλ��
	 * @param limit ÿҳ������
	 * @param total ������
	 * @return [��ʼλ�ã���ֹλ�ã���ǰҳ��]
	 */
	public static int[] getStartAndEnd(int  start,int limit,int total){ 
		return new int[]{start,start+limit,start/limit+1};
	}
	/**
	 * �ж��Ƿ�Ϊ���ַ���
	 * @param str Ҫ�жϵ��ַ���
	 * @return �����Ϊ�շ���true
	 */
	public static boolean isNotBlank(String str) {
		return (str != null && !"".equals(str)) ? true : false;
	}

	/**
	 * �ж��Ƿ�Ϊ���ַ���
	 * 
	 * @param str
	 *            Ҫ�жϵ��ַ���
	 * @return ���Ϊ�շ���true
	 */
	public static boolean isBlank(String str) {
		return !isNotBlank(str);
	}
	
	/**
	 * �ж϶����Ƿ�Ϊ�գ��վͷ���true
	 * @param obj
	 * @return
	 */
	public static boolean isBlank(Object obj) {
		return !isNotBlank(obj);
	}
	
	/**
	 * �ж϶����Ƿ�Ϊ�ǿգ��ǿվͷ���true
	 * @param obj
	 * @return
	 */
	public static boolean isNotBlank(Object obj) {
		return obj!=null;
	}

	/**
	 * ���ض���ķǿ��ַ�����ʽ
	 * 
	 * @param obj
	 * @param defaultStr
	 * @return
	 */
	public static String notBlankStr(Object obj, String defaultStr) {
		if (isBlank(obj) || "".equals(obj))
			return defaultStr;
		return obj.toString();
	}
	
	/**
	 * ���طǿ��ַ������ǿվͷ��ء���
	 * @param obj
	 * @return
	 */
	public static String notBlankStr(Object obj) {
		if (isBlank(obj))
			return "";
		return obj.toString();
	}
 
	 
	/**
	 * �ж��Ƿ�Ϊ���ַ���(�����ո�)
	 * @param str Ҫ�жϵ��ַ���
	 * @return �����Ϊ�շ���true
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null && !"".equals(str.trim())) ? true : false;
	}

	/**
	 * �ж��Ƿ�Ϊ���ַ���(�����ո�)	
	 * @param str            Ҫ�жϵ��ַ���
	 * @return ���Ϊ�շ���true
	 */
	public static boolean isEmpty(String str) {
		return !isNotEmpty(str);
	}

	/**
	 * �ַ����Ƚ�
	 * @param src
	 * @param des
	 * @return
	 */
	public static boolean equals(String src, String des) {
		if (src == null)
			return (des == null ? true : false);
		if (des == null)
			return (src == null ? true : false);
		return src.equals(des);
	}

	/**
	 * ��String������","�ż�����ַ���	 
	 * @param str       Ҫ�жϵ��ַ���
	 * @return ���Ϊ�շ���true
	 */
	public static String StringArrayToString(String[] str) {
		StringBuilder sb = new StringBuilder();
		if (str != null && str.length > 0) {
			for (String s : str) {
				if (s != null) {
					sb.append(s + ",");
				}
			}
			if (sb.length() == 0)
				return "";
			return sb.substring(0, sb.length() - 1).toString();
		}
		return str[0];
	}

	/**
	 * �ж�URL��׺�Ƿ�Ϊ.action,����ǵĻ�����ȡactionName	 
	 * @param servletPath   request.getServletPath()
	 * @return actionName
	 */
	public static String parseServletPath(String servletPath,String fiexdStr) {
		fiexdStr = "."+fiexdStr;
		if (null != servletPath && !"".equals(servletPath)) {
			if (servletPath.contains(fiexdStr)) {
				String actionName = servletPath.substring(servletPath.lastIndexOf("/") + 1,
						servletPath.indexOf(fiexdStr));
				return actionName;
			}
		}
		return "";
	}
	
	/**
	 * �����ļ����õ�������Ŀ¼��.
	 * @param filename
	 * @return
	 */
	public static String getDirName(String filename) {
		String ans = null;
		if (null != filename && !"".equals(filename)) {
			ans = filename.substring(0,filename.lastIndexOf("\\"));
		}
		return ans;
	}

	/**
	 * ʹ�÷�����ƽ�������ֵ.
	 * @param o ����
	 * @param name Ҫ���õ�����
	 * @param value Ҫ���õ�value
	 */
	public static void setPro(Object o, String name, String value) {
		PropertyDescriptor[] props;
		try {
			props = Introspector.getBeanInfo(o.getClass(), Object.class)
					.getPropertyDescriptors();
			for (int temp = 0; temp < props.length; temp++) {
				if (name.equals(props[temp].getName())) {
					try {
						props[temp].getWriteMethod().invoke(o, value);
					} catch (Exception e) {
					}
					break;
				}
			}
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * ʹ�÷�����ƽ�������ֵ.
	 * @param o ����
	 * @param name Ҫ���õ�����
	 * @param value Ҫ���õ�value
	 */
	public static void setPro(Object o, String name, Object value) {
		PropertyDescriptor[] props;
		try {
			props = Introspector.getBeanInfo(o.getClass(), Object.class)
					.getPropertyDescriptors();
			for (int temp = 0; temp < props.length; temp++) {
				if (name.equals(props[temp].getName())) {
					try {
						props[temp].getWriteMethod().invoke(o, value);
					} catch (Exception e) {
					}
					break;
				}
			}
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
		}
	}

	 
	/**
	 * �õ�ָ�������ָ������ֵ.
	 * @param o ����
	 * @param name ������
	 * @return
	 */
	public static String getPro(Object o, String name) {
		String result = "";
		PropertyDescriptor[] props;
		try {
			props = Introspector.getBeanInfo(o.getClass(), Object.class)
					.getPropertyDescriptors();
			for (int temp = 0; temp < props.length; temp++) {
				if (name.equals(props[temp].getName())) {
					try {
						result = props[temp].getReadMethod().invoke(o)
								.toString();
					} catch (Exception e) {
					}
					break;
				}
			}
			return result;
		} catch (IntrospectionException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ��ȡָ���ļ������ݣ������ı��ַ���
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param linkChar
	 *            ���з���
	 * @return
	 */
	public static String readFile(String fileName, String linkChar) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in;
		String result = "";
		try {
			// �����ļ�����������
			in = new BufferedReader(new FileReader(fileName));
			String s;
			while ((s = in.readLine()) != null) {
				sb.append(s);
				// ����ÿһ�е����ݶ�ȡ֮�������Ԫ����
				sb.append(linkChar);
			}
			in.close();
			int i = linkChar.length();
			result = sb.toString();
			result = result.subSequence(0, sb.length() - i).toString();
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ����ļ���");
			throw new Exception("�ļ��Ҳ�����");
		} catch (IOException e) {
			System.out.println("�����쳣��");
			throw new Exception("�ļ��Ҳ�����");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�����쳣��");
			throw new Exception("�ļ��Ҳ�����");
		} finally {
			return result;
		}
	}
	
	/**
	 * ����һ��map���ַ�����ʽ
	 * 
	 * @param map
	 * @return
	 */
	public static String getStrFromMap(Map map) {
		StringBuffer buf = new StringBuffer();
		if (map != null) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String v = it.next().toString();
				buf.append("['").append(v).append("',").append(map.get(v))
						.append("]").append(",");
			}
			buf.deleteCharAt(buf.length() - 1);
			return buf.toString();
		}
		return "";
	}
	/**
	 * ����һ��Ŀ¼�����ȫ����jspҳ���ַ
	 * 
	 * @param filename
	 * @return
	 */
	public static ArrayList seeAllJspFile(String filename) {
		File tempFile = new File(filename);
		ArrayList ans = new ArrayList();
		if (tempFile.isFile() && tempFile.getName().endsWith(".jsp")) {
			ans.add(tempFile.getAbsolutePath());
		} else {
			String[] list;
			list = tempFile.list();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					Iterator it = seeAllJspFile(filename + "\\" + list[i])
							.iterator();
					while (it.hasNext()) {
						ans.add(it.next());
					}
				}
			}
		}
		return ans;
	}
	/**
	 * ���ݹ��������Լ���sql�ַ���
	 * 
	 * @param str
	 *            ���磺select * from moneys
	 * @param start
	 * @param end
	 * @param whereStr
	 *            ���磺mid<100 and desc = "֧��"
	 * @param sortStr
	 *            ���磺order by mid,mtime desc
	 * @return
	 */
	public static String getQueryStr(String str, int start, int end,
			String whereStr, String sortStr) {
		StringBuffer sqlbf = new StringBuffer();
		sqlbf.append("select *									");
		sqlbf
				.append("  from (select t2_t2_t2.*, ROWNUM ROWCOUNT                                ");
		sqlbf
				.append("          from (select t1_t1_t1.*                                         ");
		sqlbf.append("                  from (" + str
				+ ")t1_t1_t1 where 1=1        ");
		if (notNull(whereStr)) {
			sqlbf.append(" and " + whereStr);
		}
		if (notNull(sortStr)) {
			sqlbf.append(sortStr);
		}
		sqlbf.append("  ) t2_t2_t2                                 ");
		sqlbf.append("         where ROWNUM < " + end
				+ ")                                               ");
		sqlbf.append(" where ROWCOUNT >= " + start);
		return sqlbf.toString();
	}
	/**
	 * ��ӡһ�����������ȫ����get����.
	 * @param o
	 */
	public static void getAllGets(Object o) {
		Method[] method = o.getClass().getMethods();
		try {
			for (int i = 0; i < method.length; i++) {
				//����������Ǻ���get�����ƣ������Ƿ��ص�string���ͣ��Լ���������Ϊ�գ��͵��ø÷�����
				if (method[i].getName().indexOf("get") != -1
						&& method[i].getGenericReturnType().toString().indexOf(
								"String") != -1
						&& method[i].getGenericParameterTypes().length == 0) {
					System.out.println(i + method[i].getName() + "():\n"
							+ method[i].invoke(o, null));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �õ�һ������ķ���string��ȫ������.
	 * @param o
	 */
	public static List<String> getAllMethods(Object o) {
		Method[] method = o.getClass().getMethods();
		List<String> methods = new ArrayList();
		try {
			for (int i = 0; i < method.length; i++) {
				//����������Ǻ���get�����ƣ������Ƿ��ص�string���ͣ��Լ���������Ϊ�գ��͵��ø÷�����
				if ( method[i].getGenericReturnType().toString().indexOf(
								"String") != -1
						&& method[i].getGenericParameterTypes().length == 0) {
					methods.add(method[i].getName());
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return methods;
	}
	
	/**
	 * �õ�һ�������ȫ������ֵ.
	 * @param o
	 * @return ������������ֵ��ӳ��
	 */
	public static Map getAllProperties(Object o) {
		Map ans = new HashMap();
		PropertyDescriptor[] props;
		try {
			props = Introspector.getBeanInfo(o.getClass(), Object.class)
					.getPropertyDescriptors();
			for (int temp = 0; temp < props.length; temp++) {
					String result = null;
					if(props[temp].getReadMethod().invoke(o)!=null)
						result = props[temp].getReadMethod().invoke(o).toString();
					ans.put(props[temp].getName(), result);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return ans;
	}
	
	/**
	 * ����һ�������ȫ������ֵ.
	 * @param o
	 * @return
	 */
	public static String getJsonFromObject(Object o) {
		StringBuffer buf = new StringBuffer();
		PropertyDescriptor[] props;
		try {
			props = Introspector.getBeanInfo(o.getClass(), Object.class)
					.getPropertyDescriptors();
			for (int temp = 0; temp < props.length; temp++) { 
					if(props[temp].getReadMethod().invoke(o)!=null){
						String result = props[temp].getReadMethod().invoke(o).toString(); 
						buf.append("'").append(props[temp].getName()).append("':'").append(result).append("',");
					}
			}
			if(buf.length()>0)
				buf = buf.deleteCharAt(buf.length()-1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return buf.toString();
	}
 
	/**
	 * ���ض�����������get���������Եļ���,ע�⼯�ϵĳ�Ա��Field����!
	 * @param obj
	 * @return
	 */
	public static List<Field> allAttrWithGetMethod(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Method[] methods = obj.getClass().getMethods();
		List result = new ArrayList();
		for (Field field : fields) {
			String fieldName = field.getName();
			String upperFirstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + upperFirstLetter
					+ fieldName.substring(1);
			for (Method method : methods) {
				if (Util.equals(getMethodName, method.getName())) {
					result.add(field);
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * �õ�һ��bean�����ȫ����������get����������. 
	 * @param obj
	 * @return �����еĳ�Ա��������.
	 */
	public static List<Field> allAttrsWithGetMethods(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Method[] methods = obj.getClass().getMethods();
		List result = new ArrayList();
		for (Field field : fields) {
			String fieldName = field.getName();
			String upperFirstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + upperFirstLetter
					+ fieldName.substring(1);
			for (Method method : methods) {
				if (Util.equals(getMethodName, method.getName())) {
					result.add(fieldName);
					break;
				}
			}
		}
		return result;
	}

	/**
	 * ʹ�÷���ִ��ָ��������޲η���,���ؽ����һ������.
	 * @param obj ����
	 * @param methodName ������
	 * @return
	 * @throws MVCException 
	 */
	public static Object invoke(Object obj, String methodName) throws Exception {
		try {
			//���û������method������Ĭ�ϵ�ʹ��execute()������			
			if(Util.isEmpty(methodName))
				methodName = "execute"; 
			Method method = obj.getClass().getMethod(methodName, null);
			return method.invoke(obj, null);
		}  
		catch (Exception e) {
			throw e;
		}
	}
	/**
	 * �жϲ���2�ַ����Ƿ������1�ַ������
	 * @param str1 ���Ƚϵ��ַ���
	 * @param str2 �Ƚϵ��ַ���
	 * @return boolean
	 */
	public static boolean equalsIgnoreCase (String str1, String str2){
		return str1.toLowerCase().equals(str2.toLowerCase());
	}
	/**
	 * ͳ���ܺͺ�����
	 * 
	 * @return
	 */
	public static double getSum(List l) {
		double ans = 0;
		Iterator mit = l.iterator();
		while (mit.hasNext()) {
			ans = Util.add(Double.parseDouble(mit.next().toString()), ans);
		}
		return ans;
	}

	/**
	 * ����ƽ����������
	 * 
	 * @param l
	 * @return
	 */
	public static double getAvg(List l) {
		double ans = 0;
		ans = Util.divide(getSum(l), l.size(), 2, RoundingMode.HALF_UP);
		return ans;
	}
	/**
	 * ת������Ϊָ�����ڸ�ʽ���ַ���
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToStr(Date date, String formatStr) {
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * ת�������ַ��������ڡ�
	 * 
	 * @param dateString
	 *            �����ַ���
	 * @param formatStr
	 *            ��ʽ���ַ���
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String dateString, String formatStr)
			throws ParseException {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat(formatStr);
		Date timeDate = dateFormat.parse(dateString);// util����
		return timeDate;
	}

	/**
	 * ����
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subtract(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(df.format(b1.subtract(b2).doubleValue()));
	}
	/**
	 * �˷�
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double multiply(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(df.format(b1.multiply(b2).doubleValue()));
	}

	/**
	 * �ӷ�
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(double d1, double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(df.format(b1.add(b2).doubleValue()));
	}

	/**
	 * ����
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            ��ȷ��
	 * @param mode
	 *            ���뷽ʽ
	 * @return
	 */
	public static double divide(double d1, double d2, int scale,
			RoundingMode mode) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(df.format(b1.divide(b2, scale, mode)
				.doubleValue()));
	}

	/**
	 * ����
	 * @param d1 ������
	 * @param d2 ����
	 * @param scale С���㾫��
	 * @return
	 */
	public static double divide(double d1, double d2, int scale) {
		return divide(d1, d2, scale, RoundingMode.HALF_UP);
	}

}
