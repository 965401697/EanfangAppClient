package com.eanfang.biz.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2018/1/25  14:57
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
     * 注册地址  registerAddress
     * 成立日期  establishDate
     * 截至日期 expirationDate
     */

    private Long adminUserId;
    private String areaCode;
    private String eMail;
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
    private String  registerAddress;
    private String  establishDate;
    private String   expirationDate;
    private int unitType;
    private Long accId;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeAddress() {
        return officeAddress;
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
        return registerAssets;
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
        return telPhone;
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

    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }
}
