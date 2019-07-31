package com.eanfang.biz.model.bean;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/18  18:45
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class TakeApplyAddBean implements Serializable{

    /**
     * entTaskPublishId : 941286182296756226
     * applyContacts : 张飞
     * applyConstactsPhone : 15940525666
     * applyCompanyName : 张飞有限公司
     * toDoorTime : 2017-12-28
     * predictTime : 50
     * projectQuote : 15000
     * description : 小意思，很好做
     * pictures : 1245
     */

    private Long shopTaskPublishId;
    private String applyContacts;
    private String applyConstactsPhone;
    private String applyCompanyName;
    private String toDoorTime;
    private int predictTime;
    private int projectQuote;
    private String description;
    private String pictures;

    public Long getShopTaskPublishId() {
        return shopTaskPublishId;
    }

    public void setShopTaskPublishId(Long shopTaskPublishId) {
        this.shopTaskPublishId = shopTaskPublishId;
    }

    public String getApplyContacts() {
        return applyContacts == null ? "" : applyContacts;
    }

    public void setApplyContacts(String applyContacts) {
        this.applyContacts = applyContacts;
    }

    public String getApplyConstactsPhone() {
        return applyConstactsPhone == null ? "" : applyConstactsPhone;
    }

    public void setApplyConstactsPhone(String applyConstactsPhone) {
        this.applyConstactsPhone = applyConstactsPhone;
    }

    public String getApplyCompanyName() {
        return applyCompanyName == null ? "" : applyCompanyName;
    }

    public void setApplyCompanyName(String applyCompanyName) {
        this.applyCompanyName = applyCompanyName;
    }

    public String getToDoorTime() {
        return toDoorTime == null ? "" : toDoorTime;
    }

    public void setToDoorTime(String toDoorTime) {
        this.toDoorTime = toDoorTime;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public int getProjectQuote() {
        return projectQuote;
    }

    public void setProjectQuote(int projectQuote) {
        this.projectQuote = projectQuote;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictures() {
        return pictures == null ? "" : pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
}
