package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * 描述：技师认证 状态
 *
 * @author Guanluocang
 * @date on 2018/6/25$  14:41$
 */
public class AuthStatusBean implements Serializable {

    /**
     * area : 4
     * biz : 7
     * service : 6
     * base : 1
     */



    private int apt;
    private int honor;
    //0实名1未实名
    private int realVerify;

    public int getRealVerify() {
        return realVerify;
    }

    public void setRealVerify(int realVerify) {
        this.realVerify = realVerify;
    }

    private int exp;
    private int base;
    private int verify;

    public int getApt() {
        return apt;
    }

    public void setApt(int apt) {
        this.apt = apt;
    }

    public int getHonor() {
        return honor;
    }

    public void setHonor(int honor) {
        this.honor = honor;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getVerify() {
        return verify;
    }

    public void setVerify(int verify) {
        this.verify = verify;
    }
}
