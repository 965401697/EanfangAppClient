package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * 我要报修里的item
 * Created by Administrator on 2017/3/21.
 */
public class ToRepairItem implements Serializable {
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
