package com.chocola.excel.reader;

import com.chocola.excel.entity.CellInfo;
import com.chocola.excel.entity.ExcelUnitData;
import org.apache.poi.ss.usermodel.Row;

public class CommonValidator {

    public static IExcelValidator<String> mobileValidator = new AbstractExcelValidator<String>() {
        @Override
        protected void validateData(String cellValue, ExcelUnitData<String> excelUnitData, CellInfo cellInfo, Row row) {
            if (ValidatorUtil.isMobile(cellValue)) {
                excelUnitData.setData(cellValue);
            }else {
                excelUnitData.setErrorInfo(cellInfo, "手机号格式错误");
            }
        }

    };



}




















