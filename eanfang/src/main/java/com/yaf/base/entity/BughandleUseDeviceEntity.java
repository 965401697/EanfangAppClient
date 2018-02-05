package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * 故障处理耗材表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-08 15:11:19
 */
@Getter
@Setter
@TableName(value = "bus_bughandle_use_device")
public class BughandleUseDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //故障处理表ID
    //@TableField(value = "bus_bughandle_id")
    private Long busBughandleId;
    //设备型号编码（基础数据表）
    //@TableField(value = "model_code")
    private String modelCode;
    //
    //@TableField(value = "device_name")
    private String deviceName;
    //
    //@TableField(value = "shop_device_id")
    private Long shopDeviceId;
    //数量
    //@TableField(value = "count")
    private Integer count;
    //备注
    //@TableField(value = "remark_info")
    private String remarkInfo;

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
     * 获取：设备型号编码（基础数据表）
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 设置：设备型号编码（基础数据表）
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    /**
     * 获取：
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置：
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取：数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置：数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取：备注
     */
    public String getRemarkInfo() {
        return remarkInfo;
    }

    /**
     * 设置：备注
     */
    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof BughandleUseDeviceEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((BughandleUseDeviceEntity) other).id);
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
