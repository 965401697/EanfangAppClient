package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/5.
 */

public class ZhuanPiaoBean implements Serializable {

    /**
     * ordernum : 32020170323161545236848
     * type : 专票
     * unitname : 麦当劳
     * taxno : 420107698347902
     * unitplace : 五道口一号
     * unitphone : 0105167458
     * kaihuhang : 工行海淀支行
     * bankaccount : 1566655513
     * postname : 老董
     * city : 北京
     * zone : 海淀
     * detailplace : 一号大街
     * postphone : 0105268494
     */

    private String ordernum;
    private String type;
    private String unitname;
    private String taxno;
    private String unitplace;
    private String unitphone;
    private String kaihuhang;
    private String bankaccount;
    private String postname;
    private String city;
    private String zone;
    private String detailplace;
    private String postphone;

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

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getUnitplace() {
        return unitplace;
    }

    public void setUnitplace(String unitplace) {
        this.unitplace = unitplace;
    }

    public String getUnitphone() {
        return unitphone;
    }

    public void setUnitphone(String unitphone) {
        this.unitphone = unitphone;
    }

    public String getKaihuhang() {
        return kaihuhang;
    }

    public void setKaihuhang(String kaihuhang) {
        this.kaihuhang = kaihuhang;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
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
