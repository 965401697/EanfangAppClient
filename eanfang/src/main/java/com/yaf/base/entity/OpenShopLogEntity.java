package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-05-22 16:37:44
 */
@TableName(value = "oa_open_shop_log")
@Getter
@Setter
public class OpenShopLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //订单编号
    //@TableField(value = "order_number")
    private String orderNumber;
    //员工进场时间
    //@TableField(value = "emp_entry_time")
    private Date empEntryTime;
    //员工退场时间
    //@TableField(value = "emp_exit_time")
    private Date empExitTime;
    //顾客进场时间
    //@TableField(value = "cus_entry_time")
    private Date cusEntryTime;
    //顾客退场时间
    //@TableField(value = "cus_exit_time")
    private Date cusExitTime;
    //收货区开启时间
    //@TableField(value = "rec_yard_sta_time")
    private Date recYardStaTime;
    //收货区关闭时间
    //@TableField(value = "rec_yard_end_time")
    private Date recYardEndTime;
    //备注信息
    //@TableField(value = "remark_info")
    private String remarkInfo;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //订单状态(0:未读,1：已读)
    //@TableField(value = "status")
    private Integer status;
    //创建人
    //@TableField(value = "owner_user_id")
    private Long ownerUserId;
    //创建人公司
    //@TableField(value = "owner_company_id")
    private Long ownerCompanyId;
    //创建人归属总公司
    //@TableField(value = "owner_top_company_id")
    private Long ownerTopCompanyId;
    //创建人归属部门编码
    //@TableField(value = "owner_org_code")
    private String ownerOrgCode;
    //接收人
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //接收人公司
    //@TableField(value = "assignee_company_id")
    private Long assigneeCompanyId;
    //接收人归属总公司
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;
    //接收人归属部门编码
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;
    //接收人联系电话
    //@TableField(value = "assignee_phone")
    private String assigneePhone;

    //--------------------------------------------业务字段，不存储在数据库--------------------------------------
    //接收人信息
    @TableField(exist = false)
    private UserEntity assigneeUser;
    //所有人
    @TableField(exist = false)
    private UserEntity ownerUser;

    //创建人单位信息
    @TableField(exist = false)
    private OrgEntity ownerCompany;
    //创建人部门信息
    @TableField(exist = false)
    private OrgEntity ownerDepartment;
    // 是否已读 未读
    private int newOrder;

    public UserEntity getAssigneeUser() {
        return assigneeUser;
    }

    public void setAssigneeUser(UserEntity assigneeUser) {
        this.assigneeUser = assigneeUser;
    }

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
     * 设置：订单编号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取：订单编号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置：员工进场时间
     */
    public void setEmpEntryTime(Date empEntryTime) {
        this.empEntryTime = empEntryTime;
    }

    /**
     * 获取：员工进场时间
     */
    public Date getEmpEntryTime() {
        return empEntryTime;
    }

    /**
     * 设置：员工退场时间
     */
    public void setEmpExitTime(Date empExitTime) {
        this.empExitTime = empExitTime;
    }

    /**
     * 获取：员工退场时间
     */
    public Date getEmpExitTime() {
        return empExitTime;
    }

    /**
     * 设置：顾客进场时间
     */
    public void setCusEntryTime(Date cusEntryTime) {
        this.cusEntryTime = cusEntryTime;
    }

    /**
     * 获取：顾客进场时间
     */
    public Date getCusEntryTime() {
        return cusEntryTime;
    }

    /**
     * 设置：顾客退场时间
     */
    public void setCusExitTime(Date cusExitTime) {
        this.cusExitTime = cusExitTime;
    }

    /**
     * 获取：顾客退场时间
     */
    public Date getCusExitTime() {
        return cusExitTime;
    }

    /**
     * 设置：收货区开启时间
     */
    public void setRecYardStaTime(Date recYardStaTime) {
        this.recYardStaTime = recYardStaTime;
    }

    /**
     * 获取：收货区开启时间
     */
    public Date getRecYardStaTime() {
        return recYardStaTime;
    }

    /**
     * 设置：收货区关闭时间
     */
    public void setRecYardEndTime(Date recYardEndTime) {
        this.recYardEndTime = recYardEndTime;
    }

    /**
     * 获取：收货区关闭时间
     */
    public Date getRecYardEndTime() {
        return recYardEndTime;
    }

    /**
     * 设置：备注信息
     */
    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    /**
     * 获取：备注信息
     */
    public String getRemarkInfo() {
        return remarkInfo;
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
     * 设置：订单状态(0:未读,1：已读)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：订单状态(0:未读,1：已读)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：创建人
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取：创建人
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * 设置：创建人公司
     */
    public void setOwnerCompanyId(Long ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    /**
     * 获取：创建人公司
     */
    public Long getOwnerCompanyId() {
        return ownerCompanyId;
    }

    /**
     * 设置：创建人归属总公司
     */
    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    /**
     * 获取：创建人归属总公司
     */
    public Long getOwnerTopCompanyId() {
        return ownerTopCompanyId;
    }

    /**
     * 设置：创建人归属部门编码
     */
    public void setOwnerOrgCode(String ownerOrgCode) {
        this.ownerOrgCode = ownerOrgCode;
    }

    /**
     * 获取：创建人归属部门编码
     */
    public String getOwnerOrgCode() {
        return ownerOrgCode;
    }

    /**
     * 设置：接收人
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：接收人
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：接收人公司
     */
    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    /**
     * 获取：接收人公司
     */
    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    /**
     * 设置：接收人归属总公司
     */
    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    /**
     * 获取：接收人归属总公司
     */
    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    /**
     * 设置：接收人归属部门编码
     */
    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    /**
     * 获取：接收人归属部门编码
     */
    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    /**
     * 设置：接收人联系电话
     */
    public void setAssigneePhone(String assigneePhone) {
        this.assigneePhone = assigneePhone;
    }

    /**
     * 获取：接收人联系电话
     */
    public String getAssigneePhone() {
        return assigneePhone;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof OpenShopLogEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((OpenShopLogEntity) other).id);
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
}
