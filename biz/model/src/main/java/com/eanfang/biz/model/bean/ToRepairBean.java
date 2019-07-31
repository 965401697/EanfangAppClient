package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ToRepairBean implements Serializable {
    private String company;
    private String detailAddress;
    private String province;
    private String city;
    private String area;
    private String name;
    private String phone;
    private String time;
    private String business;
    private String bugOneUid;
    private String longitude;
    private String latitude;
    private List<AddTroubleBean> beanList = new ArrayList<>();

    public String getCompany() {
        return company == null ? "" : company;
    }

    public String getDetailAddress() {
        return detailAddress == null ? "" : detailAddress;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public String getArea() {
        return area == null ? "" : area;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public String getBusiness() {
        return business == null ? "" : business;
    }

    public String getBugOneUid() {
        return bugOneUid == null ? "" : bugOneUid;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public List<AddTroubleBean> getBeanList() {
        if (beanList == null) {
            return new ArrayList<>();
        }
        return beanList;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public void setBugOneUid(String bugOneUid) {
        this.bugOneUid = bugOneUid;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setBeanList(List<AddTroubleBean> beanList) {
        this.beanList = beanList;
    }
}
