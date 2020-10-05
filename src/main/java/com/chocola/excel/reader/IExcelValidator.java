package com.chocola.excel.reader;

import com.chocola.excel.entity.CellInfo;
import com.chocola.excel.entity.ExcelUnitData;
import org.apache.poi.ss.usermodel.Cell;

public interface IExcelValidator<E> {

    // 校验是否通过
    ExcelUnitData<E> isPass(E cellValue, Cell cell, CellInfo cellInfo);

}














































