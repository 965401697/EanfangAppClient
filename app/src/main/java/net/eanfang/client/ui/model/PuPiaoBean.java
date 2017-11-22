package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/5.
 */

public class PuPiaoBean implements Serializable {

    /**
     * ordernum : 32020170323161208656505
     * type : 普票
     * unitname : 星巴克
     * postname : 王莉
     * city : 北京
     * zone : 昌平
     * detailplace : 一号大街
     * postphone : 0105268794
     */

    private String ordernum;
    private String type;
    private String unitname;
    private String postname;
    private String city;
    private String zone;
    private String detailplace;
    private String postphone;
    private String taxno;

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
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

    public String getDetailplace() {
        return detailplace;
    }

    public void setDetailplace(String detailplace) {
        this.detailplace = detailplace;
    }

    public String getPostphone() {
        return postphone;
    }

    public void setPostphone(String postphone) {
        this.postphone = postphone;
    }
}
