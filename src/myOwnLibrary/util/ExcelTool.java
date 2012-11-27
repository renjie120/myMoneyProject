package myOwnLibrary.util;

import java.util.HashMap;
import java.util.Map;

/**
 * jxl����excel�Ĺ�����.
 * 
 */
public class ExcelTool {
	public static int count = 1;
	// �洢���м�����Ϣ�����ݵ�λ�õ�ӳ���ϵ.
	private static Map levelToLocation = new HashMap();

	private static String allChar = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * ���ַ��еõ�����.����K-->10,A-->0,AA-->27
	 * 
	 * @return
	 */
	public static int getNumFromExcelStr(String code) {
		int result = 0;
		code = code.toLowerCase();
		if (code.length() > 1) {
			char[] c = code.toCharArray();
			int len = c.length;
			for (int i = 0; i < len; i++) {
				
				if (i < len - 1) {
					result += (allChar.indexOf(c[i]) + 1) * 26;
				} else {
					result += allChar.indexOf(c[i]) + 1;
				}
			}
			result -= 1;
		} else
			return allChar.indexOf(code);
		return result;
	}

	/**
	 * �����кź��кŵõ����ڵĵ�Ԫ��.����(3,4)-->"E4".
	 * @param vNum
	 *            ������
	 * @param hNum
	 *            ������
	 * @return
	 */
	public static String getCellInfo(int hNum, int vNum) {
		char[] cs = allChar.toCharArray();
		String hStr = "";
		if (vNum > 25) {
			hStr = String.valueOf(cs[vNum / 26 - 1])
					+ String.valueOf(cs[vNum % 26 - 1]);
		} else {
			hStr = String.valueOf(cs[vNum]);
		}
		return (hStr + Integer.toString((hNum + 1))).toUpperCase();
	}

	/**
	 * �õ�һ���ַ���������ַ�.A12-->A
	 * 
	 * @param oldStr
	 * @return
	 */
	public static String getCodeFromStr(String oldStr) {
		return oldStr.replaceAll("\\d", "");
	}

	/**
	 * �õ�һ���ַ���������ַ�.A12-->12
	 * 
	 * @param oldStr
	 * @return
	 */
	public static int getNumFromStr(String oldStr) {
		return Integer.parseInt(oldStr.replaceAll("[a-zA-Z]", "")) - 1;
	}

	/**
	 * ���кź��кţ��õ����ڵ�λ���ַ��������磺row=7��col=7--->i8
	 */
	public static String getcodefromRC(int row, int col) {
		char[] cc = allChar.toCharArray();
		return String.valueOf(cc[col]) + (++row);
	}

}
