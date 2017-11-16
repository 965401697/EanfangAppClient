package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * 选择地址的bean
 * Created by Administrator on 2017/3/25.
 */

public class SelectAddressItem implements Serializable {

    private String name;
    private String address;
    private String province;
    private String city;
    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double longitude;

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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    private String zone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SelectAddressItem{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", zone='" + zone + '\'' +
                '}';
    }
}
