package com.chocola.excel.entity;

public class ExcelUnitData<E> {

    public final static int SUCCESS = 1;
    public final static int FAIL = 0;

    // 返回信息标识 0-错误信息，1-数据
    private Integer unitFlag = SUCCESS;

    private String errorInfo;

    // 某一个属性的值
    private E data;

    public Integer getUnitFlag() {
        return unitFlag;
    }

    public void setUnitFlag(Integer unitFlag) {
        this.unitFlag = unitFlag;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.unitFlag = FAIL;
        this.errorInfo = errorInfo;
    }

    public void setErrorInfo(CellInfo cellInfo, String errorInfo) {
        this.unitFlag = FAIL;
        this.errorInfo = "第" + (cellInfo.getRowIndex()+1) + "行第" + (cellInfo.getColumnIndex()+1) + "列" + errorInfo;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.unitFlag = SUCCESS;
        this.data = data;
    }
}





































































