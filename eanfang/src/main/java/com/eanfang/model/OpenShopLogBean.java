package com.eanfang.model;

import java.util.List;

/**
 * Created by O u r on 2018/5/23.
 */

public class OpenShopLogBean {

    /**
     * currPage : 1
     * list : [{"assigneeCompanyId":"958589807934590978","assigneeOrgCode":"c","assigneePhone":"李旭","assigneeTopCompanyId":"958589807934590978","assigneeUser":{"accId":"980695065603649538","accountEntity":{"accId":"980695065603649538","accType":0,"avatar":"0","mobile":"15900000005","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"980695066010497026"},"assigneeUserId":"980695066010497026","createOrg":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgName":"褡裢坡烟酒连锁"},"createTime":"2018-05-23 14:23:05","createUser":{"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":0,"avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","realName":"烟酒梁"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"958589807934590979"},"cusEntryTime":"2018-05-23 14:22:39","cusExitTime":"2018-05-23 14:22:41","empEntryTime":"2018-05-23 14:22:36","empExitTime":"2018-05-23 14:22:37","id":"999173851722813441","orderNumber":"OS1805231423875","ownerCompanyId":"958589807934590978","ownerOrgCode":"c","ownerTopCompanyId":"958589807934590978","ownerUserId":"958589807934590979","recYardEndTime":"2018-05-23 14:22:44","recYardStaTime":"2018-05-23 14:22:42","remarkInfo":"测试","status":0}]
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
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * assigneeCompanyId : 958589807934590978
         * assigneeOrgCode : c
         * assigneePhone : 李旭
         * assigneeTopCompanyId : 958589807934590978
         * assigneeUser : {"accId":"980695065603649538","accountEntity":{"accId":"980695065603649538","accType":0,"avatar":"0","mobile":"15900000005","realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"980695066010497026"}
         * assigneeUserId : 980695066010497026
         * createOrg : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgName":"褡裢坡烟酒连锁"}
         * createTime : 2018-05-23 14:23:05
         * createUser : {"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":0,"avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","realName":"烟酒梁"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"958589807934590979"}
         * cusEntryTime : 2018-05-23 14:22:39
         * cusExitTime : 2018-05-23 14:22:41
         * empEntryTime : 2018-05-23 14:22:36
         * empExitTime : 2018-05-23 14:22:37
         * id : 999173851722813441
         * orderNumber : OS1805231423875
         * ownerCompanyId : 958589807934590978
         * ownerOrgCode : c
         * ownerTopCompanyId : 958589807934590978
         * ownerUserId : 958589807934590979
         * recYardEndTime : 2018-05-23 14:22:44
         * recYardStaTime : 2018-05-23 14:22:42
         * remarkInfo : 测试
         * status : 0
         */

        private String assigneeCompanyId;
        private String assigneeOrgCode;
        private String assigneePhone;
        private String assigneeTopCompanyId;
        private AssigneeUserBean assigneeUser;
        private String assigneeUserId;
        private CreateOrgBean createOrg;
        private String createTime;
        private CreateUserBean createUser;
        private String cusEntryTime;
        private String cusExitTime;
        private String empEntryTime;
        private String empExitTime;
        private String id;
        private String orderNumber;
        private String ownerCompanyId;
        private String ownerOrgCode;
        private String ownerTopCompanyId;
        private String ownerUserId;
        private String recYardEndTime;
        private String recYardStaTime;
        private String remarkInfo;
        private int status;

        public String getAssigneeCompanyId() {
            return assigneeCompanyId;
        }

        public void setAssigneeCompanyId(String assigneeCompanyId) {
            this.assigneeCompanyId = assigneeCompanyId;
        }

        public String getAssigneeOrgCode() {
            return assigneeOrgCode;
        }

        public void setAssigneeOrgCode(String assigneeOrgCode) {
            this.assigneeOrgCode = assigneeOrgCode;
        }

        public String getAssigneePhone() {
            return assigneePhone;
        }

        public void setAssigneePhone(String assigneePhone) {
            this.assigneePhone = assigneePhone;
        }

