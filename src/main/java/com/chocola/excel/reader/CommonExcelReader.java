package com.chocola.excel.reader;

import com.chocola.excel.entity.CellInfo;
import com.chocola.excel.entity.ColumnConfig;
import com.chocola.excel.entity.ExcelImportData;
import com.chocola.excel.entity.ExcelUnitData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class CommonExcelReader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @param excelFile 目标excel文件，只读取第一个sheet
     * @param columnConfigList
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> ExcelImportData<T> read(File excelFile, List<ColumnConfig> columnConfigList, Class<T> tClass) {
        Sheet sheet = getSheetByFile(excelFile);
        return read(sheet, columnConfigList, tClass);
    }

    public <T> ExcelImportData<T> read(Sheet sheet, List<ColumnConfig> columnConfigList, Class<T> tClass) {
        ExcelImportData<T> excelImportData = new ExcelImportData<T>();

        int count = getRealRowCount(sheet); // 真实的行数
        if (count < 2) {
            excelImportData.addErrorData("excel表格中没有数据");
            return excelImportData;
        }

        for (int i = 1; i < count; i++) {
            Row row = sheet.getRow(i); // excel中一行数据
            try {
                T data = tClass.newInstance(); // 对应excel中一行数据
                for (ColumnConfig columnConfig : columnConfigList) {
                    Field field = tClass.getDeclaredField(columnConfig.getPropertyName()); // 其中一个字段
                    field.setAccessible(true);
                    int columnIndex = columnConfig.getExcelColumnIndex();
                    CellInfo cellInfo = new CellInfo(i, columnIndex, field.getType(),
                            sheet.getRow(0).getCell(columnIndex).getStringCellValue());

                    ExcelUnitData excelUnitData = getExcelUnitData(row.getCell(columnIndex), cellInfo, columnConfig);

                    if (excelUnitData.getUnitFlag() == ExcelUnitData.SUCCESS) {
                        if (excelUnitData.getData() != null) {
                            field.set(data, excelUnitData.getData()); // 给这个字段赋值
                        }
                    }else {
                        excelImportData.addErrorData(excelUnitData.getErrorInfo());
                    }

                }

                if (excelImportData.getFlag() == ExcelImportData.SUCCESS) {
                    excelImportData.addData(data);
                }

            } catch (IllegalAccessException e) {
                logger.error("read: " + e.getMessage());
            } catch (NoSuchFieldException e) {
                logger.error("read: " + e.getMessage());
            } catch (InstantiationException e) {
                logger.error("read: " + e.getMessage());
            }

        }

        return excelImportData;

    }

    private ExcelUnitData getExcelUnitData(Cell cell, CellInfo cellInfo, ColumnConfig columnConfig) {

        ExcelUnitData excelUnitData = null;

        int cellType = cell.getCellType();
        // 有有效值的单元格类型
        if (cellType == Cell.CELL_TYPE_STRING || cellType == Cell.CELL_TYPE_NUMERIC ||
                cellType == Cell.CELL_TYPE_BOOLEAN || cellType == Cell.CELL_TYPE_FORMULA) {
            if (columnConfig.getExcelValidator() != null) {
                if (ExcelReaderUtil.isEmptyValue(cell)) {
                    excelUnitData = new ExcelUnitData();
                    excelUnitData.setErrorInfo(cellInfo, cellInfo.getColumnName() + "不能为空");
                }else {
                    Object cellValue = ExcelReaderUtil.getCellValue(cell, cellInfo.getPropertyClass(),
                            columnConfig.getStringValueType(), columnConfig.getDateFormat());
                    excelUnitData = columnConfig.getExcelValidator().isPass(cellValue, cell, cellInfo);
                }
            }else {
                excelUnitData = new ExcelUnitData();
                if (ExcelReaderUtil.isEmptyValue(cell)) {
                    if (columnConfig.isNullAbleFlag()) {
                        excelUnitData.setUnitFlag(ExcelUnitData.SUCCESS);
                    }else {
                        excelUnitData.setErrorInfo(cellInfo, cellInfo.getColumnName() + "不能为空");
                    }
                }else {
                    Object cellValue = ExcelReaderUtil.getCellValue(cell, cellInfo.getPropertyClass(),
                            columnConfig.getStringValueType(), columnConfig.getDateFormat());
                    excelUnitData.setData(cellValue);
                }
            }
        }else {
            excelUnitData = new ExcelUnitData();
            excelUnitData.setErrorInfo(cellInfo, cellInfo.getColumnName() + "单元格格式错误");
        }

        return excelUnitData;

    }

    public int getRealRowCount(Sheet sheet) {
        int count = sheet.getLastRowNum();
        for (int i = count; i >= 0 ; i--) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (!ExcelReaderUtil.isEmptyValue(row.getCell(j))) {
                    return i+1;
                }
            }
        }

        return 0;
    }

    public Sheet getSheetByInputStream(InputStream inputStream) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            return xssfWorkbook.getSheetAt(0);
        } catch (IOException e) {
            logger.error("getSheetByInputStream: " + e.getMessage());
        }

        return null;
    }

    public Sheet getSheetByFile(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            return getSheetByInputStream(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("getSheetByFile: " + e.getMessage());
        }
        return null;
    }



}









































