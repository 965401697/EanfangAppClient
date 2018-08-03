package com.eanfang.model;

import java.io.Serializable;

/**
 * 描述：面谈员工详情
 *
 * @author Guanluocang
 * @date on 2018/8/3$  17:25$
 */
public class WorkTalkDetailBean implements Serializable {


    private AssigneeUserEntityBean assigneeUserEntity;
    private String assigneeUserId;
    private String createTime;
    private String id;
    private String orderNum;
    private String ownerDepartmentId;
    private String ownerTopCompanyId;
    private OwnerUserEntityBean ownerUserEntity;
    private String ownerUserId;
    private String question1;
    private String question10;
    private String question11;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    private String question6;
    private String question7;
    private String question8;
    private String question9;
    private int status;
    private WorkerUserEntityBean workerUserEntity;
    private String workerUserId;

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

    public String getOwnerDepartmentId() {
        return ownerDepartmentId;
    }

    public void setOwnerDepartmentId(String ownerDepartmentId) {
        this.ownerDepartmentId = ownerDepartmentId;
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

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion10() {
        return question10;
    }

    public void setQuestion10(String question10) {
        this.question10 = question10;
    }

    public String getQuestion11() {
        return question11;
    }

    public void setQuestion11(String question11) {
        this.question11 = question11;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }

    public String getQuestion8() {
        return question8;
    }

    public void setQuestion8(String question8) {
        this.question8 = question8;
    }

    public String getQuestion9() {
        return question9;
    }

    public void setQuestion9(String question9) {
        this.question9 = question9;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WorkerUserEntityBean getWorkerUserEntity() {
        return workerUserEntity;
    }

    public void setWorkerUserEntity(WorkerUserEntityBean workerUserEntity) {
        this.workerUserEntity = workerUserEntity;
    }

    public String getWorkerUserId() {
        return workerUserId;
    }

    public void setWorkerUserId(String workerUserId) {
        this.workerUserId = workerUserId;
    }

    public static class AssigneeUserEntityBean {
        /**
         * accId : 958589123373846529
         * accountEntity : {"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0}
         * companyAdmin : true
         * companyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
         * companyId : 981406915978862593
         * departmentEntity : {"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"}
         * departmentId : 1001659535615930369
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
         * topCompanyId : 981406915978862593
         * updateTime : 2018-06-01 20:25:38
         * updateUser : 958589123440955393
         * userId : 981406915978862594
         * userType : 3
         */

        private String accId;
        private AccountEntityBean accountEntity;
        private boolean companyAdmin;
        private CompanyEntityBean companyEntity;
        private String companyId;
        private DepartmentEntityBean departmentEntity;
        private String departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private String userId;
        private int userType;

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

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public DepartmentEntityBean getDepartmentEntity() {
            return departmentEntity;
        }

        public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
            this.departmentEntity = departmentEntity;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public static class AccountEntityBean {
            /**
             * accId : 958589123373846529
             * accType : 3
             * address : 幻眼国际(朝阳北路与高安屯路交叉口东150米)
             * areaCode :
             * avatar : 2c00d1b320d74bdaa66d7773c056989b.png
             * birthday : 2014-06-03 00:00:00
             * email : 101@qq.com
             * gender : 1
             * idCard : 110101200001015778
             * mobile : 13800138010
             * nickName : 烟酒梁还不知
             * qrCode : b4e6ecd7946c432085b63ccef5bb75d9.png
             * realName : 烟酒梁
             * regTime : 2018-01-31 14:33:51
             * status : 0
             */

            private String accId;
            private int accType;
            private String address;
            private String areaCode;
            private String avatar;
            private String birthday;
            private String email;
            private int gender;
            private String idCard;
            private String mobile;
            private String nickName;
            private String qrCode;
            private String realName;
            private String regTime;
            private int status;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
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

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class CompanyEntityBean {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 981406915978862593
             * orgName : 在人间
             * orgType : 0
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             * verifyStatus : 1
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }

        public static class DepartmentEntityBean {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 3
             * orgCode : c.2.2
             * orgId : 1001659535615930369
             * orgName : 地狱波二级
             * orgType : 2
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }
        }

    }


    public static class OwnerUserEntityBean {
        /**
         * accId : 1002036264188542978
         * accountEntity : {"accId":"1002036264188542978","accType":0,"avatar":"ecbd2972163f425bacfbfed943e71553.png","email":"","mobile":"13800138015","nickName":"宝宝","realName":"宝宝","regTime":"2018-05-31 11:57:17","status":0}
         * companyAdmin : false
         * companyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 14:59:26","updateUser":"981406915978862594","verifyStatus":1}
         * companyId : 981406915978862593
         * departmentEntity : {"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 14:59:26","updateUser":"981406915978862594"}
         * departmentId : 1001659535615930369
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 14:59:26","updateUser":"981406915978862594","verifyStatus":1}
         * topCompanyId : 981406915978862593
         * updateTime : 2018-06-01 14:59:26
         * updateUser : 981406915978862594
         * userId : 1002444492261064705
         * userType : 6
         */

        private String accId;
        private AccountEntityBeanX accountEntity;
        private boolean companyAdmin;
        private CompanyEntityBeanXX companyEntity;
        private String companyId;
        private DepartmentEntityBeanX departmentEntity;
        private String departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private TopCompanyEntityBeanX topCompanyEntity;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private String userId;
        private int userType;

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

        public CompanyEntityBeanXX getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBeanXX companyEntity) {
            this.companyEntity = companyEntity;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public DepartmentEntityBeanX getDepartmentEntity() {
            return departmentEntity;
        }

        public void setDepartmentEntity(DepartmentEntityBeanX departmentEntity) {
            this.departmentEntity = departmentEntity;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public TopCompanyEntityBeanX getTopCompanyEntity() {
            return topCompanyEntity;
        }

        public void setTopCompanyEntity(TopCompanyEntityBeanX topCompanyEntity) {
            this.topCompanyEntity = topCompanyEntity;
        }

        public String getTopCompanyId() {
            return topCompanyId;
        }

        public void setTopCompanyId(String topCompanyId) {
            this.topCompanyId = topCompanyId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public static class AccountEntityBeanX {
            /**
             * accId : 1002036264188542978
             * accType : 0
             * avatar : ecbd2972163f425bacfbfed943e71553.png
             * email :
             * mobile : 13800138015
             * nickName : 宝宝
             * realName : 宝宝
             * regTime : 2018-05-31 11:57:17
             * status : 0
             */

            private String accId;
            private int accType;
            private String avatar;
            private String email;
            private String mobile;
            private String nickName;
            private String realName;
            private String regTime;
            private int status;

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

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
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

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class CompanyEntityBeanXX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 981406915978862593
             * orgName : 在人间
             * orgType : 0
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 14:59:26
             * updateUser : 981406915978862594
             * verifyStatus : 1
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }

        public static class DepartmentEntityBeanX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 3
             * orgCode : c.2.2
             * orgId : 1001659535615930369
             * orgName : 地狱波二级
             * orgType : 2
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 14:59:26
             * updateUser : 981406915978862594
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }
        }

        public static class TopCompanyEntityBeanX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 981406915978862593
             * orgName : 在人间
             * orgType : 0
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 14:59:26
             * updateUser : 981406915978862594
             * verifyStatus : 1
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }
    }

    public static class WorkerUserEntityBean {
        /**
         * accId : 958589123373846529
         * accountEntity : {"accId":"958589123373846529","accType":3,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"2c00d1b320d74bdaa66d7773c056989b.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0}
         * companyAdmin : true
         * companyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
         * companyId : 981406915978862593
         * departmentEntity : {"companyId":"981406915978862593","countStaff":0,"level":3,"orgCode":"c.2.2","orgId":"1001659535615930369","orgName":"地狱波二级","orgType":2,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393"}
         * departmentId : 1001659535615930369
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"981406915978862593","countStaff":0,"level":1,"orgCode":"c","orgId":"981406915978862593","orgName":"在人间","orgType":0,"topCompanyId":"981406915978862593","updateTime":"2018-06-01 20:25:38","updateUser":"958589123440955393","verifyStatus":1}
         * topCompanyId : 981406915978862593
         * updateTime : 2018-06-01 20:25:38
         * updateUser : 958589123440955393
         * userId : 981406915978862594
         * userType : 3
         */

        private String accId;
        private AccountEntityBeanXX accountEntity;
        private boolean companyAdmin;
        private CompanyEntityBeanXXX companyEntity;
        private String companyId;
        private DepartmentEntityBeanXX departmentEntity;
        private String departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private TopCompanyEntityBeanXX topCompanyEntity;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private String userId;
        private int userType;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public AccountEntityBeanXX getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntityBeanXX accountEntity) {
            this.accountEntity = accountEntity;
        }

        public boolean isCompanyAdmin() {
            return companyAdmin;
        }

        public void setCompanyAdmin(boolean companyAdmin) {
            this.companyAdmin = companyAdmin;
        }

        public CompanyEntityBeanXXX getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBeanXXX companyEntity) {
            this.companyEntity = companyEntity;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public DepartmentEntityBeanXX getDepartmentEntity() {
            return departmentEntity;
        }

        public void setDepartmentEntity(DepartmentEntityBeanXX departmentEntity) {
            this.departmentEntity = departmentEntity;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public TopCompanyEntityBeanXX getTopCompanyEntity() {
            return topCompanyEntity;
        }

        public void setTopCompanyEntity(TopCompanyEntityBeanXX topCompanyEntity) {
            this.topCompanyEntity = topCompanyEntity;
        }

        public String getTopCompanyId() {
            return topCompanyId;
        }

        public void setTopCompanyId(String topCompanyId) {
            this.topCompanyId = topCompanyId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public static class AccountEntityBeanXX {
            /**
             * accId : 958589123373846529
             * accType : 3
             * address : 幻眼国际(朝阳北路与高安屯路交叉口东150米)
             * areaCode :
             * avatar : 2c00d1b320d74bdaa66d7773c056989b.png
             * birthday : 2014-06-03 00:00:00
             * email : 101@qq.com
             * gender : 1
             * idCard : 110101200001015778
             * mobile : 13800138010
             * nickName : 烟酒梁还不知
             * qrCode : b4e6ecd7946c432085b63ccef5bb75d9.png
             * realName : 烟酒梁
             * regTime : 2018-01-31 14:33:51
             * status : 0
             */

            private String accId;
            private int accType;
            private String address;
            private String areaCode;
            private String avatar;
            private String birthday;
            private String email;
            private int gender;
            private String idCard;
            private String mobile;
            private String nickName;
            private String qrCode;
            private String realName;
            private String regTime;
            private int status;

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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
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

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getRegTime() {
                return regTime;
            }

            public void setRegTime(String regTime) {
                this.regTime = regTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class CompanyEntityBeanXXX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 981406915978862593
             * orgName : 在人间
             * orgType : 0
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             * verifyStatus : 1
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }

        public static class DepartmentEntityBeanXX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 3
             * orgCode : c.2.2
             * orgId : 1001659535615930369
             * orgName : 地狱波二级
             * orgType : 2
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }
        }

        public static class TopCompanyEntityBeanXX {
            /**
             * companyId : 981406915978862593
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 981406915978862593
             * orgName : 在人间
             * orgType : 0
             * topCompanyId : 981406915978862593
             * updateTime : 2018-06-01 20:25:38
             * updateUser : 958589123440955393
             * verifyStatus : 1
             */

            private String companyId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String orgName;
            private int orgType;
            private String topCompanyId;
            private String updateTime;
            private String updateUser;
            private int verifyStatus;

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

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(String updateUser) {
                this.updateUser = updateUser;
            }

            public int getVerifyStatus() {
                return verifyStatus;
            }

            public void setVerifyStatus(int verifyStatus) {
                this.verifyStatus = verifyStatus;
            }
        }
    }
}
