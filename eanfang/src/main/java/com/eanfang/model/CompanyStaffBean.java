package com.eanfang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.hou
 *
 * @on 2017/8/29  10:26
 * @email houzhongzhou@yeah.net
 * @desc 获取公司部门员工信息
 */

public class CompanyStaffBean implements Serializable {


    /**
     * code : 20000
     * data : [{"accId":1,"accountEntity":{"accId":1,"email":"jornlin@foxmail.com","mobile":"18500320187","nickName":"jornlin","realName":"jornlin","status":0,"subsystemAdmin":false,"superAdmin":false},"companyEntity":{"companyId":1100,"orgCode":"c.c1","orgName":"易安防北京运营公司"},"companyId":1100,"departmentEntity":{"companyId":1100,"isVerify":0,"orgCode":"c.c1.2","orgId":1102,"orgName":"财务部","orgType":2,"parentOrgId":1100,"sortNum":0,"topCompanyId":1100},"departmentId":1102,"status":0,"updateTime":"2017-11-18 13:48:29","userId":1},{"accId":"937871078913511425","accountEntity":{"accId":"937871078913511425","email":"123@qq.com","mobile":"15010263711","nickName":"侯","realName":"侯","status":0,"subsystemAdmin":false,"superAdmin":false},"companyEntity":{"companyId":1100,"orgCode":"c.c1","orgName":"易安防北京运营公司"},"companyId":1100,"departmentEntity":{"companyId":1100},"status":0,"userId":"937871079119032321"}]
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accId : 1
         * accountEntity : {"accId":1,"email":"jornlin@foxmail.com","mobile":"18500320187","nickName":"jornlin","realName":"jornlin","status":0,"subsystemAdmin":false,"superAdmin":false}
         * companyEntity : {"companyId":1100,"orgCode":"c.c1","orgName":"易安防北京运营公司"}
         * companyId : 1100
         * departmentEntity : {"companyId":1100,"isVerify":0,"orgCode":"c.c1.2","orgId":1102,"orgName":"财务部","orgType":2,"parentOrgId":1100,"sortNum":0,"topCompanyId":1100}
         * departmentId : 1102
         * status : 0
         * updateTime : 2017-11-18 13:48:29
         * userId : 1
         */

        private int accId;
        private AccountEntityBean accountEntity;
        private CompanyEntityBean companyEntity;
        private int companyId;
        private DepartmentEntityBean departmentEntity;
        private int departmentId;
        private int status;
        private String updateTime;
        private int userId;

        public int getAccId() {
            return accId;
        }

        public void setAccId(int accId) {
            this.accId = accId;
        }

        public AccountEntityBean getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntityBean accountEntity) {
            this.accountEntity = accountEntity;
        }

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public DepartmentEntityBean getDepartmentEntity() {
            return departmentEntity;
        }

        public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
            this.departmentEntity = departmentEntity;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime == null ? "" : updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public static class AccountEntityBean {
            /**
             * accId : 1
             * email : jornlin@foxmail.com
             * mobile : 18500320187
             * nickName : jornlin
             * realName : jornlin
             * status : 0
             * subsystemAdmin : false
             * superAdmin : false
             */

            private int accId;
            private String email;
            private String mobile;
            private String nickName;
            private String realName;
            private int status;
            private boolean subsystemAdmin;
            private boolean superAdmin;

            public int getAccId() {
                return accId;
            }

            public void setAccId(int accId) {
                this.accId = accId;
            }

            public String getEmail() {
                return email == null ? "" : email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile == null ? "" : mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNickName() {
                return nickName == null ? "" : nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getRealName() {
                return realName == null ? "" : realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public boolean isSubsystemAdmin() {
                return subsystemAdmin;
            }

            public void setSubsystemAdmin(boolean subsystemAdmin) {
                this.subsystemAdmin = subsystemAdmin;
            }

            public boolean isSuperAdmin() {
                return superAdmin;
            }

            public void setSuperAdmin(boolean superAdmin) {
                this.superAdmin = superAdmin;
            }
        }

        public static class CompanyEntityBean {
            /**
             * companyId : 1100
             * orgCode : c.c1
             * orgName : 易安防北京运营公司
             */

            private int companyId;
            private String orgCode;
            private String orgName;

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

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

        public static class DepartmentEntityBean {
            /**
             * companyId : 1100
             * isVerify : 0
             * orgCode : c.c1.2
             * orgId : 1102
             * orgName : 财务部
             * orgType : 2
             * parentOrgId : 1100
             * sortNum : 0
             * topCompanyId : 1100
             */

            private int companyId;
            private int VerifyStatus;
            private String orgCode;
            private int orgId;
            private String orgName;
            private int orgType;
            private int parentOrgId;
            private int sortNum;
            private int topCompanyId;

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

            public String getOrgCode() {
                return orgCode == null ? "" : orgCode;
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
                return orgName == null ? "" : orgName;
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
        }
    }
}
