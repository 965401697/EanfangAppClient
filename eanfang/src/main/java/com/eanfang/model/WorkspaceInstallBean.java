package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yaosheng on 2017/5/17.
 */

public class WorkspaceInstallBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"budget":4,"businessOneCode":"1.1","clientCompanyName":"易安防北京运营公司","connector":"锅子","connectorPhone":"18500320187","createTime":"2017-12-21 11:52","createUserId":1,"description":"啦啦啦","detailPlace":"北京金襄陵为民诊所","id":"943690495967100929","latitude":"39.923586","longitude":"116.567866","orderNo":"EO1712211152146","ownerCompanyId":1100,"ownerOrgCode":"c.c1.2","ownerTopCompanyId":1100,"ownerUserId":1,"predictTime":7,"revertTimeLimit":0,"status":0,"zone":"3.11.1.5"}]
     * pageSize : 5
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
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * budget : 4
         * businessOneCode : 1.1
         * clientCompanyName : 易安防北京运营公司
         * connector : 锅子
         * connectorPhone : 18500320187
         * createTime : 2017-12-21 11:52
         * createUserId : 1
         * description : 啦啦啦
         * detailPlace : 北京金襄陵为民诊所
         * id : 943690495967100929
         * latitude : 39.923586
         * longitude : 116.567866
         * orderNo : EO1712211152146
         * ownerCompanyId : 1100
         * ownerOrgCode : c.c1.2
         * ownerTopCompanyId : 1100
         * ownerUserId : 1
         * predictTime : 7
         * revertTimeLimit : 0
         * status : 0
         * zone : 3.11.1.5
         */

        private int budget;
        private String businessOneCode;
        private String clientCompanyName;
        private String connector;
        private String connectorPhone;
        private String createTime;
        private Long createUserId;
        private String description;
        private String detailPlace;
        private Long id;
        private String latitude;
        private String longitude;
        private String orderNo;
        private Long ownerCompanyId;
        private String ownerOrgCode;
        private int ownerTopCompanyId;
        private Long ownerUserId;
        private int predictTime;
        private int revertTimeLimit;
        private int status;
        private String zone;

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getBusinessOneCode() {
            return businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public String getClientCompanyName() {
            return clientCompanyName;
        }

        public void setClientCompanyName(String clientCompanyName) {
            this.clientCompanyName = clientCompanyName;
        }

        public String getConnector() {
            return connector;
        }

        public void setConnector(String connector) {
            this.connector = connector;
        }

        public String getConnectorPhone() {
            return connectorPhone;
        }

        public void setConnectorPhone(String connectorPhone) {
            this.connectorPhone = connectorPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDetailPlace() {
            return detailPlace;
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
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public Long getOwnerCompanyId() {
            return ownerCompanyId;
        }

        public void setOwnerCompanyId(Long ownerCompanyId) {
            this.ownerCompanyId = ownerCompanyId;
        }

        public String getOwnerOrgCode() {
            return ownerOrgCode;
        }

        public void setOwnerOrgCode(String ownerOrgCode) {
            this.ownerOrgCode = ownerOrgCode;
        }

        public int getOwnerTopCompanyId() {
            return ownerTopCompanyId;
        }

        public void setOwnerTopCompanyId(int ownerTopCompanyId) {
            this.ownerTopCompanyId = ownerTopCompanyId;
        }

        public Long getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(Long ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public int getPredictTime() {
            return predictTime;
        }

        public void setPredictTime(int predictTime) {
            this.predictTime = predictTime;
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

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }
    }
}

