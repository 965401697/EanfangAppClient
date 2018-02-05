package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/25.
 */

public class EvaluateWorkerBean implements Serializable {

    /**
     * item1 : 5
     * item2 : 5
     * item3 : 5
     * item4 : 5
     * item5 : 5
     * orderId : 940407881491148802
     * orderNum : MO1712121028320
     * ownerId : 936487014465806338
     */

    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private Long orderId;
    private String orderNum;
    private Long ownerId;

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
