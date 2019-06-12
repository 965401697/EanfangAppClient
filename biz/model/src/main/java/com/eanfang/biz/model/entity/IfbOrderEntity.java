package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author gaorirong
 * @email 1204290455@qq.com
 * @date 2019-01-08 11:57:52
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IfbOrderEntity implements Serializable {
    /**
     * 招标编号
     */
    private String ifbNum;
    /**
     * 招标单位
     */
    private String ifbCompanyName;
    /**
     * 项目地区=行政区域
     */
    private String projectArea;
    /**
     * 项目地址
     */
    private String projectAddress;
    /**
     * 采购项目名称
     */
    private String purchaseName;
    /**
     * 品目=系统类别（基础数据表）对应（sys_base_data表中的字段）
     */
    private String businessOneCode;
    /**
     * 所属行业类型编码（基础数据sys_base_data）
     */
    private String tradeTypeCode;
    /**
     * 公告名称
     */
    private String announcementName;
    /**
     * 采购单位
     */
    private String purchaseCompanyName;
    /**
     * 招标文件价格
     */
    private BigDecimal ifbFilePrice;
    /**
     * 获取招标文件地点
     */
    private String ifbFileAddress;
    /**
     * 开标时间
     */
    private Date ifbOpenTime;
    /**
     * 开标地点
     */
    private String ifbOpenAddress;
    /**
     * 预算金额
     */
    private BigDecimal budgetPrice;
    /**
     * 项目联系人
     */
    private String ifbContacts;
    /**
     * 项目联系电话
     */
    private String ifbContactPhone;
    /**
     * 代理机构名称
     */
    private String agencyCompanyName;
    /**
     * 采购单位地址
     */
    private String purchaseUnitAddress;
    /**
     * 采购单位联系方式
     */
    private String purchaseUnitPhone;
    /***
     * 代理机构联系方式
     */
    private String agencyPhone;
    /**
     * 代理机构地址
     */
    private String agencyAddress;
    /**
     * 上传附件
     */
    private String ifbFiles;
    /**
     * 公告时间
     */
    private Date announcementTime;
    /**
     * 获取招标文件开始时间
     */
    private Date ifbFileStartTime;
    /**
     * 获取招标文件结束时间
     */
    private Date ifbFileEndTime;
    /**
     * 招标创建时间=发布时间
     */
    private Date createTime;
    /**
     * 发布时间
     */
    private Date releaseTime;
    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 修改人
     */
    private String editUserId;
    /**
     * 修改时间
     */
    private Date editTime;


}
