package com.eanfang.biz.model;

import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/26  15:01
 * @email houzhongzhou@yeah.net
 * @desc
 */

public final class GrantChange {
    private List<Integer> addIds;

    private List<Integer> delIds;

    public List<Integer> getAddIds() {
        return this.addIds;
    }

    public List<Integer> getDelIds() {
        return this.delIds;
    }

    public void setAddIds(List<Integer> addIds) {
        this.addIds = addIds;
    }

    public void setDelIds(List<Integer> delIds) {
        this.delIds = delIds;
    }
}