package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:57
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class AuthCompanyBaseInfoBean implements Serializable {

    /**
     * adminUserId : 954273972575383553
     * areaCode : 3.12.1.2
     * createTime : 2018-01-19 16:46:59
     * intro : 安防公司简单介绍
     * legalName : 天天
     * licenseCode : 88888888
     * licensePic :
     * logoPic :
     * name : 安防公司单位
     * officeAddress : 天津天河区街道办事处
     * orgId : 954273972571189249
     * registerAssets : 1000
     * scale : 1
     * status : 0
     * telPhone : 13819838998
     * tradeTypeCode :
     * unitType : 3
     */

    private Long adminUserId;
    private String areaCode;
    private String createTime;
    private String intro;
    private String legalName;
    private String licenseCode;
    private String licensePic;
    private String logoPic;
    private String name;
    private String officeAddress;
    private Long orgId;
    private String registerAssets;
    private int scale;
    private int status;
    private String telPhone;
    private String tradeTypeCode;
    private int unitType;
    private Long accId;

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAreaCode() {
        return areaCode == null ? "" : areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro == null ? "" : intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLegalName() {
        return legalName == null ? "" : legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLicenseCode() {
        return licenseCode == null ? "" : licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getLicensePic() {
        return licensePic == null ? "" : licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getRegisterAssets() {
        return registerAssets == null ? "" : registerAssets;
    }

    public void setRegisterAssets(String registerAssets) {
        this.registerAssets = registerAssets;
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
        return tradeTypeCode == null ? "" : tradeTypeCode;
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

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }
}
