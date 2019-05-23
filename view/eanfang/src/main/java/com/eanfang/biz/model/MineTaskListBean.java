package com.eanfang.biz.model;

import com.eanfang.biz.model.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/17  22:59
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class MineTaskListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"budget":0,"businessOneCode":"1.2","contacts":"旭神","contactsPhone":"15940525612","createCompanyId":1100,"createOrgCode":"c.c1.2","createTopCompanyId":1000,"createUserId":2,"description":"需要快速安装","detailPlace":"北京褡裢坡","id":"941286182296756226","latitude":"8942532","longitude":"15642","pictures":"这里有图片，我们不一样，有啥不一样","predicttime":0,"projectCompanyName":"双井家乐福","publishCompanyName":"北京法案视","publishStatus":1,"status":0,"toDoorTime":"1990-11-03 00:00","type":"0","zoneCode":"1.1"}]
     * pageSize : 2147483647
     * totalCount : 1
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

    public static class ListBean implements Serializable{
        /**
         * budget : 0
         * businessOneCode : 1.2
         * contacts : 旭神
         * contactsPhone : 15940525612
         * createCompanyId : 1100
         * createOrgCode : c.c1.2
         * createTopCompanyId : 1000
         * createUserId : 2
         * description : 需要快速安装
         * detailPlace : 北京褡裢坡
         * id : 941286182296756226
         * latitude : 8942532
         * longitude : 15642
         * pictures : 这里有图片，我们不一样，有啥不一样
         * predicttime : 0
         * projectCompanyName : 双井家乐福
         * publishCompanyName : 北京法案视
         * publishStatus : 1
         * status : 0
         * toDoorTime : 1990-11-03 00:00
         * type : 0
         * zoneCode : 1.1
         */

        private int budget;
        private String businessOneCode;
        private String contacts;
        private String contactsPhone;
        private Long createCompanyId;
        private String createOrgCode;
        private Long createTopCompanyId;
        private Long createUserId;
        private String description;
        private String detailPlace;
        private Long id;
        private String latitude;
        private String longitude;
        private String pictures;
        private int predicttime;
        private String projectCompanyName;
        private String publishCompanyName;
        private int publishStatus;
        private int status;
        private String toDoorTime;
        private int type;
        private String zoneCode;
        private UserEntity assigneeUser;
        private Long shopTaskApplyId;

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getBusinessOneCode() {
            return businessOneCode == null ? "" : businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public String getContacts() {
            return contacts == null ? "" : contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getContactsPhone() {
            return contactsPhone == null ? "" : contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
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

        public String getDetailPlace() {
            return detailPlace == null ? "" : detailPlace;
        }

        public void setDetailPlace(String detailPlace) {
            this.detailPlace = detailPlace;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLatitude() {
            return latitude == null ? "" : latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude == null ? "" : longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPictures() {
            return pictures == null ? "" : pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public int getPredicttime() {
            return predicttime;
        }

        public void setPredicttime(int predicttime) {
            this.predicttime = predicttime;
        }

        public String getProjectCompanyName() {
            return projectCompanyName == null ? "" : projectCompanyName;
        }

        public void setProjectCompanyName(String projectCompanyName) {
            this.projectCompanyName = projectCompanyName;
        }

        public String getPublishCompanyName() {
            return publishCompanyName == null ? "" : publishCompanyName;
        }

        public void setPublishCompanyName(String publishCompanyName) {
            this.publishCompanyName = publishCompanyName;
        }

        public int getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(int publishStatus) {
            this.publishStatus = publishStatus;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getZoneCode() {
            return zoneCode == null ? "" : zoneCode;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public UserEntity getAssigneeUser() {
            return assigneeUser;
        }

        public void setAssigneeUser(UserEntity assigneeUser) {
            this.assigneeUser = assigneeUser;
        }

        public Long getShopTaskApplyId() {
            return shopTaskApplyId;
        }

        public void setShopTaskApplyId(Long shopTaskApplyId) {
            this.shopTaskApplyId = shopTaskApplyId;
        }
    }
}

