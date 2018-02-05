package com.eanfang.model;

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

    public String getBugOneUid() {
        return bugOneUid;
    }

    public void setBugOneUid(String bugOneUid) {
        this.bugOneUid = bugOneUid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public List<AddTroubleBean> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<AddTroubleBean> beanList) {
        this.beanList = beanList;
    }
}
