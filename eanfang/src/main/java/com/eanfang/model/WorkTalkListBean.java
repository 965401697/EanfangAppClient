package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 描述：面谈员工列表
 *
 * @author Guanluocang
 * @date on 2018/8/2$  19:51$
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkTalkListBean implements Serializable {

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
         * assigneeCompanyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"}
         * assigneeCompanyId : 958589807934590978
         * assigneeDepartmentEntity : {"belongCompany":{"$ref":"$.data.list[0].assigneeCompanyEntity"},"belongTopCompany":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"},"companyId":"958589807934590978","countStaff":0,"level":2,"orgCode":"c.2","orgId":"973097885401395202","orgName":"防损部","topCompanyId":"958589807934590978"}
         * assigneeOrgCode : c.2
         * assigneeTopCompanyId : 958589807934590978
         * assigneeUserEntity : {"accId":"984379128553951234","accountEntity":{"accId":"984379128553951234","accType":2,"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13001011991","realName":"小赵"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"1005457734260609026"}
         * assigneeUserId : 1005457734260609026
         * createTime : 2018-08-17 15:03:59
         * id : 1030349500529000449
         * orderNum : MO1808171503508
         * ownerCompanyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"}
         * ownerCompanyId : 958589807934590978
         * ownerDepartmentEntity : {"belongCompany":{"$ref":"$.data.list[0].ownerCompanyEntity"},"belongTopCompany":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"},"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","topCompanyId":"958589807934590978"}
         * ownerOrgCode : c
         * ownerTopCompanyId : 958589807934590978
         * ownerUserEntity : {"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":3,"avatar":"2c00d1b320d74bdaa66d7773c056989b.png","mobile":"13800138010","realName":"烟酒梁"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"topCompanyId":"958589807934590978","userId":"958589807934590979"}
         * ownerUserId : 958589807934590979
         * status : 0
         * workClasses : 1
         */

        private AssigneeCompanyEntityBean assigneeCompanyEntity;
        private String assigneeCompanyId;
        private AssigneeDepartmentEntityBean assigneeDepartmentEntity;
        private String assigneeOrgCode;
        private String assigneeTopCompanyId;
        private AssigneeUserEntityBean assigneeUserEntity;
        private String assigneeUserId;
        private String createTime;
        private String id;
        private String orderNum;
        private OwnerCompanyEntityBean ownerCompanyEntity;
        private String ownerCompanyId;
        private OwnerDepartmentEntityBean ownerDepartmentEntity;
        private String ownerOrgCode;
        private String ownerTopCompanyId;
        private OwnerUserEntityBean ownerUserEntity;
        private String ownerUserId;
        private int status;
        private int workClasses;

        public AssigneeCompanyEntityBean getAssigneeCompanyEntity() {
            return assigneeCompanyEntity;
        }

        public void setAssigneeCompanyEntity(AssigneeCompanyEntityBean assigneeCompanyEntity) {
            this.assigneeCompanyEntity = assigneeCompanyEntity;
        }

        public String getAssigneeCompanyId() {
            return assigneeCompanyId;
        }

        public void setAssigneeCompanyId(String assigneeCompanyId) {
            this.assigneeCompanyId = assigneeCompanyId;
        }

        public AssigneeDepartmentEntityBean getAssigneeDepartmentEntity() {
            return assigneeDepartmentEntity;
        }

        public void setAssigneeDepartmentEntity(AssigneeDepartmentEntityBean assigneeDepartmentEntity) {
            this.assigneeDepartmentEntity = assigneeDepartmentEntity;
        }

        public String getAssigneeOrgCode() {
            return assigneeOrgCode;
        }

        public void setAssigneeOrgCode(String assigneeOrgCode) {
            this.assigneeOrgCode = assigneeOrgCode;
        }

        public String getAssigneeTopCompanyId() {
            return assigneeTopCompanyId;
        }

        public void setAssigneeTopCompanyId(String assigneeTopCompanyId) {
            this.assigneeTopCompanyId = assigneeTopCompanyId;
        }

        public AssigneeUserEntityBean getAssigneeUserEntity() {
            return assigneeUserEntity;
        }

        public void setAssigneeUserEntity(AssigneeUserEntityBean assigneeUserEntity) {
            this.assigneeUserEntity = assigneeUserEntity;
        }

        public String getAssigneeUserId() {
            return assigneeUserId;
        }

        public void setAssigneeUserId(String assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public OwnerCompanyEntityBean getOwnerCompanyEntity() {
            return ownerCompanyEntity;
        }

        public void setOwnerCompanyEntity(OwnerCompanyEntityBean ownerCompanyEntity) {
            this.ownerCompanyEntity = ownerCompanyEntity;
        }

        public String getOwnerCompanyId() {
            return ownerCompanyId;
        }

        public void setOwnerCompanyId(String ownerCompanyId) {
            this.ownerCompanyId = ownerCompanyId;
        }

        public OwnerDepartmentEntityBean getOwnerDepartmentEntity() {
            return ownerDepartmentEntity;
        }

        public void setOwnerDepartmentEntity(OwnerDepartmentEntityBean ownerDepartmentEntity) {
            this.ownerDepartmentEntity = ownerDepartmentEntity;
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

        public OwnerUserEntityBean getOwnerUserEntity() {
            return ownerUserEntity;
        }

        public void setOwnerUserEntity(OwnerUserEntityBean ownerUserEntity) {
            this.ownerUserEntity = ownerUserEntity;
        }

        public String getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(String ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getWorkClasses() {
            return workClasses;
        }

        public void setWorkClasses(int workClasses) {
            this.workClasses = workClasses;
        }

        public static class AssigneeCompanyEntityBean {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgUnitEntity : {"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2}
             * topCompanyId : 958589807934590978
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private OrgUnitEntityBean orgUnitEntity;
            private String topCompanyId;

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

            public OrgUnitEntityBean getOrgUnitEntity() {
                return orgUnitEntity;
            }

            public void setOrgUnitEntity(OrgUnitEntityBean orgUnitEntity) {
                this.orgUnitEntity = orgUnitEntity;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public static class OrgUnitEntityBean {
                /**
                 * logoPic : ecbd2972163f425bacfbfed943e71553.png
                 * orgId : 958589807934590978
                 * unitType : 2
                 */

                private String logoPic;
                private String orgId;
                private int unitType;

                public String getLogoPic() {
                    return logoPic;
                }

                public void setLogoPic(String logoPic) {
                    this.logoPic = logoPic;
                }

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }

                public int getUnitType() {
                    return unitType;
                }

                public void setUnitType(int unitType) {
                    this.unitType = unitType;
                }
            }
        }

        public static class AssigneeDepartmentEntityBean {
            /**
             * belongCompany : {"$ref":"$.data.list[0].assigneeCompanyEntity"}
             * belongTopCompany : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"}
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 2
             * orgCode : c.2
             * orgId : 973097885401395202
             * orgName : 防损部
             * topCompanyId : 958589807934590978
             */

            private BelongCompanyBean belongCompany;
            private BelongTopCompanyBean belongTopCompany;
            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private String topCompanyId;

            public BelongCompanyBean getBelongCompany() {
                return belongCompany;
            }

            public void setBelongCompany(BelongCompanyBean belongCompany) {
                this.belongCompany = belongCompany;
            }

            public BelongTopCompanyBean getBelongTopCompany() {
                return belongTopCompany;
            }

            public void setBelongTopCompany(BelongTopCompanyBean belongTopCompany) {
                this.belongTopCompany = belongTopCompany;
            }

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

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public static class BelongCompanyBean {
                /**
                 * $ref : $.data.list[0].assigneeCompanyEntity
                 */

                private String $ref;

                public String get$ref() {
                    return $ref;
                }

                public void set$ref(String $ref) {
                    this.$ref = $ref;
                }
            }

            public static class BelongTopCompanyBean {
                /**
                 * companyId : 958589807934590978
                 * countStaff : 0
                 * level : 1
                 * orgCode : c
                 * orgId : 958589807934590978
                 * orgName : 褡裢坡烟酒连锁
                 * orgUnitEntity : {"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2}
                 * topCompanyId : 958589807934590978
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private OrgUnitEntityBeanX orgUnitEntity;
                private String topCompanyId;

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

                public OrgUnitEntityBeanX getOrgUnitEntity() {
                    return orgUnitEntity;
                }

                public void setOrgUnitEntity(OrgUnitEntityBeanX orgUnitEntity) {
                    this.orgUnitEntity = orgUnitEntity;
                }

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public static class OrgUnitEntityBeanX {
                    /**
                     * logoPic : ecbd2972163f425bacfbfed943e71553.png
                     * orgId : 958589807934590978
                     * unitType : 2
                     */

                    private String logoPic;
                    private String orgId;
                    private int unitType;

                    public String getLogoPic() {
                        return logoPic;
                    }

                    public void setLogoPic(String logoPic) {
                        this.logoPic = logoPic;
                    }

                    public String getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(String orgId) {
                        this.orgId = orgId;
                    }

                    public int getUnitType() {
                        return unitType;
                    }

                    public void setUnitType(int unitType) {
                        this.unitType = unitType;
                    }
                }
            }
        }

        public static class AssigneeUserEntityBean {
            /**
             * accId : 984379128553951234
             * accountEntity : {"accId":"984379128553951234","accType":2,"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13001011991","realName":"小赵"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * topCompanyId : 958589807934590978
             * userId : 1005457734260609026
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
                 * accId : 984379128553951234
                 * accType : 2
                 * avatar : ecbd2972163f425bacfbfed943e71553.png
                 * mobile : 13001011991
                 * realName : 小赵
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

        public static class OwnerCompanyEntityBean {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgUnitEntity : {"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2}
             * topCompanyId : 958589807934590978
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private OrgUnitEntityBeanXX orgUnitEntity;
            private String topCompanyId;

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

            public OrgUnitEntityBeanXX getOrgUnitEntity() {
                return orgUnitEntity;
            }

            public void setOrgUnitEntity(OrgUnitEntityBeanXX orgUnitEntity) {
                this.orgUnitEntity = orgUnitEntity;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public static class OrgUnitEntityBeanXX {
                /**
                 * logoPic : ecbd2972163f425bacfbfed943e71553.png
                 * orgId : 958589807934590978
                 * unitType : 2
                 */

                private String logoPic;
                private String orgId;
                private int unitType;

                public String getLogoPic() {
                    return logoPic;
                }

                public void setLogoPic(String logoPic) {
                    this.logoPic = logoPic;
                }

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }

                public int getUnitType() {
                    return unitType;
                }

                public void setUnitType(int unitType) {
                    this.unitType = unitType;
                }
            }
        }

        public static class OwnerDepartmentEntityBean {
            /**
             * belongCompany : {"$ref":"$.data.list[0].ownerCompanyEntity"}
             * belongTopCompany : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgUnitEntity":{"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2},"topCompanyId":"958589807934590978"}
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * topCompanyId : 958589807934590978
             */

            private BelongCompanyBeanX belongCompany;
            private BelongTopCompanyBeanX belongTopCompany;
            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private String topCompanyId;

            public BelongCompanyBeanX getBelongCompany() {
                return belongCompany;
            }

            public void setBelongCompany(BelongCompanyBeanX belongCompany) {
                this.belongCompany = belongCompany;
            }

            public BelongTopCompanyBeanX getBelongTopCompany() {
                return belongTopCompany;
            }

            public void setBelongTopCompany(BelongTopCompanyBeanX belongTopCompany) {
                this.belongTopCompany = belongTopCompany;
            }

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

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public static class BelongCompanyBeanX {
                /**
                 * $ref : $.data.list[0].ownerCompanyEntity
                 */

                private String $ref;

                public String get$ref() {
                    return $ref;
                }

                public void set$ref(String $ref) {
                    this.$ref = $ref;
                }
            }

            public static class BelongTopCompanyBeanX {
                /**
                 * companyId : 958589807934590978
                 * countStaff : 0
                 * level : 1
                 * orgCode : c
                 * orgId : 958589807934590978
                 * orgName : 褡裢坡烟酒连锁
                 * orgUnitEntity : {"logoPic":"ecbd2972163f425bacfbfed943e71553.png","orgId":"958589807934590978","unitType":2}
                 * topCompanyId : 958589807934590978
                 */

                private String companyId;
                private int countStaff;
                private int level;
                private String orgCode;
                private String orgId;
                private String orgName;
                private OrgUnitEntityBeanXXX orgUnitEntity;
                private String topCompanyId;

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

                public OrgUnitEntityBeanXXX getOrgUnitEntity() {
                    return orgUnitEntity;
                }

                public void setOrgUnitEntity(OrgUnitEntityBeanXXX orgUnitEntity) {
                    this.orgUnitEntity = orgUnitEntity;
                }

                public String getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(String topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public static class OrgUnitEntityBeanXXX {
                    /**
                     * logoPic : ecbd2972163f425bacfbfed943e71553.png
                     * orgId : 958589807934590978
                     * unitType : 2
                     */

                    private String logoPic;
                    private String orgId;
                    private int unitType;

                    public String getLogoPic() {
                        return logoPic;
                    }

                    public void setLogoPic(String logoPic) {
                        this.logoPic = logoPic;
                    }

                    public String getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(String orgId) {
                        this.orgId = orgId;
                    }

                    public int getUnitType() {
                        return unitType;
                    }

                    public void setUnitType(int unitType) {
                        this.unitType = unitType;
                    }
                }
            }
        }

        public static class OwnerUserEntityBean {
            /**
             * accId : 958589123373846529
             * accountEntity : {"accId":"958589123373846529","accType":3,"avatar":"2c00d1b320d74bdaa66d7773c056989b.png","mobile":"13800138010","realName":"烟酒梁"}
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
                 * accType : 3
                 * avatar : 2c00d1b320d74bdaa66d7773c056989b.png
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
