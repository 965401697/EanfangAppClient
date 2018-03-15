package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/6  18:46
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkCheckListBean implements Serializable {


    /**
     * currPage : 1
     * list : [{"assigneeOrg":{"orgCode":"c.c1.2","orgName":"维修部"},"assigneeOrgCode":"c.c1.2","assigneeUser":{"accId":1,"accountEntity":{"accId":1,"mobile":"18500320187","realName":"jornlin"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":1},"assigneeUserId":1,"changeDeadlineTime":"2020-11-03 00:00","changeInfo":"设备移位","companyName":"北京法案视","createCompany":{"orgId":1100,"orgName":"易安防北京运营公司"},"createCompanyId":1100,"createOrg":{"orgCode":"c.c1.2","orgName":"维修部"},"createOrgCode":"c.c1.2","createTime":"2017-12-15 10:19","createTopCompanyId":1000,"createUser":{"accId":2,"accountEntity":{"accId":2,"realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2},"createUserId":2,"id":"941492977858588673","status":0,"title":"检查设备"}]
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
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * assigneeOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
         * assigneeOrgCode : c.c1.2
         * assigneeUser : {"accId":1,"accountEntity":{"accId":1,"mobile":"18500320187","realName":"jornlin"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":1}
         * assigneeUserId : 1
         * changeDeadlineTime : 2020-11-03 00:00
         * changeInfo : 设备移位
         * companyName : 北京法案视
         * createCompany : {"orgId":1100,"orgName":"易安防北京运营公司"}
         * createCompanyId : 1100
         * createOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
         * createOrgCode : c.c1.2
         * createTime : 2017-12-15 10:19
         * createTopCompanyId : 1000
         * createUser : {"accId":2,"accountEntity":{"accId":2,"realName":"李旭"},"companyAdmin":false,"superAdmin":false,"sysAdmin":false,"userId":2}
         * createUserId : 2
         * id : 941492977858588673
         * status : 0
         * title : 检查设备
         */

        private AssigneeOrgBean assigneeOrg;
        private String assigneeOrgCode;
        private AssigneeUserBean assigneeUser;
        private Long assigneeUserId;
        private String changeDeadlineTime;
        private String changeInfo;
        private String companyName;
        private CreateCompanyBean createCompany;
        private Long createCompanyId;
        private CreateOrgBean createOrg;
        private String createOrgCode;
        private String createTime;
        private Long createTopCompanyId;
        private CreateUserBean createUser;
        private Long createUserId;
        private Long id;
        private int status;
        private String title;
        private WorkInspectDetail workInspectDetail;

        public WorkInspectDetail getWorkInspectDetail() {
            return workInspectDetail;
        }

        public void setWorkInspectDetail(WorkInspectDetail workInspectDetail) {
            this.workInspectDetail = workInspectDetail;
        }

        public AssigneeOrgBean getAssigneeOrg() {
            return assigneeOrg;
        }

        public void setAssigneeOrg(AssigneeOrgBean assigneeOrg) {
            this.assigneeOrg = assigneeOrg;
        }

        public String getAssigneeOrgCode() {
            return assigneeOrgCode;
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

        public Long getAssigneeUserId() {
            return assigneeUserId;
        }

        public void setAssigneeUserId(Long assigneeUserId) {
            this.assigneeUserId = assigneeUserId;
        }

        public String getChangeDeadlineTime() {
            return changeDeadlineTime;
        }

        public void setChangeDeadlineTime(String changeDeadlineTime) {
            this.changeDeadlineTime = changeDeadlineTime;
        }

        public String getChangeInfo() {
            return changeInfo;
        }

        public void setChangeInfo(String changeInfo) {
            this.changeInfo = changeInfo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
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
            return createOrgCode;
        }

        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }

        public String getCreateTime() {
            return createTime;
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

        public Long getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public static class AssigneeOrgBean implements Serializable {
            /**
             * orgCode : c.c1.2
             * orgName : 维修部
             */

            private String orgCode;
            private String orgName;

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

        public static class AssigneeUserBean implements Serializable {
            /**
             * accId : 1
             * accountEntity : {"accId":1,"mobile":"18500320187","realName":"jornlin"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * userId : 1
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
                 * accId : 1
                 * mobile : 18500320187
                 * realName : jornlin
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
                return orgName;
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

        public static class CreateUserBean implements Serializable {
            /**
             * accId : 2
             * accountEntity : {"accId":2,"realName":"李旭"}
             * companyAdmin : false
             * superAdmin : false
             * sysAdmin : false
             * userId : 2
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
                 * accId : 2
                 * realName : 李旭
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
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }
            }
        }

        public static class WorkInspectDetail implements Serializable {

            private Long id;
            //工作检查表ID
            //@TableField(value = "sys_work_inspect_id")
            private Long sysWorkInspectId;
            //标题
            //@TableField(value = "title")
            private String title;
            //位置区域
            //@TableField(value = "region")
            private String region;
            //三级业务类型编码（基础数据表）
            //@TableField(value = "business_three_code")
            private String businessThreeCode;
            //检查内容
            //@TableField(value = "info")
            private String info;
            //图片地址（多个图片地址用逗号分割）
            //@TableField(value = "pictures")
            private String pictures;
            //状态
            private Integer status;
            //-----------------------------------业务字段，不存在于数据库----------------------------------------


            /**
             * 获取：主键
             */
            public Long getId() {
                return id;
            }

            /**
             * 设置：主键
             */
            public void setId(Long id) {
                this.id = id;
            }

            /**
             * 获取：工作检查表ID
             */
            public Long getSysWorkInspectId() {
                return sysWorkInspectId;
            }

            /**
             * 设置：工作检查表ID
             */
            public void setSysWorkInspectId(Long sysWorkInspectId) {
                this.sysWorkInspectId = sysWorkInspectId;
            }

            /**
             * 获取：标题
             */
            public String getTitle() {
                return title;
            }

            /**
             * 设置：标题
             */
            public void setTitle(String title) {
                this.title = title;
            }

            /**
             * 获取：位置区域
             */
            public String getRegion() {
                return region;
            }

            /**
             * 设置：位置区域
             */
            public void setRegion(String region) {
                this.region = region;
            }

            /**
             * 获取：三级业务类型编码（基础数据表）
             */
            public String getBusinessThreeCode() {
                return businessThreeCode;
            }

            /**
             * 设置：三级业务类型编码（基础数据表）
             */
            public void setBusinessThreeCode(String businessThreeCode) {
                this.businessThreeCode = businessThreeCode;
            }

            /**
             * 获取：检查内容
             */
            public String getInfo() {
                return info;
            }

            /**
             * 设置：检查内容
             */
            public void setInfo(String info) {
                this.info = info;
            }

            /**
             * 获取：图片地址（多个图片地址用逗号分割）
             */
            public String getPictures() {
                return pictures;
            }

            /**
             * 设置：图片地址（多个图片地址用逗号分割）
             */
            public void setPictures(String pictures) {
                this.pictures = pictures;
            }



            /**
             * 获取状态
             */
            public Integer getStatus() {
                return status;
            }

            /**
             * 设置状态
             */
            public void setStatus(Integer status) {
                this.status = status;
            }
        }
    }
}


