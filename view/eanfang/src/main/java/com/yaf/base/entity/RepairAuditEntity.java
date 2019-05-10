package com.yaf.base.entity;



import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 报修审核信息表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-11-22 17:01:18
 */
public class RepairAuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //报修订单表ID
    //@TableField(value = "bus_repair_order_id")
    private Long busRepairOrderId;
    //结论(通过json存储，使用时请自行解析)
    //@TableField(value = "result_json")
    private String resultJson;
    //描述(通过json存储，使用时请自行解析)
    //@TableField(value = "describe_json")
    private String describeJson;
    //审核(通过json存储，使用时请自行解析)
    //@TableField(value = "time_audit_json")
    private String timeAuditJson;
    //投诉是否成立
    //@TableField(value = "is_complaint_establish")
    private Integer isComplaintEstablish;
    //审核意见
    //@TableField(value = "audit_idea")
    private String auditIdea;
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
    //状态
    //@TableField(value = "status")
    private Integer status;
    /**
     * 审核  结论
     */
    private Map<String, String> resultMap;
    /**
     * 审核  描述
     */
    private Map<String, String> describeMap;
    /**
     * 审核  时间审核
     */
    private Map<String, String> timeAuditMap;

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
     * 获取：报修订单表ID
     */
    public Long getBusRepairOrderId() {
        return busRepairOrderId;
    }

    /**
     * 设置：报修订单表ID
     */
    public void setBusRepairOrderId(Long busRepairOrderId) {
        this.busRepairOrderId = busRepairOrderId;
    }

    /**
     * 获取：结论(通过json存储，使用时请自行解析)
     */
    public String getResultJson() {
        return resultJson;
    }

    /**
     * 设置：结论(通过json存储，使用时请自行解析)
     */
    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    /**
     * 获取：描述(通过json存储，使用时请自行解析)
     */
    public String getDescribeJson() {
        return describeJson;
    }

    /**
     * 设置：描述(通过json存储，使用时请自行解析)
     */
    public void setDescribeJson(String describeJson) {
        this.describeJson = describeJson;
    }

    /**
     * 获取：审核(通过json存储，使用时请自行解析)
     */
    public String getTimeAuditJson() {
        return timeAuditJson;
    }

    /**
     * 设置：审核(通过json存储，使用时请自行解析)
     */
    public void setTimeAuditJson(String timeAuditJson) {
        this.timeAuditJson = timeAuditJson;
    }

    /**
     * 获取：投诉是否成立
     */
    public Integer getIsComplaintEstablish() {
        return isComplaintEstablish;
    }

    /**
     * 设置：投诉是否成立
     */
    public void setIsComplaintEstablish(Integer isComplaintEstablish) {
        this.isComplaintEstablish = isComplaintEstablish;
    }

    /**
     * 获取：审核意见
     */
    public String getAuditIdea() {
        return auditIdea;
    }

    /**
     * 设置：审核意见
     */
    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人
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

    /**
     * 获取：修改人
     */
    public Long getEditUserId() {
        return editUserId;
    }

    /**
     * 设置：修改人
     */
    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    /**
     * 获取：修改时间
     */
    public Date getEditTime() {
        return editTime;
    }


    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    /**
     * 设置：修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取：状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getResultMap() {
        return this.resultMap;
    }

    public Map<String, String> getDescribeMap() {
        return this.describeMap;
    }

    public Map<String, String> getTimeAuditMap() {
        return this.timeAuditMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }

    public void setDescribeMap(Map<String, String> describeMap) {
        this.describeMap = describeMap;
    }

    public void setTimeAuditMap(Map<String, String> timeAuditMap) {
        this.timeAuditMap = timeAuditMap;
    }
}
