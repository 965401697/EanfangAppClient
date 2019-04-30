package com.yaf.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


/**
 * 报修单的真实故障信息
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-11-22 17:01:18
 */
@TableName(value = "bus_repair_failure")
@Getter
@Setter
public class RepairFailureEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "RepairFailureEntity{" +
                "id=" + id +
                ", busRepairOrderId=" + busRepairOrderId +
                ", isMisinformation=" + isMisinformation +
                ", businessThreeCode='" + businessThreeCode + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", pictures='" + pictures + '\'' +
                ", bugPosition='" + bugPosition + '\'' +
                ", bugDescription='" + bugDescription + '\'' +
                ", custDeviceId=" + custDeviceId +
                ", headDeviceFailureId=" + headDeviceFailureId +
                ", headDeviceId=" + headDeviceId +
                ", deviceName='" + deviceName + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", editUserId=" + editUserId +
                ", editTime=" + editTime +
                ", status=" + status +
                ", ownerUserId=" + ownerUserId +
                ", ownerTopCompanyId=" + ownerTopCompanyId +
                ", ownerOrgCode='" + ownerOrgCode + '\'' +
                ", assigneeUserId=" + assigneeUserId +
                ", assigneeTopCompanyId=" + assigneeTopCompanyId +
                ", assigneeOrgCode='" + assigneeOrgCode + '\'' +
                ", placeId='" + placeId + '\'' +
                ", sketch='" + sketch + '\'' +
                ", locationNumber='" + locationNumber + '\'' +
                ", maintenanceStatus=" + maintenanceStatus +
                ", repairCount=" + repairCount +
                ", mp4_path='" + mp4_path + '\'' +
                ", bughandleDetailEntityList=" + bughandleDetailEntityList +
                ", repairOrderEntity=" + repairOrderEntity +
                ", ownerUserEntity=" + ownerUserEntity +
                ", ownerOrgEntity=" + ownerOrgEntity +
                ", assigneeUserEntity=" + assigneeUserEntity +
                ", assigneeOrgEntity=" + assigneeOrgEntity +
                '}';
    }

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //报修订单表ID
    //@TableField(value = "bus_repair_order_id")
    private Long busRepairOrderId;
    //是否误报(0：否，1：是)
    //@TableField(value = "is_misinformation")
    private Integer isMisinformation;
    //三级业务类型编码（基础数据表）
    //@TableField(value = "business_three_code")
    private String businessThreeCode;
    //设备类型编码（基础数据表）
    //@TableField(value = "model_code")
    private String modelCode;
    //故障图片（多张图片地址用逗号分割）
    //@TableField(value = "pictures")
    private String pictures;
    //故障位置
    //@TableField(value = "bug_position")
    private String bugPosition;
    //故障描述
    //@TableField(value = "bug_description")
    private String bugDescription;
    //客户设备表ID
    //@TableField(value = "cust_device_id")
    private Long custDeviceId;
    //关联的系统故障ID（如果客户选择了故障描述参考）
    //@TableField(value = "head_device_failure_id")
    private Long headDeviceFailureId;
    //关联的系统设备表ID（如果客户设备表选择了系统设备）
    //@TableField(value = "head_device_id")
    private Long headDeviceId;
    //设备名称
    //@TableField(value = "device_name")
    private String deviceName;

    //设备编号
    //@TableField(value = "device_no")
    private String deviceNo;


    //创建人（故障处理人）
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间（故障处理时间）
    //@TableField(value = "create_time")
    private Date createTime;
    //修改人
    //@TableField(value = "edit_user_id")
    private Long editUserId;
    //修改时间
    //@TableField(value = "edit_time")
    private Date editTime;
    //状态（0：待修复，1：修复完成，2：遗留）
    //@TableField(value = "status")
    private Integer status;

    //归属人
    //@TableField(value = "owner_user_id")
    private Long ownerUserId;
    //归属总公司
    //@TableField(value = "owner_top_company_id")
    private Long ownerTopCompanyId;
    //归属部门编码
    //@TableField(value = "owner_org_code")
    private String ownerOrgCode;
    //当前报修 最新的受理人
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //当前报修 最新的受理人总公司
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;
    //当前报修 最新的受理部门编码
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;

    //区/县 id
    //@TableField(value = "place_id")
    private String placeId;

    //故障简述
    //@TableField(value = "sketch")
    private String sketch;
    //位置编号
    //@TableField(value = "location_number")
    private String locationNumber;
    //维保状态
    //@TableField(value = "maintenance_status")
    private Integer maintenanceStatus;
    //维修次数
    //@TableField(value = "repair_count")
    private Integer repairCount;
    //短视频
    private String mp4_path;

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    public Integer getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(Integer maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public Integer getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(Integer repairCount) {
        this.repairCount = repairCount;
    }

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
     * 获取：报修订单表ID
     */
    public Long getBusRepairOrderId() {
        return busRepairOrderId;
    }

    /**
     * 设置：报修订单表ID
     */
    public void setBusRepairOrderId(Long busRepairOrderId) {
        this.busRepairOrderId = busRepairOrderId;
    }

    /**
     * 获取：是否误报(0：否，1：是)
     */
    public Integer getIsMisinformation() {
        return isMisinformation;
    }

    /**
     * 设置：是否误报(0：否，1：是)
     */
    public void setIsMisinformation(Integer isMisinformation) {
        this.isMisinformation = isMisinformation;
    }

    /**
     * 获取：三级业务类型编码（基础数据表）
     */
    public String getBusinessThreeCode() {
        return businessThreeCode;
    }

    /**
     * 设置：三级业务类型编码（基础数据表）
     */
    public void setBusinessThreeCode(String businessThreeCode) {
        this.businessThreeCode = businessThreeCode;
    }

    /**
     * 获取：设备类型编码（基础数据表）
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 设置：设备类型编码（基础数据表）
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    /**
     * 获取：故障图片（多张图片地址用逗号分割）
     */
    public String getPictures() {
        return pictures;
    }

    /**
     * 设置：故障图片（多张图片地址用逗号分割）
     */
    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    /**
     * 获取：故障位置
     */
    public String getBugPosition() {
        return bugPosition;
    }

    /**
     * 设置：故障位置
     */
    public void setBugPosition(String bugPosition) {
        this.bugPosition = bugPosition;
    }

    /**
     * 获取：故障描述
     */
    public String getBugDescription() {
        return bugDescription;
    }

    /**
     * 设置：故障描述
     */
    public void setBugDescription(String bugDescription) {
        this.bugDescription = bugDescription;
    }

    /**
     * 获取：客户设备表ID
     */
    public Long getCustDeviceId() {
        return custDeviceId;
    }

    /**
     * 设置：客户设备表ID
     */
    public void setCustDeviceId(Long custDeviceId) {
        this.custDeviceId = custDeviceId;
    }

    /**
     * 获取：关联的系统故障ID（如果客户选择了故障描述参考）
     */
    public Long getHeadDeviceFailureId() {
        return headDeviceFailureId;
    }

    /**
     * 设置：关联的系统故障ID（如果客户选择了故障描述参考）
     */
    public void setHeadDeviceFailureId(Long headDeviceFailureId) {
        this.headDeviceFailureId = headDeviceFailureId;
    }

    /**
     * 获取：关联的系统设备表ID（如果客户设备表选择了系统设备）
     */
    public Long getHeadDeviceId() {
        return headDeviceId;
    }

    /**
     * 设置：关联的系统设备表ID（如果客户设备表选择了系统设备）
     */
    public void setHeadDeviceId(Long headDeviceId) {
        this.headDeviceId = headDeviceId;
    }

    /**
     * 获取：设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置：设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取：创建人（故障处理人）
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人（故障处理人）
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建时间（故障处理时间）
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间（故障处理时间）
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：修改人
     */
    public Long getEditUserId() {
        return editUserId;
    }

    /**
     * 设置：修改人
     */
    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    /**
     * 获取：修改时间
     */
    public Date getEditTime() {
        return editTime;
    }

    /**
     * 设置：修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取：状态（0：待修复，1：修复完成，2：遗留）
     */
    public Integer getStatus() {
        return status;
    }


    /**
     * 设置：状态（0：待修复，1：修复完成，2：遗留）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


    /**
     * 当前真实故障对应的多个 故障处理
     */
    @TableField(exist = false)
    private List<BughandleDetailEntity> bughandleDetailEntityList;

    /**
     * 当前真实故障 对应的 报修单
     */
    @TableField(exist = false)
    private RepairOrderEntity repairOrderEntity;

    @TableField(exist = false)
    private UserEntity ownerUserEntity;
    @TableField(exist = false)
    private OrgEntity ownerOrgEntity;

    @TableField(exist = false)
    private UserEntity assigneeUserEntity;
    @TableField(exist = false)
    private OrgEntity assigneeOrgEntity;


}
