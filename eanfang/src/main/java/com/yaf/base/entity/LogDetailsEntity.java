package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * 日志详情
 *
 * @author jxtpro
 * @email jxtpro@163.com
 * @date 2018-05-18 14:52:36
 */
@TableName(value = "oa_log_details")
public class LogDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //序号
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //布防日志ID
    //@TableField(value = "protection_log_id")
    private Long protectionLogId;
    //防区编号
    //@TableField(value = "slip_num")
    private String slipNum;
    //防区位置
    //@TableField(value = "play_localtion")
    private String playLocaltion;
    //报警次数
    //@TableField(value = "alarm_num")
    private Integer alarmNum;
    //报警原因(旁路的报警原因：0-设备故障 1-非设备故障)
    //@TableField(value = "alarm_reason")
    private Integer alarmReason;
    //备注信息
    //@TableField(value = "note_info")
    private String noteInfo;
    //日志类型(0-旁路,1-闯防,2-误报)
    //@TableField(value = "log_type")
    private Integer logType;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
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
    //状态(0:未删除,1:已删除)
    //@TableField(value = "status")
    private Integer status;
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


    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    /**
     * 设置：序号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：序号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：布防日志ID
     */
    public void setProtectionLogId(Long protectionLogId) {
        this.protectionLogId = protectionLogId;
    }

    /**
     * 获取：布防日志ID
     */
    public Long getProtectionLogId() {
        return protectionLogId;
    }

    /**
     * 设置：防区编号
     */
    public void setSlipNum(String slipNum) {
        this.slipNum = slipNum;
    }

    /**
     * 获取：防区编号
     */
    public String getSlipNum() {
        return slipNum;
    }

    /**
     * 设置：防区位置
     */
    public void setPlayLocaltion(String playLocaltion) {
        this.playLocaltion = playLocaltion;
    }

    /**
     * 获取：防区位置
     */
    public String getPlayLocaltion() {
        return playLocaltion;
    }

    /**
     * 设置：报警次数
     */
    public void setAlarmNum(Integer alarmNum) {
        this.alarmNum = alarmNum;
    }

    /**
     * 获取：报警次数
     */
    public Integer getAlarmNum() {
        return alarmNum;
    }

    /**
     * 设置：报警原因(旁路的报警原因：0-设备故障 1-非设备故障)
     */
    public void setAlarmReason(Integer alarmReason) {
        this.alarmReason = alarmReason;
    }

    /**
     * 获取：报警原因(旁路的报警原因：0-设备故障 1-非设备故障)
     */
    public Integer getAlarmReason() {
        return alarmReason;
    }

    /**
     * 设置：备注信息
     */
    public void setNoteInfo(String noteInfo) {
        this.noteInfo = noteInfo;
    }

    /**
     * 获取：备注信息
     */
    public String getNoteInfo() {
        return noteInfo;
    }

    /**
     * 设置：日志类型(0-旁路,1-闯防,2-误报)
     */
    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    /**
     * 获取：日志类型(0-旁路,1-闯防,2-误报)
     */
    public Integer getLogType() {
        return logType;
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
     * 设置：状态(0:未删除,1:已删除)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态(0:未删除,1:已删除)
     */
    public Integer getStatus() {
        return status;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LogDetailsEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((LogDetailsEntity) other).id);
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
