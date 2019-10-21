package com.eanfang.biz.model.entity.station;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.AccountEntity;

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
public class StationDetectStaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键staff_id
    //@TableField(value = "staff_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long staffId;
    //岗位id
    //@TableField(value = "station_id")
    private Long stationId;
    //员工userId
    //@TableField(value = "user_id")
    private Long userId;
    //员工类别，0负责人 1值班人
    //@TableField(value = "staff_type")
    private Integer staffType;
    //班次类型，0早班 1中班 2晚班
    //@TableField(value = "work_type")
    private Integer workType;
    //班次开始时间
    //@TableField(value = "begin_time")
    private Date beginTime;
    //班次结束时间
    //@TableField(value = "end_time")
    private Date endTime;
    //员工状态，1启用 0禁用 2删除
    //@TableField(value = "status")
    private Integer status;
    //员工姓名
    //@TableField(value = "name")
    private String name;
    //员工手机号
    //@TableField(value = "mobile")
    private String mobile;

    /*
     * account信息
     */
    private AccountEntity accountEntity;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StationDetectStaffEntity) {
            if (this.staffId == null || other == null) {
                return false;
            }
            return this.staffId.equals(((StationDetectStaffEntity) other).staffId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
        return result;
    }
}
