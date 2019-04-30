package com.yaf.base.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;


/**
 * 支付记录表
 *
 * @author jornlin
 * @email jornlin@foxmail.com
 * @date 2018-01-13 16:03:42
 */

public class PayLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    //@TableField(value = "id")
    //数据库id 默认自增，如果全局唯一，请使用 IdType.ID_WORKER
    private Long id;
    //订单的 uid
    //@TableField(value = "order_id")
    private Long orderId;


    //订单的 编号
    //@TableField(value = "order_num")
    private String orderNum;

    //订单类型：0报修单
    //@TableField(value = "order_type")
    private Integer orderType;
    //支付类型：0支付宝，1微信
    //@TableField(value = "pay_type")
    private Integer payType;
    //支付时间
    //@TableField(value = "pay_time")
    private Date payTime;
    //支付产生的相关数据，通过json存储，使用时请自行解析
    //@TableField(value = "pay_json")
    private String payJson;
    //支付状态：0失败，1成功
    //@TableField(value = "pay_status")
    private Integer payStatus;
    //原始价格
    //@TableField(value = "origin_price")
    private Integer originPrice;
    //优惠价格
    //@TableField(value = "reduced_price")
    private Integer reducedPrice;
    //实际支付价格
    //@TableField(value = "pay_price")
    private Integer payPrice;
    //支付人（处理人）
    //@TableField(value = "assignee_user_id")
    private Long assigneeUserId;
    //支付人 姓名
    //@TableField(value = "assignee_user_name")
    private String assigneeUserName;
    //支付人  部门编码
    //@TableField(value = "assignee_org_code")
    private String assigneeOrgCode;
    //支付人 部门名称
    //@TableField(value = "assignee_org_name")
    private String assigneeOrgName;
    //支付人总公司id
    //@TableField(value = "assignee_top_company_id")
    private Long assigneeTopCompanyId;
    //支付人总公司名称
    //@TableField(value = "assignee_top_company_name")
    private String assigneeTopCompanyName;
    //备注信息
    //@TableField(value = "remark_info")
    private String remarkInfo;
    //创建时间
    //@TableField(value = "create_time")
    private Date createTime;

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：订单的 uid
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置：订单的 uid
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
     * 获取：支付类型：0支付宝，1微信
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置：支付类型：0支付宝，1微信
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取：支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置：支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取：支付产生的相关数据，通过json存储，使用时请自行解析
     */
    public String getPayJson() {
        return payJson;
    }

    /**
     * 设置：支付产生的相关数据，通过json存储，使用时请自行解析
     */
    public void setPayJson(String payJson) {
        this.payJson = payJson;
    }

    /**
     * 获取：支付状态：0失败，1成功
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 设置：支付状态：0失败，1成功
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取：原始价格
     */
    public Integer getOriginPrice() {
        return originPrice;
    }

    /**
     * 设置：原始价格
     */
    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    /**
     * 获取：优惠价格
     */
    public Integer getReducedPrice() {
        return reducedPrice;
    }

    /**
     * 设置：优惠价格
     */
    public void setReducedPrice(Integer reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    /**
     * 获取：实际支付价格
     */
    public Integer getPayPrice() {
        return payPrice;
    }

    /**
     * 设置：实际支付价格
     */
    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * 获取：支付人（处理人）
     */
    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    /**
     * 设置：支付人（处理人）
     */
    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    /**
     * 获取：支付人 姓名
     */
    public String getAssigneeUserName() {
        return assigneeUserName;
    }

    /**
     * 设置：支付人 姓名
     */
    public void setAssigneeUserName(String assigneeUserName) {
        this.assigneeUserName = assigneeUserName;
    }

    /**
     * 获取：支付人  部门编码
     */
    public String getAssigneeOrgCode() {
        return assigneeOrgCode;
    }

    /**
     * 设置：支付人  部门编码
     */
    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    /**
     * 获取：支付人 部门名称
     */
    public String getAssigneeOrgName() {
        return assigneeOrgName;
    }

    /**
     * 设置：支付人 部门名称
     */
    public void setAssigneeOrgName(String assigneeOrgName) {
        this.assigneeOrgName = assigneeOrgName;
    }

    /**
     * 获取：支付人总公司id
     */
    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    /**
     * 设置：支付人总公司id
     */
    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    /**
     * 获取：支付人总公司名称
     */
    public String getAssigneeTopCompanyName() {
        return assigneeTopCompanyName;
    }

    /**
     * 设置：支付人总公司名称
     */
    public void setAssigneeTopCompanyName(String assigneeTopCompanyName) {
        this.assigneeTopCompanyName = assigneeTopCompanyName;
    }

    /**
     * 获取：备注信息
     */
    public String getRemarkInfo() {
        return remarkInfo;
    }

    /**
     * 设置：备注信息
     */
    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof PayLogEntity) {
            if (this.id == null || other == null) {
                return false;
            }

            return this.id.equals(((PayLogEntity) other).id);
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

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
