package com.yaf.base.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 安防公司设备库
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-20 14:46:08
 */
public class ShopDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //系统设备表ID
    private Long headDeviceId;
    //设备编码
    private String deviceNo;
    //设备名称
    private String deviceName;
    //三级业务类型编码(基础数据表)
    private String businessThreeCode;
    //设备型号(基础数据表)
    private String modelCode;
    //制造商
    private String producerName;
    //产地
    private String producerPlace;
    //成本单价
    private Integer costPrice;
    //零售价
    private Integer advicePrice;
    //计量单位
    private Integer unit;
    //设备图片(多张图片地址用逗号分割)
    private String picture;
    //所属公司ID
    private Long orgId;
    //创建人
    private Long createUserId;
    //创建时间
    private Date createTime;
    //修改人
    private Long editUserId;
    //修改时间
    private Date editTime;
    //状态（0：不可用，1：可用）
    private Integer status;

    //----------------------------------------业务字段，不存在于数据库----------------------------------------------------------------------------------
    private List<ShopDeviceParamEntity> params;

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
     * 获取：系统设备表ID
     */
    public Long getHeadDeviceId() {
        return headDeviceId;
    }

    /**
     * 设置：系统设备表ID
     */
    public void setHeadDeviceId(Long headDeviceId) {
        this.headDeviceId = headDeviceId;
    }

    /**
     * 获取：设备编码
     */
    public String getDeviceNo() {
        return deviceNo;
    }

    /**
     * 设置：设备编码
     */
    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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
     * 获取：三级业务类型编码(基础数据表)
     */
    public String getBusinessThreeCode() {
        return businessThreeCode;
    }

    /**
     * 设置：三级业务类型编码(基础数据表)
     */
    public void setBusinessThreeCode(String businessThreeCode) {
        this.businessThreeCode = businessThreeCode;
    }

    /**
     * 获取：设备型号(基础数据表)
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * 设置：设备型号(基础数据表)
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    /**
     * 获取：制造商
     */
    public String getProducerName() {
        return producerName;
    }

    /**
     * 设置：制造商
     */
    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    /**
     * 获取：产地
     */
    public String getProducerPlace() {
        return producerPlace;
    }

    /**
     * 设置：产地
     */
    public void setProducerPlace(String producerPlace) {
        this.producerPlace = producerPlace;
    }

    /**
     * 获取：成本单价
     */
    public Integer getCostPrice() {
        return costPrice;
    }

    /**
     * 设置：成本单价
     */
    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取：零售价
     */
    public Integer getAdvicePrice() {
        return advicePrice;
    }

    /**
     * 设置：零售价
     */
    public void setAdvicePrice(Integer advicePrice) {
        this.advicePrice = advicePrice;
    }

    /**
     * 获取：计量单位
     */
    public Integer getUnit() {
        return unit;
    }

    /**
     * 设置：计量单位
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 获取：设备图片(多张图片地址用逗号分割)
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置：设备图片(多张图片地址用逗号分割)
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取：所属公司ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置：所属公司ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置：创建人
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建时间
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
     * 获取：状态（0：不可用，1：可用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态（0：不可用，1：可用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：设备参数
     */
    public List<ShopDeviceParamEntity> getParams() {
        return params;
    }

    /**
     * 设置：设备参数
     */
    public void setParams(List<ShopDeviceParamEntity> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShopDeviceEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ShopDeviceEntity) other).id);
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
}
