package com.eanfang.biz.model.entity.station;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-06-06 17:28:47
 */
@Getter
@Setter
public class StationDetectRulesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键id1
    //@TableField(value = "rule_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long ruleId;
    //岗位id1
    //@TableField(value = "station_id")
    private Long stationId;
    //脱岗检测配置id1
    //@TableField(value = "config_id")
    private Long configId;
    //检测规则1
    //@TableField(value = "rule_detail")
    private String ruleDetail;

    //检测规则类型1
    private Integer ruleType;

    //是否检测人形，0否1是（预留）1
    //@TableField(value = "is_human_detect")
    private Integer isHumanDetect;
    //是否人脸检测，0否1是（预留）1
    //@TableField(value = "is_face_detect")
    private Integer isFaceDetect;
    //内置规则,1
    //@TableField(value = "inner_rule")
    private Integer innerRule;
    //检测间隔时长, 见Const.detectTime, 0:10min, 1:15min, 20min,30min,45min,1h,2h,4h, 8:8h1
    //@TableField(value = "interval_length")
    private Integer intervalLength;
    //创建时间1
    //@TableField(value = "create_time")
    private Date createTime;
    //创建人user_id1
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //更新时间1
    //@TableField(value = "update_time")
    private Date updateTime;
    //更新人user_id1
    //@TableField(value = "update_user_id")
    private Long updateUserId;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StationDetectRulesEntity) {
            if (this.ruleId == null || other == null) {
                return false;
            }
            return this.ruleId.equals(((StationDetectRulesEntity) other).ruleId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ruleId == null) ? 0 : ruleId.hashCode());
        return result;
    }
}