        public String getAssigneeTopCompanyId() {
            return assigneeTopCompanyId;
        }

        public void setAssigneeTopCompanyId(String assigneeTopCompanyId) {
            this.assigneeTopCompanyId = assigneeTopCompanyId;
        }

        public AssigneeUserBean getAssigneeUser() {
            return assigneeUser;
        }

        public void setAssigneeUser(AssigneeUserBean assigneeUser) {
            this.assigneeUser = assigneeUser;
        }

        public String getAssigneeUserId() {
            return assigneeUserId;
        }

        public void setAssigneeUserId(String assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public CreateOrgBean getCreateOrg() {
            return createOrg;
        }

        public void setCreateOrg(CreateOrgBean createOrg) {
            this.createOrg = createOrg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public String getCusEntryTime() {
            return cusEntryTime;
        }

        public void setCusEntryTime(String cusEntryTime) {
            this.cusEntryTime = cusEntryTime;
        }

        public String getCusExitTime() {
            return cusExitTime;
        }

        public void setCusExitTime(String cusExitTime) {
            this.cusExitTime = cusExitTime;
        }

        public String getEmpEntryTime() {
            return empEntryTime;
        }

        public void setEmpEntryTime(String empEntryTime) {
            this.empEntryTime = empEntryTime;
        }

        public String getEmpExitTime() {
            return empExitTime;
        }

        public void setEmpExitTime(String empExitTime) {
            this.empExitTime = empExitTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOwnerCompanyId() {
            return ownerCompanyId;
        }

        public void setOwnerCompanyId(String ownerCompanyId) {
            this.ownerCompanyId = ownerCompanyId;
        }

        public String getOwnerOrgCode() {
            return ownerOrgCode;
        }

        public void setOwnerOrgCode(String ownerOrgCode) {
            this.ownerOrgCode = ownerOrgCode;
        }

        public String getOwnerTopCompanyId() {
            return ownerTopCompanyId;
        }

        public void setOwnerTopCompanyId(String ownerTopCompanyId) {
            this.ownerTopCompanyId = ownerTopCompanyId;
        }

        public String getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(String ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public String getRecYardEndTime() {
            return recYardEndTime;
        }

        public void setRecYardEndTime(String recYardEndTime) {
            this.recYardEndTime = recYardEndTime;
        }

        public String getRecYardStaTime() {
            return recYardStaTime;
        }

        public void setRecYardStaTime(String recYardStaTime) {
            this.recYardStaTime = recYardStaTime;
        }

        public String getRemarkInfo() {
            return remarkInfo;
        }

        public void setRemarkInfo(String remarkInfo) {
            this.remarkInfo = remarkInfo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class AssigneeUserBean {
            /**
             * accId : 980695065603649538
             * accountEntity : {"accId":"980695065603649538","accType":0,"avatar":"0","mobile":"15900000005","realName":"李旭"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * topCompanyId : 958589807934590978
             * userId : 980695066010497026
             */

            private String accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
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

            public static class AccountEntityBean {
                /**
                 * accId : 980695065603649538
                 * accType : 0
                 * avatar : 0
                 * mobile : 15900000005
                 * realName : 李旭
                 */

                private String accId;
                private int accType;
                private String avatar;
                private String mobile;
                private String realName;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
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

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }

        public static class CreateOrgBean {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgName : 褡裢坡烟酒连锁
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgName;

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
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

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }

        public static class CreateUserBean {
            /**
             * accId : 958589123373846529
             * accountEntity : {"accId":"958589123373846529","accType":0,"avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","mobile":"13800138010","realName":"烟酒梁"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * topCompanyId : 958589807934590978
             * userId : 958589807934590979
             */

            private String accId;
            private AccountEntityBeanX accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private String topCompanyId;
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

            public static class AccountEntityBeanX {
                /**
                 * accId : 958589123373846529
                 * accType : 0
                 * avatar : bc3513f9fab74ac0aef2db4be9d0ce40.png
                 * mobile : 13800138010
                 * realName : 烟酒梁
                 */

                private String accId;
                private int accType;
                private String avatar;
                private String mobile;
                private String realName;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
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

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }
    }
}
