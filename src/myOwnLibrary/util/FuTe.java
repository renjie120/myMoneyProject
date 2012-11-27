package myOwnLibrary.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

public class FuTe {
	// ���ͺ����ڵ���λ��
	private static final String CARCOLUMN = "B";
	// ��������������
	private static  String TODAYCOLUMN = "Q";
	// �����ۼ�������
	private static  String BENYUELEIJI = "R";
	// ��������������
	private static  String TODAYLINGSHOU = "V";
	// �����ۼ�������
	private static  String LINGSHOULEIJI = "W";
	// ���ն���
	private static  String TODAYSELL = "F";
	
	private static final String SRC = "src";
	private static final String COLUMNS = "col";
	private static final String DIRNAME = "dir";
	// ���ͼ��кŵĶ�Ӧ��ϵ.���������map��Ӧ��Key����һ���ģ����ǡ���Ԫ��λ��+�������ơ�
	private Map carToRow; 
	private POIReader reader;
	private String fileName;
	private static Preferences p = Preferences.userNodeForPackage(FuTe.class);;
	PrintStream log ;

	public FuTe(String fileName) {
		try {
			this.fileName = fileName;
			log = new PrintStream(new File("d:\\log.txt")); 
			carToRow = new HashMap(); 
			reader = new POIReader(fileName); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void set(String key,String val){
		p.put(key, val);
	}
	
	private static String get(String key,String defaultStr){
		return (String)p.get(key, defaultStr);
	}
	
	public FuTe(File f) {
		try {
			this.fileName = f.getName();
			log = new PrintStream(new File("d:\\log.txt")); 
			carToRow = new HashMap(); 
			reader = new POIReader(f); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public POIReader getReader() {
		return reader;
	}

	private boolean checkIsCar(String carno) {
		if ("".equals(carno) || carno.indexOf("Dealer") != -1
				|| carno.indexOf("Mondeo") != -1
				|| carno.indexOf("Focus") != -1 || carno.indexOf("MAX") != -1
				|| carno.indexOf("Fiesta") != -1
				|| carno.indexOf("Total") != -1) {
			return false;
		}
		return true;
	}

	/**
	 * �õ�ĳ��sheet����ĳ��͵��к�.
	 * 
	 * @param sheetNum
	 */
	private Map getAnayCarToRow(int sheetNum, int startRow, int endRow) {
		String sheetName = reader.getSheetName(sheetNum);
		// ���ﲻ�����ÿ��sheet���ظ����㳵�Ͷ�Ӧ���кţ�����Ϊÿ��excel����һ���ģ��������һ����Ҫȥ��������жϣ���
		if (carToRow.keySet().size() != 0)
			return carToRow;
		int cellNum = reader.getNumFromExcelStr(CARCOLUMN);
		for (int i = startRow; i < endRow; i++) { 
			// �õ����͵�������Ϣ��
			String cellValue = reader.getCellAsStringByIndex(sheetNum, i,
					cellNum);
			if (checkIsCar(cellValue)) {
				carToRow.put(reader.getCellInfo(i, cellNum) + "_" + cellValue,
						i);
			}
		}
		return carToRow;
	}

	/**
	 * ��ӡmap�ļ�ֵ��.
	 * 
	 * @param m
	 */
	private void printMap(Map m) {
		Set enteySet = m.entrySet();
		Iterator it = enteySet.iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			System.out.println((String) e.getKey() + "--" + e.getValue());
		}
	} 
 
	public Map getMap(int sheetNum,int cellNum) {
		Map m = new HashMap();
		Set enteySet = carToRow.entrySet(); 
		Iterator it = enteySet.iterator(); 
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String carType = (String) e.getKey();
			int row = (Integer) e.getValue();
			m.put(carType, reader.getCellAsStringByIndex(sheetNum,
					row, cellNum, "0"));
		}
		return m;
	} 
	 
	/**
	 * ���д���ľ��巽���� 
	 * @param newFileName
	 *            �µ��������ڵ��ļ�,�ļ���Ҫ����Ҫ��excel�е�����һ�£�
	 */
	public void console(String dirName) { 
		long start = System.currentTimeMillis();
		File f = new File(dirName); 
		//�����������ڵ��к�.
		int RCell = reader.getNumFromExcelStr(BENYUELEIJI);
		//�����������ڵ��к�.
		int QCell = reader.getNumFromExcelStr(TODAYCOLUMN); 
		//�����������ڵ��к�.
		int VCell = reader.getNumFromExcelStr(TODAYLINGSHOU);
		//�����ۼ����ڵ��к�.
		int WCell = reader.getNumFromExcelStr(LINGSHOULEIJI); 
		//���ն������ڵ��к�.
		int todaySell = reader.getNumFromExcelStr(TODAYSELL);   
		List allSheetNames = reader.getSheetNames(); 
		File[] subFiles = f.listFiles();
		for(int ii=0,jj=subFiles.length;ii<jj;ii++){
			String excelName = subFiles[ii].getName().replace(".xls", "");
			if(excelName.equals("newexcel")){
				continue;
			}
			// �õ��ļ���Ӧ��sheet����.
			int sheetNum = getIndexIgnoreCase(allSheetNames, excelName); 
			if (sheetNum == -1) {
				error("�ļ���" + excelName + "���������󣬺�sheet���ƶ�Ӧ���ϣ����顣");
				continue;
			}
			System.out.println("��ʼ�����ļ���"+excelName+"��...");
			// �õ���Ҫ��excel�еĵ�ǰsheet����ĳ��Ͷ�Ӧ���к���Ϣ.
			Map anayCarToRow = getAnayCarToRow(sheetNum, 9, 115); 
			// �õ���Ҫ��excel�еġ�������������Ϣ��
			Map ORMap = getMap(sheetNum,RCell);  
			// �õ���Ҫ��excel�еġ������ۼơ���Ϣ��
			Map OWMap = getMap(sheetNum,WCell); 
			
			File sub = subFiles[ii];
			FuTe test2 = new FuTe(sub);
			// ��Ҫ���кϲ������excel�ļ�����λ�õĸ�ֵ.
			test2.setCarToRow(anayCarToRow); 
			Map NRMap = test2.getMap(0,RCell); 
			//�����ۼ�
			Map NWMap = test2.getMap(0,WCell);
			Map todaySellMap = test2.getMap(0,todaySell);
			
			if (NWMap.keySet().size() == 0) {
				error("�ļ���" + sub.getName() + "����û���ҵ��������,ԭ���ǣ���һ��sheet�в��������ݡ�");
				return;
			}
			
			Iterator it = anayCarToRow.entrySet().iterator();
			// ѭ��ȫ����Ʒ���������д���.
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// �õ�Ʒ������
				String brand = (String) e.getKey();
				// �õ���Ӧ������.
				int rowNum = (Integer) e.getValue();
				//���������ն������ۼƶ���.
				String oldBenyue = (String) ORMap.get(brand); 
				String newBenyue = (String) NRMap.get(brand);   
				reader.updateCellValue(sheetNum, rowNum, RCell, newBenyue); 
				String newToday = (parseInt(newBenyue)-parseInt(oldBenyue))+"";
				reader.updateCellValue(sheetNum, rowNum, QCell, newToday);
				
				//��������µ��ۼ����ۺͽ�������.
				String oldLinshouSum = (String) OWMap.get(brand); 
				String newLinshouSum = (String) NWMap.get(brand);  
				reader.updateCellValue(sheetNum, rowNum, WCell, newLinshouSum); 
				String newXiaoshou = (parseInt(newLinshouSum)-parseInt(oldLinshouSum))+""; 
				reader.updateCellValue(sheetNum, rowNum, VCell, newXiaoshou);
				
				//���ն�����.
				String todaySellNum = (String) todaySellMap.get(brand);  
				reader.updateCellValue(sheetNum, rowNum, todaySell, todaySellNum); 
			}
		}
		reader.saveAsNewFile(dirName+"\\newexcel.xls"); 
		long end = System.currentTimeMillis();
		error("������ϣ������ʱ��"+(end-start)/1000.0+"�룡");
	}
	
	private void updateTwoColumn(int sheetNum,int oneCol,int sumCol,File f,Map anayCarToRow){
		FuTe test2 = new FuTe(f);
		// ��Ҫ���кϲ������excel�ļ�����λ�õĸ�ֵ.
		test2.setCarToRow(anayCarToRow); 
		// �õ���Ҫ��excel�еġ�������������Ϣ��
		Map ORMap = getMap(sheetNum,sumCol); 
		Map NRMap = test2.getMap(0,sumCol); 
		Iterator it = anayCarToRow.entrySet().iterator();
		// ѭ��ȫ����Ʒ���������д���.
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			// �õ�Ʒ������
			String brand = (String) e.getKey();
			// �õ���Ӧ������.
			int rowNum = (Integer) e.getValue();
			//���������ն������ۼƶ���.
			String oldBenyue = (String) ORMap.get(brand); 
			String newBenyue = (String) NRMap.get(brand);   
			reader.updateCellValue(sheetNum, rowNum, sumCol, newBenyue); 
			String newToday = (parseInt(newBenyue)-parseInt(oldBenyue))+"";
			reader.updateCellValue(sheetNum, rowNum, oneCol, newToday);
		}
	}

