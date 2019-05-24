package com.eanfang.biz.model.entity;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShopCompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //组织机构id
    private Long orgId;

    //管理员用户 id
    private Long adminUserId;

    //从业等级
    private Integer workingLevel;
    //从业年限
    private Integer workingYear;
    //法人授权图片
    private String legalAuthorizationPic;
    //荣誉证书，多张图片，逗号分隔
    private String honorPics;
    //资质证书，多张，逗号分隔
    private String aptitudePics;
    //创建时间
    private Date createTime;
    //报修单数量
    private Integer repairCount;
    //报装单数量
    private Integer installCount;
    //设计单数量
    private Integer designCount;
    //维保单数量
    private Integer maintainCount;

    //接包数量
    private Integer receiveCount;
    //好评率
    private Integer goodRate;
    //口碑值
    private Integer publicPraise;
    //评分项1
    private Integer item1;
    //评分项2
    private Integer item2;
    //评分项3
    private Integer item3;
    //评分项4
    private Integer item4;
    //评分项5
    private Integer item5;
    //好评个数
    private Integer goodNum;
    //收到评价数量
    private Integer evaluateNum;
    //整体评价（1：好评，2：中评，3：差评）
    private Integer generalEvaluation;
    //是否生产厂商（1=否，2=是）
    private Integer isManufacturer;
    //是否官网显示（0：不显示，1：显示）
    private Integer showWww;
    // 从业年限
    private int working_level;
    // 能力等级
    private int working_year;
    // 公司类型
    //是否生产厂商
    private int is_manufacturer;


    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    /**
     * 公司组织信息
     */
    private OrgEntity orgEntity;

    /**
     * 公司认证信息
     */
    private OrgUnitEntity companyEntity;

    /**
     * 管理员用户信息
     */
    private UserEntity adminUserEntity;


    private AccountEntity accountEntity;

//    private CooperationEntity cooperationEntity;

    private List<BaseDataEntity> baseDataEntityList;


    /**
     * 好评率百分比展示字段
     */
    private BigDecimal goodReputation;

}
