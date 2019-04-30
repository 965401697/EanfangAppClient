package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.mybatisplus.annotations.TableId;
import com.mybatisplus.annotations.TableName;
import com.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * 故障处理审核信息表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-08 15:11:19
 */
public class BughandleAuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //故障处理表ID
    //@TableField(value = "bus_bughandle_id")
    private Long busBughandleId;
    //结论(通过json存储，使用时请自行解析)
    //@TableField(value = "result_json")
    private String resultJson;
    //描述(通过json存储，使用时请自行解析)
    //@TableField(value = "describe_json")
    private String describeJson;
    //审核意见
    //@TableField(value = "audit_idea")
    private String auditIdea;
    //是否合格
    //@TableField(value = "is_qualified")
    private Integer isQualified;
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
     * 获取：故障处理表ID
     */
    public Long getBusBughandleId() {
        return busBughandleId;
    }

    /**
     * 设置：故障处理表ID
     */
    public void setBusBughandleId(Long busBughandleId) {
        this.busBughandleId = busBughandleId;
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
     * 获取：是否合格
     */
    public Integer getIsQualified() {
        return isQualified;
    }

    /**
     * 设置：是否合格
     */
    public void setIsQualified(Integer isQualified) {
        this.isQualified = isQualified;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BughandleAuditEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BughandleAuditEntity) other).id);
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
