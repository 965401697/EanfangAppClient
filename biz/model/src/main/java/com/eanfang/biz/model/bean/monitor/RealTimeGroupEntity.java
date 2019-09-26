package com.eanfang.biz.model.bean.monitor;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 实时监控分组表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-09-02 11:37:41
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealTimeGroupEntity implements Serializable {

    private boolean isHaveSubGroup = false;
    //分组id
    //@TableField(value = "group_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long groupId;
    //coinfig表id
    //@TableField(value = "org_id")
    private Long configId;
    //分组名
    //@TableField(value = "group_name")
    private String groupName;
    //设备数量
    //@TableField(value = "device_count")
    private Integer deviceCount;
    //上级分组id，默认是0顶级分组
    //@TableField(value = "parent_group_id")
    private Long parentGroupId;
    //上级分组name，默认是0顶级分组
    //@TableField(value = "parent_group_id")
    private String parentGroupName;
    //分组状态，0禁用/删除，1可用
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

    /**
     * 下级分组列表
     */
    private List<RealTimeGroupEntity> subGroupList;

    /**
     * 分组下包含的设备列表
     */
    private List<Ys7DevicesEntity> deviceList;

    /**
     * 上级分组
     */
    private RealTimeGroupEntity parentGroup;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RealTimeGroupEntity) {
            if (this.groupId == null || other == null) {
                return false;
            }
            return this.groupId.equals(((RealTimeGroupEntity) other).groupId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
        return result;
    }
}
