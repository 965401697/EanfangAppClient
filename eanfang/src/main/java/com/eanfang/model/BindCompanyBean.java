package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  22:03
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class BindCompanyBean implements Serializable{

    /**
     * assigneeOrgId : 1100
     * beginTime : 2017-12-12
     * busType : 0
     * businessOneCode : 1.3
     * endTime : 2018-12-12
     * ownerOrgId : 2110
     * status : null
     */

    private Long assigneeOrgId;
    private String beginTime;
    private int busType;
    private String businessOneCode;
    private String endTime;
    private Long ownerOrgId;
    private int status;

    public Long getAssigneeOrgId() {
        return assigneeOrgId;
    }

    public void setAssigneeOrgId(Long assigneeOrgId) {
        this.assigneeOrgId = assigneeOrgId;
    }

    public String getBeginTime() {
        return beginTime == null ? "" : beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getBusType() {
        return busType;
    }

    public void setBusType(int busType) {
        this.busType = busType;
    }

    public String getBusinessOneCode() {
        return businessOneCode == null ? "" : businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
