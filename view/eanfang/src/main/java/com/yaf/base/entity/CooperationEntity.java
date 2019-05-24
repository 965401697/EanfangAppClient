package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-25 16:27:35
 */
public class CooperationEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    private boolean isChecked;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;

    //绑定发起人的组织id（一般指安防公司）
    //@TableField(value = "owner_top_company_id")
    private Long ownerTopCompanyId;
    //绑定发起人的组织id（一般指安防公司）
    //@TableField(value = "owner_org_id")
    private Long ownerOrgId;

    //被绑定的组织id（一般指客户公司）
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;

    //被绑定的组织id（一般指客户公司）
    //@TableField(value = "assignee_org_id")
    private Long assigneeOrgId;
    //绑定的业务类型：0维修，1安装
    //@TableField(value = "bus_type")
    private Integer busType;
    //系统类别（基础数据表）
    //@TableField(value = "business_one_code")
    private String businessOneCode;
    //合作业务开始时间
    //@TableField(value = "begin_time")
    private Date beginTime;
    //合作业务结束时间
    //@TableField(value = "end_time")
    private Date endTime;
    //状态：0待审核，1审核通过，2失效
    //@TableField(value = "status")
    private Integer status;
    //创建人id
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    /**
     * 绑定发起人的组织（一般指安防公司）
     */
    private OrgEntity ownerOrg;
    /**
     * 被绑定的组织（一般指客户公司）
     */
    private OrgEntity assigneeOrg;
    /**
     * 标识
     */
    private String sign;

    /**
     * 创建人
     */
    private UserEntity createUserEntity;

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
     * 获取：绑定发起人的组织id（一般指安防公司）
     */
    public Long getOwnerOrgId() {
        return ownerOrgId;
    }

    /**
     * 设置：绑定发起人的组织id（一般指安防公司）
     */
    public void setOwnerOrgId(Long ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    /**
     * 获取：被绑定的组织id（一般指客户公司）
     */
    public Long getAssigneeOrgId() {
        return assigneeOrgId;
    }

    /**
     * 设置：被绑定的组织id（一般指客户公司）
     */
    public void setAssigneeOrgId(Long assigneeOrgId) {
        this.assigneeOrgId = assigneeOrgId;
    }

    /**
     * 获取：绑定的业务类型：0维修，1安装
     */
    public Integer getBusType() {
        return busType;
    }

    /**
     * 设置：绑定的业务类型：0维修，1安装
     */
    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    /**
     * 获取：系统类别（基础数据表）
     */
    public String getBusinessOneCode() {
        return businessOneCode;
    }

    /**
     * 设置：系统类别（基础数据表）
     */
    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    /**
     * 获取：合作业务开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置：合作业务开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取：合作业务结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置：合作业务结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：状态：0待审核，1审核通过，2失效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态：0待审核，1审核通过，2失效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：创建人id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人id
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    @Override
    public boolean equals(Object other) {
        if (other instanceof CooperationEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((CooperationEntity) other).id);
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

    public Long getOwnerTopCompanyId() {
        return this.ownerTopCompanyId;
    }

    public Long getAssigneeTopCompanyId() {
        return this.assigneeTopCompanyId;
    }

    public OrgEntity getOwnerOrg() {
        return this.ownerOrg;
    }

    public OrgEntity getAssigneeOrg() {
        return this.assigneeOrg;
    }

    public String getSign() {
        return this.sign;
    }

    public UserEntity getCreateUserEntity() {
        return this.createUserEntity;
    }

    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    public void setOwnerOrg(OrgEntity ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public void setAssigneeOrg(OrgEntity assigneeOrg) {
        this.assigneeOrg = assigneeOrg;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setCreateUserEntity(UserEntity createUserEntity) {
        this.createUserEntity = createUserEntity;
    }
}
