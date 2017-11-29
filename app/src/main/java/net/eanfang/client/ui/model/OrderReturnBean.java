package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/19.
 */

public class OrderReturnBean implements Serializable {

    /**
     * ordernum : MO32020170414094832620256
     * status : 0
     */

    private String ordernum;
    private String status;
    private String doorfee;

    public String getDoorfee() {
        return doorfee;
    }

    public void setDoorfee(String doorfee) {
        this.doorfee = doorfee;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
