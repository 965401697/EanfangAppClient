package com.eanfang.biz.model.entity.tender;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.model.entity.WorkerEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 任务发布表
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskPublishEntity implements Serializable {

    private Long id;
    /**
     * 发布招标编号
     */
    private String publishNum;
    /**
     * 联系人==发布人：
     */
    private String contacts;
    /**
     * 联系人电话
     */
    private String contactsPhone;
    /**
     * 任务发布单位名==用工单位：
     */
    private String publishCompanyName;
    /**
     * 项目单位名
     */
    private String projectCompanyName;
    /**
     * 详细地址==项目地址
     */
    private String province;
    /**
     * 收获城市
     */
    private String city;
    /**
     * 收获区县
     */
    private String county;
    private String detailPlace;
    /**
     * 区（县）编码(基础数据表)
     */
    private String zoneCode;
    /**
     * 区（县）ID
     */
    private Long zoneId;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 项目类型（0安装，1维修）
     */
    private Integer type;
    /**
     * 上门时间
     */
    private Date toDoorTime;
    /**
     * 预计工期（0当天完工，1三天左右，2一周左右，3一个月左右，4三个月左右，5六个月左右，6一年左右，7一年以上）
     */
    private Integer predicttime;
    /**
     * 项目预算
     */
    private BigDecimal budget;
    /**
     * 预算单位
     */
    private String budgetUnit;
    /**
     * 一级业务类型==业务类型：
     */
    private String businessOneCode;
    /**
     * 一级业务类型ID（基础数据表）
     */
    private Long businessOneId;
    /**
     * 需求描述==环境描述
     */
    private String description;
    /**
     * 图片地址（多个地址用逗号分割）
     */
    private String pictures;
    /**
     * 接包人ID
     */
    private Long assigneeUserId;
    /**
     * 接包人部门编码（基础数据表）
     */
    private String assigneeOrgCode;
    /**
     * 接包人总公司ID
     */
    private Long assigneeTopCompanyId;
    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人总公司ID
     */
    private Long createTopCompanyId;
    /**
     * 创建人部门编码（基础数据表）
     */
    private String createOrgCode;
    /**
     * 创建人公司ID
     */
    private Long createCompanyId;
    /**
     * 接包人公司ID
     */
    private Long assigneeCompanyId;
    /**
     * 任务的状态，待确认0，待支付1，待完工2，待验收3，已完成4
     */
    private Integer status;
    /**
     * 0关闭发包(只有在status是待确认状态才能关闭发包)，1在发布==招标中，2已被接受==已中标3已流标
     */
    private Integer publishStatus;
    /**
     * //结束时间==开始时间==截止时间
     * //被选中的申请ID
     */
    private Long shopTaskApplyId;
    /**
     * 被选中的申请金额
     */
    private BigDecimal taskApplyMoney;
    /**
     * 订单编号
     */
    private String orderNum;
    /**
     * 系统类别--用工发布
     */
    private String systemType;
    /**
     * 用工要求
     */
    private String laborRequirements;
    /**
     * 报价数量
     */
    private Integer offerCount;

    private List<TaskApplyEntity> taskApplys;
    /**
     * 接包人信息
     */
    private UserEntity assigneeUser;

    private OrgEntity CompanyEntity;

    private UserEntity ownerUser;

    private TaskApplyEntity taskApplyEntity;
    @Getter
    @Setter
    private UserEntity userEntity;
    @Getter
    @Setter
    private WorkerEntity workerEntity;


}
