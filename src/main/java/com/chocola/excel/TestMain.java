package com.chocola.excel;

import com.chocola.excel.entity.ColumnConfig;
import com.chocola.excel.entity.ExcelImportData;
import com.chocola.excel.reader.CommonExcelReader;
import com.chocola.excel.reader.CommonValidator;

import java.io.File;
import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        CommonExcelReader commonExcelReader = new CommonExcelReader();

        File currentFile = new File("C:\\Users\\Administrator\\Desktop\\新建文件夹\\distance.xlsx");

        List<ColumnConfig> columnConfigList = new ColumnConfig.ConfigBuilder(new String[]{"testPropertyOne",
                "testPropertyTwo", "testPropertyThird", "testPropertyFourth"})
                .setValidator("testPropertyOne", CommonValidator.mobileValidator)
                .build();

        ExcelImportData<TestExcelEntity> excelImportData = commonExcelReader.read(currentFile, columnConfigList, TestExcelEntity.class);
        if (excelImportData.getFlag() == ExcelImportData.SUCCESS) {
            System.out.println("导入成功!");
        }else {
            System.out.println("导入失败!");
        }

    }


}
