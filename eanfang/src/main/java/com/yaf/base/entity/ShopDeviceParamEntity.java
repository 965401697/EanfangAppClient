package com.yaf.base.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;


/**
 * 安防公司设备参数表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-20 14:46:08
 */
@TableName(value = "shop_device_param")
public class ShopDeviceParamEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    //安防公司设备表ID
    //@TableField(value = "shop_device_id")
    private Long shopDeviceId;
    //参数值
    //@TableField(value = "param_value")
    private String paramValue;
    //参数名
    //@TableField(value = "param_name")
    private String paramName;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;
    //修改人
    //@TableField(value = "edit_user_id")
    private Long editUserId;
    //修改时间
    //@TableField(value = "edit_time")
    private Date editTime;
    //状态（0：不可用，1：可用）
    //@TableField(value = "status")
    private Integer status;

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
     * 获取：安防公司设备表ID
     */
    public Long getShopDeviceId() {
        return shopDeviceId;
    }

    /**
     * 设置：安防公司设备表ID
     */
    public void setShopDeviceId(Long shopDeviceId) {
        this.shopDeviceId = shopDeviceId;
    }

    /**
     * 获取：参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置：参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 获取：参数名
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置：参数名
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShopDeviceParamEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((ShopDeviceParamEntity) other).id);
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
