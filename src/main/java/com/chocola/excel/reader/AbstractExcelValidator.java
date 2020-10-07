package com.chocola.excel.reader;

import com.chocola.excel.entity.CellInfo;
import com.chocola.excel.entity.ExcelUnitData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public abstract class AbstractExcelValidator<E> implements IExcelValidator<E> {

    @Override
    public final ExcelUnitData<E> isPass(E cellValue, Cell cell, CellInfo cellInfo) {
        ExcelUnitData<E> excelUnitData = new ExcelUnitData<E>();
        validateData(cellValue, excelUnitData, cellInfo, cell.getRow());
        return excelUnitData;

    }

    /**
     * 校验方法，如果需要校验重写这个方法即可
     * @param cellValue 单元格的值
     * @param excelUnitData 单元格读取信息封装，单元格值或错误信息
     * @param cellInfo 单元格信息
     * @param row 单元格所在行
     */
    protected void validateData(E cellValue, ExcelUnitData<E> excelUnitData, CellInfo cellInfo, Row row) {
        excelUnitData.setData(cellValue);
    }



}
































