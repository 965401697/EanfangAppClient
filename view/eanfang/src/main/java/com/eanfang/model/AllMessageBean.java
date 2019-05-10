package com.eanfang.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guanluocang
 * @data 2018/10/30
 * @description
 */

@Getter
@Setter
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
    private int noReadCount;
    private int commentNoRead;


}
