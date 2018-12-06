package com.eanfang.model;

import com.yaf.base.entity.WorkerEntity;
import com.yaf.sys.entity.OrgEntity;
import com.yaf.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaosheng on 2017/6/23.
 */

public class CollectionWorkerListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"assigneeId":"936487014465806338","assigneeUserEntity":{"accId":"936487014348365825","accountEntity":{"accId":"936487014348365825","accType":0,"avatar":"0","realName":"张三"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"936487014465806338"},"createTime":"2018-01-04 14:39","id":18,"ownerId":"936487014465806337","ownerUserEntity":{"accId":"936487014348365825","accountEntity":{"$ref":"$.data.list[0].assigneeUserEntity.accountEntity"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"936487014465806337"},"type":0}]
     * pageSize : 10
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
         * assigneeId : 936487014465806338
         * assigneeUserEntity : {"accId":"936487014348365825","accountEntity":{"accId":"936487014348365825","accType":0,"avatar":"0","realName":"张三"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"936487014465806338"}
         * createTime : 2018-01-04 14:39
         * id : 18
         * ownerId : 936487014465806337
         * ownerUserEntity : {"accId":"936487014348365825","accountEntity":{"$ref":"$.data.list[0].assigneeUserEntity.accountEntity"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"936487014465806337"}
         * type : 0
         */

        private Long assigneeId;
        private String createTime;
        private Long id;
        private Long ownerId;
        private int type;
        private UserEntity assigneeUserEntity;
        private WorkerEntity workerEntity;
        private OrgEntity assigneeCompanyEntity;

        public Long getAssigneeId() {
            return assigneeId;
        }

        public void setAssigneeId(Long assigneeId) {
            this.assigneeId = assigneeId;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public UserEntity getAssigneeUserEntity() {
            return assigneeUserEntity;
        }

        public void setAssigneeUserEntity(UserEntity assigneeUserEntity) {
            this.assigneeUserEntity = assigneeUserEntity;
        }

        public WorkerEntity getWorkerEntity() {
            return workerEntity;
        }

        public void setWorkerEntity(WorkerEntity workerEntity) {
            this.workerEntity = workerEntity;
        }

        public OrgEntity getAssigneeCompanyEntity() {
            return assigneeCompanyEntity;
        }

        public void setAssigneeCompanyEntity(OrgEntity assigneeCompanyEntity) {
            this.assigneeCompanyEntity = assigneeCompanyEntity;
        }
    }
}

