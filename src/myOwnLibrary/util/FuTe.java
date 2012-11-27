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
	// 车型号所在的列位置
	private static final String CARCOLUMN = "B";
	// 今日销量所在列
	private static  String TODAYCOLUMN = "Q";
	// 本月累计所在列
	private static  String BENYUELEIJI = "R";
	// 今日零售所在列
	private static  String TODAYLINGSHOU = "V";
	// 零售累计所在列
	private static  String LINGSHOULEIJI = "W";
	// 今日订货
	private static  String TODAYSELL = "F";
	
	private static final String SRC = "src";
	private static final String COLUMNS = "col";
	private static final String DIRNAME = "dir";
	// 车型及行号的对应关系.下面的三个map对应的Key都是一样的，都是“单元格位置+车型名称”
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
	 * 得到某个sheet里面的车型的行号.
	 * 
	 * @param sheetNum
	 */
	private Map getAnayCarToRow(int sheetNum, int startRow, int endRow) {
		String sheetName = reader.getSheetName(sheetNum);
		// 这里不是针对每个sheet都重复计算车型对应的行号！！因为每个excel都是一样的！！如果不一样就要去掉这里的判断！！
		if (carToRow.keySet().size() != 0)
			return carToRow;
		int cellNum = reader.getNumFromExcelStr(CARCOLUMN);
		for (int i = startRow; i < endRow; i++) { 
			// 得到车型的描述信息。
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
	 * 打印map的键值对.
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
	 * 进行处理的具体方法。 
	 * @param newFileName
	 *            新的数据所在的文件,文件名要和主要的excel中的名称一致！
	 */
	public void console(String dirName) { 
		long start = System.currentTimeMillis();
		File f = new File(dirName); 
		//本月数据所在的列号.
		int RCell = reader.getNumFromExcelStr(BENYUELEIJI);
		//今日数据所在的列号.
		int QCell = reader.getNumFromExcelStr(TODAYCOLUMN); 
		//今日零售所在的列号.
		int VCell = reader.getNumFromExcelStr(TODAYLINGSHOU);
		//零售累计所在的列号.
		int WCell = reader.getNumFromExcelStr(LINGSHOULEIJI); 
		//今日订货所在的列号.
		int todaySell = reader.getNumFromExcelStr(TODAYSELL);   
		List allSheetNames = reader.getSheetNames(); 
		File[] subFiles = f.listFiles();
		for(int ii=0,jj=subFiles.length;ii<jj;ii++){
			String excelName = subFiles[ii].getName().replace(".xls", "");
			if(excelName.equals("newexcel")){
				continue;
			}
			// 得到文件对应的sheet索引.
			int sheetNum = getIndexIgnoreCase(allSheetNames, excelName); 
			if (sheetNum == -1) {
				error("文件【" + excelName + "】名称有误，和sheet名称对应不上，请检查。");
				continue;
			}
			System.out.println("开始处理文件【"+excelName+"】...");
			// 得到主要的excel中的当前sheet里面的车型对应的行号信息.
			Map anayCarToRow = getAnayCarToRow(sheetNum, 9, 115); 
			// 得到主要的excel中的“本月销量”信息。
			Map ORMap = getMap(sheetNum,RCell);  
			// 得到主要的excel中的“零售累计”信息。
			Map OWMap = getMap(sheetNum,WCell); 
			
			File sub = subFiles[ii];
			FuTe test2 = new FuTe(sub);
			// 对要进行合并处理的excel文件进行位置的赋值.
			test2.setCarToRow(anayCarToRow); 
			Map NRMap = test2.getMap(0,RCell); 
			//零售累计
			Map NWMap = test2.getMap(0,WCell);
			Map todaySellMap = test2.getMap(0,todaySell);
			
			if (NWMap.keySet().size() == 0) {
				error("文件【" + sub.getName() + "】中没有找到相关数据,原因是：第一个sheet中不存在数据。");
				return;
			}
			
			Iterator it = anayCarToRow.entrySet().iterator();
			// 循环全部的品牌行数进行处理.
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// 得到品牌名称
				String brand = (String) e.getKey();
				// 得到对应的行数.
				int rowNum = (Integer) e.getValue();
				//下面计算今日订单和累计订单.
				String oldBenyue = (String) ORMap.get(brand); 
				String newBenyue = (String) NRMap.get(brand);   
				reader.updateCellValue(sheetNum, rowNum, RCell, newBenyue); 
				String newToday = (parseInt(newBenyue)-parseInt(oldBenyue))+"";
				reader.updateCellValue(sheetNum, rowNum, QCell, newToday);
				
				//下面计算新的累计销售和今日销售.
				String oldLinshouSum = (String) OWMap.get(brand); 
				String newLinshouSum = (String) NWMap.get(brand);  
				reader.updateCellValue(sheetNum, rowNum, WCell, newLinshouSum); 
				String newXiaoshou = (parseInt(newLinshouSum)-parseInt(oldLinshouSum))+""; 
				reader.updateCellValue(sheetNum, rowNum, VCell, newXiaoshou);
				
				//今日订货量.
				String todaySellNum = (String) todaySellMap.get(brand);  
				reader.updateCellValue(sheetNum, rowNum, todaySell, todaySellNum); 
			}
		}
		reader.saveAsNewFile(dirName+"\\newexcel.xls"); 
		long end = System.currentTimeMillis();
		error("处理完毕，处理耗时："+(end-start)/1000.0+"秒！");
	}
	
	private void updateTwoColumn(int sheetNum,int oneCol,int sumCol,File f,Map anayCarToRow){
		FuTe test2 = new FuTe(f);
		// 对要进行合并处理的excel文件进行位置的赋值.
		test2.setCarToRow(anayCarToRow); 
		// 得到主要的excel中的“本月销量”信息。
		Map ORMap = getMap(sheetNum,sumCol); 
		Map NRMap = test2.getMap(0,sumCol); 
		Iterator it = anayCarToRow.entrySet().iterator();
		// 循环全部的品牌行数进行处理.
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			// 得到品牌名称
			String brand = (String) e.getKey();
			// 得到对应的行数.
			int rowNum = (Integer) e.getValue();
			//下面计算今日订单和累计订单.
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
			error("单元格中内容有误，不是数字");
			return 0;
		}
	}
	/**
	 * 在一个list里面查找指定的字符串内容.忽略大小写。
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
					System.out.print("请输入上月excel文件所在路径(默认为'"+get(SRC,"d:\\福特.xls")+"')：");
					fielName = bufferedreader.readLine(); 
					if(Util.isEmpty(fielName)){
						fielName = get(SRC,"d:\\福特.xls");
						done = true;
					}else{
						File ff = new File(fielName);
						if(!ff.exists()){
							System.out.println("输入的文件名不存在,请重新输入。");							
						}else{
							set(SRC,fielName);
							done = true;
						}
					}
				}
				done = false; 
				while(!done){
					System.out.print("请输入要处理的excel列，(默认为'"+get(COLUMNS,"Q,R,V,W,F")+"'):"); 
					columns = bufferedreader.readLine(); 
					if(Util.isEmpty(columns)){
						columns =get(COLUMNS,"Q,R,V,W,F");
						done = true;
					}else{
						String[] cols = columns.split(","); 
						if(cols.length!=5){
							System.out.println("必须输入5列，请重新输入.");							
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
					System.out.print("请输入要进行处理的多个供应商文件所在的目录(默认为"+get(DIRNAME,"d:\\test")+")：");
					dirname = bufferedreader.readLine(); 
					if(Util.isEmpty(dirname)){
						dirname = get(DIRNAME,"d:\\test");
						done = true;
					}else{
						File ff = new File(dirname);
						if(!ff.exists()){
							System.out.println("输入的目录不存在,请重新输入。");							
						}else{
							if(!ff.isDirectory()){
								System.out.println("输入的不是目录地址,请重新输入。");	
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
			System.out.println("......开始处理了，请等待,马上就好.........."); 
			FuTe test = new FuTe(fielName); 
			
			test.check(dirname);
			
			test.console(dirname); 
			
			first = false;
			System.out.println("");
			System.out.println("查看日志：d:\\log.text查看日志。\n查看结果："+dirname+"\\newexcel.xls!\n回车结束程序.");
			bufferedreader.readLine();
		}  
	}

	/**
	 * 检查没有提交文件的供应商.
	 */
	private void check(String dirName){
		List allSheetNames =new ArrayList();
		allSheetNames.addAll(reader.getSheetNames());
		File f = new File(dirName); 
		File[] subFiles = f.listFiles();
		for(int ii=0,jj=subFiles.length;ii<jj;ii++){
			String excelName = subFiles[ii].getName().replace(".xls", "");
			int index=  getIndexIgnoreCase(allSheetNames, excelName);
			//如果在sheet名称中找到了这个文件，就从sheetNames集合中删除
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
			error("没有提交文件的第"+num++ +"个供应商：【"+temp+"】");
		}
	}
	public Map getCarToRow() {
		return carToRow;
	}

	public void setCarToRow(Map carToRow) {
		this.carToRow = carToRow;
	}
}
