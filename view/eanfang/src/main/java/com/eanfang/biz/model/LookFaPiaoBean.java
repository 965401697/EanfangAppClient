package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/6/14.
 */

public class LookFaPiaoBean implements Serializable {

    /**
     * bankaccount : 1566655513
     * city : 北京
     * detailplace : 一号大街
     * id : 2
     * kaihuhang : 工行海淀支行
     * ordernum : 32020170323161545236848
     * postname : 老董
     * postphone : 0105268494
     * taxno : 420107698347902
     * type : 专票
     * unitname : 麦当劳
     * unitphone : 0105167458
     * unitplace : 五道口一号
     * zone : 海淀
     */

    private String bankaccount;
    private String city;
    private String detailplace;
    private int id;
    private String kaihuhang;
    private String ordernum;
    private String postname;
    private String postphone;
    private String taxno;
    private String type;
    private String unitname;
    private String unitphone;
    private String unitplace;
    private String zone;

    public String getBankaccount() {
        return bankaccount == null ? "" : bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailplace() {
        return detailplace == null ? "" : detailplace;
    }

    public void setDetailplace(String detailplace) {
        this.detailplace = detailplace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKaihuhang() {
        return kaihuhang == null ? "" : kaihuhang;
    }

    public void setKaihuhang(String kaihuhang) {
        this.kaihuhang = kaihuhang;
    }

    public String getOrdernum() {
        return ordernum == null ? "" : ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getPostname() {
        return postname == null ? "" : postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getPostphone() {
        return postphone == null ? "" : postphone;
    }

    public void setPostphone(String postphone) {
        this.postphone = postphone;
    }

    public String getTaxno() {
        return taxno == null ? "" : taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnitname() {
        return unitname == null ? "" : unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnitphone() {
        return unitphone == null ? "" : unitphone;
    }

    public void setUnitphone(String unitphone) {
        this.unitphone = unitphone;
    }

    public String getUnitplace() {
        return unitplace == null ? "" : unitplace;
    }

    public void setUnitplace(String unitplace) {
        this.unitplace = unitplace;
    }

    public String getZone() {
        return zone == null ? "" : zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
