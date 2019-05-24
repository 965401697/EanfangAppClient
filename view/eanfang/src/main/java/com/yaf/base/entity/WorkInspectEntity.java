package com.yaf.base.entity;

import com.eanfang.biz.model.entity.OrgEntity;
import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.List;


/**
 * 工作检查表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-05 20:41:57
 */
public class WorkInspectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //标题
    //@TableField(value = "title")
    private String title;
    //被检单位名称
    //@TableField(value = "company_name")
    private String companyName;
    //整改期限
    //@TableField(value = "change_deadline_time")
    private String changeDeadlineTime;
    //整改内容
    //@TableField(value = "change_info")
    private String changeInfo;
    //工作协同人ID
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //工作协同人部门
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;
    //创建人ID
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建人总公司ID
    //@TableField(value = "create_top_company_id")
    private Long createTopCompanyId;
    //创建人部门编码
    //@TableField(value = "create_org_code")
    private String createOrgCode;
    //创建时间
    //@TableField(value = "create_time")
    private String createTime;
    //状态（0:待处理，1:待审核，2:已拒绝，3:处理完成）
    //@TableField(value = "status")
    private Integer status;
    //创建人公司ID
    private Long createCompanyId;

    //--------------------------------------------业务字段，不存储在数据库--------------------------------------
    //工作协同人信息
    private UserEntity assigneeUser;
    //工作协同人部门信息
    private OrgEntity assigneeOrg;
    //创建人信息
    private UserEntity createUser;
    //创建人部门信息
    private OrgEntity createOrg;
    //创建人公司信息
    private OrgEntity createCompany;
    //创建人明细列表
    private List<WorkInspectDetailEntity> workInspectDetails;
    //明细----用于APP图片显示
    private WorkInspectDetailEntity workInspectDetail;
    //处理明细
    private WorkInspectDetailDisposeEntity workInpectDetailDispose;
    //是否是新订单  0否 1是
    private int newOrder;

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：被检单位名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置：被检单位名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取：整改期限
     */
    public String getChangeDeadlineTime() {
        return changeDeadlineTime;
    }

    /**
     * 设置：整改期限
     */
    public void setChangeDeadlineTime(String changeDeadlineTime) {
        this.changeDeadlineTime = changeDeadlineTime;
    }

    /**
     * 获取：整改内容
     */
    public String getChangeInfo() {
        return changeInfo;
    }

    /**
     * 设置：整改内容
     */
    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    /**
     * 获取：工作协同人ID
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：工作协同人ID
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：工作协同人部门
     */
    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    /**
     * 设置：工作协同人部门
     */
    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    /**
     * 获取：创建人ID
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人总公司ID
     */
    public Long getCreateTopCompanyId() {
        return createTopCompanyId;
    }

    /**
     * 设置：创建人总公司ID
     */
    public void setCreateTopCompanyId(Long createTopCompanyId) {
        this.createTopCompanyId = createTopCompanyId;
    }

    /**
     * 获取：创建人部门编码
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置：创建人部门编码
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取：创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：状态（0:待处理，1:待审核，2:已拒绝，3:处理完成）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态（0:待处理，1:待审核，2:已拒绝，3:处理完成）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：工作协同人信息
     */
    public UserEntity getAssigneeUser() {
        return assigneeUser;
    }

    /**
     * 设置：工作协同人信息
     */
    public void setAssigneeUser(UserEntity assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

    /**
     * 获取：工作协同人部门信息
     */
    public OrgEntity getAssigneeOrg() {
        return assigneeOrg;
    }

    /**
     * 设置：工作协同人部门信息
     */
    public void setAssigneeOrg(OrgEntity assigneeOrg) {
        this.assigneeOrg = assigneeOrg;
    }

    /**
     * 获取：创建人公司ID
     */
    public Long getCreateCompanyId() {
        return createCompanyId;
    }

    /**
     * 设置：创建人公司ID
     */
    public void setCreateCompanyId(Long createCompanyId) {
        this.createCompanyId = createCompanyId;
    }

    /**
     * 获取：创建人信息
     */
    public UserEntity getCreateUser() {
        return createUser;
    }

    /**
     * 设置：创建人信息
     */
    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取：创建人部门信息
     */
    public OrgEntity getCreateOrg() {
        return createOrg;
    }

    /**
     * 设置：创建人部门信息
     */
    public void setCreateOrg(OrgEntity createOrg) {
        this.createOrg = createOrg;
    }

    /**
     * 获取：创建人公司信息
     */
    public OrgEntity getCreateCompany() {
        return createCompany;
    }

    /**
     * 设置：创建人公司信息
     */
    public void setCreateCompany(OrgEntity createCompany) {
        this.createCompany = createCompany;
    }

    /**
     * 获取工作检查明细列表
     */
    public List<WorkInspectDetailEntity> getWorkInspectDetails() {
        return workInspectDetails;
    }

    /**
     * 设置工作检查明细列表
     */
    public void setWorkInspectDetails(List<WorkInspectDetailEntity> workInspectDetails) {
        this.workInspectDetails = workInspectDetails;
    }

    /**
     * 获取工作检查明细
     */
    public WorkInspectDetailEntity getWorkInspectDetail() {
        return workInspectDetail;
    }

    /**
     * 设置工作检查明细
     */
    public void setWorkInspectDetail(WorkInspectDetailEntity workInspectDetail) {
        this.workInspectDetail = workInspectDetail;
    }

    public WorkInspectDetailDisposeEntity getWorkInpectDetailDispose() {
        return workInpectDetailDispose;
    }

    public void setWorkInpectDetailDispose(WorkInspectDetailDisposeEntity workInpectDetailDispose) {
        this.workInpectDetailDispose = workInpectDetailDispose;
    }

    public int getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(int newOrder) {
        this.newOrder = newOrder;
    }


}
