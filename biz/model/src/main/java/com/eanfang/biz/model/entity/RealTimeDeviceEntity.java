package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 实时监控设备关系表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:37:41
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    //@TableField(value = "real_time_device_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long realTimeDeviceId;
    //设备分组表id
    //@TableField(value = "group_id")
    private Long groupId;
    //设备表id
    //@TableField(value = "device_id")
    private Long deviceId;
    //生效时间
    //@TableField(value = "begin_date")
    private Date beginTime;
    //失效时间
    //@TableField(value = "end_date")
    private Date endTime;

    /**
     * 设备
     */
    private Ys7DevicesEntity ys7DevicesEntity;

    /**
     * 实时监控config表
     */
    private RealTimeConfigEntity realTimeConfigEntity;

    /*
     * ys7Account
     */
    private Ys7AccountEntity ys7AccountEntity;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RealTimeDeviceEntity) {
            if (this.realTimeDeviceId == null || other == null) {
                return false;
            }
            return this.realTimeDeviceId.equals(((RealTimeDeviceEntity) other).realTimeDeviceId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((realTimeDeviceId == null) ? 0 : realTimeDeviceId.hashCode());
        return result;
    }
}
