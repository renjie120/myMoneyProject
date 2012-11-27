package myOwnLibrary.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ��ȡPOI����.����excel2003��Ч.
 * 
 * @author renjie120 connect my:(QQ)1246910068
 * 
 */
public class POIReader extends ExcelTool {
	/**
	 * �洢excel�����ݵ����ݽṹ��
	 */
	private Hashtable data = null;
	/**
	 * �洢excel��������Ե����ݽṹ.
	 */
	private Map prop = null;
	/**
	 * ������
	 */
	private HSSFWorkbook workbook;

	/**
	 * ��־��¼
	 */
	private Logger log;

	/**
	 * excel��
	 */
	private HSSFSheet sheet;

	/**
	 * ��ǰ��ȡ��sheet��ҳ��
	 */
	private int currentSheetNum;
	private int currentRow;
	private int currentCell;

	/**
	 * excel�ļ���
	 */
	private FileInputStream fis;

	/**
	 * ��Ϣ
	 */
	private StringBuffer msg = null;

	/**
	 * ��ʼ������.
	 * 
	 * @param exl
	 * @throws IOException
	 */
	public POIReader(File exl) throws IOException {
		fis = new FileInputStream(exl);
		workbook = new HSSFWorkbook(fis);
		msg = new StringBuffer();
		data = new Hashtable();
		data.put("workbook", workbook);
		prop = new HashMap();
	}

	public POIReader(String exlFileName) throws IOException {
		File file = new File(exlFileName);
		fis = new FileInputStream(file);
		workbook = new HSSFWorkbook(fis);
		msg = new StringBuffer();
		data = new Hashtable();
		data.put("workbook", workbook);
		prop = new HashMap();
	}

	public POIReader(InputStream input) throws IOException {
		workbook = new HSSFWorkbook(input);
		msg = new StringBuffer();
		data = new Hashtable();
		data.put("workbook", workbook);
		prop = new HashMap();
	}

	public void destory() {
		try {
			msg = null;
			if (fis != null)
				fis.close();
		} catch (Exception ex) {
			msg.append(ex.getMessage());
		}
	}

	public void error(Object o) {
		if (o instanceof Exception) {
			Exception e = (Exception) o;
			e.printStackTrace();
		} else
			System.out.println(o);
	}

	/**
	 * ���µ�Ԫ������Ϊ�µ�����.
	 * @param sheetNum
	 * @param rowindex
	 * @param colIndex
	 * @param newValue
	 */
	public void updateCellValue(int sheetNum, int rowindex, int colIndex,
			String newValue) {
		//����ֻ������cell������û����ʾset��data���󣬻���ô�������᲻�����data�����workbook�����������Ķ������ã�����Ϊ�᣿����أ�
		HSSFCell cell = getCell(sheetNum, rowindex, colIndex);
		updateCellValue(cell,newValue);
	}
	
	/**
	 * ����ָ����cell����.
	 * @param c
	 * @param newValue
	 * @return
	 */
	public HSSFCell updateCellValue(HSSFCell c, String newValue) { 
		HSSFCell cell = c;
		if (cell != null) {
			switch (cell.getCellType()) {
			case 0: {// ����
				cell.setCellValue(Double.parseDouble(newValue));
				break;
			}
			case 1: {// �ַ���
				cell.setCellValue(newValue);
				break;
			}
			case 3: {// ��ֵ
				try{
					cell.setCellValue(Double.parseDouble(newValue));
				}catch(Exception e){
					cell.setCellValue(newValue);
				}
				break;
			}
			case 4: {// boolean����
				cell.setCellValue(Boolean.parseBoolean(newValue));
				break;
			}
			default:
				break;
			}
		}
		return cell;
	}

