package com.eanfang.biz.model.bean;

import com.eanfang.biz.model.entity.HonorCertificateEntity;

import java.util.List;

/**
 * Created by O u r on 2018/10/16.
 */

public class HonorCerticificateListBean {
    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<HonorCertificateEntity> list;

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

    public List<HonorCertificateEntity> getList() {
        return list;
    }

    public void setList(List<HonorCertificateEntity> list) {
        this.list = list;
    }
}
