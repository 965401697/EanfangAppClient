package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by jornl on 2017/9/8.
 */

public class DesignOrderInfoBean implements Serializable {

    /**
     * budgetLimit : 0
     * businessOneCode : 1.1
     * contactPhone : 15940525612
     * contactUser : 李旭
     * createCompanyId : 1100
     * createOrgCode : c.c1.2
     * createTime : 2017-12-14 16:12:49
     * createTopCompanyId : 1000
     * createUserId : 937871079119032321
     * detailPlace : 北京朝阳区褡裢坡
     * id : 1
     * latitude : 758465.01
     * longitude : 542156.01
     * orderNum : TUO45864
     * predictTime : 0
     * remarkInfo : 好好干
     * revertTimeLimit : 0
     * status : 0
     * userName : 旭神
     * zoneCode : 3.10.2
     */

    private int budgetLimit;
    private String businessOneCode;
    private String contactPhone;
    private String contactUser;
    private int createCompanyId;
    private String createOrgCode;
    private String createTime;
    private Long createTopCompanyId;
    private Long createUserId;
    private String detailPlace;
    private Long id;
    private String latitude;
    private String longitude;
    private String orderNum;
    private int predictTime;
    private String remarkInfo;
    private int revertTimeLimit;
    private int status;
    private String userName;
    private String zoneCode;

    public int getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(int budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public String getBusinessOneCode() {
        return businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public int getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(int createCompanyId) {
        this.createCompanyId = createCompanyId;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTopCompanyId() {
        return createTopCompanyId;
    }

    public void setCreateTopCompanyId(Long createTopCompanyId) {
        this.createTopCompanyId = createTopCompanyId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public int getRevertTimeLimit() {
        return revertTimeLimit;
    }

    public void setRevertTimeLimit(int revertTimeLimit) {
        this.revertTimeLimit = revertTimeLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }
}
