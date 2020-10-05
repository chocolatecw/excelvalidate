package com.chocola.excel.entity;

public class CellInfo {

    private int rowIndex; // 第几行

    private int columnIndex; // 第几列

    private Class propertyClass; // 属性类型

    private String columnName; // 列名，即标题行对应的列

    public CellInfo(int rowIndex, int columnIndex, Class propertyClass, String columnName) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.propertyClass = propertyClass;
        this.columnName = columnName;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Class getPropertyClass() {
        return propertyClass;
    }

    public void setPropertyClass(Class propertyClass) {
        this.propertyClass = propertyClass;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


}





















