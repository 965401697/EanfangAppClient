package com.eanfang.biz.model.entity.station;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 脱岗设备关系表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:40:12
 */
@Getter
@Setter
public class StationDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //关系表id
    //@TableField(value = "station_device_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long stationDeviceId;
    //设备表id
    //@TableField(value = "device_id")
    private Long deviceId;
    //岗位id
    //@TableField(value = "station_id")
    private Long stationId;
    //脱岗配置表id
    //@TableField(value = "station_config_id")
    private Long stationConfigId;
    //生效时间
    //@TableField(value = "begin_date")
    private Date beginTime;
    //失效时间
    //@TableField(value = "end_date")
    private Date endTime;
    //脱岗设备可用状态，0不可用，1可用
    //@TableField(value = "end_date")
    private Integer status;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    /*
     * 设备
     */
    private Ys7DevicesEntity ys7DevicesEntity;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StationDeviceEntity) {
            if (this.stationDeviceId == null || other == null) {
                return false;
            }
            return this.stationDeviceId.equals(((StationDeviceEntity) other).stationDeviceId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stationDeviceId == null) ? 0 : stationDeviceId.hashCode());
        return result;
    }
}
