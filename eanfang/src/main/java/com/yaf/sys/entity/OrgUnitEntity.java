package com.yaf.sys.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.base.entity.ShopCompanyEntity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 组织单位
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-12-20 16:59:55
 */

@TableName(value = "sys_org_unit")
public class OrgUnitEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "org_id", type = IdType.INPUT)
    //组织结构id
    //@TableField(value = "org_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long orgId;

    //管理员
    //@TableField(value = "admin_user_id")
    @NotNull
    @Digits(integer = 19, fraction = 0)
    private Long adminUserId;

    //单位类型0平台总公司1城市平台公司2企事业单位3安防公司
    //@TableField(value = "unit_type")
    @NotNull
    @Digits(integer = 3, fraction = 0)
    private Integer unitType;
    //单位名称
    //@TableField(value = "name")
    @Size(min = 0, max = 50)
    private String name;
    //单位logo图片
    //@TableField(value = "logo_pic")
    @Size(min = 0, max = 50)
    private String logoPic;
    //证件号码
    //@TableField(value = "license_code")
    @Size(min = 0, max = 50)
    private String licenseCode;
    //证件照片
    //@TableField(value = "license_pic")
    @Size(min = 0, max = 50)
    private String licensePic;
    //地区编码
    //@TableField(value = "area_code")
    @Size(min = 0, max = 20)
    private String areaCode;
    //办公地详细地址
    //@TableField(value = "office_address")
    @Size(min = 0, max = 20)
    private String officeAddress;
    //单位电话
    //@TableField(value = "tel_phone")
    @Size(min = 0, max = 15)
    private String telPhone;
    //法人代表
    //@TableField(value = "legal_name")
    @Size(min = 0, max = 5)
    private String legalName;
    //注册资本
    //@TableField(value = "register_assets")
    @Size(min = 0, max = 10)
    private String registerAssets;
    //行业类型编码（基础数据）
    //@TableField(value = "trade_type_code")
    @Size(min = 0, max = 10)
    private String tradeTypeCode;
    //规模0微1小2中3大4超大
    //@TableField(value = "scale")
    @Digits(integer = 3, fraction = 0)
    private Integer scale;
    //公司简介
    //@TableField(value = "intro")
    @Size(min = 0, max = 2000)
    private String intro;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //是否 认领过
    private Integer isclaim;
    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;
    //状态0草稿 1认证中，2认证通过，3认证拒绝，4禁用/删除
    //@TableField(value = "status")
    @Digits(integer = 3, fraction = 0)
    private Integer status;
    //审核时间
    //@TableField(value = "verify_time")
    private Date verifyTime;
    //审核操作人员
    //@TableField(value = "verify_user_name")
    @Size(min = 0, max = 20)
    private String verifyUserName;
    //审核信息
    //@TableField(value = "verify_message")
    @Size(min = 0, max = 200)
    private String verifyMessage;


    public Integer getIsclaim() {
        return isclaim;
    }

    public void setIsclaim(Integer isclaim) {
        this.isclaim = isclaim;
    }

    /**
     * 获取：管理员
     */
    public Long getAdminUserId() {
        return adminUserId;
    }

    /**
     * 设置：管理员
     */
    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    /**
     * 获取：组织结构id
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置：组织结构id
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取：单位类型0平台总公司1城市平台公司2企事业单位3安防公司
     */
    public Integer getUnitType() {
        return unitType;
    }

    /**
     * 设置：单位类型0平台总公司1城市平台公司2企事业单位3安防公司
     */
    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    /**
     * 获取：单位名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：单位名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：单位logo图片
     */
    public String getLogoPic() {
        return logoPic;
    }

    /**
     * 设置：单位logo图片
     */
    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    /**
     * 获取：证件号码
     */
    public String getLicenseCode() {
        return licenseCode;
    }

    /**
     * 设置：证件号码
     */
    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    /**
     * 获取：证件照片
     */
    public String getLicensePic() {
        return licensePic;
    }

    /**
     * 设置：证件照片
     */
    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    /**
     * 获取：地区编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置：地区编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取：办公地详细地址
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * 设置：办公地详细地址
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    /**
     * 获取：单位电话
     */
    public String getTelPhone() {
        return telPhone;
    }

    /**
     * 设置：单位电话
     */
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    /**
     * 获取：法人代表
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * 设置：法人代表
     */
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    /**
     * 获取：注册资本
     */
    public String getRegisterAssets() {
        return registerAssets;
    }

    /**
     * 设置：注册资本
     */
    public void setRegisterAssets(String registerAssets) {
        this.registerAssets = registerAssets;
    }

    /**
     * 获取：行业类型编码（基础数据）
     */
    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    /**
     * 设置：行业类型编码（基础数据）
     */
    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    /**
     * 获取：规模0微1小2中3大4超大
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * 设置：规模0微1小2中3大4超大
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * 获取：公司简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置：公司简介
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：状态0认证中，1认证通过，2认证拒绝，3禁用/删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态0认证中，1认证通过，2认证拒绝，3禁用/删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：审核时间
     */
    public Date getVerifyTime() {
        return verifyTime;
    }

    /**
     * 设置：审核时间
     */
    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    /**
     * 获取：审核操作人员
     */
    public String getVerifyUserName() {
        return verifyUserName;
    }

    /**
     * 设置：审核操作人员
     */
    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName;
    }

    /**
     * 获取：审核信息
     */
    public String getVerifyMessage() {
        return verifyMessage;
    }

    /**
     * 设置：审核信息
     */
    public void setVerifyMessage(String verifyMessage) {
        this.verifyMessage = verifyMessage;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OrgUnitEntity) {
            if (this.adminUserId == null || other == null) {
                return false;
            }

            return this.adminUserId.equals(((OrgUnitEntity) other).adminUserId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adminUserId == null) ? 0 : adminUserId.hashCode());
        return result;
    }

    //手工代码
    @TableField(exist = false)
    private UserEntity userEntity;
    //手工代码
    @TableField(exist = false)
    private ShopCompanyEntity shopCompanyEntity;

    private Long accId;

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public ShopCompanyEntity getShopCompanyEntity() {
        return this.shopCompanyEntity;
    }

    public Long getAccId() {
        return this.accId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setShopCompanyEntity(ShopCompanyEntity shopCompanyEntity) {
        this.shopCompanyEntity = shopCompanyEntity;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }
}