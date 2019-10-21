package com.eanfang.biz.model.entity.station;

import com.alibaba.fastjson.JSON;
import com.eanfang.biz.model.bean.LeavePostDetailBean;
import com.eanfang.biz.model.entity.AccountEntity;
import com.eanfang.biz.model.entity.UserEntity;
import com.eanfang.biz.model.entity.Ys7DevicesEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 岗位表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2019-06-06 17:28:46
 */
@Getter
@Setter
@Accessors(chain = true)
public class StationDetectStationsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //岗位id
    //@TableField(value = "station_id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long stationId;
    //station_detect主键，子账号id
    //@TableField(value = "stationDeviceId")
    private Long stationDeviceId;
    //脱岗配置表id
    //@TableField(value = "config_id")
    private Long configId;
    //岗位名称
    //@TableField(value = "station_name")
    private String stationName;
    //岗位位置
    //@TableField(value = "station_area")
    private String stationArea;
    //位置编号
    //@TableField(value = "station_code")
    private String stationCode;
    //岗位描述
    //@TableField(value = "station_deccribe")
    private String stationDescribe;
    //城市编码
    //@TableField(value = "station_place_code")
    private String stationPlaceCode;
    //地区名称
    //@TableField(value = "station_place_name")
    private String stationPlaceName;
    //项目名称
    //@TableField(value = "station_project")
    private String stationProject;
    //项目部id(预留)
    //@TableField(value = "department_project_id")
    private Long departmentProjectId;
    //部门id
    //@TableField(value = "department_id")
    private Long departmentId;
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
    //岗位状态， 1正常，0禁用， 2删除
    //@TableField(value = "status")
    private Integer status;
    //详细地址
    //@TableField(value = "station_address")
    private String stationAddress;
    //详细地址
    //@TableField(value = "img_x")
    private Integer imgX;
    //详细地址
    //@TableField(value = "img_y")
    private Integer imgY;
    //img_width
    //@TableField(value = "img_width")
    private Integer imgWidth;
    //img_height
    //@TableField(value = "img_height")
    private Integer imgHeight;

    /**
     * 绑定的设备信息
     */

    private Ys7DevicesEntity deviceEntity;

    /**
     * 岗位设备关系表
     */

    private StationDeviceEntity stationDevice;

    /**
     * 值班人员userId列表
     */

    private List<Long> dutyUserIdList;

    /**
     * 负责人userId列表
     */

    private List<Long> chargeUserIdList;

    /**
     * 值班人员Staff列表
     */

    private List<StationDetectStaffEntity> dutyStaffList;

    /**
     * 负责人Staff列表
     */

    private List<StationDetectStaffEntity> chargeStaffList;

    /**
     * 值班人user列表
     */

    private List<UserEntity> dutyUserList;

    /**
     * 负责人user列表
     */

    private List<UserEntity> chargeUserList;

    /**
     * 报警数量
     */

    private Long alertsCount;

    /**
     * 岗位数量
     */

    private Long stationCount;

    /**
     * 查询时间
     */

    private String queryDate;

    /**
     * 创建人
     */

    private AccountEntity createAccount;

    /**
     * 更新人
     */

    private AccountEntity updateAccount;

    /**
     * 检测间隔时长(分钟)
     */

    private Integer intervalLength;

    /**
     * 岗位对应的规则 列表
     */

    private List<StationDetectRulesEntity> rulesList;

    private LeavePostDetailBean mLeavePostDetailBean;
    private UserEntity nowUser;

    public LeavePostDetailBean getLeavePostDetailBean() {
        mLeavePostDetailBean = new LeavePostDetailBean();
        mLeavePostDetailBean.setName(stationName);
        mLeavePostDetailBean.setAreaCode(stationPlaceCode);
        mLeavePostDetailBean.setPosition(stationArea);
        mLeavePostDetailBean.setStatus(status);
        if (deviceEntity != null) {
            mLeavePostDetailBean.setImg(deviceEntity.getLivePic());
        }
        mLeavePostDetailBean.setPageType(0);
        mLeavePostDetailBean.setStationId(stationId);
        return mLeavePostDetailBean;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StationDetectStationsEntity) {
            if (this.stationId == null || other == null) {
                return false;
            }
            return this.stationId.equals(((StationDetectStationsEntity) other).stationId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stationId == null) ? 0 : stationId.hashCode());
        return result;
    }

}
