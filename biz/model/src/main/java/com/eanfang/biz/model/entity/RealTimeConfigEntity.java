package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 实时监控配置表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:37:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //实时监控配置表id
    //@TableField(value = "real_time_config_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long realTimeConfigId;
    //生效时间
    //@TableField(value = "begin_time")
    private Date beginTime;
    //失效时间
    //@TableField(value = "end_time")
    private Date endTime;
    //子账号表id
    //@TableField(value = "account_id")
    private Long accountId;
    //备注
    //@TableField(value = "remark")
    private String remark;
    //可用状态 0不可用 1可用
    //@TableField(value = "status")
    private Integer status;
    //设备数量
    //@TableField(value = "device_count")
    private Integer deviceCount;

    /**
     *
     */
    private Ys7AccountEntity ys7AccountEntity;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RealTimeConfigEntity) {
            if (this.realTimeConfigId == null || other == null) {
                return false;
            }
            return this.realTimeConfigId.equals(((RealTimeConfigEntity) other).realTimeConfigId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((realTimeConfigId == null) ? 0 : realTimeConfigId.hashCode());
        return result;
    }
}
