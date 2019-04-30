package com.eanfang.model.sys;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Accessors(chain = true)
public class WorkerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //关联的acc_id
    private Long accId;

    //技师编号
    private String workerNumber;
    //当前技师最新的认证资料
    private Long verifyId;
    //报修单数量
    private Integer repairCount;
    //口碑值
    private Integer publicPraise;
    //好评率
    private Integer goodRate;
    //评价项1
    private Integer item1;
    //
    private Integer item2;
    //
    private Integer item3;
    //
    private Integer item4;
    //
    private Integer item5;
    //当前技师的工作状态：0空闲，1工作中，2停止接单
    private Integer workStatus;
    /**
     * 技师是否认证，0未认证，1已认证
     */
    private Integer verifyStatus;

    //技师最新上报位置，经度
    private String lon;
    //技师最新上报位置，纬度
    private String lat;
    //技师最新上报位置，区域编码 （基础数据）
    private String placeCode;
    //筛选技师时，数据行的编号，禁止排重用
    private Integer i;

    private Integer qualification;

    private Integer trainStatus;
    //设计单数量
    private Integer designNum;
    //报装单数量
    private Integer installNum;
    //维保单数量
    private Integer maintainCount;
    //接包数量
    private Integer receiveCount;
    //收到好评个数
    private Integer goodNum;
    //收到评价数量
    private Integer evaluateNum;
    //整体评价（1：好评，2：中评，3：差评）
    private Integer generalEvaluation;

    //后台设定的基础权重值。
    private Integer weightBase;

    //通过系统计算出来的技师权重值。（使用时需要基础权重值+计算权重值）
    private Integer weight;

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

//    /**
//     * 当前技师所在的公司中userId
//     */
//    private Long companyUserId;
//    /**
//     * 好评率百分比展示字段
//     */
//    private BigDecimal goodReputation;
//    /**
//     * 所属的account
//     */
//    private AccountEntity accountEntity;
//    /**
//     * 当前worker对应的最新 verify
//     */
//    private WorkerVerifyEntity verifyEntity;
//    /**
//     * 技师服务的 业务类型 id
//     */
//    private List<Long> businessList;
//    /**
//     * 技师的 服务类型 id
//     */
//    private List<Long> serviceList;
//    /**
//     * 技师服务的 区域 id
//     */
//    private List<Long> regionList;
//
//    /**
//     * 技师服务的 业务类型(一级) code
//     */
//    private Set<String> businessCodeSet;
//    /**
//     * 技师的 服务类型(一级) code
//     */
//    private Set<String> serviceCodeSet;
//    /**
//     * 技师服务的 区域(一级) code
//     */
//    private Set<String> regionCodeSet;
//
//    /**
//     * 荣誉证书集合
//     */
//    private List<HonorCertificateEntity> honorList;
//
//
//    /**
//     * 资质证书集合
//     */
//    private List<QualificationCertificateEntity> qualificationList;
//
//
//    /**
//     * 筛选时技师所在的公司
//     */
//    private OrgEntity companyEntity;
//    /**
//     * 被此用户 收藏的id
//     */
//    private Long collectId;
//    /**
//     * 筛选时技师所在的部门
//     */
//    private OrgEntity departmentEntity;
//    /**
//     * 专家认证
//     */
//    private ExpertsCertificationEntity expertsCertificationEntity;


}
