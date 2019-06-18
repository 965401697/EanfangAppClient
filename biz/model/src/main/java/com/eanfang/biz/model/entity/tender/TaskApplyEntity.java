package com.eanfang.biz.model.entity.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.model.entity.WorkerEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskApplyEntity implements Serializable {

    private Long id;
    /**
     * 报价编号
     */
    private String applyNum;
    /**
     * 任务发布表ID
     */
    private Long shopTaskPublishId;
    /**
     * 联系人==报价人
     */
    private String applyContacts;
    /**
     * 申请联系人的电话
     */
    private String applyConstactsPhone;
    /**
     * 申请单位名==报价单位
     */
    private String applyCompanyName;
    /**
     * 上门时间
     */
    private Date toDoorTime;
    /**
     * 预计工期
     */
    private Integer predictTime;
    /**
     * 项目报价==预算金额
     */
    private BigDecimal projectQuote;
    /**
     * 需求描述==施工方案
     */
    private String description;
    /**
     * 图片地址（多个图片地址用逗号分割）==附件
     */
    private String pictures;
    /**
     * 任务申请的状态==待评标，0待确认，1被确认==待评标，2被忽略,3已完成==已中标
     */
    private Integer status;
    /**
     * 申请确认时间
     */
    private Date confirmTime;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 创建人总公司
     */
    private Long createTopCompanyId;
    /**
     * 创建人部门编码
     */
    private String createOrgCode;
    /**
     * 创建人公司ID
     */
    private Long createCompanyId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 预算单位
     */
    private String budgetUnit;

    @Getter
    @Setter
    private Integer applyCount;

    /**
     * 认证状态
     */
    @Getter
    @Setter
    private Integer verifyStatus;

    private TaskPublishEntity taskPublishEntity;

    @Getter
    @Setter
    private WorkerEntity workerEntity;

    @Getter
    @Setter
    private UserEntity userEntity;

}
