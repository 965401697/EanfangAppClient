package com.eanfang.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author gaorirong
 * @email 1204290455@qq.com
 * @date 2019-01-08 11:57:52
 */
public class IfbOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键，序号
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //招标编号
    //@TableField(value = "ifb_num")
    private String ifbNum;
    //招标单位
    //@TableField(value = "ifb_company_name")
    private String ifbCompanyName;
    //项目地区=行政区域
    //@TableField(value = "project_area")
    private String projectArea;
    //项目地址
    //@TableField(value = "project_address")
    private String projectAddress;
    //采购项目名称
    //@TableField(value = "purchase_name")
    private String purchaseName;
    //品目=系统类别（基础数据表）对应（sys_base_data表中的字段）
    //@TableField(value = "business_one_code")
    private String businessOneCode;
    //所属行业类型编码（基础数据sys_base_data）
    //@TableField(value = "trade_type_code")
    private String tradeTypeCode;
    //公告名称
    //@TableField(value = "announcement_name")
    private String announcementName;
    //采购单位
    //@TableField(value = "purchase_company_name")
    private String purchaseCompanyName;
    //招标文件价格
    //@TableField(value = "ifb_file_price")
    private BigDecimal ifbFilePrice;
    //获取招标文件地点
    //@TableField(value = "ifb_file_address")
    private String ifbFileAddress;
    //开标时间
    //@TableField(value = "ifb_open_time")
    private Date ifbOpenTime;
    //开标地点
    //@TableField(value = "ifb_open_address")
    private String ifbOpenAddress;
    //预算金额
    //@TableField(value = "budget_price")
    private BigDecimal budgetPrice;
    //项目联系人
    //@TableField(value = "ifb_contacts")
    private String ifbContacts;
    //项目联系电话
    //@TableField(value = "ifb_contact_phone")
    private String ifbContactPhone;
    //代理机构名称
    //@TableField(value = "Agency_company_name")
    private String agencyCompanyName;
    //采购单位地址
    //@TableField(value = "purchase_unit_address")
    private String purchaseUnitAddress;
    //采购单位联系方式
    //@TableField(value = "purchase_unit_phone")
    private String purchaseUnitPhone;
    //代理机构联系方式
    //@TableField(value = "Agency_phone")
    private String agencyPhone;
    //代理机构地址
    //@TableField(value = "Agency_address")
    private String agencyAddress;
    //上传附件
    //@TableField(value = "ifb_files")
    private String ifbFiles;
    //公告时间
    //@TableField(value = "announcement_time")
    private Date announcementTime;
    //获取招标文件开始时间
    //@TableField(value = "ifb_file_start_time")
    private Date ifbFileStartTime;
    //获取招标文件结束时间
    //@TableField(value = "ifb_file_end_time")
    private Date ifbFileEndTime;
    //招标创建时间=发布时间
    //@TableField(value = "create_time")
    private Date createTime;
    //发布时间
    //@TableField(value = "release_time")
    private Date releaseTime;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //修改人
    //@TableField(value = "edit_user_id")
    private Long editUserId;
    //修改时间
    //@TableField(value = "edit_time")
    private Date editTime;

    /**
     * 设置：主键，序号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键，序号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：招标编号
     */
    public void setIfbNum(String ifbNum) {
        this.ifbNum = ifbNum;
    }

    /**
     * 获取：招标编号
     */
    public String getIfbNum() {
        return ifbNum;
    }

    /**
     * 设置：招标单位
     */
    public void setIfbCompanyName(String ifbCompanyName) {
        this.ifbCompanyName = ifbCompanyName;
    }

    /**
     * 获取：招标单位
     */
    public String getIfbCompanyName() {
        return ifbCompanyName;
    }

    /**
     * 设置：项目地区=行政区域
     */
    public void setProjectArea(String projectArea) {
        this.projectArea = projectArea;
    }

    /**
     * 获取：项目地区=行政区域
     */
    public String getProjectArea() {
        return projectArea;
    }

    /**
     * 设置：项目地址
     */
    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    /**
     * 获取：项目地址
     */
    public String getProjectAddress() {
        return projectAddress;
    }

    /**
     * 设置：采购项目名称
     */
    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    /**
     * 获取：采购项目名称
     */
    public String getPurchaseName() {
        return purchaseName;
    }

    /**
     * 设置：品目=系统类别（基础数据表）对应（sys_base_data表中的字段）
     */
    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    /**
     * 获取：品目=系统类别（基础数据表）对应（sys_base_data表中的字段）
     */
    public String getBusinessOneCode() {
        return businessOneCode;
    }

    /**
     * 设置：所属行业类型编码（基础数据sys_base_data）
     */
    public void setTradeTypeCode(String tradeTypeCode) {
        this.tradeTypeCode = tradeTypeCode;
    }

    /**
     * 获取：所属行业类型编码（基础数据sys_base_data）
     */
    public String getTradeTypeCode() {
        return tradeTypeCode;
    }

    /**
     * 设置：公告名称
     */
    public void setAnnouncementName(String announcementName) {
        this.announcementName = announcementName;
    }

    /**
     * 获取：公告名称
     */
    public String getAnnouncementName() {
        return announcementName;
    }

    /**
     * 设置：采购单位
     */
    public void setPurchaseCompanyName(String purchaseCompanyName) {
        this.purchaseCompanyName = purchaseCompanyName;
    }

    /**
     * 获取：采购单位
     */
    public String getPurchaseCompanyName() {
        return purchaseCompanyName;
    }

    /**
     * 设置：招标文件价格
     */
    public void setIfbFilePrice(BigDecimal ifbFilePrice) {
        this.ifbFilePrice = ifbFilePrice;
    }

    /**
     * 获取：招标文件价格
     */
    public BigDecimal getIfbFilePrice() {
        return ifbFilePrice;
    }

    /**
     * 设置：获取招标文件地点
     */
    public void setIfbFileAddress(String ifbFileAddress) {
        this.ifbFileAddress = ifbFileAddress;
    }

    /**
     * 获取：获取招标文件地点
     */
    public String getIfbFileAddress() {
        return ifbFileAddress;
    }

    /**
     * 设置：开标时间
     */
    public void setIfbOpenTime(Date ifbOpenTime) {
        this.ifbOpenTime = ifbOpenTime;
    }

    /**
     * 获取：开标时间
     */
    public Date getIfbOpenTime() {
        return ifbOpenTime;
    }

    /**
     * 设置：开标地点
     */
    public void setIfbOpenAddress(String ifbOpenAddress) {
        this.ifbOpenAddress = ifbOpenAddress;
    }

    /**
     * 获取：开标地点
     */
    public String getIfbOpenAddress() {
        return ifbOpenAddress;
    }

    /**
     * 设置：预算金额
     */
    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }

    /**
     * 获取：预算金额
     */
    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    /**
     * 设置：项目联系人
     */
    public void setIfbContacts(String ifbContacts) {
        this.ifbContacts = ifbContacts;
    }

    /**
     * 获取：项目联系人
     */
    public String getIfbContacts() {
        return ifbContacts;
    }

    /**
     * 设置：项目联系电话
     */
    public void setIfbContactPhone(String ifbContactPhone) {
        this.ifbContactPhone = ifbContactPhone;
    }

    /**
     * 获取：项目联系电话
     */
    public String getIfbContactPhone() {
        return ifbContactPhone;
    }

    /**
     * 设置：代理机构名称
     */
    public void setAgencyCompanyName(String agencyCompanyName) {
        this.agencyCompanyName = agencyCompanyName;
    }

    /**
     * 获取：代理机构名称
     */
    public String getAgencyCompanyName() {
        return agencyCompanyName;
    }

    /**
     * 设置：采购单位地址
     */
    public void setPurchaseUnitAddress(String purchaseUnitAddress) {
        this.purchaseUnitAddress = purchaseUnitAddress;
    }

    /**
     * 获取：采购单位地址
     */
    public String getPurchaseUnitAddress() {
        return purchaseUnitAddress;
    }

    /**
     * 设置：采购单位联系方式
     */
    public void setPurchaseUnitPhone(String purchaseUnitPhone) {
        this.purchaseUnitPhone = purchaseUnitPhone;
    }

    /**
     * 获取：采购单位联系方式
     */
    public String getPurchaseUnitPhone() {
        return purchaseUnitPhone;
    }

    /**
     * 设置：代理机构联系方式
     */
    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone;
    }

    /**
     * 获取：代理机构联系方式
     */
    public String getAgencyPhone() {
        return agencyPhone;
    }

    /**
     * 设置：代理机构地址
     */
    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress;
    }

    /**
     * 获取：代理机构地址
     */
    public String getAgencyAddress() {
        return agencyAddress;
    }

    /**
     * 设置：上传附件
     */
    public void setIfbFiles(String ifbFiles) {
        this.ifbFiles = ifbFiles;
    }

    /**
     * 获取：上传附件
     */
    public String getIfbFiles() {
        return ifbFiles;
    }

    /**
     * 设置：公告时间
     */
    public void setAnnouncementTime(Date announcementTime) {
        this.announcementTime = announcementTime;
    }

    /**
     * 获取：公告时间
     */
    public Date getAnnouncementTime() {
        return announcementTime;
    }

    /**
     * 设置：获取招标文件开始时间
     */
    public void setIfbFileStartTime(Date ifbFileStartTime) {
        this.ifbFileStartTime = ifbFileStartTime;
    }

    /**
     * 获取：获取招标文件开始时间
     */
    public Date getIfbFileStartTime() {
        return ifbFileStartTime;
    }

    /**
     * 设置：获取招标文件结束时间
     */
    public void setIfbFileEndTime(Date ifbFileEndTime) {
        this.ifbFileEndTime = ifbFileEndTime;
    }

    /**
     * 获取：获取招标文件结束时间
     */
    public Date getIfbFileEndTime() {
        return ifbFileEndTime;
    }

    /**
     * 设置：招标创建时间=发布时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：招标创建时间=发布时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：发布时间
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 获取：发布时间
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置：创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：修改人
     */
    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    /**
     * 获取：修改人
     */
    public Long getEditUserId() {
        return editUserId;
    }

    /**
     * 设置：修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IfbOrderEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((IfbOrderEntity) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
