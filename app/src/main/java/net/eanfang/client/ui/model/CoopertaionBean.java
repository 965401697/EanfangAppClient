package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/2  15:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class CoopertaionBean implements Serializable {

    /**
     * assigneeOrgId : 1100
     * beginTime : 2017-12-12 00:00:00
     * busType : 0
     * businessOneCode : 1.2
     * createTime : 2017-12-26 11:31:39
     * createUserId : 1
     * endTime : 2018-12-12 00:00:00
     * id : 3
     * ownerOrgId : 2110
     * status : 0
     */

    private int assigneeOrgId;
    private String beginTime;
    private int busType;
    private String businessOneCode;
    private String createTime;
    private int createUserId;
    private String endTime;
    private int id;
    private int ownerOrgId;
    private int status;

    public int getAssigneeOrgId() {
        return assigneeOrgId;
    }

    public void setAssigneeOrgId(int assigneeOrgId) {
        this.assigneeOrgId = assigneeOrgId;
    }

    public String getBeginTime() {
        return beginTime;
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
        return businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(int ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

