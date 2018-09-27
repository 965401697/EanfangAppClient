package com.eanfang.model;

import com.yaf.base.entity.ProjectEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/9/26.
 */

public class ProjectListBean implements Serializable {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ProjectEntity> list;

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

    public List<ProjectEntity> getList() {
        return list;
    }

    public void setList(List<ProjectEntity> list) {
        this.list = list;
    }
}
