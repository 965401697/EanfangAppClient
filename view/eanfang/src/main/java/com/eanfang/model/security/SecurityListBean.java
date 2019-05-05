package com.eanfang.model.security;

import com.eanfang.model.sys.AccountEntity;
import com.eanfang.model.sys.OrgUnitEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author guanluocang
 * @data 2019/2/13
 * @description
 */
public class SecurityListBean implements Serializable {

    private List<ListBean> list;

    public SecurityListBean(List<ListBean> list) {
        this.list = list;
    }

    public SecurityListBean() {
    }

    public List<ListBean> getList() {
        return this.list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        private AccountEntity accountEntity;
        private int collectionCount;
        private int commentCount;
        private Date createTime;
        private String editTime;
        private int followCount;
        private int followsStatus;
        private int friend;
        private int likesCount;
        private int likeStatus;
        private Long publisherCompanyId;
        private String publisherCompanyName;
        private PublisherOrgBean publisherOrg;
        private String publisherOrgCode;
        private Long publisherTopCompanyId;
        private PublisherUserBean publisherUser;
        private Long publisherUserId;
        private int readCount;
        private String spcContent;
        private Long spcId;
        private String spcImg;
        private int verifyStatus;
        private String questionContent;
        private String questionId;
        private String type;

        public ListBean(AccountEntity accountEntity, int collectionCount, int commentCount, Date createTime, String editTime, int followCount, int followsStatus, int friend, int likesCount, int likeStatus, Long publisherCompanyId, String publisherCompanyName, PublisherOrgBean publisherOrg, String publisherOrgCode, Long publisherTopCompanyId, PublisherUserBean publisherUser, Long publisherUserId, int readCount, String spcContent, Long spcId, String spcImg, int verifyStatus, String questionContent, String questionId, String type) {
            this.accountEntity = accountEntity;
            this.collectionCount = collectionCount;
            this.commentCount = commentCount;
            this.createTime = createTime;
            this.editTime = editTime;
            this.followCount = followCount;
            this.followsStatus = followsStatus;
            this.friend = friend;
            this.likesCount = likesCount;
            this.likeStatus = likeStatus;
            this.publisherCompanyId = publisherCompanyId;
            this.publisherCompanyName = publisherCompanyName;
            this.publisherOrg = publisherOrg;
            this.publisherOrgCode = publisherOrgCode;
            this.publisherTopCompanyId = publisherTopCompanyId;
            this.publisherUser = publisherUser;
            this.publisherUserId = publisherUserId;
            this.readCount = readCount;
            this.spcContent = spcContent;
            this.spcId = spcId;
            this.spcImg = spcImg;
            this.verifyStatus = verifyStatus;
            this.questionContent = questionContent;
            this.questionId = questionId;
            this.type = type;
        }

        public ListBean() {
        }

        public AccountEntity getAccountEntity() {
            return this.accountEntity;
        }

        public int getCollectionCount() {
            return this.collectionCount;
        }

        public int getCommentCount() {
            return this.commentCount;
        }

        public Date getCreateTime() {
            return this.createTime;
        }

        public String getEditTime() {
            return this.editTime;
        }

        public int getFollowCount() {
            return this.followCount;
        }

        public int getFollowsStatus() {
            return this.followsStatus;
        }

        public int getFriend() {
            return this.friend;
        }

        public int getLikesCount() {
            return this.likesCount;
        }

        public int getLikeStatus() {
            return this.likeStatus;
        }

        public Long getPublisherCompanyId() {
            return this.publisherCompanyId;
        }

        public String getPublisherCompanyName() {
            return this.publisherCompanyName;
        }

        public PublisherOrgBean getPublisherOrg() {
            return this.publisherOrg;
        }

        public String getPublisherOrgCode() {
            return this.publisherOrgCode;
        }

        public Long getPublisherTopCompanyId() {
            return this.publisherTopCompanyId;
        }

        public PublisherUserBean getPublisherUser() {
            return this.publisherUser;
        }

        public Long getPublisherUserId() {
            return this.publisherUserId;
        }

        public int getReadCount() {
            return this.readCount;
        }

        public String getSpcContent() {
            return this.spcContent;
        }

        public Long getSpcId() {
            return this.spcId;
        }

        public String getSpcImg() {
            return this.spcImg;
        }

        public int getVerifyStatus() {
            return this.verifyStatus;
        }

        public String getQuestionContent() {
            return this.questionContent;
        }

        public String getQuestionId() {
            return this.questionId;
        }

        public String getType() {
            return this.type;
        }

        public void setAccountEntity(AccountEntity accountEntity) {
            this.accountEntity = accountEntity;
        }

        public void setCollectionCount(int collectionCount) {
            this.collectionCount = collectionCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public void setFollowsStatus(int followsStatus) {
            this.followsStatus = followsStatus;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }

        public void setLikesCount(int likesCount) {
            this.likesCount = likesCount;
        }

        public void setLikeStatus(int likeStatus) {
            this.likeStatus = likeStatus;
        }

        public void setPublisherCompanyId(Long publisherCompanyId) {
            this.publisherCompanyId = publisherCompanyId;
        }

        public void setPublisherCompanyName(String publisherCompanyName) {
            this.publisherCompanyName = publisherCompanyName;
        }

        public void setPublisherOrg(PublisherOrgBean publisherOrg) {
            this.publisherOrg = publisherOrg;
        }

        public void setPublisherOrgCode(String publisherOrgCode) {
            this.publisherOrgCode = publisherOrgCode;
        }

        public void setPublisherTopCompanyId(Long publisherTopCompanyId) {
            this.publisherTopCompanyId = publisherTopCompanyId;
        }

        public void setPublisherUser(PublisherUserBean publisherUser) {
            this.publisherUser = publisherUser;
        }

        public void setPublisherUserId(Long publisherUserId) {
            this.publisherUserId = publisherUserId;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public void setSpcContent(String spcContent) {
            this.spcContent = spcContent;
        }

        public void setSpcId(Long spcId) {
            this.spcId = spcId;
        }

        public void setSpcImg(String spcImg) {
            this.spcImg = spcImg;
        }

        public void setVerifyStatus(int verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public void setQuestionContent(String questionContent) {
            this.questionContent = questionContent;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class PublisherOrgBean implements Serializable {

            private Long companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private Long orgId;
            private String orgName;
            private OrgUnitEntity orgUnitEntity;
            private Long topCompanyId;

            public PublisherOrgBean(Long companyId, int countStaff, int level, String orgCode, Long orgId, String orgName, OrgUnitEntity orgUnitEntity, Long topCompanyId) {
                this.companyId = companyId;
                this.countStaff = countStaff;
                this.level = level;
                this.orgCode = orgCode;
                this.orgId = orgId;
                this.orgName = orgName;
                this.orgUnitEntity = orgUnitEntity;
                this.topCompanyId = topCompanyId;
            }

            public PublisherOrgBean() {
            }

            public Long getCompanyId() {
                return this.companyId;
            }

            public int getCountStaff() {
                return this.countStaff;
            }

            public int getLevel() {
                return this.level;
            }

            public String getOrgCode() {
                return this.orgCode;
            }

            public Long getOrgId() {
                return this.orgId;
            }

            public String getOrgName() {
                return this.orgName;
            }

            public OrgUnitEntity getOrgUnitEntity() {
                return this.orgUnitEntity;
            }

            public Long getTopCompanyId() {
                return this.topCompanyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public void setCountStaff(int countStaff) {
                this.countStaff = countStaff;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public void setOrgUnitEntity(OrgUnitEntity orgUnitEntity) {
                this.orgUnitEntity = orgUnitEntity;
            }

            public void setTopCompanyId(Long topCompanyId) {
                this.topCompanyId = topCompanyId;
            }
        }

        public static class PublisherUserBean implements Serializable {
            private Long accId;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long topCompanyId;
            private Long userId;

            public PublisherUserBean(Long accId, boolean companyAdmin, boolean superAdmin, boolean sysAdmin, Long topCompanyId, Long userId) {
                this.accId = accId;
                this.companyAdmin = companyAdmin;
                this.superAdmin = superAdmin;
                this.sysAdmin = sysAdmin;
                this.topCompanyId = topCompanyId;
                this.userId = userId;
            }

            public PublisherUserBean() {
            }

            public Long getAccId() {
                return this.accId;
            }

            public boolean isCompanyAdmin() {
                return this.companyAdmin;
            }

            public boolean isSuperAdmin() {
                return this.superAdmin;
            }

            public boolean isSysAdmin() {
                return this.sysAdmin;
            }

            public Long getTopCompanyId() {
                return this.topCompanyId;
            }

            public Long getUserId() {
                return this.userId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }

            public void setSysAdmin(boolean sysAdmin) {
                this.sysAdmin = sysAdmin;
            }

            public void setTopCompanyId(Long topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }
        }
    }
}
