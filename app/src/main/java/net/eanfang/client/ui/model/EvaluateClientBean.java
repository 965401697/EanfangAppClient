package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by
 */

public class EvaluateClientBean implements Serializable {

    /**
     * ordernum : 32020170325224954787062
     * workefficient : 5
     * serviceattitude : 4
     * techlevel : 3
     * resptime : 2
     * prostandard : 1
     */

    private String ordernum;
    private int zysbsxcd;
    private int gzxzzc;
    private int jsjs;
    private int tdrqyh;
    private int xchj;

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }


    public int getZysbsxcd() {
        return zysbsxcd;
    }

    public void setZysbsxcd(int zysbsxcd) {
        this.zysbsxcd = zysbsxcd;
    }

    public int getGzxzzc() {
        return gzxzzc;
    }

    public void setGzxzzc(int gzxzzc) {
        this.gzxzzc = gzxzzc;
    }

    public int getJsjs() {
        return jsjs;
    }

    public void setJsjs(int jsjs) {
        this.jsjs = jsjs;
    }

    public int getTdrqyh() {
        return tdrqyh;
    }

    public void setTdrqyh(int tdrqyh) {
        this.tdrqyh = tdrqyh;
    }

    public int getXchj() {
        return xchj;
    }

    public void setXchj(int xchj) {
        this.xchj = xchj;
    }
}
