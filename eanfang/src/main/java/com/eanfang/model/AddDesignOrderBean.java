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

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public int getRevertTimeLimit() {
        return revertTimeLimit;
    }

    public void setRevertTimeLimit(int revertTimeLimit) {
        this.revertTimeLimit = revertTimeLimit;
    }

    public String getBusinessOneCode() {
        return businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public int getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(int budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }
}
