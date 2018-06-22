package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 客户设备变更记录表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-06-04 16:20:38
 */
@Getter
@Setter
@TableName(value = "cust_device_change_log")
public class CustDeviceChangeLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //设备编号
    //@TableField(value = "device_no")
    private String deviceNo;
    //负责人
    //@TableField(value = "charge_user")
    private Long chargeUser;
    //负责部门
    //@TableField(value = "charge_org")
    private String chargeOrg;
    //保修期限
    //@TableField(value = "warranty_period")
    private String warrantyPeriod;
    //保修状态(0,保内，1,保外)
    //@TableField(value = "warranty_status")
    private Integer warrantyStatus;
    //现场位置图
    //@TableField(value = "location_pictures")
    private String locationPictures;
    //维修人
    //@TableField(value = "repair_user")
    private String repairUser;
    //维修公司
    //@TableField(value = "repair_company")
    private String repairCompany;
    //设备状况备注信息
    //@TableField(value = "device_status_info")
    private String deviceStatusInfo;
    //版本号
    //@TableField(value = "change_log_version")
    private Integer changeLogVersion;
    //安装位置
    //@TableField(value = "location")
    private String location;
    //安装时间
    //@TableField(value = "install_date")
    private Date installDate;
    //位置编号
    //@TableField(value = "location_number")
    private String locationNumber;
    //状态(0出厂,1仓储运输，2正常运行，3故障待修复，4备用，5禁用，6报废)

    //@TableField(value = "status")
    private Integer status;
    //接收人
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //接收人公司
    //@TableField(value = "assignee_company_id")
    private Long assigneeCompanyId;
    //设备参数列表
    @TableField(exist = false)
    private List<CustDeviceParamEntity> params;

    public List<CustDeviceParamEntity> getParams() {
        return params;
    }

    public void setParams(List<CustDeviceParamEntity> params) {
        this.params = params;
    }

    /**
     * 设置：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：设备编号
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    /**
     * 获取：设备编号
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置：负责人
     */
    public void setChargeUser(Long chargeUser) {
        this.chargeUser = chargeUser;
    }

    /**
     * 获取：负责人
     */
    public Long getChargeUser() {
        return chargeUser;
    }

    /**
     * 设置：负责部门
     */
    public void setChargeOrg(String chargeOrg) {
        this.chargeOrg = chargeOrg;
    }

    /**
     * 获取：负责部门
     */
    public String getChargeOrg() {
        return chargeOrg;
    }

    /**
     * 设置：保修期限
     */
    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    /**
     * 获取：保修期限
     */
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    /**
     * 设置：保修状态(0,保内，1,保外)
     */
    public void setWarrantyStatus(Integer warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    /**
     * 获取：保修状态(0,保内，1,保外)
     */
    public Integer getWarrantyStatus() {
        return warrantyStatus;
    }

    /**
     * 设置：现场位置图
     */
    public void setLocationPictures(String locationPictures) {
        this.locationPictures = locationPictures;
    }

    /**
     * 获取：现场位置图
     */
    public String getLocationPictures() {
        return locationPictures;
    }

    /**
     * 设置：维修人
     */
    public void setRepairUser(String repairUser) {
        this.repairUser = repairUser;
    }

    /**
     * 获取：维修人
     */
    public String getRepairUser() {
        return repairUser;
    }

    /**
     * 设置：维修公司
     */
    public void setRepairCompany(String repairCompany) {
        this.repairCompany = repairCompany;
    }

    /**
     * 获取：维修公司
     */
    public String getRepairCompany() {
        return repairCompany;
    }

    /**
     * 设置：设备状况备注信息
     */
    public void setDeviceStatusInfo(String deviceStatusInfo) {
        this.deviceStatusInfo = deviceStatusInfo;
    }

    /**
     * 获取：设备状况备注信息
     */
    public String getDeviceStatusInfo() {
        return deviceStatusInfo;
    }

    /**
     * 设置：版本号
     */
    public void setChangeLogVersion(Integer changeLogVersion) {
        this.changeLogVersion = changeLogVersion;
    }

    /**
     * 获取：版本号
     */
    public Integer getChangeLogVersion() {
        return changeLogVersion;
    }

    /**
     * 设置：安装位置
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取：安装位置
     */
    public String getLocation() {
        return location;
    }

    /**
     * 设置：安装时间
     */
    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    /**
     * 获取：安装时间
     */
    public Date getInstallDate() {
        return installDate;
    }

    /**
     * 设置：位置编号
     */
    public void setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
    }

    /**
     * 获取：位置编号
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * 设置：状态(0出厂,1仓储运输，2正常运行，3故障待修复，4备用，5禁用，6报废)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态(0出厂,1仓储运输，2正常运行，3故障待修复，4备用，5禁用，6报废)
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：接收人
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：接收人
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：接收人公司
     */
    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    /**
     * 获取：接收人公司
     */
    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof CustDeviceChangeLogEntity) {
            if (this.id == null || other == null)
                return false;

            return this.id.equals(((CustDeviceChangeLogEntity) other).id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @TableField(exist = false)
    public UserEntity chargeUserEntity;
    @TableField(exist = false)
    public OrgEntity chargeOrgEntity;
}
