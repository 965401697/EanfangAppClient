package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/15.
 */

public class OrderProgressBean implements Serializable {


    /**
     * createTime : 2017-12-12 10:28
     * createUserId : 1
     * id : 20
     * nodeCode : 1
     * nodeInfo :
     * nodeName :
     * orderId : 940407881491148802
     * orderType : 0
     */

    private String createTime;
    private Long createUserId;
    private Long id;
    private int nodeCode;
    private String nodeInfo;
    private String nodeName;
    private Long orderId;
    private int orderType;

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(int nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getNodeInfo() {
        return nodeInfo == null ? "" : nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public String getNodeName() {
        return nodeName == null ? "" : nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}

