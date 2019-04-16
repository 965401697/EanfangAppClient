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
import java.util.List;


/**
 * 布放日志
 *
 * @author jxtpro
 * @email jxtpro@163.com
 * @date 2018-05-22 16:46:20
 */
@TableName(value = "oa_protection_log")
public class ProtectionLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //订单编号
    //@TableField(value = "order_number")
    private String orderNumber;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //开启时间
    //@TableField(value = "open_time")
    private Date openTime;
    //关闭时间
    //@TableField(value = "close_time")
    private Date closeTime;
    //接收人ID
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //接收人所属总公司
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;
    //接收人受理部门编码
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;
    //接收人所属公司
    //@TableField(value = "assignee_company_id")
    private Long assigneeCompanyId;
    //布防日志发布人ID
    //@TableField(value = "owner_user_id")
    private Long ownerUserId;
    //布防日志发布人所属公司
    //@TableField(value = "owner_company_id")
    private Long ownerCompanyId;
    //布防日志发布人所属总公司
    //@TableField(value = "owner_top_company_id")
    private Long ownerTopCompanyId;
    //布防日志发布人归属部门编码
    //@TableField(value = "owner_org_code")
    private String ownerOrgCode;
    //状态(0:未读,1:已读,2:删除)
    //@TableField(value = "status")
    private Integer status;
    // 是否已读 未读
    private int newOrder;


    //-----------------------------------业务字段，不存在于数据库----------------------------------------
    //旁路
    @TableField(exist = false)
    private List<LogDetailsEntity> bypassList;
    //闯防
    @TableField(exist = false)
    private List<LogDetailsEntity> throughList;
    //误报
    @TableField(exist = false)
    private List<LogDetailsEntity> falseList;


    //创建人信息
    @TableField(exist = false)
    private UserEntity ownerUser;
    //接收人信息
    @TableField(exist = false)
    private UserEntity assigneeUser;

    //创建人单位信息
    @TableField(exist = false)
    private OrgEntity ownerCompany;
    //创建人部门信息
    @TableField(exist = false)
    private OrgEntity ownerDepartment;


    /**
     * 获取：接收人
     */
    public UserEntity getAssigneeUser() {
        return assigneeUser;
    }

    /**
     * 设置：接收人
     */
    public void setAssigneeUser(UserEntity assigneeUser) {
        this.assigneeUser = assigneeUser;
    }


    /**
     * 获取：旁路
     */
    public List<LogDetailsEntity> getBypassList() {
        return bypassList;
    }

    /**
     * 设置：旁路
     */
    public void setBypassList(List<LogDetailsEntity> logDetailsEntityList) {
        this.bypassList = logDetailsEntityList;
    }

    /**
     * 获取：误报
     */
    public List<LogDetailsEntity> getFalseList() {
        return falseList;
    }

    /**
     * 设置：误报
     */
    public void setFalseList(List<LogDetailsEntity> falseList) {
        this.falseList = falseList;
    }

    /**
     * 获取：闯防
     */
    public List<LogDetailsEntity> getThroughList() {
        return throughList;
    }

    /**
     * 设置：闯防
     */
    public void setThroughList(List<LogDetailsEntity> throughList) {
        this.throughList = throughList;
    }


    /**
     * 设置：ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：ID
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
     * 设置：开启时间
     */
    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    /**
     * 获取：开启时间
     */
    public Date getOpenTime() {
        return openTime;
    }

    /**
     * 设置：关闭时间
     */
    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 获取：关闭时间
     */
    public Date getCloseTime() {
        return closeTime;
    }

    /**
     * 设置：接收人ID
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：接收人ID
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：接收人所属总公司
     */
    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    /**
     * 获取：接收人所属总公司
     */
    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    /**
     * 设置：接收人受理部门编码
     */
    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    /**
     * 获取：接收人受理部门编码
     */
    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    /**
     * 设置：接收人所属公司
     */
    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    /**
     * 获取：接收人所属公司
     */
    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    /**
     * 设置：布防日志发布人ID
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取：布防日志发布人ID
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * 设置：布防日志发布人所属公司
     */
    public void setOwnerCompanyId(Long ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    /**
     * 获取：布防日志发布人所属公司
     */
    public Long getOwnerCompanyId() {
        return ownerCompanyId;
    }

    /**
     * 设置：布防日志发布人所属总公司
     */
    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    /**
     * 获取：布防日志发布人所属总公司
     */
    public Long getOwnerTopCompanyId() {
        return ownerTopCompanyId;
    }

    /**
     * 设置：布防日志发布人归属部门编码
     */
    public void setOwnerOrgCode(String ownerOrgCode) {
        this.ownerOrgCode = ownerOrgCode;
    }

    /**
     * 获取：布防日志发布人归属部门编码
     */
    public String getOwnerOrgCode() {
        return ownerOrgCode;
    }

    /**
     * 设置：状态(0:未读,1:已读,2:删除)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态(0:未读,1:已读,2:删除)
     */
    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ProtectionLogEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ProtectionLogEntity) other).id);
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

    public UserEntity getOwnerUser() {
        return this.ownerUser;
    }

    public OrgEntity getOwnerCompany() {
        return this.ownerCompany;
    }

    public OrgEntity getOwnerDepartment() {
        return this.ownerDepartment;
    }

    public void setNewOrder(int newOrder) {
        this.newOrder = newOrder;
    }

    public void setOwnerUser(UserEntity ownerUser) {
        this.ownerUser = ownerUser;
    }

    public void setOwnerCompany(OrgEntity ownerCompany) {
        this.ownerCompany = ownerCompany;
    }

    public void setOwnerDepartment(OrgEntity ownerDepartment) {
        this.ownerDepartment = ownerDepartment;
    }
}
