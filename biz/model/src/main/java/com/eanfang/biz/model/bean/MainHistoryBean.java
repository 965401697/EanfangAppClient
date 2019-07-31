package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/6/15.
 */

public class MainHistoryBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"clientCompanyName":"北京物美","clientUserName":"刘妹妹","clientUserPhone":"1397989549","createCompany":{"companyId":1100,"isVerify":1,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1},"createCompanyId":1100,"createOrg":{"companyId":2100,"isVerify":0,"level":3,"orgCode":"c.c1.2","orgId":2102,"orgName":"维修部","orgType":2,"parentOrgId":2100,"sortNum":0,"topCompanyId":2000,"updateTime":"2017-12-05 13:34","updateUser":1},"createOrgCode":"c.c1.2","createTime":"2017-12-19 10:55","createTopCompanyId":1100,"createUser":{"accId":2,"accountEntity":{"accId":2,"accType":5,"avatar":"1","mobile":"15940525612","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2},"createUserId":2,"cycle":0,"id":"942951548685230082","maintainDetail":{"count":5,"pictures":"d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png","shopMaintainId":"942951548685230082"},"orderNum":"MO201712190058","status":0}]
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
         * clientCompanyName : 北京物美
         * clientUserName : 刘妹妹
         * clientUserPhone : 1397989549
         * createCompany : {"companyId":1100,"isVerify":1,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1}
         * createCompanyId : 1100
         * createOrg : {"companyId":2100,"isVerify":0,"level":3,"orgCode":"c.c1.2","orgId":2102,"orgName":"维修部","orgType":2,"parentOrgId":2100,"sortNum":0,"topCompanyId":2000,"updateTime":"2017-12-05 13:34","updateUser":1}
         * createOrgCode : c.c1.2
         * createTime : 2017-12-19 10:55
         * createTopCompanyId : 1100
         * createUser : {"accId":2,"accountEntity":{"accId":2,"accType":5,"avatar":"1","mobile":"15940525612","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2}
         * createUserId : 2
         * cycle : 0
         * id : 942951548685230082
         * maintainDetail : {"count":5,"pictures":"d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png","shopMaintainId":"942951548685230082"}
         * orderNum : MO201712190058
         * status : 0
         */

        private String clientCompanyName;
        private String clientUserName;
        private String clientUserPhone;
        private Long createCompanyId;
        private String createOrgCode;
        private String createTime;
        private Long createTopCompanyId;
        private Long createUserId;
        private int cycle;
        private Long id;
        private MaintainDetailBean maintainDetail;
        private String orderNum;
        private int status;

        public String getClientCompanyName() {
            return clientCompanyName == null ? "" : clientCompanyName;
        }

        public void setClientCompanyName(String clientCompanyName) {
            this.clientCompanyName = clientCompanyName;
        }

        public String getClientUserName() {
            return clientUserName == null ? "" : clientUserName;
        }

        public void setClientUserName(String clientUserName) {
            this.clientUserName = clientUserName;
        }

        public String getClientUserPhone() {
            return clientUserPhone == null ? "" : clientUserPhone;
        }

        public void setClientUserPhone(String clientUserPhone) {
            this.clientUserPhone = clientUserPhone;
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

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public MaintainDetailBean getMaintainDetail() {
            return maintainDetail;
        }

        public void setMaintainDetail(MaintainDetailBean maintainDetail) {
            this.maintainDetail = maintainDetail;
        }

        public String getOrderNum() {
            return orderNum == null ? "" : orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class MaintainDetailBean implements Serializable {
            /**
             * count : 5
             * pictures : d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png
             * shopMaintainId : 942951548685230082
             */

            private int count;
            private String pictures;
            private String shopMaintainId;
            private String businessThreeCode;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getPictures() {
                return pictures == null ? "" : pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }

            public String getShopMaintainId() {
                return shopMaintainId == null ? "" : shopMaintainId;
            }

            public void setShopMaintainId(String shopMaintainId) {
                this.shopMaintainId = shopMaintainId;
            }

            public String getBusinessThreeCode() {
                return businessThreeCode == null ? "" : businessThreeCode;
            }

            public void setBusinessThreeCode(String businessThreeCode) {
                this.businessThreeCode = businessThreeCode;
            }
        }
    }
}



