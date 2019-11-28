package com.eanfang.biz.model.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2019/11/28
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeToDoOrderBean implements Serializable {

    private String msg;
    private OrderBean order;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

}
