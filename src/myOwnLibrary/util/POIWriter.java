package myOwnLibrary.util; 
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * ��Excel2003����д����.
 * @author renjie120
 * connect my:(QQ)1246910068
 *
 */
public class POIWriter{
    /**
     * ������
     */
    private HSSFWorkbook workbook;

    /**
     * ��־��¼
     */
    private Log log = LogFactory.getLog("logger");

    /**
     * excel��
     */
    private HSSFSheet sheet;

    /**
     * excel�ļ���
     */
    private FileInputStream fis;

    /**
     * ��Ϣ
     */
    private StringBuffer msg = null;

    public POIWriter()
    {
            
    }
    /**
     * ��һ��javaBean�ļ�����Ŀ���ļ�д����
     * @param data һ��װ��javaBean��list
     * @param targetFile Ŀ��excel�ļ�
     */
    public String setSheetFromTable(List data, String targetFile) {
            try {
                    HSSFWorkbook targetWorkbook = new HSSFWorkbook();
                    FileOutputStream fout = new FileOutputStream(targetFile,true);
                    setDataToWorksheet(data, targetWorkbook, 0);
                    targetWorkbook.write(fout);
                    fout.flush();
                    fout.close();
                    return "ok";
            } catch (Exception e) {
                    log.error("�����쳣", e);
                    return "";
            }
    }

    /**
     * ��list�����ݷŽ�excel��һ����������ȥ��
     * @param data ���ݵ���Դlist����һ�����������ݿ��װ����javabean��list
     * @param workbook Ŀ��excel������
     * @param sheetNum Ŀ��excel�������ı��Ҫ��д���ݵ�ҳ��
     */
    public void setDataToWorksheet(List data,HSSFWorkbook workbook,int sheetNum)
    {
            HSSFRow title = null;
            HSSFSheet sheet = null;
            try{
            if(data.size()<1)
            {
                    return ;
            }
            sheet = workbook.createSheet();
            
            //��������cell�����ָ�ʽ
            font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setUnderline((byte)1);
            font.setColor(HSSFColor.BLUE.index);
            //�������ñ����е���ʽ                
            titleStyle = workbook.createCellStyle();
            titleStyle.setBorderBottom((short)1);
            titleStyle.setBorderLeft((short)1);
            titleStyle.setBorderRight((short)1);
            titleStyle.setBorderTop((short)1);
            titleStyle.setFont(font);
            titleStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
            titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            
            //ȡlist�еĵ�һ�����ݣ������������ƵĶ�ȡ��׼���ŵ�excel����еĵ�һ��
            Object aData = data.get(0);
            PropertyDescriptor[] props = Introspector.getBeanInfo(
                            aData.getClass(), Object.class).getPropertyDescriptors();
            //�ڱ��ĵ�һ�н���һ�������У�����������Щ���Ե�����
            title = sheet.createRow(0);
            //�����и�.ע��ֵ���õĺܴ󡣡�
            title.setHeight((short)500);
            for(short temp = 0; temp < props.length; temp++)
            {        HSSFCell cell = title.createCell(temp);
                    cell.setCellStyle(titleStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(props[temp].getShortDescription());
                    //���ø����еĿ��
                    sheet.setColumnWidth((short)temp, (short)5000);
            }
            for(int temp = 0;temp<data.size();temp++)
            {
                    //ʵ�ʵ������ǿ�ʼ�ӵڶ��п�ʼ���д��ݵ�
                    HSSFRow row = sheet.createRow(temp+1);
                    //ȡ��javabean��������ĸ������Ե�ֵ
                    Object obj = data.get(temp);
                    String values[] = getPropertyOfBean(obj);
                    for(short cellNum=0;cellNum<values.length;cellNum++){
                            HSSFCell cell = row.createCell(cellNum);
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(values[cellNum]);
                    }
            }
            }catch(Exception e)
            {
                    log.error("����bug",e);
            }
    }

    HSSFFont font = null;
    HSSFCellStyle titleStyle = null;
    /**
     * ����excel������ʽ
     *
     */
    private void setStyle()
    {
            
    }
    
    /**
     * ����һ��javabean���󣬷���������������ֵ���ϣ�ʹ�õ�������ơ�
     * @return
     */
    private String[] getPropertyOfBean(Object obj) {
            String[] result = null;
            try {
                    PropertyDescriptor[] props = Introspector.getBeanInfo(
                                    obj.getClass(), Object.class).getPropertyDescriptors();
                    result = new String[props.length];
                    for (int temp = 0; temp < props.length; temp++) {
                            try {
                                    result[temp] = props[temp].getReadMethod().invoke(obj)
                                                    .toString();
                            } catch (Exception e) {
                                    log.error("�����쳣", e);
                                    return null;
                            }
                    }
            } catch (Exception e1) {
                    log.error("�����쳣", e1);
                    return null;
            }
            return result;
    }
}