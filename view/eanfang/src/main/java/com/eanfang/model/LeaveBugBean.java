package com.eanfang.model;

import com.yaf.base.entity.RepairFailureEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/2/28  14:23
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class LeaveBugBean implements Serializable {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<RepairFailureEntity> list;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<RepairFailureEntity> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<RepairFailureEntity> list) {
        this.list = list;
    }
}