	/**
	 * ����Ϊ�µ�excel.
	 * 
	 * @param newFileName
	 *            ���ļ���
	 * @throws FileNotFoundException
	 */
	public void saveAsNewFile(String newFileName) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(newFileName);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			error(e);
		} catch (IOException e) {
			error(e);
		}
	}

	/**
	 * �õ�excel���ƶ�sheetNum�����ơ� ������prop��.
	 * 
	 * @param num
	 * @return
	 */
	public String getSheetName(int num) {
		setCurrentSheet(num);
		String result = (String) prop.get("sheetName_" + num);
		if (result == null) {
			result = sheet.getSheetName();
			prop.put("sheetName_" + num, result);
		}
		return result;
	}

	/**
	 * ���Կ�������������������ȷ����һ��excel�ļ�ҳ�������һ����������
	 * 
	 * @param sheetNum
	 *            �ļ���ҳ��
	 * @param firstRowNum
	 *            ��һ�е�����
	 * @param lastRowNum
	 *            ���һ�е�����
	 * @param firstColIndex
	 *            ��һ�е�����
	 * @param lastColIndex
	 *            ���һ�е�����
	 * @return
	 */
	public String[][] getSheetAsTable(int sheetNum, int firstRowNum,
			int lastRowNum, int firstColIndex, int lastColIndex) {
		String[][] cells = null;
		if (setCurrentSheet(sheetNum)) {
			cells = new String[lastRowNum - firstRowNum + 1][lastColIndex
					- firstColIndex + 1];
			for (int c1 = firstRowNum; c1 <= lastRowNum; c1++) {
				for (int c2 = firstColIndex; c2 <= lastColIndex; c2++) {
					try {
						cells[c1][c2] = getCellAsStringByIndex(c1, c2);
					} catch (Exception e) {
						e.printStackTrace();
						cells[c1][c2] = "";
					}
				}
			}
		}
		return cells;
	}

	/**
	 * ���õ�ǰexcel��sheetҳ.
	 * 
	 * @param num
	 * @return
	 */
	public boolean setCurrentSheet(int num) {
		if (workbook != null && num < workbook.getNumberOfFonts()) {
			try {
				String index = getDataIndex(num);
				sheet = (HSSFSheet) data.get(index);
				if (sheet == null) {
					sheet = workbook.getSheetAt(num);
					if (sheet != null)
						data.put(index, sheet);
				}
				currentSheetNum = num;
				return true;
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private String getDataIndex(int sheetNum) {
		return "sheet_" + sheetNum;
	}

	private String getDataIndex(int sheetNum, int rownum) {
		return "sheet_" + sheetNum + "_row_" + rownum;
	}

	private String getDataIndex(int sheetNum, int rownum, int cellnum) {
		return "sheet_" + sheetNum + "_row_" + rownum + "_cell_" + cellnum;
	}

	private HSSFRow getRow(int rowId) {
		return getRow(currentSheetNum, rowId);
	}

	/**
	 * �õ�excel�е�ָ����.
	 * 
	 * @param sheetId
	 *            sheet����
	 * @param rowId
	 *            ������
	 * @return
	 */
	public HSSFRow getRow(int sheetId, int rowId) {
		String sheetRowIndex = getDataIndex(sheetId, rowId);
		HSSFRow row = (HSSFRow) data.get(sheetRowIndex);
		if (row == null) {
			row = sheet.getRow(rowId);
			if (row != null)
				data.put(sheetRowIndex, row);
		}
		return row;
	}

	/**
	 * �õ�excel�е��ƶ�cell
	 * 
	 * @param sheetId
	 *            sheet����
	 * @param rowId
	 *            ������
	 * @param colId
	 *            ������
	 * @return
	 */
	public HSSFCell getCell(int sheetId, int rowId, int colId) {
		HSSFRow row = getRow(rowId);
		String sheetCellIndex = getDataIndex(sheetId, rowId, colId);
		HSSFCell cell = (HSSFCell) data.get(sheetCellIndex);
		if (cell == null) {
			cell = row.getCell((short) colId);
			if (cell != null)
				data.put(sheetCellIndex, cell);
		}
		return cell;
	}

	private HSSFCell getCell(int rowId, int colId) {
		return getCell(currentSheetNum, rowId, colId);
	}

	/**
	 * ����ָ��λ�õĵ�Ԫ��.
	 * 
	 * @param rowId
	 *            ���õ�Ԫ�����
	 * @param colId
	 *            ���õ�Ԫ�����
	 * @return
	 */
	public String getCellAsStringByIndex(int rowId, int colId) {
		String cellStr = "";
		if (sheet != null && rowId < sheet.getLastRowNum() + 1) {
			try {
				HSSFRow row = getRow(rowId);
				if (row != null) {
					if (colId < row.getLastCellNum()) {
						HSSFCell cell = getCell(rowId, colId);
						currentRow = rowId;
						currentCell = colId;
						cellStr = getCellContent(cell);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				cellStr = "";
			}
		}
		//System.out.println("rowid:"+getCellInfo(rowId, colId)+",cellStr="+cellStr);
		return cellStr;
	}

	/**
	 * �õ�number��Ԫ�������.
	 * 
	 * @param cell
	 * @return
	 */
	private String getNumber(HSSFCell cell) {
		String str = NumberFormat.getNumberInstance().format(
				cell.getNumericCellValue());
		while (str.indexOf(",") > -1) {
			str = str.substring(0, str.indexOf(","))
					+ str.substring(str.indexOf(",") + 1);
		}
		return str;
	}

	/**
	 * �õ����ڵ�Ԫ�������.
	 * 
	 * @param cell
	 * @return
	 */
	private String getDate(HSSFCell cell) {
		Date d = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(d);
	}

	private String getCellContent(HSSFCell cell) {
		String cellStr = "";
		if (cell != null) {
			try {
				switch (cell.getCellType()) {
				case 0: {// ����
					// ����ǲ�����������.
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellStr = getDate(cell);
					} else {
						cellStr = getNumber(cell); 
					}
					break;
				}
				case 1: {// �ַ���
					cellStr = cell.getStringCellValue();
					break;
				}
				case 2: {// ��ʽ
					String formula = cell.getCellFormula();
					if (formula.indexOf("DATE(") >= 0) {
						cellStr = getDate(cell);
					} else if (formula.indexOf("SUM(") >= 0) {
						cellStr = Double.toString(cell.getNumericCellValue());
					} else if (formula.indexOf("SIN(") >= 0) {
						cellStr = Double.toString(cell.getNumericCellValue());
					} else {
						// �����ֵ����ڸ�ʽ,�Ͷ�ȡ������ʽ.
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							cellStr = getDate(cell);
						} else {
							try {
								cellStr = cell.getStringCellValue();
							} catch (Exception eee) {
								try {
									cellStr = getNumber(cell);
								} catch (Exception f) {
									error(f);
								}
							}
						}
					}
					break;
				}
				case 3: {// ��ֵ.
					cellStr = cell.getStringCellValue();
					break;
				}
				case 4: {// boolean����
					cellStr = Boolean.toString(cell.getBooleanCellValue());
					break;
				}
				default: {// ����
					cellStr = new String("");
				}
					if (cellStr == null) {
						cellStr = "";
					}
				}
			} catch (Exception e) {
				error(e);
				cellStr = "";
			}
		}
		return cellStr;
	}

	private void error(Exception f) {
		f.printStackTrace();
		System.out.println("���ִ���!sheet:" + currentSheetNum + ";����Ԫ��:"
				+ ExcelTool.getCellInfo(currentRow, currentCell));
	}

	/**
	 * ����ָ��sheet,ָ����,ָ���еĵ�Ԫ������.
	 * 
	 * @param sheetNum
	 * @param rowindex
	 * @param colIndex
	 * @return
	 */
	public String getCellAsStringByIndex(int sheetNum, int rowindex,
			int colIndex) {
		return getCellAsStringByIndex(sheetNum, rowindex, colIndex, "");
	}

	/**
	 * ����ָ��sheet,ָ����,ָ���еĵ�Ԫ������.
	 * 
	 * @param sheetNum
	 *            sheet����
	 * @param rowindex
	 *            ������
	 * @param colIndex
	 *            ������
	 * @param defaultStr
	 *            ��ֵת��ΪĬ��ֵ
	 * @return
	 */
	public String getCellAsStringByIndex(int sheetNum, int rowindex,
			int colIndex, String defaultStr) {
		if (setCurrentSheet(sheetNum)) {
			return Util.notBlank(getCellAsStringByIndex(rowindex, colIndex),
					defaultStr);
		}
		return defaultStr;
	}

	public void emptyCell(HSSFCell cell) {
		System.out.println(cell.getCellType());
	}

	public String getErrorMessage() {
		return msg.toString();
	}

	/**
	 * ���ص�ǰ��ҳ��
	 * 
	 * @return
	 */
	public HSSFSheet getSheet() {
		return sheet;
	}

	/**
	 * ���ص�ǰ�Ĺ�����
	 * 
	 * @return
	 */
	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	/**
	 * 
	 * @param fileName
	 *            Ҫ��ȡ��excel�ļ���
	 * @param sheetNum
	 *            Ҫ��ȡ�ı�����Ŀ
	 * @param row
	 *            Ҫ��ȡ�ĵ�Ԫ������
	 * @param col
	 *            Ҫ��ȡ�ĵ�Ԫ������
	 * @return
	 */
	public String readExcel(int sheetNum, int row, int col) {
		try {
			setCurrentSheet(sheetNum - 1);
			return getCellAsStringByIndex((row - 1), (col - 1));
		} catch (Exception e) {
			e.printStackTrace();
			return "�����쳣���������ļ�δ�ҵ���";
		}
	}

	/**
	 * ��ȡexcel�ļ���һ���������Ļ�����Ϣ���������.
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param sheetNum
	 *            Ҫ��ȡ�ı�����Ŀ
	 * @return
	 */
	public String getRowNum(int sheetNum) {
		try {
			setCurrentSheet(sheetNum - 1);
			HSSFSheet sheet = getSheet();
			int rowNum = sheet.getLastRowNum() + 1;
			return new Integer(rowNum).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "���������ļ��Ƿ���ڻ���ҳ�治���ڣ�";
		}
	}

	/**
	 * ��ȡexcel��ĳһ������ĳһ�е��������.
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param sheetNum
	 *            ������Ŀ
	 * @param row
	 *            ����
	 * @return
	 */
	public String getColNum(int sheetNum, int rowId) {
		HSSFRow row;
		int colNum;
		try {
			setCurrentSheet(sheetNum - 1);
			row = getRow(rowId);
			colNum = row.getLastCellNum();
			return new Integer(colNum).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "���������ļ��Ƿ���ڣ�";
		}
	}

	/**
	 * ��ȡ�ļ��Ĺ���������Ŀ.
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return
	 */
	public String getSheetNum() {
		try {
			String result = (String) prop.get("sheetNum");
			if (result == null) {
				result = new Integer(workbook.getNumberOfSheets()).toString();
				prop.put("sheetNum", result);
			}
			return result;
		} catch (Exception e) {
			error(e);
			return "";
		}
	}

	/**
	 * ����ȫ����sheet������.�Ǽ�������.
	 * 
	 * @return
	 */
	public List getSheetNames() {
		int num = Integer.parseInt(getSheetNum());
		List result = (List) prop.get("sheetNames");
		if (result == null) {
			result= new ArrayList();
			for (int i = 0; i < num; i++) {
				result.add(workbook.getSheetName(i));
			}
			prop.put("sheetNames", result);
		}
		return result;
	}

	/**
	 * ����ָ���ļ���ҳ���ȫ������.
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param sheetNum
	 *            �ļ��ı���
	 * @return String[][]
	 */
	public String[][] readAllExcel(int sheetNum) {
		return readAllExcel(sheetNum, false);
	}

	/**
	 * ����ָ���ļ�ҳ��ȫ������.
	 * 
	 * @param fileName
	 *            �ļ���
	 * @param sheetNums
	 *            ָ���ļ���sheetid�ļ���,�Զ��id��ɵ�����.
	 * @return
	 */
	public List<String[][]> readAllExcel(int[] sheetNums) {
		return readAllExcel(sheetNums, false);
	}

	/**
	 * ����ָ��excel�е�ָ��sheet����.
	 * 
	 * @param sheetNum
	 *            sheet����
	 * @param returnMeetFirstNullRow
	 *            �Ƿ�������һ�������Զ�����
	 * @return
	 */
	public String[][] readAllExcel(int sheetNum, boolean returnMeetFirstNullRow) {
		int[] sheetNums = new int[] { sheetNum };
		List<String[][]> ans = readAllExcel(sheetNums, returnMeetFirstNullRow);
		return ans.get(0);
	}

	/**
	 * ����ָ��excel�е�ָ��sheet����.
	 * 
	 * @param sheetNum
	 *            sheet����
	 * @param returnMeetFirstNullRow
	 *            �Ƿ�������һ�������Զ�����
	 * @param autoClose
	 *            ��ȡ����Ƿ��Զ��ر�
	 * @return
	 */
	public String[][] readAllExcel(int sheetNum,
			boolean returnMeetFirstNullRow, boolean autoClose) {
		int[] sheetNums = new int[] { sheetNum };
		List<String[][]> ans = readAllExcel(sheetNums, returnMeetFirstNullRow,
				autoClose);
		return ans.get(0);
	}

	public void printStrArr(String[][] result) {
		int i = result.length;
		for (int j = 0; j < i; j++) {
			for (int k = 0, m = result[j].length; k < m; k++) {

				System.out.println(ExcelTool.getCellInfo(j, k) + ",val = "
						+ result[j][k]);
			}
		}

	}

	/**
	 * �Ƿ��Զ��ر�excel.
	 * 
	 * @param sheetNums
	 * @param returnMeetFirstNullRow
	 * @param autoClose
	 * @return
	 */
	private List<String[][]> readAllExcel(int[] sheetNums,
			boolean returnMeetFirstNullRow, boolean autoClose) {
		// ���������һ�������Զ�����,��������ķ���.
		List<String[][]> ans = new ArrayList<String[][]>();
		int sheetId;
		HSSFSheet sheet;
		int maxRowNum;
		HSSFRow row;
		int maxColNum;
		String[][] result;
		try {
			for (int i = sheetNums.length - 1; i >= 0; i--) {
				result = new String[][] { {} };
				sheetId = sheetNums[i] - 1;
				setCurrentSheet(sheetId);
				sheet = getSheet();
				// ���������Ҫ������һ�����о��Զ�����,�ͼ���maxRowNum!
				if (returnMeetFirstNullRow) {
					maxRowNum = 0;
					row = null;
					// �õ���0�п�ʼ�ĵ�һ���ǿ�����.
					for (;; maxRowNum++) {
						row = getRow(maxRowNum);
						if (row == null) {
							if (maxRowNum != 0)
								maxRowNum--;
							break;
						}
					}
				}
				// ����ֱ��ȡȫ����excel������
				else {
					maxRowNum = sheet.getLastRowNum();
				}
				if (maxRowNum != 0) {
					// �õ���һ�е�����.
					row = getRow(0);
					maxColNum = row.getLastCellNum();
					result = getSheetAsTable(sheetId, 0, maxRowNum, 0,
							maxColNum - 1);
				}
				ans.add(result);
			}
			if (autoClose)
				destory();
			return ans;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * һ�η��ض��sheet������.
	 * 
	 * @param excelRd
	 * @param sheetNums
	 * @param returnMeetFirstNullRow
	 * @return
	 */
	private List<String[][]> readAllExcel(int[] sheetNums,
			boolean returnMeetFirstNullRow) {
		return readAllExcel(sheetNums, returnMeetFirstNullRow, true);
	}

	public static void main(String[] arg) {
		try {
			POIReader reader = new POIReader("D:\\���\\chrome����\\�����ؿ�(000157)_�ʲ���ծ��.xls");
			PrintStream temp = System.out;
			PrintStream oo = new PrintStream(new File("d:\\outout.txt"));
			System.setOut(oo);
			// System.out.println(reader.getSheetNum());
			// System.out.println(reader.getRowNum(24));
			// System.out.println(readfer.getColNum(24,1));
			List allSheetname = reader.getSheetNames();
			Iterator it = allSheetname.iterator();
			int count = 1;
			while (it.hasNext()) {
				System.out.println(count++ + ":" + it.next());
			}
			String[][] result = reader.readAllExcel(0, false, false);
			// System.out.println(reader.getSheetNames());
			reader.printStrArr(result);

			reader.destory();
			// System.out.println(reader.readExcel(24,8,4));
			System.setOut(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}