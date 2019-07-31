package com.eanfang.biz.model.bean;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/24  19:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class SigninBean implements Serializable {

    /**
     * longitude : 123.45
     * latitude : 54.321
     * detailPlace : 菲律宾马尼拉
     * zoneCode : 3.11.1.5
     * signTime : 2018-01-12 10:59:00
     * remarkInfo : 啦啦
     * visitorName : 总统
     * status : 1
     */

    private String longitude;
    private String latitude;
    private String detailPlace;
    private String zoneCode;
    private String signTime;
    private String remarkInfo;
    private String visitorName;
    private int status;
    private String pictures;

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDetailPlace() {
        return detailPlace == null ? "" : detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public String getZoneCode() {
        return zoneCode == null ? "" : zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getSignTime() {
        return signTime == null ? "" : signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getRemarkInfo() {
        return remarkInfo == null ? "" : remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public String getVisitorName() {
        return visitorName == null ? "" : visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPictures() {
        return pictures == null ? "" : pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

}
