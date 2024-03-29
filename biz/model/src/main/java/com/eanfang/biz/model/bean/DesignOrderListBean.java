package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jornl on 2017/9/8.
 */

public class DesignOrderListBean {


    /**
     * currPage : 1
     * list : [{"budgetLimit":"0","businessOneCode":"1.1","contactPhone":"15940525612","contactUser":"李旭","createOrgCode":"c.c1.2","createTime":"2017-12-14 16:12","createTopCompanyId":1000,"createUserId":"937871079119032321","detailPlace":"北京朝阳区褡裢坡","id":1,"latitude":"758465.01","longitude":"542156.01","orderNum":"TUO45864","predictTime":"0","remarkInfo":"好好干","revertTimeLimit":0,"status":0,"userName":"旭神","zoneCode":"3.10.2"}]
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

    public static class ListBean implements Serializable {
        /**
         * budgetLimit : 0
         * businessOneCode : 1.1
         * contactPhone : 15940525612
         * contactUser : 李旭
         * createOrgCode : c.c1.2
         * createTime : 2017-12-14 16:12
         * createTopCompanyId : 1000
         * createUserId : 937871079119032321
         * detailPlace : 北京朝阳区褡裢坡
         * id : 1
         * latitude : 758465.01
         * longitude : 542156.01
         * orderNum : TUO45864
         * predictTime : 0
         * remarkInfo : 好好干
         * revertTimeLimit : 0
         * status : 0
         * userName : 旭神
         * zoneCode : 3.10.2
         */

        private String budgetLimit;
        private String businessOneCode;
        private String contactPhone;
        private String contactUser;
        private String createOrgCode;
        private String createTime;
        private Long createTopCompanyId;
        private Long createUserId;
        private Long createCompanyId;
        private String detailPlace;
        private Long id;
        private String latitude;
        private String longitude;
        private String orderNum;
        private int predictTime;
        private String remarkInfo;
        private int revertTimeLimit;
        private int status;
        private String userName;
        private String zoneCode;
        // 是否已读 未读
        private int newOrder;

        public Long getCreateCompanyId() {
            return createCompanyId;
        }

        public void setCreateCompanyId(Long createCompanyId) {
            this.createCompanyId = createCompanyId;
        }

        public String getBudgetLimit() {
            return budgetLimit == null ? "" : budgetLimit;
        }

        public void setBudgetLimit(String budgetLimit) {
            this.budgetLimit = budgetLimit;
        }

        public String getBusinessOneCode() {
            return businessOneCode == null ? "" : businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public String getContactPhone() {
            return contactPhone == null ? "" : contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getContactUser() {
            return contactUser == null ? "" : contactUser;
        }

        public void setContactUser(String contactUser) {
            this.contactUser = contactUser;
        }

        public String getCreateOrgCode() {
            return createOrgCode == null ? "" : createOrgCode;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getOrderNum() {
            return orderNum == null ? "" : orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getPredictTime() {
            return predictTime;
        }

        public void setPredictTime(int predictTime) {
            this.predictTime = predictTime;
        }

        public String getRemarkInfo() {
            return remarkInfo == null ? "" : remarkInfo;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public int getRevertTimeLimit() {
            return revertTimeLimit;
        }

        public void setRevertTimeLimit(int revertTimeLimit) {
            this.revertTimeLimit = revertTimeLimit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserName() {
            return userName == null ? "" : userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getZoneCode() {
            return zoneCode == null ? "" : zoneCode;
        }

        public void setZoneCode(String zoneCode) {
            this.zoneCode = zoneCode;
        }

        public int getNewOrder() {
            return this.newOrder;
        }

        public void setNewOrder(int newOrder) {
            this.newOrder = newOrder;
        }
    }
}




