package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * 维保执行计划确认信息的检查结果表（上门解决）
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-07-13 14:07:54
 */
@TableName(value = "shop_maintenance_exam_result")
public class ShopMaintenanceExamResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键递增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //维保执行计划确认信息表id
    //@TableField(value = "shop_bughandle_maintenance_confirm_id")
    private Long shopBughandleMaintenanceConfirmId;
    //存在问题
    //@TableField(value = "exist_questions")
    private String existQuestions;
    //处理过程
    //@TableField(value = "dispose_course")
    private String disposeCourse;
    //备注
    //@TableField(value = "info")
    private String info;
    //处理结论
    //@TableField(value = "exist_result")
    private Integer existResult;
    //处理图片
    //@TableField(value = "picture")
    private String picture;
    //检查人（创建人）
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //检查时间
    //@TableField(value = "time")
    private Date time;
    //状态（0：遗留，1：完成，2：损坏）
    //@TableField(value = "status")
    private Integer status;

    private Long shopMaintenanceOrderId;

    public Long getShopMaintenanceOrderId() {
        return shopMaintenanceOrderId;
    }

    public void setShopMaintenanceOrderId(Long shopMaintenanceOrderId) {
        this.shopMaintenanceOrderId = shopMaintenanceOrderId;
    }

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
     * 设置：维保执行计划确认信息表id
     */
    public void setShopBughandleMaintenanceConfirmId(Long shopBughandleMaintenanceConfirmId) {
        this.shopBughandleMaintenanceConfirmId = shopBughandleMaintenanceConfirmId;
    }

    /**
     * 获取：维保执行计划确认信息表id
     */
    public Long getShopBughandleMaintenanceConfirmId() {
        return shopBughandleMaintenanceConfirmId;
    }

    /**
     * 设置：存在问题
     */
    public void setExistQuestions(String existQuestions) {
        this.existQuestions = existQuestions;
    }

    /**
     * 获取：存在问题
     */
    public String getExistQuestions() {
        return existQuestions;
    }

    /**
     * 设置：处理过程
     */
    public void setDisposeCourse(String disposeCourse) {
        this.disposeCourse = disposeCourse;
    }

    /**
     * 获取：处理过程
     */
    public String getDisposeCourse() {
        return disposeCourse;
    }

    /**
     * 设置：备注
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 获取：备注
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置：处理结论
     */
    public void setExistResult(Integer existResult) {
        this.existResult = existResult;
    }

    /**
     * 获取：处理结论
     */
    public Integer getExistResult() {
        return existResult;
    }

    /**
     * 设置：处理图片
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取：处理图片
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置：检查人（创建人）
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：检查人（创建人）
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：检查时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取：检查时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置：状态（0：遗留，1：完成，2：损坏）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态（0：遗留，1：完成，2：损坏）
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
        if (other instanceof ShopMaintenanceExamResultEntity) {
            if (this.id == null || other == null)
                return false;

            return this.id.equals(((ShopMaintenanceExamResultEntity) other).id);
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
