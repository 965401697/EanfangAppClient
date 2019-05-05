package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.eanfang.model.sys.OrgEntity;
import com.eanfang.model.sys.UserEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 项目表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:05:54
 */
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键递增
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //项目名称
    private String projectName;
    //合同编号
    private String pactCode;
    //系统类别
    private String businessOneCode;
    //项目性质
    private Integer projectNature;
    //项目预算
    private String projectBudget;
    //项目工期
    private String projectDuration;
    //项目开始时间
    private Date startTime;
    //项目竣工时间
    private Date endTime;
    //项目状态
    private Integer status;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //区域code对应的id
    private Long placeId;
    //项目地址所在城市编号(即：区/县 编码)
    private String areaCode;
    //地址详情
    private String address;
    //项目业主id
    private Long projectOwnerId;
    //项目负责人(owner)
    private String projectAssumeMan;
    //其他人员
    private String otherMan;
    //承建单位id
    private Long buildCompanyId;
    //承建方项目负责人id(assignee)
    private Long buildAssumeManId;
    //承建方技术负责人id(assignee)
    private Long buildTechniqueManId;
    //承建方安全负责人id(assignee)
    private Long buildSafeManId;
    //承建方其他人员id(assignee)(多个人员用逗号分隔)
    private String buildOtherManId;
    //监理单位
    private String supervisorCompany;
    //监理方项目负责人
    private String supervisorAssumeMan;
    //监理方其他人员
    private String supervisorOtherMan;
    //创建人id
    private Long createUserId;
    //创建时间
    private Date createTime;
    //修改人id
    private Long updateUserId;
    //修改时间
    private Date updateTime;

    /**
     * 设置：主键递增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键递增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取：项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置：合同编号
     */
    public void setPactCode(String pactCode) {
        this.pactCode = pactCode;
    }

    /**
     * 获取：合同编号
     */
    public String getPactCode() {
        return pactCode;
    }

    /**
     * 设置：系统类别
     */
    public String getBusinessOneCode() {
        return businessOneCode;
    }


    /**
     * 获取：系统类别
     */
    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    /**
     * 设置：项目性质
     */
    public void setProjectNature(Integer projectNature) {
        this.projectNature = projectNature;
    }

    /**
     * 获取：项目性质
     */
    public Integer getProjectNature() {
        return projectNature;
    }

    /**
     * 设置：项目预算
     */
    public void setProjectBudget(String projectBudget) {
        this.projectBudget = projectBudget;
    }

    /**
     * 获取：项目预算
     */
    public String getProjectBudget() {
        return projectBudget;
    }

    /**
     * 设置：项目工期
     */
    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    /**
     * 获取：项目工期
     */
    public String getProjectDuration() {
        return projectDuration;
    }

    /**
     * 设置：项目开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取：项目开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置：项目竣工时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：项目竣工时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置：项目状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：项目状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取：经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置：纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取：纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置：区域code对应的id
     */
    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    /**
     * 获取：区域code对应的id
     */
    public Long getPlaceId() {
        return placeId;
    }

    /**
     * 设置：项目地址所在城市编号(即：区/县 编码)
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取：项目地址所在城市编号(即：区/县 编码)
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置：地址详情
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：地址详情
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：项目业主id
     */
    public void setProjectOwnerId(Long projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    /**
     * 获取：项目业主id
     */
    public Long getProjectOwnerId() {
        return projectOwnerId;
    }

    /**
     * 设置：项目负责人(owner)
     */
    public void setProjectAssumeMan(String projectAssumeMan) {
        this.projectAssumeMan = projectAssumeMan;
    }

    /**
     * 获取：项目负责人(owner)
     */
    public String getProjectAssumeMan() {
        return projectAssumeMan;
    }

    /**
     * 设置：其他人员
     */
    public void setOtherMan(String otherMan) {
        this.otherMan = otherMan;
    }

    /**
     * 获取：其他人员
     */
    public String getOtherMan() {
        return otherMan;
    }

    /**
     * 设置：承建单位id
     */
    public void setBuildCompanyId(Long buildCompanyId) {
        this.buildCompanyId = buildCompanyId;
    }

    /**
     * 获取：承建单位id
     */
    public Long getBuildCompanyId() {
        return buildCompanyId;
    }

    /**
     * 设置：承建方项目负责人id(assignee)
     */
    public void setBuildAssumeManId(Long buildAssumeManId) {
        this.buildAssumeManId = buildAssumeManId;
    }

    /**
     * 获取：承建方项目负责人id(assignee)
     */
    public Long getBuildAssumeManId() {
        return buildAssumeManId;
    }

    /**
     * 设置：承建方技术负责人id(assignee)
     */
    public void setBuildTechniqueManId(Long buildTechniqueManId) {
        this.buildTechniqueManId = buildTechniqueManId;
    }

    /**
     * 获取：承建方技术负责人id(assignee)
     */
    public Long getBuildTechniqueManId() {
        return buildTechniqueManId;
    }

    /**
     * 设置：承建方安全负责人id(assignee)
     */
    public void setBuildSafeManId(Long buildSafeManId) {
        this.buildSafeManId = buildSafeManId;
    }

    /**
     * 获取：承建方安全负责人id(assignee)
     */
    public Long getBuildSafeManId() {
        return buildSafeManId;
    }

    /**
     * 设置：承建方其他人员id(assignee)(多个人员用逗号分隔)
     */
    public void setBuildOtherManId(String buildOtherManId) {
        this.buildOtherManId = buildOtherManId;
    }

    /**
     * 获取：承建方其他人员id(assignee)(多个人员用逗号分隔)
     */
    public String getBuildOtherManId() {
        return buildOtherManId;
    }

    /**
     * 设置：监理单位
     */
    public void setSupervisorCompany(String supervisorCompany) {
        this.supervisorCompany = supervisorCompany;
    }

    /**
     * 获取：监理单位
     */
    public String getSupervisorCompany() {
        return supervisorCompany;
    }

    /**
     * 设置：监理方项目负责人
     */
    public void setSupervisorAssumeMan(String supervisorAssumeMan) {
        this.supervisorAssumeMan = supervisorAssumeMan;
    }

    /**
     * 获取：监理方项目负责人
     */
    public String getSupervisorAssumeMan() {
        return supervisorAssumeMan;
    }

    /**
     * 设置：监理方其他人员
     */
    public void setSupervisorOtherMan(String supervisorOtherMan) {
        this.supervisorOtherMan = supervisorOtherMan;
    }

    /**
     * 获取：监理方其他人员
     */
    public String getSupervisorOtherMan() {
        return supervisorOtherMan;
    }

    /**
     * 设置：创建人id
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人id
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：修改人id
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取：修改人id
     */
    public Long getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置：修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ProjectEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ProjectEntity) other).id);
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


    private UserEntity ownerUserEntity;
    private OrgEntity ownerOrgEntity;


    private UserEntity assigneeUserEntity;
    private OrgEntity assigneeOrgEntity;


    public UserEntity getOwnerUserEntity() {
        return ownerUserEntity;
    }

    public void setOwnerUserEntity(UserEntity ownerUserEntity) {
        this.ownerUserEntity = ownerUserEntity;
    }

    public OrgEntity getOwnerOrgEntity() {
        return ownerOrgEntity;
    }

    public void setOwnerOrgEntity(OrgEntity ownerOrgEntity) {
        this.ownerOrgEntity = ownerOrgEntity;
    }

    public UserEntity getAssigneeUserEntity() {
        return assigneeUserEntity;
    }

    public void setAssigneeUserEntity(UserEntity assigneeUserEntity) {
        this.assigneeUserEntity = assigneeUserEntity;
    }

    public OrgEntity getAssigneeOrgEntity() {
        return assigneeOrgEntity;
    }

    public void setAssigneeOrgEntity(OrgEntity assigneeOrgEntity) {
        this.assigneeOrgEntity = assigneeOrgEntity;
    }


}
