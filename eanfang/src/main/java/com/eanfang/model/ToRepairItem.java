package com.eanfang.model;

import java.io.Serializable;

import lombok.Setter;

/**
 * 我要报修里的item
 * Created by Administrator on 2017/3/21.
 */
@Setter
public class ToRepairItem implements Serializable {
    private String name;

    public String getName() {
        return name == null ? "" : name;
    }
}
