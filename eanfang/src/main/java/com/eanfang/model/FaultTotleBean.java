package com.eanfang.model;

/**
 * Created by O u r on 2018/6/20.
 */

public class FaultTotleBean {

    /**
     * bugOneCode : 1.1
     * status0 : 1
     * status1 : 7
     * status2 : 17
     */

    private String bugOneCode;
    private int status0;
    private int status1;
    private int status2;

    public String getBugOneCode() {
        return bugOneCode;
    }

    public void setBugOneCode(String bugOneCode) {
        this.bugOneCode = bugOneCode;
    }

    public int getStatus0() {
        return status0;
    }

    public void setStatus0(int status0) {
        this.status0 = status0;
    }

    public int getStatus1() {
        return status1;
    }

    public void setStatus1(int status1) {
        this.status1 = status1;
    }

    public int getStatus2() {
        return status2;
    }

    public void setStatus2(int status2) {
        this.status2 = status2;
    }
}
