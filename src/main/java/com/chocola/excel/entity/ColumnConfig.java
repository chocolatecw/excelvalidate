package com.chocola.excel.entity;

import com.chocola.excel.reader.IExcelValidator;

import java.util.*;

public class ColumnConfig {
    // 实体类对应的属性名
    private String propertyName;

    // 对应excel中的列序号
    private Integer excelColumnIndex;

    private int stringValueType; // CELL_TYPE_STRING 对应的取值类型（0-String, 1-RichString）

    private String dateFormat = "yyyy-MM-dd HH:mm";   // 日期类型，Date转String 格式字符串，默认值yyyy-MM-dd HH:mm

    private boolean nullAbleFlag = false; // 是否可空，默认不可空

    // 校验器
    private IExcelValidator excelValidator;

    public ColumnConfig(String propertyName, Integer excelColumnIndex) {
        this.propertyName = propertyName;
        this.excelColumnIndex = excelColumnIndex;
    }

    public ColumnConfig(String propertyName, Integer excelColumnIndex, IExcelValidator excelValidator) {
        this.propertyName = propertyName;
        this.excelColumnIndex = excelColumnIndex;
        this.excelValidator = excelValidator;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Integer getExcelColumnIndex() {
        return excelColumnIndex;
    }

    public void setExcelColumnIndex(Integer excelColumnIndex) {
        this.excelColumnIndex = excelColumnIndex;
    }

    public IExcelValidator getExcelValidator() {
        return excelValidator;
    }

    public void setExcelValidator(IExcelValidator excelValidator) {
        this.excelValidator = excelValidator;
    }

    public int getStringValueType() {
        return stringValueType;
    }

    public void setStringValueType(int stringValueType) {
        this.stringValueType = stringValueType;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isNullAbleFlag() {
        return nullAbleFlag;
    }

    public void setNullAbleFlag(boolean nullAbleFlag) {
        this.nullAbleFlag = nullAbleFlag;
    }


    public static class ConfigBuilder {

        private Map<String, ColumnConfig> columnConfigMap = new HashMap<String, ColumnConfig>();

        public ConfigBuilder(String[] propertyNameArr) {
            if (propertyNameArr != null && propertyNameArr.length > 0) {
                for (int i = 0; i < propertyNameArr.length; i++) {
                    String propertyName = propertyNameArr[i];
                    ColumnConfig columnConfig = new ColumnConfig(propertyName, i);
                    columnConfigMap.put(propertyName, columnConfig);
                }
            }
        }

        public ConfigBuilder setValidator(String propertyName, IExcelValidator iExcelValidator) {
            if (propertyName == null || iExcelValidator == null) {
                return this;
            }
            ColumnConfig columnConfig = columnConfigMap.get(propertyName);
            if (columnConfig != null) {
                columnConfig.setExcelValidator(iExcelValidator);
            }
            return this;
        }

        public ConfigBuilder setStringValueType(String propertyName, int type) {
            if (propertyName == null || (type != 0 && type != 1)) {
                return this;
            }
            ColumnConfig columnConfig = columnConfigMap.get(propertyName);
            if (columnConfig != null) {
                columnConfig.setStringValueType(type);
            }
            return this;
        }

        public ConfigBuilder setDateFormat(String propertyName, String dateFormat) {
            if (propertyName == null || dateFormat == null) {
                return this;
            }
            ColumnConfig columnConfig = columnConfigMap.get(propertyName);
            if (columnConfig != null) {
                columnConfig.setDateFormat(dateFormat);
            }
            return this;
        }

        public ConfigBuilder setNullAbleFlag(String propertyName, boolean nullAbleFlag) {
            if (propertyName == null) {
                return this;
            }
            ColumnConfig columnConfig = columnConfigMap.get(propertyName);
            if (columnConfig != null) {
                columnConfig.setNullAbleFlag(nullAbleFlag);
            }
            return this;
        }

        public List<ColumnConfig> build() {
            List<ColumnConfig> columnConfigList = new ArrayList<ColumnConfig>(columnConfigMap.values());

            Collections.sort(columnConfigList, new Comparator<ColumnConfig>() {
                public int compare(ColumnConfig o1, ColumnConfig o2) {
                    return o1.getExcelColumnIndex() - o2.getExcelColumnIndex();
                }

            });

            return columnConfigList;
        }


    }


}

















