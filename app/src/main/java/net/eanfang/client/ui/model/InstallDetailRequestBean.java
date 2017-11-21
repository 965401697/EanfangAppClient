package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/20.
 */

public class InstallDetailRequestBean implements Serializable {
    private String ordernum;

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
}
