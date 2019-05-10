package com.eanfang.model;

import com.eanfang.model.sys.OrgEntity;
import com.eanfang.model.sys.UserEntity;
import com.yaf.base.entity.WorkerEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 关注列表bean类
 */
public class FollowDataBean implements Serializable {

    private int currPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<FollowListBean> list;

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

    public List<FollowListBean> getList() {
        return list;
    }

    public void setList(List<FollowListBean> list) {
        this.list = list;
    }

    public static class FollowListBean {

        /**
         * 被关注人id
         */
        private String asUserId;
        /**
         * 被关注人公司id
         */
        private String asCompanyId;
        /**
         * 被关注人总公司id
         */
        private String asTopCompanyId;
        /**
         * 关注状态 1：关注 0：未关注
         */
        private int followsStatus;
        /**
         * 好友状态 1：好友 0：非好友
         */
        private int friend;
        private OrgEntity orgEntity;
        private UserEntity userEntity;
        private WorkerEntity workerEntity;

        public String getAsUserId() {
            return asUserId;
        }

        public void setAsUserId(String asUserId) {
            this.asUserId = asUserId;
        }

        public String getAsCompanyId() {
            return asCompanyId;
        }

        public void setAsCompanyId(String asCompanyId) {
            this.asCompanyId = asCompanyId;
        }

        public String getAsTopCompanyId() {
            return asTopCompanyId;
        }

        public void setAsTopCompanyId(String asTopCompanyId) {
            this.asTopCompanyId = asTopCompanyId;
        }

        public int getFollowsStatus() {
            return followsStatus;
        }

        public void setFollowsStatus(int followsStatus) {
            this.followsStatus = followsStatus;
        }

        public int getFriend() {
            return friend;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }

        public OrgEntity getOrgEntity() {
            return orgEntity;
        }

        public void setOrgEntity(OrgEntity orgEntity) {
            this.orgEntity = orgEntity;
        }
        public UserEntity getUserEntity() {
            return userEntity;
        }

        public void setUserEntity(UserEntity userEntity) {
            this.userEntity = userEntity;
        }

        public WorkerEntity getWorkerEntity() {
            return workerEntity;
        }

        public void setWorkerEntity(WorkerEntity workerEntity) {
            this.workerEntity = workerEntity;
        }

    }
}
