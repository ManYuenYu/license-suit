package org.dormi.learn.pojo.po;

import org.dormi.learn.pojo.base.BasePO;

public class CommonDict extends BasePO {

    private String catalog;

    private String value;

    private String remark;

    private static final long serialVersionUID = 1L;


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog == null ? null : catalog.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

}