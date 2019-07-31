package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/6/3.
 */

public class ApplyTaskListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"applyCompanyName":"安防监控公司","applyConstactsPhone":"13940523658","applyContacts":"项羽","confirmTime":"2017-12-18 15:49:17.0","createCompanyId":1100,"createOrgCode":"c.c1.2","createTopCompanyId":1100,"createUserId":1,"description":"我们可以很好的完成","id":1,"pictures":"图片","predictTime":30,"projectQuote":100000,"shopTaskPublishId":"941286182296756226","status":0,"toDoorTime":"2017-12-18 15:42"},{"applyCompanyName":"张飞有限公司","applyConstactsPhone":"15940525666","applyContacts":"张飞","confirmTime":"2018-01-03 14:42:46.0","createCompanyId":1100,"createOrgCode":"c.c1.2","createTopCompanyId":1100,"createUserId":1,"description":"小意思，很好做","id":"942675403125391361","pictures":"1245","predictTime":50,"projectQuote":15000,"shopTaskPublishId":"941286182296756226","status":0,"toDoorTime":"2017-12-28 00:00"},{"applyCompanyName":"张飞有限公司","applyConstactsPhone":"15940525666","applyContacts":"张飞","createCompanyId":1100,"createOrgCode":"c.c1.2","createTopCompanyId":1100,"createUserId":1,"description":"小意思，很好做","id":"942676916061175809","pictures":"1245","predictTime":50,"projectQuote":15000,"shopTaskPublishId":"941286182296756226","status":0,"toDoorTime":"2017-12-28 00:00"}]
     * pageSize : 2147483647
     * totalCount : 3
     * totalPage : 1
     */

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * applyCompanyName : 安防监控公司
         * applyConstactsPhone : 13940523658
         * applyContacts : 项羽
         * confirmTime : 2017-12-18 15:49:17.0
         * createCompanyId : 1100
         * createOrgCode : c.c1.2
         * createTopCompanyId : 1100
         * createUserId : 1
         * description : 我们可以很好的完成
         * id : 1
         * pictures : 图片
         * predictTime : 30
         * projectQuote : 100000
         * shopTaskPublishId : 941286182296756226
         * status : 0
         * toDoorTime : 2017-12-18 15:42
         */

        private String applyCompanyName;
        private String applyConstactsPhone;
        private String applyContacts;
        private String confirmTime;
        private Long createCompanyId;
        private String createOrgCode;
        private Long createTopCompanyId;
        private Long createUserId;
        private String description;
        private Long id;
        private String pictures;
        private int predictTime;
        private int projectQuote;
        private Long shopTaskPublishId;
        private int status;
        private String toDoorTime;

        public String getApplyCompanyName() {
            return applyCompanyName == null ? "" : applyCompanyName;
        }

        public void setApplyCompanyName(String applyCompanyName) {
            this.applyCompanyName = applyCompanyName;
        }

        public String getApplyConstactsPhone() {
            return applyConstactsPhone == null ? "" : applyConstactsPhone;
        }

        public void setApplyConstactsPhone(String applyConstactsPhone) {
            this.applyConstactsPhone = applyConstactsPhone;
        }

        public String getApplyContacts() {
            return applyContacts == null ? "" : applyContacts;
        }

        public void setApplyContacts(String applyContacts) {
            this.applyContacts = applyContacts;
        }

        public String getConfirmTime() {
            return confirmTime == null ? "" : confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public Long getCreateCompanyId() {
            return createCompanyId;
        }

        public void setCreateCompanyId(Long createCompanyId) {
            this.createCompanyId = createCompanyId;
        }

        public String getCreateOrgCode() {
            return createOrgCode == null ? "" : createOrgCode;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public Long getCreateTopCompanyId() {
            return createTopCompanyId;
        }

        public void setCreateTopCompanyId(Long createTopCompanyId) {
            this.createTopCompanyId = createTopCompanyId;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public String getDescription() {
            return description == null ? "" : description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPictures() {
            return pictures == null ? "" : pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public int getPredictTime() {
            return predictTime;
        }

        public void setPredictTime(int predictTime) {
            this.predictTime = predictTime;
        }

        public int getProjectQuote() {
            return projectQuote;
        }

        public void setProjectQuote(int projectQuote) {
            this.projectQuote = projectQuote;
        }

        public Long getShopTaskPublishId() {
            return shopTaskPublishId;
        }

        public void setShopTaskPublishId(Long shopTaskPublishId) {
            this.shopTaskPublishId = shopTaskPublishId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToDoorTime() {
            return toDoorTime == null ? "" : toDoorTime;
        }

        public void setToDoorTime(String toDoorTime) {
            this.toDoorTime = toDoorTime;
        }
    }
}

