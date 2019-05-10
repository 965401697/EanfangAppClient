package com.eanfang.model;

/**
 * Created by jornl on 2017/9/7.
 */
public class AddDesignOrderBean {

    /**
     * userName : 万二尔
     * zoneCode : 3.11.3
     * detailPlace : 河北廊坊
     * longitude : 325354
     * latitude : 589567
     * contactUser : 胡老二
     * contact_phone : 15847895422
     * revertTimeLimit : 0
     * businessOneCode : 1.2
     * predictTime : 1
     * budgetLimit : 1
     * remarkInfo : 备注信息啊
     */

    private String userName;
    private String zoneCode;
    private String detailPlace;
    private String longitude;
    private String latitude;
    private String contactUser;
    private String contact_phone;
    private int revertTimeLimit;
    private String businessOneCode;
    private int predictTime;
    private int budgetLimit;
    private String remarkInfo;
    private Long businessOneId;
    private Long zoneId;

    public String getUserName() {
        return this.userName;
    }

    public String getZoneCode() {
        return this.zoneCode;
    }

    public String getDetailPlace() {
        return this.detailPlace;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getContactUser() {
        return this.contactUser;
    }

    public String getContact_phone() {
        return this.contact_phone;
    }

    public int getRevertTimeLimit() {
        return this.revertTimeLimit;
    }

    public String getBusinessOneCode() {
        return this.businessOneCode;
    }

    public int getPredictTime() {
        return this.predictTime;
    }

    public int getBudgetLimit() {
        return this.budgetLimit;
    }

    public String getRemarkInfo() {
        return this.remarkInfo;
    }

    public Long getBusinessOneId() {
        return this.businessOneId;
    }

    public Long getZoneId() {
        return this.zoneId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public void setRevertTimeLimit(int revertTimeLimit) {
        this.revertTimeLimit = revertTimeLimit;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public void setBudgetLimit(int budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public void setBusinessOneId(Long businessOneId) {
        this.businessOneId = businessOneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}
