package myOwnLibrary.flashchart;

/** 
 * @ClassName: Lib 
 * @Description: 小工具类
 * @author 419723443@qq.com
 * @date Jan 20, 2010 7:18:18 PM 
 *  
 */
public class Lib {
	public static String notNull(String str) {
		return str == null ? "" : str;
	}

	public static String notNull(Object obj) {
		return obj == null ? new String("") : (String) obj;
	}

	public static Integer notNull(Integer val) {
		return val == null ? new Integer(0) : val;
	}

	public static Double notNull(Double val) {
		return val == null ? new Double(0.0) : val;
	}

	public static String notNull(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	public static String toNull(String str) {
		return (str == null || str.length() == 0) ? null : str;
	}

	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj);
	}

	public static boolean isEmpty(int i) {
		return i == 0;
	}
	
	public static boolean isEmpty(boolean i) {
		return i ;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.equals("");
	}

	public static boolean isEmpty(byte[] str) {
		return str == null || str.length == 0;
	}

	public static boolean isEmpty(String[] str) {
		return str == null || str.length == 0;
	}

	public static int substringCount(String src, String substr) {
		int result = 0;
		int idx = 0;
		int len = substr.length();
		int srcLen = src.length();
		while ((idx = src.indexOf(substr, idx)) >= 0 && idx < srcLen) {
			idx += len;
			result++;
		}
		return result;
	}

	public static String getNum(int num2) {
		String[] units = { "十", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		if (num2 < 1 || num2 > 100)
			return null;
		else if (num2 < 10)
			return units[num2];
		else if (num2 == 10)
			return units[0];
		else if (num2 < 20)
			return units[0] + units[num2 % 10];
		else
			return num2 % 10 != 0 ? units[num2 / 10] + units[0]
					+ units[num2 % 10] : units[num2 / 10] + units[0];
	}
}
