package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 发票信息表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2017-12-27 11:12:01
 */
@TableName(value = "bus_invoice")
public class InvoiceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    //收货地址id
    //@TableField(value = "receice_id")
    private Long receiveId;
    //发票类型，0普通发票，1增值税发票
    //@TableField(value = "type")
    private Integer type;
    //发票抬头
    //@TableField(value = "title")
    private String title;
    //税号
    //@TableField(value = "duty_no")
    private String dutyNo;
    //公司地址
    //@TableField(value = "address")
    private String address;
    //电话
    //@TableField(value = "tel_phone")
    private String telPhone;
    //开户行
    //@TableField(value = "bank")
    private String bank;
    //账号
    //@TableField(value = "account")
    private String account;
    //0待支付,1待处理,2已处理
    //@TableField(value = "status")
    private Integer status;
    //创建人
    //@TableField(value = "create_user_id")
    private Long createUserId;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    //订单id
    //@TableField(value = "order_id")
    private Long orderId;

    //订单类型
    //@TableField(value = "order_id")
    private Integer orderType;

    /**
     * 设置：主键自增
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：主键自增
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：发票类型，0普通发票，1增值税发票
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：发票类型，0普通发票，1增值税发票
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置：发票抬头
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取：发票抬头
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置：税号
     */
    public void setDutyNo(String dutyNo) {
        this.dutyNo = dutyNo;
    }

    /**
     * 获取：税号
     */
    public String getDutyNo() {
        return dutyNo;
    }

    /**
     * 设置：公司地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：公司地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：电话
     */
    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    /**
     * 获取：电话
     */
    public String getTelPhone() {
        return telPhone;
    }

    /**
     * 设置：开户行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取：开户行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置：账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取：账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置：0待处理，1已处理
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：0待处理，1已处理
     */
    public Integer getStatus() {
        return status;
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof InvoiceEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((InvoiceEntity) other).id);
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

    /*
     *===================================================================================================================================================
     *-----------------------------------------------------------------华丽的分割线------------------------------------------------------------------------
     *===================================================================================================================================================
     */


    /**
     * 地址
     */
    @TableField(exist = false)
    private ReceiveAddressEntity receiveAddressEntity;
    /**
     * 创建人
     */
    @TableField(exist = false)
    private UserEntity createUser;

    public Long getReceiveId() {
        return this.receiveId;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Integer getOrderType() {
        return this.orderType;
    }

    public ReceiveAddressEntity getReceiveAddressEntity() {
        return this.receiveAddressEntity;
    }

    public UserEntity getCreateUser() {
        return this.createUser;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void setReceiveAddressEntity(ReceiveAddressEntity receiveAddressEntity) {
        this.receiveAddressEntity = receiveAddressEntity;
    }

    public void setCreateUser(UserEntity createUser) {
        this.createUser = createUser;
    }
}
