package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/25.
 */

public class EvaluateWorkerBean implements Serializable {

    /**
     * ordernum : 32020170325224954787062
     * workefficient : 5
     * serviceattitude : 4
     * techlevel : 3
     * resptime : 2
     * prostandard : 1
     */

    private String ordernum;
    private int workefficient;
    private int serviceattitude;
    private int techlevel;
    private int resptime;
    private int prostandard;

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public int getWorkefficient() {
        return workefficient;
    }

    public void setWorkefficient(int workefficient) {
        this.workefficient = workefficient;
    }

    public int getServiceattitude() {
        return serviceattitude;
    }

    public void setServiceattitude(int serviceattitude) {
        this.serviceattitude = serviceattitude;
    }

    public int getTechlevel() {
        return techlevel;
    }

    public void setTechlevel(int techlevel) {
        this.techlevel = techlevel;
    }

    public int getResptime() {
        return resptime;
    }

    public void setResptime(int resptime) {
        this.resptime = resptime;
    }

    public int getProstandard() {
        return prostandard;
    }

    public void setProstandard(int prostandard) {
        this.prostandard = prostandard;
    }
}
