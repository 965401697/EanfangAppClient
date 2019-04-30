package com.yaf.base.entity;

import com.yaf.sys.entity.AccountEntity;
import com.yaf.sys.entity.OrgEntity;

import java.io.Serializable;
import java.util.List;


/**
 * user扩展信息表，描述worker
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-11-30 20:02:44
 */
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

    // 技师认证状态
    private Integer verifyStatus;

    //技师最新上报位置，经度
    private String lon;
    //技师最新上报位置，纬度
    private String lat;
    //技师最新上报位置，区域编码 （基础数据）
    private String placeCode;
    //筛选技师时，数据行的编号，禁止排重用
    private Integer i;

    private List<HonorCertificateEntity> honorList;

    private List<QualificationCertificateEntity> qualificationList;

    private Integer qualification;

    private Integer trainStatus;

    private Integer designNum;

    private Integer installNum;

    private Integer evaluateNum;

    /**
     * 获取：主键自增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：关联的accId
     */
    public Long getAccId() {
        return accId;
    }

    /**
     * 设置：关联的accId
     */
    public void setAccId(Long accId) {
        this.accId = accId;
    }

    /**
     * 获取：技师编号
     */
    public String getWorkerNumber() {
        return workerNumber;
    }

    /**
     * 设置：技师编号
     */
    public void setWorkerNumber(String workerNumber) {
        this.workerNumber = workerNumber;
    }

    /**
     * 获取：当前技师最新的认证资料
     */
    public Long getVerifyId() {
        return verifyId;
    }

    /**
     * 设置：当前技师最新的认证资料
     */
    public void setVerifyId(Long verifyId) {
        this.verifyId = verifyId;
    }

    /**
     * 获取：报修单数量
     */
    public Integer getRepairCount() {
        return repairCount;
    }

    /**
     * 设置：报修单数量
     */
    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

    /**
     * 获取：口碑值
     */
    public Integer getPublicPraise() {
        return publicPraise;
    }

    /**
     * 设置：口碑值
     */
    public void setPublicPraise(Integer publicPraise) {
        this.publicPraise = publicPraise;
    }

    /**
     * 获取：好评率
     */
    public Integer getGoodRate() {
        return goodRate;
    }

    /**
     * 设置：好评率
     */
    public void setGoodRate(Integer goodRate) {
        this.goodRate = goodRate;
    }

    /**
     * 获取：评价项1
     */
    public Integer getItem1() {
        return item1;
    }

    /**
     * 设置：评价项1
     */
    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    /**
     * 获取：
     */
    public Integer getItem2() {
        return item2;
    }

    /**
     * 设置：
     */
    public void setItem2(Integer item2) {
        this.item2 = item2;
    }


    /**
     * 获取：
     */
    public Integer getItem3() {
        return item3;
    }

    /**
     * 设置：
     */
    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    /**
     * 获取：
     */
    public Integer getItem4() {
        return item4;
    }

    /**
     * 设置：
     */
    public void setItem4(Integer item4) {
        this.item4 = item4;
    }

    /**
     * 获取：
     */
    public Integer getItem5() {
        return item5;
    }

    /**
     * 设置：
     */
    public void setItem5(Integer item5) {
        this.item5 = item5;
    }

    /**
     * 获取：当前技师的工作状态：0空闲，1工作中，2停止接单
     */
    public Integer getWorkStatus() {
        return workStatus;
    }

    /**
     * 设置：当前技师的工作状态：0空闲，1工作中，2停止接单
     */
    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * 获取：honor_pics
     */
    public List<HonorCertificateEntity> getHonorList() {
        return honorList;
    }

    /**
     * 设置：honor_pics
     */
    public void setHonorList(List<HonorCertificateEntity> honorList) {
        this.honorList = honorList;
    }

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    /**
     * 当前技师所在的公司中userId
     */
    private Long companyUserId;

    /**
     * 所属的account
     */
    private AccountEntity accountEntity;
    /**
     * 当前worker对应的最新 verify
     */
    private WorkerVerifyEntity verifyEntity;
    /**
     * 技师服务的 业务类型 id
     */
    private List<Integer> businessList;
    /**
     * 技师的 服务类型 id
     */
    private List<Integer> serviceList;
    /**
     * 技师服务的 区域 id
     */
    private List<Integer> regionList;
    /**
     * 筛选时技师所在的公司
     */
    private OrgEntity companyEntity;
    /**
     * 被此用户 收藏的id
     */
    private Long collectId;
    /**
     * 筛选时技师所在的部门
     */
    private OrgEntity departmentEntity;


    public Integer getVerifyStatus() {
        return this.verifyStatus;
    }

    public String getLon() {
        return this.lon;
    }

    public String getLat() {
        return this.lat;
    }

    public String getPlaceCode() {
        return this.placeCode;
    }

    public Integer getI() {
        return this.i;
    }

    public List<QualificationCertificateEntity> getQualificationList() {
        return this.qualificationList;
    }

    public Integer getQualification() {
        return this.qualification;
    }

    public Integer getTrainStatus() {
        return this.trainStatus;
    }

    public Integer getDesignNum() {
        return this.designNum;
    }

    public Integer getInstallNum() {
        return this.installNum;
    }

    public Integer getEvaluateNum() {
        return this.evaluateNum;
    }

    public Long getCompanyUserId() {
        return this.companyUserId;
    }

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }

    public WorkerVerifyEntity getVerifyEntity() {
        return this.verifyEntity;
    }

    public List<Integer> getBusinessList() {
        return this.businessList;
    }

    public List<Integer> getServiceList() {
        return this.serviceList;
    }

    public List<Integer> getRegionList() {
        return this.regionList;
    }

    public OrgEntity getCompanyEntity() {
        return this.companyEntity;
    }

    public Long getCollectId() {
        return this.collectId;
    }

    public OrgEntity getDepartmentEntity() {
        return this.departmentEntity;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setQualificationList(List<QualificationCertificateEntity> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public void setQualification(Integer qualification) {
        this.qualification = qualification;
    }

    public void setTrainStatus(Integer trainStatus) {
        this.trainStatus = trainStatus;
    }

    public void setDesignNum(Integer designNum) {
        this.designNum = designNum;
    }

    public void setInstallNum(Integer installNum) {
        this.installNum = installNum;
    }

    public void setEvaluateNum(Integer evaluateNum) {
        this.evaluateNum = evaluateNum;
    }

    public void setCompanyUserId(Long companyUserId) {
        this.companyUserId = companyUserId;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setVerifyEntity(WorkerVerifyEntity verifyEntity) {
        this.verifyEntity = verifyEntity;
    }

    public void setBusinessList(List<Integer> businessList) {
        this.businessList = businessList;
    }

    public void setServiceList(List<Integer> serviceList) {
        this.serviceList = serviceList;
    }

    public void setRegionList(List<Integer> regionList) {
        this.regionList = regionList;
    }

    public void setCompanyEntity(OrgEntity companyEntity) {
        this.companyEntity = companyEntity;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public void setDepartmentEntity(OrgEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }
}

