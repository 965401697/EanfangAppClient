package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 维保执行计划订单表02
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:08:08
 */
public class ShopMaintenanceOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键递增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //维保单编号
    //@TableField(value = "order_num")
    private String orderNum;
    //订单类型，（0普通订单，1在保维修，2一般执行，3总部执行）
    //@TableField(value = "order_type")
    private Long orderType;
    //订单级别（0：一般，1：紧急，2：暂缓）
    //@TableField(value = "order_level")
    private Integer orderLevel;
    //所属项目id
    //@TableField(value = "project_id")
    private Long projectId;
    //维保单位（安防公司）
    //@TableField(value = "repair_company")
    private String repairCompany;
    //联系人（技师）
    //@TableField(value = "repair_contacts")
    private String repairContacts;
    //性别：0男1女
    //@TableField(value = "sex")
    private Integer sex;
    //联系电话（技师）
    //@TableField(value = "repair_contact_phone")
    private String repairContactPhone;
    //到达时限，常量表
    //@TableField(value = "arrive_time_limit")
    private Long arriveTimeLimit;
    //经度
    //@TableField(value = "longitude")
    private String longitude;
    //纬度
    //@TableField(value = "latitude")
    private String latitude;
    //区/县 编码
    //@TableField(value = "place_code")
    private String placeCode;
    //区域code对应的id
    //@TableField(value = "place_id")
    private Long placeId;
    //维保详细地址
    //@TableField(value = "address")
    private String address;
    //备注
    //@TableField(value = "context")
    private String context;
    //系统类别（基础数据表）)
    //@TableField(value = "business_one_code")
    private String businessOneCode;
    //维保周期
    //@TableField(value = "period")
    private Integer period;
    //预计开始时间
    //@TableField(value = "begin_time")
    private Date beginTime;
    //预计竣工时间
    //@TableField(value = "end_time")
    private Date endTime;
    //提醒设置
    //@TableField(value = "remind_set")
    private Integer remindSet;
    //维保标准
    //@TableField(value = "standard")
    private String standard;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //修改人
    //@TableField(value = "edit_user_id")
    private Long editUserId;
    //修改时间
    //@TableField(value = "edit_time")
    private Date editTime;
    //归属人（这里指客户）
    //@TableField(value = "owner_user_id")
    private Long ownerUserId;
    //被维修公司
    //@TableField(value = "owner_company_id")
    private Long ownerCompanyId;
    //归属总公司
    //@TableField(value = "owner_top_company_id")
    private Long ownerTopCompanyId;
    //归属部门编码
    //@TableField(value = "owner_org_code")
    private String ownerOrgCode;
    //当前报修 最新的受理人（这里指技师，安防公司）
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //维修公司
    //@TableField(value = "assignee_company_id")
    private Long assigneeCompanyId;
    //当前报修 最新的受理人总公司
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;
    //当前报修 最新的受理部门编码
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;
    //订单状态( 0:待执行，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
    //@TableField(value = "status")
    private Integer status;
    //是否合格（1合格，0不合格）
    //@TableField(value = "audit_pass")
    private Integer auditPass;

    private String signLongitude;

    private String signLatitude;

    private Integer signScope;

    private Date bookTime;

    private Date signTime;
    // 是否已读 未读
    private Integer newOrder;

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
     * 设置：维保单编号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：维保单编号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * 设置：订单类型，（0普通订单，1在保维修，2一般执行，3总部执行）
     */
    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取：订单类型，（0普通订单，1在保维修，2一般执行，3总部执行）
     */
    public Long getOrderType() {
        return orderType;
    }

    /**
     * 设置：订单级别（0：一般，1：紧急，2：暂缓）
     */
    public void setOrderLevel(Integer orderLevel) {
        this.orderLevel = orderLevel;
    }

    /**
     * 获取：订单级别（0：一般，1：紧急，2：暂缓）
     */
    public Integer getOrderLevel() {
        return orderLevel;
    }

    /**
     * 设置：所属项目id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取：所属项目id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 设置：维保单位（安防公司）
     */
    public void setRepairCompany(String repairCompany) {
        this.repairCompany = repairCompany;
    }

    /**
     * 获取：维保单位（安防公司）
     */
    public String getRepairCompany() {
        return repairCompany;
    }

    /**
     * 设置：联系人（技师）
     */
    public void setRepairContacts(String repairContacts) {
        this.repairContacts = repairContacts;
    }

    /**
     * 获取：联系人（技师）
     */
    public String getRepairContacts() {
        return repairContacts;
    }

    /**
     * 设置：性别：0男1女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取：性别：0男1女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置：联系电话（技师）
     */
    public void setRepairContactPhone(String repairContactPhone) {
        this.repairContactPhone = repairContactPhone;
    }

    /**
     * 获取：联系电话（技师）
     */
    public String getRepairContactPhone() {
        return repairContactPhone;
    }

    /**
     * 设置：到达时限，常量表
     */
    public void setArriveTimeLimit(Long arriveTimeLimit) {
        this.arriveTimeLimit = arriveTimeLimit;
    }

    /**
     * 获取：到达时限，常量表
     */
    public Long getArriveTimeLimit() {
        return arriveTimeLimit;
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
     * 设置：区/县 编码
     */
    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    /**
     * 获取：区/县 编码
     */
    public String getPlaceCode() {
        return placeCode;
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
     * 设置：维保详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：维保详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：备注
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * 获取：备注
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置：系统类别（基础数据表）)
     */
    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    /**
     * 获取：系统类别（基础数据表）)
     */
    public String getBusinessOneCode() {
        return businessOneCode;
    }

    /**
     * 设置：维保周期
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 获取：维保周期
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * 设置：预计开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取：预计开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置：预计竣工时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取：预计竣工时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置：提醒设置
     */
    public void setRemindSet(Integer remindSet) {
        this.remindSet = remindSet;
    }

    /**
     * 获取：提醒设置
     */
    public Integer getRemindSet() {
        return remindSet;
    }

    /**
     * 设置：维保标准
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * 获取：维保标准
     */
    public String getStandard() {
        return standard;
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

    /**
     * 设置：归属人（这里指客户）
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取：归属人（这里指客户）
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * 设置：被维修公司
     */
    public void setOwnerCompanyId(Long ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    /**
     * 获取：被维修公司
     */
    public Long getOwnerCompanyId() {
        return ownerCompanyId;
    }

    /**
     * 设置：归属总公司
     */
    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    /**
     * 获取：归属总公司
     */
    public Long getOwnerTopCompanyId() {
        return ownerTopCompanyId;
    }

    /**
     * 设置：归属部门编码
     */
    public void setOwnerOrgCode(String ownerOrgCode) {
        this.ownerOrgCode = ownerOrgCode;
    }

    /**
     * 获取：归属部门编码
     */
    public String getOwnerOrgCode() {
        return ownerOrgCode;
    }

    /**
     * 设置：当前报修 最新的受理人（这里指技师，安防公司）
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：当前报修 最新的受理人（这里指技师，安防公司）
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：维修公司
     */
    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    /**
     * 获取：维修公司
     */
    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    /**
     * 设置：当前报修 最新的受理人总公司
     */
    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    /**
     * 获取：当前报修 最新的受理人总公司
     */
    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    /**
     * 设置：当前报修 最新的受理部门编码
     */
    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    /**
     * 获取：当前报修 最新的受理部门编码
     */
    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    /**
     * 设置：订单状态( 0:待执行，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：订单状态( 0:待执行，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：是否合格（1合格，0不合格）
     */
    public void setAuditPass(Integer auditPass) {
        this.auditPass = auditPass;
    }

    /**
     * 获取：是否合格（1合格，0不合格）
     */
    public Integer getAuditPass() {
        return auditPass;
    }

    public String getSignLongitude() {
        return signLongitude;
    }

    public void setSignLongitude(String signLongitude) {
        this.signLongitude = signLongitude;
    }

    public String getSignLatitude() {
        return signLatitude;
    }

    public void setSignLatitude(String signLatitude) {
        this.signLatitude = signLatitude;
    }

    public Integer getSignScope() {
        return signScope;
    }

    public void setSignScope(Integer signScope) {
        this.signScope = signScope;
    }

    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShopMaintenanceOrderEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ShopMaintenanceOrderEntity) other).id);
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

    private ProjectEntity projectEntity;

    private ShopBughandleMaintenanceConfirmEntity confirmEntity;


    private UserEntity ownerUserEntity;
    private OrgEntity ownerOrgEntity;


    private UserEntity assigneeUserEntity;
    private OrgEntity assigneeOrgEntity;
    //创建人信息
    private UserEntity createUser;

    /**
     * 主要设备
     */
    private List<ShopMaintenanceDeviceEntity> deviceEntityList;

    /**
     * 重点检查设备
     */
    private List<ShopMaintenanceExamDeviceEntity> examDeviceEntityList;
    /**
     * 检查结果
     */
    private List<ShopMaintenanceExamResultEntity> examResultEntityList;


    public UserEntity getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }

    public ShopBughandleMaintenanceConfirmEntity getConfirmEntity() {
        return confirmEntity;
    }

    public void setConfirmEntity(ShopBughandleMaintenanceConfirmEntity confirmEntity) {
        this.confirmEntity = confirmEntity;
    }

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

    public List<ShopMaintenanceDeviceEntity> getDeviceEntityList() {
        return deviceEntityList;
    }

    public void setDeviceEntityList(List<ShopMaintenanceDeviceEntity> deviceEntityList) {
        this.deviceEntityList = deviceEntityList;
    }

    public List<ShopMaintenanceExamDeviceEntity> getExamDeviceEntityList() {
        return examDeviceEntityList;
    }

    public void setExamDeviceEntityList(List<ShopMaintenanceExamDeviceEntity> examDeviceEntityList) {
        this.examDeviceEntityList = examDeviceEntityList;
    }

    public List<ShopMaintenanceExamResultEntity> getExamResultEntityList() {
        return examResultEntityList;
    }

    public void setExamResultEntityList(List<ShopMaintenanceExamResultEntity> examResultEntityList) {
        this.examResultEntityList = examResultEntityList;
    }


    public Integer getNewOrder() {
        return this.newOrder;
    }

    public void setNewOrder(Integer newOrder) {
        this.newOrder = newOrder;
    }
}
