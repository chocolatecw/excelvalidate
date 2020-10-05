package com.chocola.excel.entity;

import java.util.ArrayList;
import java.util.List;

public class ExcelImportData<T> {
    public final static int SUCCESS = 1;
    public final static int FAIL = 0;

    private Integer flag = SUCCESS; // 导入是否成功标识，默认是成功的

    private List<T> dataList = new ArrayList<>(); // 导入成功时的数据list

    private List<String> errorList = new ArrayList<>(); // 错误信息list

    public void addData(T data) {
        this.dataList.add(data);
    }

    public void addErrorData(String error) {
        this.flag = FAIL;
        this.errorList.add(error);
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<T> getExcelDataList() {
        if (flag == SUCCESS) {
            return this.dataList;
        }else {
            return null;
        }
    }

    public List<String> getErrorDataList() {
        if (flag == FAIL) {
            return this.errorList;
        }else {
            return null;
        }
    }

    public String getErrorConnectStr(String conjunction) {
        if (flag == FAIL) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String error : this.errorList) {
                stringBuilder.append(error + conjunction);
            }
            return stringBuilder.toString();
        }else {
            return null;
        }
    }

}
















