package com.yaf.base.entity;

import java.util.List;

/**
 * Created by Our on 2019/1/29.
 */

public class AskQuestionsListBean {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<AskQuestionsEntity> list;

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

    public List<AskQuestionsEntity> getList() {
        return list;
    }

    public void setList(List<AskQuestionsEntity> list) {
        this.list = list;
    }
}
