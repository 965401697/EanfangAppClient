package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.AptitudeCertificateEntity;

import java.util.List;

/**
 * @author guanluocang
 * @data 2018/10/27
 * @description
 */

public class AptitudeCertificateBean {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<AptitudeCertificateEntity> list;

    @Override
    public String toString() {
        return "AptitudeCertificateBean{" +
                "currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }

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

    public List<AptitudeCertificateEntity> getList() {
        return list;
    }

    public void setList(List<AptitudeCertificateEntity> list) {
        this.list = list;
    }
}