	private int parseInt(String s){
		try{
			return Integer.parseInt(s);
		}catch(Exception e){ 
			error("��Ԫ�����������󣬲�������");
			return 0;
		}
	}
	/**
	 * ��һ��list�������ָ�����ַ�������.���Դ�Сд��
	 * 
	 * @param l
	 * @param name
	 * @return
	 */
	public int getIndexIgnoreCase(List l, String name) {
		Iterator it = l.iterator();
		int num = 0;
		while (it.hasNext()) {
			String nm = (String) it.next();
			if (nm.equalsIgnoreCase(name)) {
				return num;
			}
			num++;
		}
		return -1;
	}

	public void error(Object o) {
		System.out.println(o);
		log.append(o+"\n");
	}
	 

	public static void main(String[] arg) throws IOException {
		BufferedReader bufferedreader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
		String fielName="",dirname="",columns=""; 
		boolean done = false,first = true; 
		while (first) {
			try { 
				done = false; 
				while(!done){
					System.out.print("����������excel�ļ�����·��(Ĭ��Ϊ'"+get(SRC,"d:\\����.xls")+"')��");
					fielName = bufferedreader.readLine(); 
					if(Util.isEmpty(fielName)){
						fielName = get(SRC,"d:\\����.xls");
						done = true;
					}else{
						File ff = new File(fielName);
						if(!ff.exists()){
							System.out.println("������ļ���������,���������롣");							
						}else{
							set(SRC,fielName);
							done = true;
						}
					}
				}
				done = false; 
				while(!done){
					System.out.print("������Ҫ�����excel�У�(Ĭ��Ϊ'"+get(COLUMNS,"Q,R,V,W,F")+"'):"); 
					columns = bufferedreader.readLine(); 
					if(Util.isEmpty(columns)){
						columns =get(COLUMNS,"Q,R,V,W,F");
						done = true;
					}else{
						String[] cols = columns.split(","); 
						if(cols.length!=5){
							System.out.println("��������5�У�����������.");							
						}else{
							TODAYCOLUMN = cols[0];
							BENYUELEIJI = cols[1];
							TODAYLINGSHOU = cols[2];
							LINGSHOULEIJI = cols[3];
							TODAYSELL = cols[4];
							set(COLUMNS,"Q,R,V,W,F");
							done = true;
						}
					}
				}
				done = false; 
				while(!done){
					System.out.print("������Ҫ���д���Ķ����Ӧ���ļ����ڵ�Ŀ¼(Ĭ��Ϊ"+get(DIRNAME,"d:\\test")+")��");
					dirname = bufferedreader.readLine(); 
					if(Util.isEmpty(dirname)){
						dirname = get(DIRNAME,"d:\\test");
						done = true;
					}else{
						File ff = new File(dirname);
						if(!ff.exists()){
							System.out.println("�����Ŀ¼������,���������롣");							
						}else{
							if(!ff.isDirectory()){
								System.out.println("����Ĳ���Ŀ¼��ַ,���������롣");	
							}else{
								set(DIRNAME,dirname);
								done = true;
							}
						}
					}
				} 
			}catch(Exception e){
				e.printStackTrace();
			}    
			System.out.println("......��ʼ�����ˣ���ȴ�,���Ͼͺ�.........."); 
			FuTe test = new FuTe(fielName); 
			
			test.check(dirname);
			
			test.console(dirname); 
			
			first = false;
			System.out.println("");
			System.out.println("�鿴��־��d:\\log.text�鿴��־��\n�鿴�����"+dirname+"\\newexcel.xls!\n�س���������.");
			bufferedreader.readLine();
		}  
	}

	/**
	 * ���û���ύ�ļ��Ĺ�Ӧ��.
	 */
	private void check(String dirName){
		List allSheetNames =new ArrayList();
		allSheetNames.addAll(reader.getSheetNames());
		File f = new File(dirName); 
		File[] subFiles = f.listFiles();
		for(int ii=0,jj=subFiles.length;ii<jj;ii++){
			String excelName = subFiles[ii].getName().replace(".xls", "");
			int index=  getIndexIgnoreCase(allSheetNames, excelName);
			//�����sheet�������ҵ�������ļ����ʹ�sheetNames������ɾ��
			if(index>-1)
			{
				allSheetNames.remove(index);
			}			
		}
		Iterator it = allSheetNames.iterator();
		int num = 1;
		while(it.hasNext()){
			String temp = (String)it.next(); 
			if(temp.indexOf("-")==-1&&temp.indexOf("(1)")!=-1)
			error("û���ύ�ļ��ĵ�"+num++ +"����Ӧ�̣���"+temp+"��");
		}
	}
	public Map getCarToRow() {
		return carToRow;
	}

	public void setCarToRow(Map carToRow) {
		this.carToRow = carToRow;
	}
}
