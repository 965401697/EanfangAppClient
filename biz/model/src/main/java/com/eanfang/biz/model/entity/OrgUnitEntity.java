package com.eanfang.biz.model.entity;


import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 组织单位
 *
 * @author xuding
 * @email 29698868@qq.com
 * @date 2017-12-20 16:59:55
 */

@Getter
@Setter
public class OrgUnitEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //组织结构id
    private Long orgId;

    //管理员
    private Long adminUserId;

    //单位类型0平台总公司1城市平台公司2企事业单位3安防公司
    private Integer unitType;
    //单位名称
    private String name;
    //单位logo图片
    private String logoPic;
    //证件号码
    private String licenseCode;
    //证件照片
    private String licensePic;
    //地区编码
    private String areaCode;
    //办公地详细地址
    private String officeAddress;
    //单位电话
    private String telPhone;
    //法人代表
    private String legalName;
    //注册资本
    private String registerAssets;
    //行业类型编码（基础数据）
    private String tradeTypeCode;
    //规模0微1小2中3大4超大
    private Integer scale;
    //公司简介
    private String intro;
    //创建时间
    private Date createTime;
    //电子邮箱
    private String eMail;

    //更新时间
    private Date updateTime;
    //状态0草稿 1认证中，2认证通过，3认证拒绝，4禁用/删除
    private Integer status;
    //审核时间
    private Date verifyTime;
    //审核操作人员
    private String verifyUserName;
    //审核信息
    private String verifyMessage;

    /**
     * 默认省市区县code
     */
    private String defaultPlaceCode;
    /**
     * 默认地址
     */
    private String defaultAddress;
    /**
     * 默认纬度（业务使用）
     */
    private String defaultLat;
    /**
     * 默认经度（业务使用）
     */
    private String defaultLon;
    /**
     * 默认项目ID
     */
    private Long defaultProjectId;
    /**
     * 默认项目名称
     */
    private String defaultProjectName;
    /**
     * 启用禁用
     */
    private Integer isEnable;
    /**
     * 认领状态  0待认领 1 已认领
     */
    private Integer claimStatus;
    /**
     * 认领人ID
     */
    private Long claimUserId;

    /**
     * 注册地址
     */
    private String registerAddress;

    /**
     * 认领人成立时间ID
     */
    private String establishDate;

    /**
     * 截至日期
     */
    private String expirationDate;


    /**
     * APP认领企业列表判断是否是可认领企业 （0可认领，1不可认领）
     */
    private Integer isclaim;


    //手工代码
    private UserEntity userEntity;

    private Long accId;

    private AccountEntity accountEntity;


    //    /**
//     * orgUnit 对应的 org实体
//     */
//    private OrgEntity orgEntity;
//
//
    private ShopCompanyEntity shopCompanyEntity;
//
//    private Integer cooperationStatus;
//    //用于区分当前实体类是否为子公司，（0，总公司。1，子公司）
//    private String son;
//
//    private List<BaseDataEntity> baseDataEntityList;
//
//
//    /**
//     * 公司的荣誉证书
//     */
//    private List<GloryCertificateEntity> gloryList;
//
//    /**
//     * 公司的资质证书
//     */
//    private List<AptitudeCertificateEntity> aptitudeList;

}