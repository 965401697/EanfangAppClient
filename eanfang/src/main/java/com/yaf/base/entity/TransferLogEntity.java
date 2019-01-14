package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;


/**
 * 业务订单，转单记录表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-19 10:18:11
 */
@Getter
@Setter
@TableName(value = "bus_transfer_log")
public class TransferLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
//@TableField(value = "id")
//数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //订单类型：0报修单
//@TableField(value = "order_type")
    private Integer orderType;
    //新订单id
//@TableField(value = "order_id")
    private Long orderId;
    //新订单num
//@TableField(value = "order_num")
    private String orderNum;
    //原始订单id
//@TableField(value = "original_order_id")
    private Long originalOrderId;
    //原始订单num
//@TableField(value = "original_order_num")
    private String originalOrderNum;
    //原始转单人id
//@TableField(value = "original_user_id")
    private Long originalUserId;
    //接收人id
//@TableField(value = "receive_user_id")
    private Long receiveUserId;
    //挂单原因
//@TableField(value = "case")
    private Integer cause;
    //创建时间
//@TableField(value = "create_time")
    private Date createTime;

    //备注信息
//@TableField(value = "remark")
    private String remark;
    /**
     * 接收人
     */
    @TableField(exist = false)
    private UserEntity receiveUserEntity;
    /**
     * 转单人/原始处理人
     */
    @TableField(exist = false)
    private UserEntity originalUserEntity;

    /**
     * 获取：主键自增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：订单类型：0报修单
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 设置：订单类型：0报修单
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取：订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置：订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：原始转单人id
     */
    public Long getOriginalUserId() {
        return originalUserId;
    }

    /**
     * 设置：原始转单人id
     */
    public void setOriginalUserId(Long originalUserId) {
        this.originalUserId = originalUserId;
    }

    /**
     * 获取：接收人id
     */
    public Long getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * 设置：接收人id
     */
    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    /**
     * 获取：挂单原因
     */
    public Integer getCause() {
        return cause;
    }

    /**
     * 设置：挂单原因
     */
    public void setCause(Integer cause) {
        this.cause = cause;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */

    @Override
    public boolean equals(Object other) {
        if (other instanceof TransferLogEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((TransferLogEntity) other).id);
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
