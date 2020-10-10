引入依赖
```xml
	    <dependency>
	      <groupId>com.github.chocolatecw</groupId>
	      <artifactId>excelvalidate</artifactId>
	      <version>1.0.0</version>
	    </dependency>
```
使用示例
```java
        CommonExcelReader commonExcelReader = new CommonExcelReader();
        File excelFile = new File("excel文件路径");
        // 实体类字段配置，与excel表格的列一一对应，可使用ConfigBuilder类生成，数组的顺序即表格列的顺序
        List<ColumnConfig> columnConfigList = new ColumnConfig.ConfigBuilder(new String[]{"testPropertyOne",
                "testPropertyTwo", "testPropertyThird", "testPropertyFourth"})
                .setValidator("testPropertyOne", CommonValidator.mobileValidator) // 设置字段的校验器，key为字段名
                .build();

        ExcelImportData<TestExcelEntity> excelImportData = commonExcelReader.read(excelFile, columnConfigList, TestExcelEntity.class);
        if (excelImportData.getFlag() == ExcelImportData.SUCCESS) {
            // 导入成功，数据获取list
            List<TestExcelEntity> dataList = excelImportData.getExcelDataList();
            System.out.println("导入成功!");
        }else {
            // 导入失败，getErrorConnectStr 获取全部错误信息
            System.out.println("导入失败：" + excelImportData.getErrorConnectStr(","));
        }



```
