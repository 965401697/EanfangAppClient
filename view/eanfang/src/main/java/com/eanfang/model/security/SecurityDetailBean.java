package com.eanfang.model.security;

import com.eanfang.model.sys.AccountEntity;
import com.eanfang.model.sys.OrgUnitEntity;

import java.io.Serializable;
import java.util.List;


/**
 * @author guanluocang
 * @data 2019/2/27
 * @description
 */
public class SecurityDetailBean implements Serializable {


    private List<ListBean> list;
    private List<SpcListBean> spcList;

    public SecurityDetailBean(List<ListBean> list, List<SpcListBean> spcList) {
        this.list = list;
        this.spcList = spcList;
    }

    public SecurityDetailBean() {
    }

    public List<ListBean> getList() {
        return this.list;
    }

    public List<SpcListBean> getSpcList() {
        return this.spcList;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public void setSpcList(List<SpcListBean> spcList) {
        this.spcList = spcList;
    }

    public static class ListBean implements Serializable {

        private String asCompanyId;
        private int asId;
        private String asTopCompanyId;
        private String asUserId;
        private CommentUserBean commentUser;
        private CommentOrgBean commentOrg;
        private String commentsAnswerId;
        private String commentsCompanyId;
        private String commentsContent;
        private String commentsImg;
        private String commentsTopCompanyId;
        private String createTime;
        private int friend;
        private int id;
        private int status;
        private int type;
        private int verifyStatus;

        public ListBean(String asCompanyId, int asId, String asTopCompanyId, String asUserId, CommentUserBean commentUser, CommentOrgBean commentOrg, String commentsAnswerId, String commentsCompanyId, String commentsContent, String commentsImg, String commentsTopCompanyId, String createTime, int friend, int id, int status, int type, int verifyStatus) {
            this.asCompanyId = asCompanyId;
            this.asId = asId;
            this.asTopCompanyId = asTopCompanyId;
            this.asUserId = asUserId;
            this.commentUser = commentUser;
            this.commentOrg = commentOrg;
            this.commentsAnswerId = commentsAnswerId;
            this.commentsCompanyId = commentsCompanyId;
            this.commentsContent = commentsContent;
            this.commentsImg = commentsImg;
            this.commentsTopCompanyId = commentsTopCompanyId;
            this.createTime = createTime;
            this.friend = friend;
            this.id = id;
            this.status = status;
            this.type = type;
            this.verifyStatus = verifyStatus;
        }

        public ListBean() {
        }

        public String getAsCompanyId() {
            return this.asCompanyId;
        }

        public int getAsId() {
            return this.asId;
        }

        public String getAsTopCompanyId() {
            return this.asTopCompanyId;
        }

        public String getAsUserId() {
            return this.asUserId;
        }

        public CommentUserBean getCommentUser() {
            return this.commentUser;
        }

        public CommentOrgBean getCommentOrg() {
            return this.commentOrg;
        }

        public String getCommentsAnswerId() {
            return this.commentsAnswerId;
        }

        public String getCommentsCompanyId() {
            return this.commentsCompanyId;
        }

        public String getCommentsContent() {
            return this.commentsContent;
        }

        public String getCommentsImg() {
            return this.commentsImg;
        }

        public String getCommentsTopCompanyId() {
            return this.commentsTopCompanyId;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public int getFriend() {
            return this.friend;
        }

        public int getId() {
            return this.id;
        }

        public int getStatus() {
            return this.status;
        }

        public int getType() {
            return this.type;
        }

        public int getVerifyStatus() {
            return this.verifyStatus;
        }

        public void setAsCompanyId(String asCompanyId) {
            this.asCompanyId = asCompanyId;
        }

        public void setAsId(int asId) {
            this.asId = asId;
        }

        public void setAsTopCompanyId(String asTopCompanyId) {
            this.asTopCompanyId = asTopCompanyId;
        }

        public void setAsUserId(String asUserId) {
            this.asUserId = asUserId;
        }

        public void setCommentUser(CommentUserBean commentUser) {
            this.commentUser = commentUser;
        }

        public void setCommentOrg(CommentOrgBean commentOrg) {
            this.commentOrg = commentOrg;
        }

        public void setCommentsAnswerId(String commentsAnswerId) {
            this.commentsAnswerId = commentsAnswerId;
        }

        public void setCommentsCompanyId(String commentsCompanyId) {
            this.commentsCompanyId = commentsCompanyId;
        }

        public void setCommentsContent(String commentsContent) {
            this.commentsContent = commentsContent;
        }

        public void setCommentsImg(String commentsImg) {
            this.commentsImg = commentsImg;
        }

        public void setCommentsTopCompanyId(String commentsTopCompanyId) {
            this.commentsTopCompanyId = commentsTopCompanyId;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setFriend(int friend) {
            this.friend = friend;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setVerifyStatus(int verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public static class CommentUserBean implements Serializable {

            private String accId;
            private AccountEntity accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;

            public CommentUserBean(String accId, AccountEntity accountEntity, boolean companyAdmin, boolean superAdmin, boolean sysAdmin, String topCompanyId, String userId) {
                this.accId = accId;
                this.accountEntity = accountEntity;
                this.companyAdmin = companyAdmin;
                this.superAdmin = superAdmin;
                this.sysAdmin = sysAdmin;
                this.topCompanyId = topCompanyId;
                this.userId = userId;
            }

            public CommentUserBean() {
            }

            public String getAccId() {
                return this.accId;
            }

            public AccountEntity getAccountEntity() {
                return this.accountEntity;
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

            public String getTopCompanyId() {
                return this.topCompanyId;
            }

            public String getUserId() {
                return this.userId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public void setAccountEntity(AccountEntity accountEntity) {
                this.accountEntity = accountEntity;
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

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class CommentOrgBean implements Serializable {

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private OrgUnitEntity orgUnitEntity;
            private String topCompanyId;

            public CommentOrgBean(String companyId, int countStaff, int level, String orgCode, String orgId, String orgName, OrgUnitEntity orgUnitEntity, String topCompanyId) {
                this.companyId = companyId;
                this.countStaff = countStaff;
                this.level = level;
                this.orgCode = orgCode;
                this.orgId = orgId;
                this.orgName = orgName;
                this.orgUnitEntity = orgUnitEntity;
                this.topCompanyId = topCompanyId;
            }

            public CommentOrgBean() {
            }

            public String getCompanyId() {
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

            public String getOrgId() {
                return this.orgId;
            }

            public String getOrgName() {
                return this.orgName;
            }

            public OrgUnitEntity getOrgUnitEntity() {
                return this.orgUnitEntity;
            }

            public String getTopCompanyId() {
                return this.topCompanyId;
            }

            public void setCompanyId(String companyId) {
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

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public void setOrgUnitEntity(OrgUnitEntity orgUnitEntity) {
                this.orgUnitEntity = orgUnitEntity;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }
        }
    }

    public static class SpcListBean {
        private int readCount;
        private int commentCount;

        public SpcListBean(int readCount, int commentCount) {
            this.readCount = readCount;
            this.commentCount = commentCount;
        }

        public SpcListBean() {
        }

        public int getReadCount() {
            return this.readCount;
        }

        public int getCommentCount() {
            return this.commentCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }
    }
}
