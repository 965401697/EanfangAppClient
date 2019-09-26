package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 萤石云设备表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:39:44
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ys7DevicesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id
    //@TableField(value = "device_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long deviceId;
    //设备序列号
    //@TableField(value = "ys7_device_serial")
    private String ys7DeviceSerial;
    //设备验证码
    //@TableField(value = "ys7_device_validate_code")
    private String ys7DeviceValidateCode;
    //设备名
    //@TableField(value = "device_name")
    private String deviceName;
    //萤石云视频接入基础url
    //@TableField(value = "ys7_url")
    private String ys7Url;
    //通道号，IPC设备是1
    //@TableField(value = "ys7_channel")
    private Integer ys7Channel;
    //归属公司id
    //@TableField(value = "company_id")
    private Long companyId;
    //设备状态，1启用 0禁用 2删除
    //@TableField(value = "status")
    private Integer status;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //创建人user_id
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //更新时间
    //@TableField(value = "update_time")
    private Date updateTime;
    //更新人user_id
    //@TableField(value = "update_user_id")
    private Long updateUserId;
    //是否占用状态，1占用 0未占用
    //@TableField(value = "is_in_use")
    private Integer isInUse;
    //最近一次的图像截图
    //@TableField(value = "live_pic")
    private String livePic;
    //设备归属，0平台 1用户
    //@TableField(value = "belong_to")
    private Integer belongTo;
    //设备支持功能，0脱岗 1实时监控 2人脸检测
    //@TableField(value = "function_in_use")
    private String functionInUse;

    private boolean isChecked;

    /**
     * 直播地址
     */
    private String liveAddress;

    /**
     * 高清直播地址
     */
    private String hdAddress;

    /**
     * 公司信息
     */
    private OrgUnitEntity orgUnitEntity;

    /*
     * 子账号
     */
    private Ys7AccountEntity ys7AccountEntity;

    /**
     * station关系表
     */
    private StationDeviceEntity stationDevice;

    /**
     * real_time关系表
     */
    private RealTimeDeviceEntity realTimeDevice;

    /**
     * face_detect关系表
     */
//    private FaceDetectDeviceEntity faceDetectDevice;

    /**
     * 分组
     */
//    private RealTimeGroupEntity realTimeGroupEntity;
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Ys7DevicesEntity) {
            if (this.deviceId == null || other == null) {
                return false;
            }
            return this.deviceId.equals(((Ys7DevicesEntity) other).deviceId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        return result;
    }
}
