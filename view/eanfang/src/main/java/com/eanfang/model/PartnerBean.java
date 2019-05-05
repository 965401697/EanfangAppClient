package com.eanfang.model;

import com.eanfang.model.sys.OrgEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/23  13:24
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class PartnerBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"assignee":{"companyId":1100,"isVerify":0,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1},"assigneeOrgId":1100,"beginTime":"2017-12-12 00:00","busType":0,"businessOneCode":"1.3","createTime":"2017-12-26 11:30","createUserId":1,"endTime":"2018-12-12 00:00","id":1,"ownerOrg":{"companyId":2110,"isVerify":0,"level":3,"orgCode":"c.c1.c1","orgId":2110,"orgName":"家乐福双井公司","orgType":1,"parentOrgId":2100,"sortNum":0,"topCompanyId":2000,"updateTime":"2017-12-05 13:34","updateUser":1},"ownerOrgId":2110,"status":0},{"assignee":{"$ref":"$.data.list[0].assignee"},"assigneeOrgId":1100,"beginTime":"2017-12-12 00:00","busType":1,"businessOneCode":"1.3","createTime":"2017-12-26 11:31","createUserId":1,"endTime":"2018-12-12 00:00","id":2,"ownerOrg":{"$ref":"$.data.list[0].ownerOrg"},"ownerOrgId":2110,"status":0},{"assignee":{"$ref":"$.data.list[0].assignee"},"assigneeOrgId":1100,"beginTime":"2017-12-12 00:00","busType":0,"businessOneCode":"1.2","createTime":"2017-12-26 11:31","createUserId":1,"endTime":"2018-12-12 00:00","id":3,"ownerOrg":{"$ref":"$.data.list[0].ownerOrg"},"ownerOrgId":2110,"status":0}]
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

    public static class ListBean implements Serializable{
        /**
         * assignee : {"companyId":1100,"isVerify":0,"level":2,"orgCode":"c.c1","orgId":1100,"orgName":"易安防北京运营公司","orgType":1,"parentOrgId":0,"sortNum":0,"topCompanyId":1000,"updateTime":"2017-12-05 13:34","updateUser":1}
         * assigneeOrgId : 1100
         * beginTime : 2017-12-12 00:00
         * busType : 0
         * businessOneCode : 1.3
         * createTime : 2017-12-26 11:30
         * createUserId : 1
         * endTime : 2018-12-12 00:00
         * id : 1
         * ownerOrg : {"companyId":2110,"isVerify":0,"level":3,"orgCode":"c.c1.c1","orgId":2110,"orgName":"家乐福双井公司","orgType":1,"parentOrgId":2100,"sortNum":0,"topCompanyId":2000,"updateTime":"2017-12-05 13:34","updateUser":1}
         * ownerOrgId : 2110
         * status : 0
         */

        private OrgEntity assigneeOrg;
        private Long assigneeOrgId;
        private String beginTime;
        private int busType;
        private String businessOneCode;
        private String createTime;
        private Long createUserId;
        private String endTime;
        private Long id;
        private OrgEntity ownerOrg;
        private Long ownerOrgId;
        private int status;

        public OrgEntity getAssigneeOrg() {
            return assigneeOrg;
        }

        public void setAssigneeOrg(OrgEntity assigneeOrg) {
            this.assigneeOrg = assigneeOrg;
        }

        public Long getAssigneeOrgId() {
            return assigneeOrgId;
        }

        public void setAssigneeOrgId(Long assigneeOrgId) {
            this.assigneeOrgId = assigneeOrgId;
        }

        public String getBeginTime() {
            return beginTime == null ? "" : beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public int getBusType() {
            return busType;
        }

        public void setBusType(int busType) {
            this.busType = busType;
        }

        public String getBusinessOneCode() {
            return businessOneCode == null ? "" : businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
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

        public String getEndTime() {
            return endTime == null ? "" : endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public OrgEntity getOwnerOrg() {
            return ownerOrg;
        }

        public void setOwnerOrg(OrgEntity ownerOrg) {
            this.ownerOrg = ownerOrg;
        }

        public Long getOwnerOrgId() {
            return ownerOrgId;
        }

        public void setOwnerOrgId(Long ownerOrgId) {
            this.ownerOrgId = ownerOrgId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

