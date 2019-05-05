package com.eanfang.model.security;

import com.yaf.base.entity.WorkerEntity;
import com.eanfang.model.sys.AccountEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/3/25
 * @description 安防圈评论列表
 */

public class SecurityCommentListBean implements Serializable {

    private List<ListBean> list;

    public SecurityCommentListBean(List<ListBean> list) {
        this.list = list;
    }

    public SecurityCommentListBean() {
    }

    public List<ListBean> getList() {
        return this.list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private AccountEntity accountEntity;
        private CommentsEntityBean commentsEntity;
        private String createTime;
        private PublisherUserBean publisherUser;
        private String spcContent;
        private int spcId;
        private int type;
        private WorkerEntity workerEntity;

        public ListBean(AccountEntity accountEntity, CommentsEntityBean commentsEntity, String createTime, PublisherUserBean publisherUser, String spcContent, int spcId, int type, WorkerEntity workerEntity) {
            this.accountEntity = accountEntity;
            this.commentsEntity = commentsEntity;
            this.createTime = createTime;
            this.publisherUser = publisherUser;
            this.spcContent = spcContent;
            this.spcId = spcId;
            this.type = type;
            this.workerEntity = workerEntity;
        }

        public ListBean() {
        }

        public AccountEntity getAccountEntity() {
            return this.accountEntity;
        }

        public CommentsEntityBean getCommentsEntity() {
            return this.commentsEntity;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public PublisherUserBean getPublisherUser() {
            return this.publisherUser;
        }

        public String getSpcContent() {
            return this.spcContent;
        }

        public int getSpcId() {
            return this.spcId;
        }

        public int getType() {
            return this.type;
        }

        public WorkerEntity getWorkerEntity() {
            return this.workerEntity;
        }

        public void setAccountEntity(AccountEntity accountEntity) {
            this.accountEntity = accountEntity;
        }

        public void setCommentsEntity(CommentsEntityBean commentsEntity) {
            this.commentsEntity = commentsEntity;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setPublisherUser(PublisherUserBean publisherUser) {
            this.publisherUser = publisherUser;
        }

        public void setSpcContent(String spcContent) {
            this.spcContent = spcContent;
        }

        public void setSpcId(int spcId) {
            this.spcId = spcId;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setWorkerEntity(WorkerEntity workerEntity) {
            this.workerEntity = workerEntity;
        }

        public static class CommentsEntityBean {

            private String commentsContent;
            private String createTime;
            private int type;

            public CommentsEntityBean(String commentsContent, String createTime, int type) {
                this.commentsContent = commentsContent;
                this.createTime = createTime;
                this.type = type;
            }

            public CommentsEntityBean() {
            }

            public String getCommentsContent() {
                return this.commentsContent;
            }

            public String getCreateTime() {
                return this.createTime;
            }

            public int getType() {
                return this.type;
            }

            public void setCommentsContent(String commentsContent) {
                this.commentsContent = commentsContent;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
        public static class PublisherUserBean {

            private String accId;
            private boolean companyAdmin;
            private String createTime;
            private boolean superAdmin;
            private boolean sysAdmin;

            public PublisherUserBean(String accId, boolean companyAdmin, String createTime, boolean superAdmin, boolean sysAdmin) {
                this.accId = accId;
                this.companyAdmin = companyAdmin;
                this.createTime = createTime;
                this.superAdmin = superAdmin;
                this.sysAdmin = sysAdmin;
            }

            public PublisherUserBean() {
            }

            public String getAccId() {
                return this.accId;
            }

            public boolean isCompanyAdmin() {
                return this.companyAdmin;
            }

            public String getCreateTime() {
                return this.createTime;
            }

            public boolean isSuperAdmin() {
                return this.superAdmin;
            }

            public boolean isSysAdmin() {
                return this.sysAdmin;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }

            public void setSysAdmin(boolean sysAdmin) {
                this.sysAdmin = sysAdmin;
            }
        }

    }
}
