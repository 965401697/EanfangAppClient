package com.eanfang.biz.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  9:20
 * @email houzhongzhou@yeah.net
 * @desc 汇报列表
 */

public class WorkReportListBean implements Serializable {

    /**
     * currPage : 1
     * list : [{"assigneeOrgCode":"c.c1.2","assigneeUser":{"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","mobile":"15010263711","realName":"侯"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"937871079119032321"},"assigneeUserId":"937871079119032321","createCompany":{"orgId":1100,"orgName":"易安防北京运营公司"},"createCompanyId":1100,"createOrg":{"orgCode":"c.c1.2","orgName":"维修部"},"createOrgCode":"c.c1.2","createTime":"2017-12-11 11:43","createTopCompanyId":1000,"createUser":{"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","realName":"侯"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"937871079119032321"},"createUserId":"937871079119032321","firstLookTime":"2017-12-11 11:42","id":1,"status":0,"type":0,"workReportDetail":{"pictures":"2233L","sysWorkReportId":1}}]
     * pageSize : 2147483647
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
         * assigneeOrgCode : c.c1.2
         * assigneeUser : {"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","mobile":"15010263711","realName":"侯"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"937871079119032321"}
         * assigneeUserId : 937871079119032321
         * createCompany : {"orgId":1100,"orgName":"易安防北京运营公司"}
         * createCompanyId : 1100
         * createOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
         * createOrgCode : c.c1.2
         * createTime : 2017-12-11 11:43
         * createTopCompanyId : 1000
         * createUser : {"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","realName":"侯"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":"937871079119032321"}
         * createUserId : 937871079119032321
         * firstLookTime : 2017-12-11 11:42
         * id : 1
         * status : 0
         * type : 0
         * workReportDetail : {"pictures":"2233L","sysWorkReportId":1}
         */

        private String assigneeOrgCode;
        private AssigneeUserBean assigneeUser;
        private String assigneeUserId;
        private CreateCompanyBean createCompany;
        private Long createCompanyId;
        private CreateOrgBean createOrg;
        private String createOrgCode;
        private String createTime;
        private Long createTopCompanyId;
        private CreateUserBean createUser;
        private String createUserId;
        private String firstLookTime;
        private Long id;
        private int status;
        private int type;
        private WorkReportDetailBean workReportDetail;
        // 是否已读 未读
        private int newOrder;

        public String getAssigneeOrgCode() {
            return assigneeOrgCode == null ? "" : assigneeOrgCode;
        }

        public void setAssigneeOrgCode(String assigneeOrgCode) {
            this.assigneeOrgCode = assigneeOrgCode;
        }

        public AssigneeUserBean getAssigneeUser() {
            return assigneeUser;
        }

        public void setAssigneeUser(AssigneeUserBean assigneeUser) {
            this.assigneeUser = assigneeUser;
        }

        public String getAssigneeUserId() {
            return assigneeUserId == null ? "" : assigneeUserId;
        }

        public void setAssigneeUserId(String assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public CreateCompanyBean getCreateCompany() {
            return createCompany;
        }

        public void setCreateCompany(CreateCompanyBean createCompany) {
            this.createCompany = createCompany;
        }

        public Long getCreateCompanyId() {
            return createCompanyId;
        }

        public void setCreateCompanyId(Long createCompanyId) {
            this.createCompanyId = createCompanyId;
        }

        public CreateOrgBean getCreateOrg() {
            return createOrg;
        }

        public void setCreateOrg(CreateOrgBean createOrg) {
            this.createOrg = createOrg;
        }

        public String getCreateOrgCode() {
            return createOrgCode == null ? "" : createOrgCode;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Long getCreateTopCompanyId() {
            return createTopCompanyId;
        }

        public void setCreateTopCompanyId(Long createTopCompanyId) {
            this.createTopCompanyId = createTopCompanyId;
        }

        public CreateUserBean getCreateUser() {
            return createUser;
        }

        public void setCreateUser(CreateUserBean createUser) {
            this.createUser = createUser;
        }

        public String getCreateUserId() {
            return createUserId == null ? "" : createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getFirstLookTime() {
            return firstLookTime == null ? "" : firstLookTime;
        }

        public void setFirstLookTime(String firstLookTime) {
            this.firstLookTime = firstLookTime;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public WorkReportDetailBean getWorkReportDetail() {
            return workReportDetail;
        }

        public void setWorkReportDetail(WorkReportDetailBean workReportDetail) {
            this.workReportDetail = workReportDetail;
        }

        public int getNewOrder() {
            return this.newOrder;
        }

        public void setNewOrder(int newOrder) {
            this.newOrder = newOrder;
        }

        public static class AssigneeUserBean implements Serializable {
            /**
             * accId : 937871078913511425
             * accountEntity : {"accId":"937871078913511425","mobile":"15010263711","realName":"侯"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * userId : 937871079119032321
             */

            private Long accId;
            private AccountEntityBean accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long userId;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
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

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public static class AccountEntityBean implements Serializable {
                /**
                 * accId : 937871078913511425
                 * mobile : 15010263711
                 * realName : 侯
                 */

                private Long accId;
                private String mobile;
                private String realName;

                public Long getAccId() {
                    return accId;
                }

                public void setAccId(Long accId) {
                    this.accId = accId;
                }

                public String getMobile() {
                    return mobile == null ? "" : mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getRealName() {
                    return realName == null ? "" : realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }

        public static class CreateCompanyBean implements Serializable {
            /**
             * orgId : 1100
             * orgName : 易安防北京运营公司
             */

            private Long orgId;
            private String orgName;

            public Long getOrgId() {
                return orgId;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName == null ? "" : orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }

        public static class CreateOrgBean implements Serializable {
            /**
             * orgCode : c.c1.2
             * orgName : 维修部
             */

            private String orgCode;
            private String orgName;

            public String getOrgCode() {
                return orgCode == null ? "" : orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public String getOrgName() {
                return orgName == null ? "" : orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }

        public static class CreateUserBean implements Serializable {
            /**
             * accId : 937871078913511425
             * accountEntity : {"accId":"937871078913511425","realName":"侯"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * userId : 937871079119032321
             */

            private Long accId;
            private AccountEntityBeanX accountEntity;
            private boolean companyAdmin;
            private boolean superAdmin;
            private boolean sysAdmin;
            private Long userId;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
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

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public static class AccountEntityBeanX implements Serializable {
                /**
                 * accId : 937871078913511425
                 * realName : 侯
                 */

                private Long accId;
                private String realName;

                public Long getAccId() {
                    return accId;
                }

                public void setAccId(Long accId) {
                    this.accId = accId;
                }

                public String getRealName() {
                    return realName == null ? "" : realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }

        public static class WorkReportDetailBean implements Serializable {
            /**
             * pictures : 2233L
             * sysWorkReportId : 1
             */

            private String pictures;
            private Long sysWorkReportId;

            public String getPictures() {
                return pictures == null ? "" : pictures;
            }

            public void setPictures(String pictures) {
                this.pictures = pictures;
            }

            public Long getSysWorkReportId() {
                return sysWorkReportId;
            }

            public void setSysWorkReportId(Long sysWorkReportId) {
                this.sysWorkReportId = sysWorkReportId;
            }
        }
    }
}




