package com.eanfang.model;

import com.eanfang.model.sys.OrgUnitEntity;

import java.util.List;

/**
 * Created by O u r on 2018/11/13.
 */

public class OrgUnitListBean {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<OrgUnitEntity> list;

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

    public List<OrgUnitEntity> getList() {
        return list;
    }

    public void setList(List<OrgUnitEntity> list) {
        this.list = list;
    }
}
