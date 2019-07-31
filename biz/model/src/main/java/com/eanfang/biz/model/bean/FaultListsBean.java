package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.RepairFailureEntity;

import java.util.List;

/**
 * Created by O u r on 2018/6/20.
 */

public class FaultListsBean {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    @Override
    public String toString() {
        return "FaultListsBean{" +
                "currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }

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
        return list;
    }

    public void setList(List<RepairFailureEntity> list) {
        this.list = list;
    }
}
