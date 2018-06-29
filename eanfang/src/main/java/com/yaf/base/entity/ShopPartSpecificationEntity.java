package com.yaf.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 安防公司配件规格表
 *
 * @author lixu
 * @email 15940525612@163.com
 * @date 2017-12-20 14:46:08
 */
@TableName(value = "shop_part_specification")
public class ShopPartSpecificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //安防公司配件表ID
    //@TableField(value = "shop_accessories_part_id")
    private Long shopAccessoriesPartId;
    //配件规格，大小
    //@TableField(value = "part_speciication")
    private String partSpeciication;
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
    //状态（0，不可用，1：可用）
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
     * 获取：安防公司配件表ID
     */
    public Long getShopAccessoriesPartId() {
        return shopAccessoriesPartId;
    }

    /**
     * 设置：安防公司配件表ID
     */
    public void setShopAccessoriesPartId(Long shopAccessoriesPartId) {
        this.shopAccessoriesPartId = shopAccessoriesPartId;
    }

    /**
     * 获取：配件规格，大小
     */
    public String getPartSpeciication() {
        return partSpeciication;
    }

    /**
     * 设置：配件规格，大小
     */
    public void setPartSpeciication(String partSpeciication) {
        this.partSpeciication = partSpeciication;
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
     * 获取：状态（0，不可用，1：可用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：状态（0，不可用，1：可用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ShopPartSpecificationEntity) {
            if (this.id == null || other == null)
                return false;

            return this.id.equals(((ShopPartSpecificationEntity) other).id);
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
