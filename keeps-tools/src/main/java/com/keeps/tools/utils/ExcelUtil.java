package com.keeps.tools.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 
 * <p>Title: CookieUtil.java</p>  
 * <p>Description: 操作工具类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年8月27日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class ExcelUtil {
	/**
	  * @Title:			downloadExeclFile 
	  * @Description:
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月21日
	 */
	public static void downloadExeclFile(HSSFWorkbook workbook,Sheet sheet,String[] titlenames,String filename,HttpServletResponse response){
        sheet.setDefaultColumnWidth((short) 20);

        CellStyle headerStyle = (CellStyle) workbook .createCellStyle();// 创建标题样式
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);	//设置垂直居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);	//设置水平居中
        Font headerFont = (Font) workbook.createFont();	//创建字体样式
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);	//字体加粗
        headerFont.setFontName("宋体");	//设置字体类型
        headerFont.setFontHeightInPoints((short) 13);	//设置字体大小
        headerStyle.setFont(headerFont);	//为标题样式设置字体样式
        
        int k = -1;
    	Row row = sheet.createRow((int)0);//创建一行标题
    	row.setRowStyle(headerStyle);
        for (String titlename : titlenames) {
        	k++;
        	Cell cell = row.createCell((int)k);//创建一列
        	cell.setCellType(Cell.CELL_TYPE_STRING);
        	cell.setCellValue(titlename);
        	cell.setCellStyle(headerStyle);
		}
        try {
        	response.reset();
    		response.setCharacterEncoding("gb2312");
    		response.setHeader("Content-Disposition","attachment;filename="+new String((filename+".xls").getBytes("GBK"),"ISO8859_1"));
			workbook.write(response.getOutputStream());
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * @Title:			createCell 
	  * @Description:
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月21日
	 */
	public static void createCell(Row row,int index,Object value){
		Cell cell = row.createCell((int)index);
    	cell.setCellType(Cell.CELL_TYPE_STRING);
    	cell.setCellValue(value==null?"":String.valueOf(value));
	}
	
	public static void createCell(Row row,int index,Object value,HSSFCellStyle style){
		Cell cell = row.createCell((int)index);
    	cell.setCellType(Cell.CELL_TYPE_STRING);
    	cell.setCellValue(value==null?"":String.valueOf(value));
    	cell.setCellStyle(style);
	}
	
	/**
	  * @Title:			getValue 
	  * @Description:	取单元格值
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月21日
	 */
	@SuppressWarnings("static-access")
	public static String getValue(Cell cell) {
		if (cell == null) {
			return "";
		}
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue()).trim().replace("\n", "");
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
        	if(HSSFDateUtil.isCellDateFormatted(cell)){
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
        	}
        	DecimalFormat df = new DecimalFormat("#.##");  
            return String.valueOf(df.format(cell.getNumericCellValue())).trim().replace("\n", "");
        } else {
            return String.valueOf(cell.getStringCellValue()).trim().replace("\n", "");
        }
    }
	
	
	/**
	  * @Title:			isMergedRow 
	  * @Description:	判断指定的单元格是否是合并单元格
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月14日
	 */
    public static boolean isMergedRegion(Sheet sheet,int rowNum ,int cellNum) {
    	int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();//起始列
            int lastColumn = range.getLastColumn();//结束列
            int firstRow = range.getFirstRow();//起始行
            int lastRow = range.getLastRow();//结束行
            if(rowNum >= firstRow && rowNum <= lastRow){
                if(cellNum >= firstColumn && cellNum <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
    /**
      * @Title:			hasMerged 
      * @Description:	判断是否有合并单元格
      * @param:
      * @return: 
      * @author:		keeps
      * @data:			2016年9月14日
     */
    public static boolean hasMerged(Sheet sheet) {
    	return sheet.getNumMergedRegions() > 0 ? true : false;  
    }
    /**
     * 
      * @Title:			getMergedRegionValue 
      * @Description:	获取合并单元格的值
      * @param:
      * @return: 
      * @author:		keeps
      * @data:			2016年9月14日
     */
    public static String getMergedRegionValue(Sheet sheet,Integer rowNum , Integer cellNum){    
    	int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);    
            int firstColumn = ca.getFirstColumn();    
            int lastColumn = ca.getLastColumn();    
            int firstRow = ca.getFirstRow();    
            int lastRow = ca.getLastRow();    
            if(rowNum >= firstRow && rowNum <= lastRow){
                if(cellNum >= firstColumn && cellNum <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);    
                    Cell fCell = fRow.getCell(firstColumn);    
                    return getValue(fCell);
                }    
            }    
        }
        //没有合并 这样取
        Row fRow = sheet.getRow(rowNum); 
        Cell fCell = fRow.getCell(cellNum);
        return getValue(fCell);    
    }   
}
