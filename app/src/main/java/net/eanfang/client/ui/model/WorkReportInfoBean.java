package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/9/5  11:55
 * @email houzhongzhou@yeah.net
 * @desc
 */

public class WorkReportInfoBean implements Serializable {

    /**
     * assigneeOrg : {"orgCode":"c.c1.2","orgName":"维修部"}
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
     * workReportDetails : [{"field1":"1112","field2":"2233","id":"940483448991739905","sysWorkReportId":1,"type":0},{"field1":"2223","field2":"4455","id":"940483449654439938","sysWorkReportId":1,"type":1},{"field1":"1112L","field2":"2233L","id":"940484451531059201","sysWorkReportId":1,"type":0},{"field1":"2223","field2":"4455","id":"940484452017598465","sysWorkReportId":1,"type":1},{"field1":"1112L","field2":"2233L","field3":"2233L","field4":"2233L","field5":"2233L","id":"940490189334736898","pictures":"2233L","sysWorkReportId":1,"type":0},{"field1":"2223","field2":"4455","field3":"2233L","field4":"2233L","field5":"2233L","id":"940490189586395137","pictures":"2233L","sysWorkReportId":1,"type":0}]
     */

    private AssigneeOrgBean assigneeOrg;
    private String assigneeOrgCode;
    private AssigneeUserBean assigneeUser;
    private Long assigneeUserId;
    private CreateCompanyBean createCompany;
    private Long createCompanyId;
    private CreateOrgBean createOrg;
    private String createOrgCode;
    private String createTime;
    private Long createTopCompanyId;
    private CreateUserBean createUser;
    private Long createUserId;
    private String firstLookTime;
    private Long id;
    private int status;
    private int type;
    private List<WorkReportDetailsBean> workReportDetails;

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

    public String getFirstLookTime() {
        return firstLookTime;
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

    public List<WorkReportDetailsBean> getWorkReportDetails() {
        return workReportDetails;
    }

    public void setWorkReportDetails(List<WorkReportDetailsBean> workReportDetails) {
        this.workReportDetails = workReportDetails;
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
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }
        }
    }

    public static class WorkReportDetailsBean implements Serializable {
        /**
         * field1 : 1112
         * field2 : 2233
         * id : 940483448991739905
         * sysWorkReportId : 1
         * type : 0
         * field3 : 2233L
         * field4 : 2233L
         * field5 : 2233L
         * pictures : 2233L
         */

        private String field1;
        private String field2;
        private Long id;
        private int sysWorkReportId;
        private int type;
        private String field3;
        private String field4;
        private String field5;
        private String pictures;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getSysWorkReportId() {
            return sysWorkReportId;
        }

        public void setSysWorkReportId(int sysWorkReportId) {
            this.sysWorkReportId = sysWorkReportId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getField3() {
            return field3;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }

        public String getField4() {
            return field4;
        }

        public void setField4(String field4) {
            this.field4 = field4;
        }

        public String getField5() {
            return field5;
        }

        public void setField5(String field5) {
            this.field5 = field5;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }
    }
}


