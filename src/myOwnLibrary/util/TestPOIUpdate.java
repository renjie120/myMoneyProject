package myOwnLibrary.util; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2011-10-10
 * Time: 16:10:29
 * To change this template use File | Settings | File Templates.
 */
public class TestPOIUpdate {

    /**
     * ֻ��һ��demo����������޸ĵ�ֵ��String����
     * @param exlFile
     * @param sheetIndex
     * @param col
     * @param row
     * @param value
     * @throws Exception
     */
    public static void updateExcel(File exlFile,int sheetIndex,int col,int row,String value)throws Exception{
        FileInputStream fis=new FileInputStream(exlFile);
        HSSFWorkbook workbook=new HSSFWorkbook(fis);
//        workbook.
        HSSFSheet sheet=workbook.getSheetAt(sheetIndex);

        HSSFRow r=sheet.getRow(row);
        HSSFCell cell=r.getCell(col);
//        int type=cell.getCellType();
        String str1=cell.getStringCellValue();
        //��������Ӧ��Ԫ��ԭ��������Ҳ��String����
        cell.setCellValue(value);
        System.out.println("��Ԫ��ԭ��ֵΪ"+str1);
        System.out.println("��Ԫ��ֵ������Ϊ"+value);

        fis.close();//�ر��ļ�������

        FileOutputStream fos=new FileOutputStream(exlFile);
        workbook.write(fos);
        fos.close();//�ر��ļ������
    }


    private String getCellValue(HSSFCell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                cellValue = df.format(cell.getNumericCellValue()).toString();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub

//            ����ĳ����Լ���xls�ļ����в��ԣ�2003��ʽ�ģ�����2007
            File file=new File("f:\\f2.xls");

         //���波�Ը��ĵ�һ�е�һ�еĵ�Ԫ���ֵ
            TestPOIUpdate.updateExcel(file,23,3,7,"11111");
    }
}