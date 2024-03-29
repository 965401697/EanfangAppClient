package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.NoticePushEntity;

import java.util.List;

/**
 * Created by O u r on 2018/10/15.
 */

public class OfficialListBean {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<NoticePushEntity> list;

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

    public List<NoticePushEntity> getList() {
        return list;
    }

    public void setList(List<NoticePushEntity> list) {
        this.list = list;
    }
}
