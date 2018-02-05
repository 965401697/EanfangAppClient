package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 故障处理表(上门解决)
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-08 15:11:19
 */
@Getter
@Setter
@TableName(value = "bus_bughandle_detail")
public class BughandleDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //故障处理确认信息表ID
    //@TableField(value = "bus_bughandle_confirm_id")
    private Long busBughandleConfirmId;
    //订单故障表ID
    //@TableField(value = "bus_repair_failure_id")
    private Long busRepairFailureId;
    //检查过程
    //@TableField(value = "check_process")
    private String checkProcess;
    //原因
    //@TableField(value = "cause")
    private String cause;
    //处理
    //@TableField(value = "handle")
    private String handle;
    //故障点照片（多张图片地址用逗号分割）
    //@TableField(value = "point_pictures")
    private String pointPictures;
    //故障恢复后表象照片（多张图片地址用逗号分割）
    //@TableField(value = "restore_pictures")
    private String restorePictures;
    //处理后现场照片（多张图片地址用逗号分割）
    //@TableField(value = "after_handle_pictures")
    private String afterHandlePictures;
    //设备回装照片（多张图片地址用逗号分割）
    //@TableField(value = "device_return_install_pictures")
    private String deviceReturnInstallPictures;
    //状态（0：维修完成，1：报价更换，2：未解决）
    //@TableField(value = "status")
    private Integer status;
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
    //故障表象照片
    private String presentationPictures;
    //工具及蓝布照片
    private String toolPictures;
    /**
     * 当前故障处理明细对应的 真实故障
     */
    @TableField(exist = false)
    private RepairFailureEntity failureEntity;
    /**
     * 故障处理明细 参数
     */
    @TableField(exist = false)
    private List<BughandleParamEntity> paramEntityList;
    /**
     * 故障处理明细 设备
     */
    @TableField(exist = false)
    private List<BughandleUseDeviceEntity> useDeviceEntityList;

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
     * 获取：故障处理确认信息表ID
     */
    public Long getBusBughandleConfirmId() {
        return busBughandleConfirmId;
    }

    /**
     * 设置：故障处理确认信息表ID
     */
    public void setBusBughandleConfirmId(Long busBughandleConfirmId) {
        this.busBughandleConfirmId = busBughandleConfirmId;
    }

    /**
     * 获取：检查过程
     */
    public String getCheckProcess() {
        return checkProcess;
    }

    /**
     * 设置：检查过程
     */
    public void setCheckProcess(String checkProcess) {
        this.checkProcess = checkProcess;
    }

    /**
     * 获取：原因
     */
    public String getCause() {
        return cause;
    }

    /**
     * 设置：原因
     */
    public void setCause(String cause) {
        this.cause = cause;
    }

    /**
     * 获取：处理
     */
    public String getHandle() {
        return handle;
    }

    /**
     * 设置：处理
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * 获取：故障点照片（多张图片地址用逗号分割）
     */
    public String getPointPictures() {
        return pointPictures;
    }

    /**
     * 设置：故障点照片（多张图片地址用逗号分割）
     */
    public void setPointPictures(String pointPictures) {
        this.pointPictures = pointPictures;
    }

    /**
     * 获取：故障恢复后表象照片（多张图片地址用逗号分割）
     */
    public String getRestorePictures() {
        return restorePictures;
    }

    /**
     * 设置：故障恢复后表象照片（多张图片地址用逗号分割）
     */
    public void setRestorePictures(String restorePictures) {
        this.restorePictures = restorePictures;
    }

    /**
     * 获取：处理后现场照片（多张图片地址用逗号分割）
     */
    public String getAfterHandlePictures() {
        return afterHandlePictures;
    }

    /**
     * 设置：处理后现场照片（多张图片地址用逗号分割）
     */
    public void setAfterHandlePictures(String afterHandlePictures) {
        this.afterHandlePictures = afterHandlePictures;
    }

    /**
     * 获取：设备回装照片（多张图片地址用逗号分割）
     */
    public String getDeviceReturnInstallPictures() {
        return deviceReturnInstallPictures;
    }

    /**
     * 设置：设备回装照片（多张图片地址用逗号分割）
     */
    public void setDeviceReturnInstallPictures(String deviceReturnInstallPictures) {
        this.deviceReturnInstallPictures = deviceReturnInstallPictures;
    }

    /**
     * 获取：状态（0：维修完成，1：报价更换，2：未解决）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态（0：维修完成，1：报价更换，2：未解决）
     */
    public void setStatus(Integer status) {
        this.status = status;
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

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BughandleDetailEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BughandleDetailEntity) other).id);
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
