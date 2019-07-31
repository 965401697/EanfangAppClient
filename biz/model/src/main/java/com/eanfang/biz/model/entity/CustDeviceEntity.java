package com.eanfang.biz.model.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 客户设备表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-06-04 16:20:38
 */
public class CustDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //系统设备表ID
    private Long headDeviceId;
    //三级业务类型编码(基础数据表)
    private String businessThreeCode;
    //设备名称
    private String deviceName;
    //设备型号(基础数据表)
    private String modelCode;
    //制造商
    private String producerName;
    //产地
    private String producerPlace;
    //单位
    private Integer unit;
    //设备图片(多张图片地址用逗号分割)
    private String picture;
    //安装位置
    private String location;
    //安装时间
    private Date installDate;
    //设备价格
    private Integer devicePrice;
    //保修期限
    private String warrantyPeriod;
    //保修状态(0,保内，1,保外)
    private Integer warrantyStatus;
    //创建人
    private Long createUserId;
    //创建时间
    private Date createTime;
    //修改人
    private Long editUserId;
    //修改时间
    private Date editTime;
    //状态(0出厂,1仓储运输，2正常运行，3故障待修复，4备用，5禁用，6报废)

    private Integer status;
    //序列号
    private String serialNumber;
    //位置编号
    private String locationNumber;
    //采购人
    private Long purchaser;
    //采购部门
    private String purchaseOrg;
    //销售单位
    private String sellUnit;
    //购买时间
    private Date buyTime;
    //设备信息备注
    private String deviceInfo;
    //现场位置图
    private String locationPictures;
    //负责人
    private Long chargeUser;
    //负责部门
    private String chargeOrg;
    //维修人
    private String repairUser;
    //维修公司
    private String repairCompany;
    //设备状况备注信息
    private String deviceStatusInfo;
    //设备编号
    private String deviceNo;
    //版本号
    private Integer deviceVersion;
    //创建人
    private Long ownerUserId;
    //创建人公司
    private Long ownerCompanyId;
    //创建人归属总公司
    private Long ownerTopCompanyId;
    //创建人归属部门编码
    private String ownerOrgCode;
    //接收人
    private Long assigneeUserId;
    //接收人公司
    private Long assigneeCompanyId;
    //设备参数列表
    private List<CustDeviceParamEntity> params;

    public UserEntity ownerUserEntity;
    public OrgEntity ownerCompanyEnityt;

    public List<CustDeviceParamEntity> getParams() {
        return params;
    }

    public void setParams(List<CustDeviceParamEntity> params) {
        this.params = params;
    }

    public UserEntity getOwnerUserEntity() {
        return ownerUserEntity;
    }

    public void setOwnerUserEntity(UserEntity ownerUserEntity) {
        this.ownerUserEntity = ownerUserEntity;
    }

    public OrgEntity getOwnerCompanyEnityt() {
        return ownerCompanyEnityt;
    }

    public void setOwnerCompanyEnityt(OrgEntity ownerCompanyEnityt) {
        this.ownerCompanyEnityt = ownerCompanyEnityt;
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
     * 设置：系统设备表ID
     */
    public void setHeadDeviceId(Long headDeviceId) {
        this.headDeviceId = headDeviceId;
    }

    /**
     * 获取：系统设备表ID
     */
    public Long getHeadDeviceId() {
        return headDeviceId;
    }

    /**
     * 设置：三级业务类型编码(基础数据表)
     */
    public void setBusinessThreeCode(String businessThreeCode) {
        this.businessThreeCode = businessThreeCode;
    }

    /**
     * 获取：三级业务类型编码(基础数据表)
     */
    public String getBusinessThreeCode() {
        return businessThreeCode;
    }

    /**
     * 设置：设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取：设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置：设备型号(基础数据表)
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    /**
     * 获取：设备型号(基础数据表)
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 设置：制造商
     */
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    /**
     * 获取：制造商
     */
    public String getProducerName() {
        return producerName;
    }

    /**
     * 设置：产地
     */
    public void setProducerPlace(String producerPlace) {
        this.producerPlace = producerPlace;
    }

    /**
     * 获取：产地
     */
    public String getProducerPlace() {
        return producerPlace;
    }

    /**
     * 设置：单位
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 获取：单位
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * 设置：设备图片(多张图片地址用逗号分割)
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取：设备图片(多张图片地址用逗号分割)
     */
    public String getPicture() {
        return picture;
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
     * 设置：设备价格
     */
    public void setDevicePrice(Integer devicePrice) {
        this.devicePrice = devicePrice;
    }

    /**
     * 获取：设备价格
     */
    public Integer getDevicePrice() {
        return devicePrice;
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
     * 设置：创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：修改人
     */
    public void setEditUserId(Long editUserId) {
        this.editUserId = editUserId;
    }

    /**
     * 获取：修改人
     */
    public Long getEditUserId() {
        return editUserId;
    }

    /**
     * 设置：修改时间
     */
    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    /**
     * 获取：修改时间
     */
    public Date getEditTime() {
        return editTime;
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
     * 设置：序列号
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 获取：序列号
     */
    public String getSerialNumber() {
        return serialNumber;
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
     * 设置：采购人
     */
    public void setPurchaser(Long purchaser) {
        this.purchaser = purchaser;
    }

    /**
     * 获取：采购人
     */
    public Long getPurchaser() {
        return purchaser;
    }

    /**
     * 设置：采购部门
     */
    public void setPurchaseOrg(String purchaseOrg) {
        this.purchaseOrg = purchaseOrg;
    }

    /**
     * 获取：采购部门
     */
    public String getPurchaseOrg() {
        return purchaseOrg;
    }

    /**
     * 设置：销售单位
     */
    public void setSellUnit(String sellUnit) {
        this.sellUnit = sellUnit;
    }

    /**
     * 获取：销售单位
     */
    public String getSellUnit() {
        return sellUnit;
    }

    /**
     * 设置：购买时间
     */
    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    /**
     * 获取：购买时间
     */
    public Date getBuyTime() {
        return buyTime;
    }

    /**
     * 设置：设备信息备注
     */
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /**
     * 获取：设备信息备注
     */
    public String getDeviceInfo() {
        return deviceInfo;
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
     * 设置：版本号
     */
    public void setDeviceVersion(Integer deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    /**
     * 获取：版本号
     */
    public Integer getDeviceVersion() {
        return deviceVersion;
    }

    /**
     * 设置：创建人
     */
    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    /**
     * 获取：创建人
     */
    public Long getOwnerUserId() {
        return ownerUserId;
    }

    /**
     * 设置：创建人公司
     */
    public void setOwnerCompanyId(Long ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    /**
     * 获取：创建人公司
     */
    public Long getOwnerCompanyId() {
        return ownerCompanyId;
    }

    /**
     * 设置：创建人归属总公司
     */
    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    /**
     * 获取：创建人归属总公司
     */
    public Long getOwnerTopCompanyId() {
        return ownerTopCompanyId;
    }

    /**
     * 设置：创建人归属部门编码
     */
    public void setOwnerOrgCode(String ownerOrgCode) {
        this.ownerOrgCode = ownerOrgCode;
    }

    /**
     * 获取：创建人归属部门编码
     */
    public String getOwnerOrgCode() {
        return ownerOrgCode;
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
        if (other instanceof CustDeviceEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((CustDeviceEntity) other).id);
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


    public UserEntity chargeUserEntity;
    public OrgEntity chargeOrgEntity;

    public UserEntity getChargeUserEntity() {
        return this.chargeUserEntity;
    }

    public OrgEntity getChargeOrgEntity() {
        return this.chargeOrgEntity;
    }

    public void setChargeUserEntity(UserEntity chargeUserEntity) {
        this.chargeUserEntity = chargeUserEntity;
    }

    public void setChargeOrgEntity(OrgEntity chargeOrgEntity) {
        this.chargeOrgEntity = chargeOrgEntity;
    }
}
