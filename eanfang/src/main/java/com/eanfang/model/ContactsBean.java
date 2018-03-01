package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2018/1/19  14:05
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class ContactsBean implements Serializable {

    /**
     * children : [{"children":[{"level":3,"orgCode":"c.1.1","orgId":"953891950006747138","orgName":"研发部"},{"level":3,"orgCode":"c.1.2","orgId":"953909782232809473","orgName":"测试部"},{"level":3,"orgCode":"c.1.3","orgId":"953912544316211202","orgName":"品牌推广部"}],"level":2,"orgCode":"c.1","orgId":1001,"orgName":"技术部","staff":[{"accId":"948063813624786945","accountEntity":{"accId":"948063813624786945","avatar":"0","mobile":"13911054999","nickName":"从平台员工","realName":"从平台员工"},"companyAdmin":false,"departmentId":1001,"superAdmin":false,"sysAdmin":false,"userId":"948063814081966082"}]},{"level":2,"orgCode":"c.2","orgId":1002,"orgName":"市场部"},{"level":2,"orgCode":"c.3","orgId":1003,"orgName":"财务部","staff":[{"accId":"948067875447500802","accountEntity":{"accId":"948067875447500802","avatar":"2","mobile":"13911054888","nickName":"总平员工二","realName":"总平员工"},"companyAdmin":false,"departmentId":1003,"superAdmin":false,"sysAdmin":false,"userId":"948067875984371714"}]}]
     * companyId : 1000
     * isVerify : 0
     * level : 1
     * orgCode : c
     * orgId : 1000
     * orgName : 易安防总平台
     * orgType : 0
     * parentOrgId : 0
     * sortNum : 0
     * staff : [{"accId":"945852308275638274","accountEntity":{"accId":"945852308275638274","avatar":"0","mobile":"13011054113","nickName":"四灯","realName":"四灯"},"companyAdmin":false,"departmentId":1000,"superAdmin":false,"sysAdmin":false,"userId":"945852308359524354"},{"accId":"945848576578203649","accountEntity":{"accId":"945848576578203649","avatar":"5","mobile":"13011054002","nickName":"三灯","realName":"徐三灯"},"companyAdmin":false,"departmentId":1000,"superAdmin":false,"sysAdmin":false,"userId":"945867718131724289"}]
     * topCompanyId : 1000
     * updateUser : 1
     */

    private int companyId;
    private int VerifyStatus;
    private int level;
    private String orgCode;
    private int orgId;
    private String orgName;
    private int orgType;
    private int parentOrgId;
    private int sortNum;
    private int topCompanyId;
    private int updateUser;
    private List<ChildrenBeanX> children;
    private List<StaffBeanX> staff;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getVerifyStatus() {
        return VerifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        VerifyStatus = verifyStatus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public int getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(int parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public int getTopCompanyId() {
        return topCompanyId;
    }

    public void setTopCompanyId(int topCompanyId) {
        this.topCompanyId = topCompanyId;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public List<ChildrenBeanX> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBeanX> children) {
        this.children = children;
    }

    public List<StaffBeanX> getStaff() {
        return staff;
    }

    public void setStaff(List<StaffBeanX> staff) {
        this.staff = staff;
    }

    public static class ChildrenBeanX {
        /**
         * children : [{"level":3,"orgCode":"c.1.1","orgId":"953891950006747138","orgName":"研发部"},{"level":3,"orgCode":"c.1.2","orgId":"953909782232809473","orgName":"测试部"},{"level":3,"orgCode":"c.1.3","orgId":"953912544316211202","orgName":"品牌推广部"}]
         * level : 2
         * orgCode : c.1
         * orgId : 1001
         * orgName : 技术部
         * staff : [{"accId":"948063813624786945","accountEntity":{"accId":"948063813624786945","avatar":"0","mobile":"13911054999","nickName":"从平台员工","realName":"从平台员工"},"companyAdmin":false,"departmentId":1001,"superAdmin":false,"sysAdmin":false,"userId":"948063814081966082"}]
         */

        private int level;
        private String orgCode;
        private int orgId;
        private String orgName;
        private List<ChildrenBean> children;
        private List<StaffBean> staff;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public List<StaffBean> getStaff() {
            return staff;
        }

        public void setStaff(List<StaffBean> staff) {
            this.staff = staff;
        }

        public static class ChildrenBean {
            /**
             * level : 3
             * orgCode : c.1.1
             * orgId : 953891950006747138
             * orgName : 研发部
             */

            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }

        public static class StaffBean {
            /**
             * accId : 948063813624786945
             * accountEntity : {"accId":"948063813624786945","avatar":"0","mobile":"13911054999","nickName":"从平台员工","realName":"从平台员工"}
             * companyAdmin : false
             * departmentId : 1001
             * superAdmin : false
             * sysAdmin : false
             * userId : 948063814081966082
             */

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private int departmentId;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String userId;

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public AccountEntityBean getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountEntityBean accountEntity) {
                this.accountEntity = accountEntity;
            }

            public boolean isCompanyAdmin() {
                return companyAdmin;
            }

            public void setCompanyAdmin(boolean companyAdmin) {
                this.companyAdmin = companyAdmin;
            }

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public static class AccountEntityBean {
                /**
                 * accId : 948063813624786945
                 * avatar : 0
                 * mobile : 13911054999
                 * nickName : 从平台员工
                 * realName : 从平台员工
                 */

                private String accId;
                private String avatar;
                private String mobile;
                private String nickName;
                private String realName;

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

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }
    }

    public static class StaffBeanX {
        /**
         * accId : 945852308275638274
         * accountEntity : {"accId":"945852308275638274","avatar":"0","mobile":"13011054113","nickName":"四灯","realName":"四灯"}
         * companyAdmin : false
         * departmentId : 1000
         * superAdmin : false
         * sysAdmin : false
         * userId : 945852308359524354
         */

        private String accId;
        private AccountEntityBeanX accountEntity;
        private boolean companyAdmin;
        private int departmentId;
        private boolean superAdmin;
        private boolean sysAdmin;
        private String userId;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public AccountEntityBeanX getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntityBeanX accountEntity) {
            this.accountEntity = accountEntity;
        }

        public boolean isCompanyAdmin() {
            return companyAdmin;
        }

        public void setCompanyAdmin(boolean companyAdmin) {
            this.companyAdmin = companyAdmin;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class AccountEntityBeanX {
            /**
             * accId : 945852308275638274
             * avatar : 0
             * mobile : 13011054113
             * nickName : 四灯
             * realName : 四灯
             */

            private String accId;
            private String avatar;
            private String mobile;
            private String nickName;
            private String realName;

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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }
        }
    }
}

