package com.eanfang.model;

import com.baozi.treerecyclerview.base.BaseItemData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/5/30.
 */

public class SectionBean extends BaseItemData implements Serializable {

    /**
     * children : [{"countStaff":1,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","parentEntity":{"orgName":"地狱波","parentOrgId":"981406915978862593","level":2,"orgCode":"c.2","orgId":"999932390514700289"},"parentOrgId":"999932390514700289","staff":[{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","nickName":"烟酒梁还不知道","realName":"烟酒梁"},"companyAdmin":false,"departmentId":"1001659535615930369","superAdmin":false,"sysAdmin":false,"userId":"981406915978862594"}]}]
     * countStaff : 2
     * level : 2
     * orgCode : c.2
     * orgId : 999932390514700289
     * orgName : 地狱波
     * parentOrgId : 981406915978862593
     * staff : [{"accId":2,"accountEntity":{"accId":2,"avatar":"ce5ade25c6d74d6a9f5ed0c64b1c3579.png","mobile":"15940525612","nickName":"李旭","realName":"李旭"},"companyAdmin":false,"departmentId":"999932390514700289","superAdmin":false,"sysAdmin":false,"userId":"999932500837478402"}]
     */

    private int countStaff;
    private int level;
    private String orgCode;
    private String orgId;
    private String orgName;
    private String parentOrgId;
    private int flag;//是否是单选  和一些业务的选择
    private boolean isChecked;// 选中
    private List<ChildrenBean> children;
    private List<StaffBeanX> staff;


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getCountStaff() {
        return countStaff;
    }

    public void setCountStaff(int countStaff) {
        this.countStaff = countStaff;
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

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public List<StaffBeanX> getStaff() {
        return staff;
    }

    public void setStaff(List<StaffBeanX> staff) {
        this.staff = staff;
    }

    public static class ChildrenBean extends BaseItemData implements Serializable {
        /**
         * countStaff : 1
         * level : 3
         * orgCode : c.2.2
         * orgId : 1001659535615930369
         * orgName : 地狱波二级
         * parentEntity : {"orgName":"地狱波","parentOrgId":"981406915978862593","level":2,"orgCode":"c.2","orgId":"999932390514700289"}
         * parentOrgId : 999932390514700289
         * staff : [{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","nickName":"烟酒梁还不知道","realName":"烟酒梁"},"companyAdmin":false,"departmentId":"1001659535615930369","superAdmin":false,"sysAdmin":false,"userId":"981406915978862594"}]
         */

        private int countStaff;
        private int level;
        private String orgCode;
        private String orgId;
        private String orgName;
        private ParentEntityBean parentEntity;
        private String parentOrgId;
        private List<StaffBean> staff;
        private int flag;//是否是单选  和一些业务的选择


        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getCountStaff() {
            return countStaff;
        }

        public void setCountStaff(int countStaff) {
            this.countStaff = countStaff;
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

        public ParentEntityBean getParentEntity() {
            return parentEntity;
        }

        public void setParentEntity(ParentEntityBean parentEntity) {
            this.parentEntity = parentEntity;
        }

        public String getParentOrgId() {
            return parentOrgId;
        }

        public void setParentOrgId(String parentOrgId) {
            this.parentOrgId = parentOrgId;
        }

        public List<StaffBean> getStaff() {
            return staff;
        }

        public void setStaff(List<StaffBean> staff) {
            this.staff = staff;
        }

        public static class ParentEntityBean implements Serializable {
            /**
             * orgName : 地狱波
             * parentOrgId : 981406915978862593
             * level : 2
             * orgCode : c.2
             * orgId : 999932390514700289
             */

            private String orgName;
            private String parentOrgId;
            private int level;
            private String orgCode;
            private String orgId;

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getParentOrgId() {
                return parentOrgId;
            }

            public void setParentOrgId(String parentOrgId) {
                this.parentOrgId = parentOrgId;
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

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }
        }

        public static class StaffBean implements Serializable {
            /**
             * accId : 958589123373846529
             * accountEntity : {"accId":"958589123373846529","avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","nickName":"烟酒梁还不知道","realName":"烟酒梁"}
             * companyAdmin : false
             * departmentId : 1001659535615930369
             * superAdmin : false
             * sysAdmin : false
             * userId : 981406915978862594
             */

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private String departmentId;
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

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
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

            public static class AccountEntityBean extends BaseItemData implements Serializable {
                /**
                 * accId : 958589123373846529
                 * avatar : bc3513f9fab74ac0aef2db4be9d0ce40.png
                 * mobile : 13800138010
                 * nickName : 烟酒梁还不知道
                 * realName : 烟酒梁
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

    public static class StaffBeanX extends BaseItemData implements Serializable {
        /**
         * accId : 2
         * accountEntity : {"accId":2,"avatar":"ce5ade25c6d74d6a9f5ed0c64b1c3579.png","mobile":"15940525612","nickName":"李旭","realName":"李旭"}
         * companyAdmin : false
         * departmentId : 999932390514700289
         * superAdmin : false
         * sysAdmin : false
         * userId : 999932500837478402
         */

        private String accId;
        private AccountEntityBeanX accountEntity;
        private boolean companyAdmin;
        private String departmentId;
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

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
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

        public static class AccountEntityBeanX implements Serializable {
            /**
             * accId : 2
             * avatar : ce5ade25c6d74d6a9f5ed0c64b1c3579.png
             * mobile : 15940525612
             * nickName : 李旭
             * realName : 李旭
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
