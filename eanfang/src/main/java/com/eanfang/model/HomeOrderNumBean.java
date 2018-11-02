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
public class HomeOrderNumBean implements Serializable {


    private int repair;
    private int quote;
    private int task;
    private int install;
    private int maintain;
    private int design;
    private int report;
    private int inspect;

}
