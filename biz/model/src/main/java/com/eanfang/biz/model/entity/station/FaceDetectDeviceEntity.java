package com.eanfang.biz.model.entity.station;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 人脸识别设备关系表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:36:47
 */
@Getter
@Setter
public class FaceDetectDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //人脸设备关系表id
    //@TableField(value = "face_detect_device_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long faceDetectDeviceId;
    //设备表id
    //@TableField(value = "device_id")
    private Long deviceId;
    //config表id
    //@TableField(value = "config_id")
    private Long configId;
    //生效时间
    //@TableField(value = "begin_date")
    private Date beginTime;
    //失效时间
    //@TableField(value = "end_date")
    private Date endTime;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof FaceDetectDeviceEntity) {
            if (this.faceDetectDeviceId == null || other == null) {
                return false;
            }
            return this.faceDetectDeviceId.equals(((FaceDetectDeviceEntity) other).faceDetectDeviceId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((faceDetectDeviceId == null) ? 0 : faceDetectDeviceId.hashCode());
        return result;
    }
}
