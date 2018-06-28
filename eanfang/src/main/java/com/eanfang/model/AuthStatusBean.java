package com.eanfang.model;

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

    private int area;
    private int biz;
    private int service;
    private int base;

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getBiz() {
        return biz;
    }

    public void setBiz(int biz) {
        this.biz = biz;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }
}
