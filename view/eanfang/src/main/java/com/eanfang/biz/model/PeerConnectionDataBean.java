package com.eanfang.biz.model;

import com.eanfang.biz.model.entity.OrgEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019/4/9
 * Describe : 同行人脉列表bean类
 */
public class PeerConnectionDataBean implements Serializable {

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

    public static class ListBean {
        /**
         * accId : 979985982284677121
         * avatar : account/avatar/7cff7eb2ddf34dd4b59b0e674499e2fc.png
         * defaultUser : {"accId":"979985982284677121","companyAdmin":false,"companyId":0,"superAdmin":false,"sysAdmin":false,"topCompanyId":0,"userId":"979985982318231553"}
         * nickName : 管理员
         * orgEntity : {"companyId":0,"countStaff":0,"level":0,"orgName":"个人","topCompanyId":0}
         * realName : 管理员
         * simplePwd : false
         */

        private String accId;
        private String avatar;
        private DefaultUserBean defaultUser;
        private String nickName;
        private OrgEntity orgEntity;
        private String realName;
        private boolean simplePwd;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public DefaultUserBean getDefaultUser() {
            return defaultUser;
        }

        public void setDefaultUser(DefaultUserBean defaultUser) {
            this.defaultUser = defaultUser;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public OrgEntity getOrgEntity() {
            return orgEntity;
        }

        public void setOrgEntity(OrgEntity orgEntity) {
            this.orgEntity = orgEntity;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public boolean isSimplePwd() {
            return simplePwd;
        }

        public void setSimplePwd(boolean simplePwd) {
            this.simplePwd = simplePwd;
        }

        public static class DefaultUserBean {
            /**
             * accId : 979985982284677121
             * companyAdmin : false
             * companyId : 0
             * superAdmin : false
             * sysAdmin : false
             * topCompanyId : 0
             * userId : 979985982318231553
             */

            private String accId;
            private boolean companyAdmin;
            private String companyId;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
            private String userId;
            /**
             * 关注状态 0关注 1未关注
             */
            private int followStatus;

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public boolean isCompanyAdmin() {
                return companyAdmin;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public boolean isSuperAdmin() {
                return superAdmin;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }

            public boolean isSysAdmin() {
                return sysAdmin;
            }

            public void setSysAdmin(boolean sysAdmin) {
                this.sysAdmin = sysAdmin;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getFollowStatus() {
                return followStatus;
            }

            public void setFollowStatus(int followStatus) {
                this.followStatus = followStatus;
            }
        }

    }
}
