package com.eanfang.model;

import java.io.Serializable;

/**
 * 任务发布
 * Created by yaosheng on 2017/6/6.
 */

public class TaskPublishBean implements Serializable {


    /**
     * contacts : 旭神
     * contactsPhone : 15940525612
     * publishCompanyName : 北京法案视
     * projectCompanyName : 双井家乐福
     * zoneCode : 1.1
     * detailPlace : 北京褡裢坡
     * longitude : 15642
     * latitude : 8942532
     * type : 0
     * toDoorTime : 1990-11-03
     * predicttime : 0
     * budget : 0
     * businessOneCode : 1.2
     * description : 需要快速安装
     * pictures : 这里有图片，我们不一样，有啥不一样
     */

    private String contacts;
    private String contactsPhone;
    private String publishCompanyName;
    private String projectCompanyName;
    private String zoneCode;
    private String detailPlace;
    private String longitude;
    private String latitude;
    private int type;
    private String toDoorTime;
    private int predicttime;
    private int budget;
    private String businessOneCode;
    private String description;
    private String pictures;

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getPublishCompanyName() {
        return publishCompanyName;
    }

    public void setPublishCompanyName(String publishCompanyName) {
        this.publishCompanyName = publishCompanyName;
    }

    public String getProjectCompanyName() {
        return projectCompanyName;
    }

    public void setProjectCompanyName(String projectCompanyName) {
        this.projectCompanyName = projectCompanyName;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToDoorTime() {
        return toDoorTime;
    }

    public void setToDoorTime(String toDoorTime) {
        this.toDoorTime = toDoorTime;
    }

    public int getPredicttime() {
        return predicttime;
    }

    public void setPredicttime(int predicttime) {
        this.predicttime = predicttime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getBusinessOneCode() {
        return businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
}
