package com.eanfang.model;

import java.io.Serializable;

/**
 * @author guanluocang
 * @data 2018/10/30
 * @description
 */

public class AllMessageBean implements Serializable {

    /**
     * repair : 0
     * biz : 11
     * quote : 0
     * task : 0
     * install : 0
     * maintain : 0
     * design : 0
     * report : 0
     * inspect : 0
     * sys : 1
     */

    private int repair;
    private int biz;
    private int quote;
    private int task;
    private int install;
    private int maintain;
    private int design;
    private int report;
    private int inspect;
    private int sys;
    private int cmp;
    private int totalCount;

    public int getRepair() {
        return this.repair;
    }

    public int getBiz() {
        return this.biz;
    }

    public int getQuote() {
        return this.quote;
    }

    public int getTask() {
        return this.task;
    }

    public int getInstall() {
        return this.install;
    }

    public int getMaintain() {
        return this.maintain;
    }

    public int getDesign() {
        return this.design;
    }

    public int getReport() {
        return this.report;
    }

    public int getInspect() {
        return this.inspect;
    }

    public int getSys() {
        return this.sys;
    }

    public int getCmp() {
        return this.cmp;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setRepair(int repair) {
        this.repair = repair;
    }

    public void setBiz(int biz) {
        this.biz = biz;
    }

    public void setQuote(int quote) {
        this.quote = quote;
    }

    public void setTask(int task) {
        this.task = task;
    }

    public void setInstall(int install) {
        this.install = install;
    }

    public void setMaintain(int maintain) {
        this.maintain = maintain;
    }

    public void setDesign(int design) {
        this.design = design;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public void setInspect(int inspect) {
        this.inspect = inspect;
    }

    public void setSys(int sys) {
        this.sys = sys;
    }

    public void setCmp(int cmp) {
        this.cmp = cmp;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
