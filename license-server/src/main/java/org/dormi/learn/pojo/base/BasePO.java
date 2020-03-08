/**
 * @author : dormi330
 * @date : 2020-03-03
 * description : 文件描述
 */

package org.dormi.learn.pojo.base;


import java.util.Date;

public class BasePO extends BasePojo {

    private Long id;
    private Date createdTime;
    private Date updatedTime;
    private int enabled;
    private String remark;

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
