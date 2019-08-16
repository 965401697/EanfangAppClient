package com.eanfang.biz.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 报修单表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-05-28 09:15:32
 */
public class RepairOrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //报修单编号
    //@TableField(value = "order_num")
    private String orderNum;
    //订单类型，（0普通订单，1在保维修，2一般执行，3总部执行）
    //@TableField(value = "order_type")
    private Long orderType;
    //订单级别（0：一般，1：紧急，2：暂缓）
    //@TableField(value = "order_level")
    private Integer orderLevel;
    //报修单位
    //@TableField(value = "repair_company")
    private String repairCompany;
    //报修方式（0：APP报修，1：系统报修，2：电话报修）
    //@TableField(value = "repair_way")
    private Integer repairWay;
    //报修联系人
    //@TableField(value = "repair_contacts")
    private String repairContacts;
    //报修联系电话
    //@TableField(value = "repair_contact_phone")
    private String repairContactPhone;
    //到达时限，常量表
    //@TableField(value = "arrive_time_limit")
    private Integer arriveTimeLimit;
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
    private String placeId;
    //报修详细地址
    //@TableField(value = "address")
    private String address;
    //性别（0：女，1：男）
    //@TableField(value = "sex")
    private Integer sex;
    //签到经度
    //@TableField(value = "sign_longitude")
    private String signLongitude;
    //签到纬度
    //@TableField(value = "sign_latitude")
    private String signLatitude;
    //是否正常范围签到（0：是，1：否）
    //@TableField(value = "sign_scope")
    private Integer signScope;
    //当前报修 最新回电时间
    //@TableField(value = "reply_time")
    private Date replyTime;
    //当前报修 最新预约时间
    //@TableField(value = "book_time")
    private Date bookTime;
    //当前报修 最新签到时间
    //@TableField(value = "sing_in_time")
    private Date singInTime;
    //当前报修 最新完工时间
    //@TableField(value = "finish_work_time")
    private Date finishWorkTime;
    //当前报修 最新确认完工时间
    //@TableField(value = "confirm_time")
    private Date confirmTime;
    //是否电话解决（0：未解决，1：已解决）
    //@TableField(value = "is_phone_solve")
    private Integer isPhoneSolve;
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
    //归属人
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
    //当前报修 最新的受理人
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
    //订单状态( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
    //@TableField(value = "status")
    private Integer status;
    //是否合格（0合格，1不合格）
    //@TableField(value = "pass")
    // 是否已读 未读
    private int newOrder;
    private Integer pass;
    private String projectId;
    private String projectName;
    private String remarkInfo;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public UserEntity getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserEntity ownerUser) {
        this.ownerUser = ownerUser;
    }

    public OrgEntity getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(OrgEntity ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public UserEntity getAssigneeUser() {
        return assigneeUser;
    }

    public void setAssigneeUser(UserEntity assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    public OrgEntity getAssigneeOrg() {
        return assigneeOrg;
    }

    public void setAssigneeOrg(OrgEntity assigneeOrg) {
        this.assigneeOrg = assigneeOrg;
    }

    public List<RepairBugEntity> getBugEntityList() {
        return bugEntityList;
    }

    public void setBugEntityList(List<RepairBugEntity> bugEntityList) {
        this.bugEntityList = bugEntityList;
    }

    public List<RepairFailureEntity> getFailureEntityList() {
        return failureEntityList;
    }

    public void setFailureEntityList(List<RepairFailureEntity> failureEntityList) {
        this.failureEntityList = failureEntityList;
    }

    public Long getWorkerEvaluateId() {
        return workerEvaluateId;
    }

    public void setWorkerEvaluateId(Long workerEvaluateId) {
        this.workerEvaluateId = workerEvaluateId;
    }

    public Long getClientEvaluateId() {
        return clientEvaluateId;
    }

    public void setClientEvaluateId(Long clientEvaluateId) {
        this.clientEvaluateId = clientEvaluateId;
    }

    public List<TransferLogEntity> getTransferLogEntityList() {
        return transferLogEntityList;
    }

    public void setTransferLogEntityList(List<TransferLogEntity> transferLogEntityList) {
        this.transferLogEntityList = transferLogEntityList;
    }

    public RepairFailureEntity getFailureEntity() {
        return failureEntity;
    }

    public void setFailureEntity(RepairFailureEntity failureEntity) {
        this.failureEntity = failureEntity;
    }

    public Integer getBugCount() {
        return bugCount;
    }

    public void setBugCount(Integer bugCount) {
        this.bugCount = bugCount;
    }

    /**
     * 归属人
     */
    private UserEntity ownerUser;
    /**
     * 归属组织
     */
    private OrgEntity ownerOrg;
    /**
     * 受理人
     */
    private UserEntity assigneeUser;
    /**
     * 受理组织
     */
    private OrgEntity assigneeOrg;
    /**
     * 报修明细
     */
    private List<RepairBugEntity> bugEntityList;
    /**
     * 真实故障
     */
    private List<RepairFailureEntity> failureEntityList;
    /**
     * 技师收到的评价id
     */
    private Long workerEvaluateId;
    /**
     * 客户收到的评价id
     */
    private Long clientEvaluateId;
    /**
     * 上门费
     */
    private PayLogEntity payLogEntity;

    public PayLogEntity getPayLogEntity() {
        return payLogEntity;
    }

    public void setPayLogEntity(PayLogEntity payLogEntity) {
        this.payLogEntity = payLogEntity;
    }

    /**
     * 转单记录
     */
    private List<TransferLogEntity> transferLogEntityList;
    /**
     * 真实故障(用于APP列表图片显示)
     */
    private RepairFailureEntity failureEntity;
    /**
     * 故障数量
     */
    private Integer bugCount;

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：报修单编号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：报修单编号
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
     * 设置：报修单位
     */
    public void setRepairCompany(String repairCompany) {
        this.repairCompany = repairCompany;
    }

    /**
     * 获取：报修单位
     */
    public String getRepairCompany() {
        return repairCompany;
    }

    /**
     * 报修方式（0：APP报修，1：电话报修，2：电子邮件报修，3：PC官网报修，4：wap官网报修）
     */
    public void setRepairWay(Integer repairWay) {
        this.repairWay = repairWay;
    }

    /**
     * 获取：报修方式（0：APP报修，1：系统报修，2：电话报修）
     */
    public Integer getRepairWay() {
        return repairWay;
    }

    /**
     * 设置：报修联系人
     */
    public void setRepairContacts(String repairContacts) {
        this.repairContacts = repairContacts;
    }

    /**
     * 获取：报修联系人
     */
    public String getRepairContacts() {
        return repairContacts;
    }

    /**
     * 设置：报修联系电话
     */
    public void setRepairContactPhone(String repairContactPhone) {
        this.repairContactPhone = repairContactPhone;
    }

    /**
     * 获取：报修联系电话
     */
    public String getRepairContactPhone() {
        return repairContactPhone;
    }

    /**
     * 设置：到达时限，常量表
     */
    public void setArriveTimeLimit(Integer arriveTimeLimit) {
        this.arriveTimeLimit = arriveTimeLimit;
    }

    /**
     * 获取：到达时限，常量表
     */
    public Integer getArriveTimeLimit() {
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
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * 获取：区域code对应的id
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * 设置：报修详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：报修详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：性别（0：女，1：男）
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取：性别（0：女，1：男）
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置：签到经度
     */
    public void setSignLongitude(String signLongitude) {
        this.signLongitude = signLongitude;
    }

    /**
     * 获取：签到经度
     */
    public String getSignLongitude() {
        return signLongitude;
    }

    /**
     * 设置：签到纬度
     */
    public void setSignLatitude(String signLatitude) {
        this.signLatitude = signLatitude;
    }

    /**
     * 获取：签到纬度
     */
    public String getSignLatitude() {
        return signLatitude;
    }

    /**
     * 设置：是否正常范围签到（0：是，1：否）
     */
    public void setSignScope(Integer signScope) {
        this.signScope = signScope;
    }

    /**
     * 获取：是否正常范围签到（0：是，1：否）
     */
    public Integer getSignScope() {
        return signScope;
    }

    /**
     * 设置：当前报修 最新回电时间
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 获取：当前报修 最新回电时间
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * 设置：当前报修 最新预约时间
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    /**
     * 获取：当前报修 最新预约时间
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置：当前报修 最新签到时间
     */
    public void setSingInTime(Date singInTime) {
        this.singInTime = singInTime;
    }

    /**
     * 获取：当前报修 最新签到时间
     */
    public Date getSingInTime() {
        return singInTime;
    }

    /**
     * 设置：当前报修 最新完工时间
     */
    public void setFinishWorkTime(Date finishWorkTime) {
        this.finishWorkTime = finishWorkTime;
    }

    /**
     * 获取：当前报修 最新完工时间
     */
    public Date getFinishWorkTime() {
        return finishWorkTime;
    }

    /**
     * 设置：当前报修 最新确认完工时间
     */
    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    /**
     * 获取：当前报修 最新确认完工时间
     */
    public Date getConfirmTime() {
        return confirmTime;
    }

    /**
     * 设置：是否电话解决（0：未解决，1：已解决）
     */
    public void setIsPhoneSolve(Integer isPhoneSolve) {
        this.isPhoneSolve = isPhoneSolve;
    }

    /**
     * 获取：是否电话解决（0：未解决，1：已解决）
     */
    public Integer getIsPhoneSolve() {
        return isPhoneSolve;
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
     * 设置：归属人
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取：归属人
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
     * 设置：当前报修 最新的受理人
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：当前报修 最新的受理人
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
     * 设置：订单状态( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：订单状态( 0:待支付，1:待回电，2:待上门，3:待完工，4:待确认，5:订单完成)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：是否合格（0合格，1不合格）
     */
    public void setPass(Integer pass) {
        this.pass = pass;
    }

    /**
     * 获取：是否合格（0合格，1不合格）
     */
    public Integer getPass() {
        return pass;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RepairOrderEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((RepairOrderEntity) other).id);
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

    public int getNewOrder() {
        return this.newOrder;
    }

    public void setNewOrder(int newOrder) {
        this.newOrder = newOrder;
    }
}
