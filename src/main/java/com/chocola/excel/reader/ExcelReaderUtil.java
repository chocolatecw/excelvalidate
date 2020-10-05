package com.chocola.excel.reader;

import com.chocola.excel.entity.CellInfo;
import com.chocola.excel.entity.ExcelUnitData;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelReaderUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);

    public static boolean isEmptyValue(Cell cell) {
        if (cell == null) {
            return true;
        }
        Object cellValue = null;
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        }else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) { // 有多种类型值的可能
            cellValue = cell.getNumericCellValue();
        }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            cellValue = cell.getCellFormula();
        }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        }else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {

        }else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
            cellValue = cell.getErrorCellValue();
        }

        return cellValue == null;

    }

    // 空字段处理
    public static void feildNullDeal(boolean isNullAble, ExcelUnitData excelUnitData, CellInfo cellInfo) {
        if (!isNullAble) { // 不允许为空
            excelUnitData.setErrorInfo(cellInfo, cellInfo.getColumnName() + "不能为空");
        }else { // 允许为空
            excelUnitData.setUnitFlag(ExcelUnitData.SUCCESS);
        }
    }

    // double原样转String
    public static String doubleToString(double d) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMaximumFractionDigits(15);
        return decimalFormat.format(d);
    }

    /**
     * Date 转 String
     * @param date 日期
     * @param pattern 模式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String result = simpleDateFormat.format(date);

        return result;

    }

    /**
     * 获得与属性类型相匹配的单元格值，单元格类型必须有有效值
     * @param cell 单元格
     * @param propertyClass 属性类型
     * @return
     */
    public static Object getCellValue(Cell cell, Class propertyClass) {
        return getCellValue(cell, propertyClass, 0, "yyyy-MM-dd HH:mm");
    }

    /**
     * 获得与属性类型相匹配的单元格值，单元格类型必须有有效值
     * @param cell 单元格
     * @param propertyClass 属性类型
     * @param stringValueType CELL_TYPE_STRING 对应的取值类型（0-String, 1-RichString）
     * @param dateFormat 日期类型，Date转String 格式字符串
     * @return
     */
    public static Object getCellValue(Cell cell, Class propertyClass, int stringValueType, String dateFormat) {
        Object result = null;

        try {
            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                if (stringValueType == 1) { // 富文本类型
                    if (propertyClass == String.class) {
                        result = cell.getRichStringCellValue().getString();
                    }else {
                        throw new ExcelReaderException("富文本类型只支持String类型");
                    }
                }else {
                    if (propertyClass == String.class) {
                        result = cell.getStringCellValue();
                    }else {
                        throw new ExcelReaderException("字符串类型只支持String类型");
                    }
                }
            }else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) { // 有多种类型值的可能
                if (HSSFDateUtil.isCellDateFormatted(cell)) { // 日期类型
                    if (propertyClass == String.class) {
                        result = ExcelReaderUtil.dateToString(cell.getDateCellValue(), dateFormat);
                    }else if (propertyClass == Date.class) {
                        result = cell.getDateCellValue();
                    }else {
                        throw new ExcelReaderException("日期类型只支持String与Date类型");
                    }
                }else { // 数值类型
                    if (propertyClass == double.class || propertyClass == Double.class) {
                        result = cell.getNumericCellValue();
                    }else if (propertyClass == String.class) {
                        result = ExcelReaderUtil.doubleToString(cell.getNumericCellValue());
                    }else {
                        throw new ExcelReaderException("数值类型只支持double，Double与String类型");
                    }
                }

            }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                if (propertyClass == String.class) {
                    result = cell.getCellFormula();
                }else {
                    throw new ExcelReaderException("公式类型只支持String类型");
                }
            }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                if (propertyClass == boolean.class || propertyClass == Boolean.class) {
                    result = cell.getBooleanCellValue();
                }else {
                    throw new ExcelReaderException("布尔类型只支持boolean与Boolean类型");
                }
            }

        }catch (Exception e) {
            logger.error("getCellValue: " + e.getMessage());
        }

        return result;

    }




}
