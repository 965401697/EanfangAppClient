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

    public AuthCompanyBaseInfoBean(Long adminUserId, String areaCode, String createTime, String intro, String legalName, String licenseCode, String licensePic, String logoPic, String name, String officeAddress, Long orgId, String registerAssets, int scale, int status, String telPhone, String tradeTypeCode, int unitType, Long accId) {
        this.adminUserId = adminUserId;
        this.areaCode = areaCode;
        this.createTime = createTime;
        this.intro = intro;
        this.legalName = legalName;
        this.licenseCode = licenseCode;
        this.licensePic = licensePic;
        this.logoPic = logoPic;
        this.name = name;
        this.officeAddress = officeAddress;
        this.orgId = orgId;
        this.registerAssets = registerAssets;
        this.scale = scale;
        this.status = status;
        this.telPhone = telPhone;
        this.tradeTypeCode = tradeTypeCode;
        this.unitType = unitType;
        this.accId = accId;
    }

    public AuthCompanyBaseInfoBean() {
    }

    public Long getAdminUserId() {
        return this.adminUserId;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public String getIntro() {
        return this.intro;
    }

    public String getLegalName() {
        return this.legalName;
    }

    public String getLicenseCode() {
        return this.licenseCode;
    }

    public String getLicensePic() {
        return this.licensePic;
    }

    public String getLogoPic() {
        return this.logoPic;
    }

    public String getName() {
        return this.name;
    }

    public String getOfficeAddress() {
        return this.officeAddress;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public String getRegisterAssets() {
        return this.registerAssets;
    }

    public int getScale() {
        return this.scale;
    }

    public int getStatus() {
        return this.status;
    }

    public String getTelPhone() {
        return this.telPhone;
    }

    public String getTradeTypeCode() {
        return this.tradeTypeCode;
    }

    public int getUnitType() {
        return this.unitType;
    }

    public Long getAccId() {
        return this.accId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public void setRegisterAssets(String registerAssets) {
        this.registerAssets = registerAssets;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }
}
