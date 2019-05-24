package com.eanfang.model;

public class CompanyPagesData {

    /**
     * areaCode : 3.11.1.5
     * intro : 北京法安视科技有限公司
     * logoPic : 62fb9a88b0bb4e2a9b131e1170be7f5c.png
     * name : 北京法安视科技有限公司
     * officeAddress : 朝阳区
     * orgId : 979995434422681602
     * scale : 2
     * status : 2
     * telPhone : 18614098179
     * tradeTypeCode : 4.5.3
     * unitType : 3
     */

    private String areaCode;

    public String geteMail() {
        return eMail == null ? "" : eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    private String eMail;
    private String intro;
    private String logoPic;
    private String name;
    private String officeAddress;
    private String orgId;
    private int scale;
    private int status;
    private String telPhone;
    private String tradeTypeCode;
    private int unitType;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLogoPic() {
        return logoPic == null ? "" : logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeAddress() {
        return officeAddress == null ? "" : officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTelPhone() {
        return telPhone == null ? "" : telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "CompanyPagesData{" +
                "areaCode='" + areaCode + '\'' +
                ", intro='" + intro + '\'' +
                ", logoPic='" + logoPic + '\'' +
                ", name='" + name + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", orgId='" + orgId + '\'' +
                ", scale=" + scale +
                ", status=" + status +
                ", telPhone='" + telPhone + '\'' +
                ", tradeTypeCode='" + tradeTypeCode + '\'' +
                ", unitType=" + unitType +
                '}';
    }
}
