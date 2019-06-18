package com.eanfang.biz.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/1/11
 * @description 招标信息
 */

public class TenderBean implements Serializable {

    private List<IfbOrderEntity> list;

    public TenderBean(List<IfbOrderEntity> list) {
        this.list = list;
    }

    public TenderBean() {
    }


    public List<IfbOrderEntity> getList() {
        return list;
    }

    public void setList(List<IfbOrderEntity> list) {
        this.list = list;
    }

}
