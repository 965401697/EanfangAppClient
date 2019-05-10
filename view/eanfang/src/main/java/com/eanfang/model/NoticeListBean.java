package com.eanfang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/2/9  16:55
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class NoticeListBean implements Serializable {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<NoticeEntity> list;

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

    public List<NoticeEntity> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<NoticeEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NoticeListBean{" +
                "currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }
}
