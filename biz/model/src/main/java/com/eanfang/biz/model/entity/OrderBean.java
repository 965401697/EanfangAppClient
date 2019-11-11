package com.eanfang.biz.model.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author : lin
 * @date : 2019/10/30 14:20
 */
@Getter
@Setter
@Accessors(chain = true)
public class OrderBean {
    /**
     * 缓存key
     */
    private String cacheKey;

    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单类型  常量 OrderType
     */
    private int type;
    /**
     * 订单编号
     */
    private String num;
    /**
     * 系统类别编码 默认取第一个
     */
    private String sysCode;
    /**
     * 客户（订单拥有者）公司id
     */
    private Long ownerCompanyId;

    /**
     * 技师（订单处理者）公司id
     */
    private Long assigneeCompanyId;
    /**
     * 订单状态
     */
    private int status;
    /**
     * 地区code
     */
    private String placeCode;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 报修联系人单位
     */
    private String contactCompany;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     * 客户（订单拥有者）id
     */
    private Long ownerUserId;
    /**
     * 技师（订单处理者）id
     */
    private Long assigneeUserId;

}
