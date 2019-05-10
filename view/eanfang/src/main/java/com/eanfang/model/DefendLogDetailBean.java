package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/5/24.
 */

public class DefendLogDetailBean implements Serializable {


    /**
     * assigneeCompanyId : 958589807934590978
     * assigneeOrgCode : c
     * assigneeTopCompanyId : 958589807934590978
     * assigneeUser : {"accId":"980695065603649538","accountEntity":{"accId":"980695065603649538","accType":0,"avatar":"0","email":"15940525877@163.com","mobile":"15900000005","nickName":"李旭","qrCode":"4e70fea2a725445a85433ef94eff56c2.png","realName":"李旭","regTime":"2018-04-02 14:34:59","status":0},"companyAdmin":false,"companyEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075","verifyStatus":0},"companyId":"958589807934590978","departmentEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075"},"departmentId":"958589807934590978","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075","verifyStatus":0},"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075","userId":"980695066010497026","userType":6}
     * assigneeUserId : 980695066010497026
     * bypassList : []
     * closeTime : 2018-05-25 10:41:46
     * createTime : 2018-05-25 10:41:51
     * createUser : {"accId":"958589123373846529","accountEntity":{"accId":"958589123373846529","accType":0,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知道","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0},"companyAdmin":true,"companyEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0,"verifyStatus":0},"companyId":"958589807934590978","departmentEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0},"departmentId":"958589807934590978","status":0,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0,"verifyStatus":0},"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0,"userId":"958589807934590979","userType":3}
     * createUserId : 958589807934590979
     * falseList : []
     * id : 998465894028619779
     * openTime : 2018-05-25 10:41:44
     * orderNumber : PL1805251041258
     * ownerCompanyId : 958589807934590978
     * ownerOrgCode : c
     * ownerTopCompanyId : 958589807934590978
     * ownerUserId : 958589807934590979
     * status : 1
     * throughList : []
     */

    private String assigneeCompanyId;
    private String assigneeOrgCode;
    private String assigneeTopCompanyId;
    private AssigneeUserBean assigneeUser;
    private String assigneeUserId;
    private String closeTime;
    private String createTime;
    private CreateUserBean createUser;
    private String createUserId;
    private String id;
    private String openTime;
    private String orderNumber;
    private String ownerCompanyId;
    private String ownerOrgCode;
    private String ownerTopCompanyId;
    private String ownerUserId;
    private int status;
    private List<ListBean> bypassList;
    private List<ListBean> falseList;
    private List<ListBean> throughList;

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

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListBean> getBypassList() {
        return bypassList;
    }

    public void setBypassList(List<ListBean> bypassList) {
        this.bypassList = bypassList;
    }

    public List<ListBean> getFalseList() {
        return falseList;
    }

    public void setFalseList(List<ListBean> falseList) {
        this.falseList = falseList;
    }

    public List<ListBean> getThroughList() {
        return throughList;
    }

    public void setThroughList(List<ListBean> throughList) {
        this.throughList = throughList;
    }

    public static class AssigneeUserBean {
        /**
         * accId : 980695065603649538
         * accountEntity : {"accId":"980695065603649538","accType":0,"avatar":"0","email":"15940525877@163.com","mobile":"15900000005","nickName":"李旭","qrCode":"4e70fea2a725445a85433ef94eff56c2.png","realName":"李旭","regTime":"2018-04-02 14:34:59","status":0}
         * companyAdmin : false
         * companyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075","verifyStatus":0}
         * companyId : 958589807934590978
         * departmentEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075"}
         * departmentId : 958589807934590978
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-04-02 14:34:59","updateUser":"978946335464579075","verifyStatus":0}
         * topCompanyId : 958589807934590978
         * updateTime : 2018-04-02 14:34:59
         * updateUser : 978946335464579075
         * userId : 980695066010497026
         * userType : 6
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
        private TopCompanyEntityBean topCompanyEntity;
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

        public TopCompanyEntityBean getTopCompanyEntity() {
            return topCompanyEntity;
        }

        public void setTopCompanyEntity(TopCompanyEntityBean topCompanyEntity) {
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

        public static class AccountEntityBean {
            /**
             * accId : 980695065603649538
             * accType : 0
             * avatar : 0
             * email : 15940525877@163.com
             * mobile : 15900000005
             * nickName : 李旭
             * qrCode : 4e70fea2a725445a85433ef94eff56c2.png
             * realName : 李旭
             * regTime : 2018-04-02 14:34:59
             * status : 0
             */

            private String accId;
            private int accType;
            private String avatar;
            private String email;
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
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-04-02 14:34:59
             * updateUser : 978946335464579075
             * verifyStatus : 0
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
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-04-02 14:34:59
             * updateUser : 978946335464579075
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

        public static class TopCompanyEntityBean {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-04-02 14:34:59
             * updateUser : 978946335464579075
             * verifyStatus : 0
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

    public static class CreateUserBean {
        /**
         * accId : 958589123373846529
         * accountEntity : {"accId":"958589123373846529","accType":0,"address":"幻眼国际(朝阳北路与高安屯路交叉口东150米)","areaCode":"","avatar":"bc3513f9fab74ac0aef2db4be9d0ce40.png","birthday":"2014-06-03 00:00:00","email":"101@qq.com","gender":1,"idCard":"110101200001015778","mobile":"13800138010","nickName":"烟酒梁还不知道","qrCode":"b4e6ecd7946c432085b63ccef5bb75d9.png","realName":"烟酒梁","regTime":"2018-01-31 14:33:51","status":0}
         * companyAdmin : true
         * companyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0,"verifyStatus":0}
         * companyId : 958589807934590978
         * departmentEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0}
         * departmentId : 958589807934590978
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-01-31 14:36:35","updateUser":0,"verifyStatus":0}
         * topCompanyId : 958589807934590978
         * updateTime : 2018-01-31 14:36:35
         * updateUser : 0
         * userId : 958589807934590979
         * userType : 3
         */

        private String accId;
        private AccountEntityBeanX accountEntity;
        private boolean companyAdmin;
        private CompanyEntityBeanX companyEntity;
        private String companyId;
        private DepartmentEntityBeanX departmentEntity;
        private String departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private TopCompanyEntityBeanX topCompanyEntity;
        private String topCompanyId;
        private String updateTime;
        private long updateUser;
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

        public CompanyEntityBeanX getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBeanX companyEntity) {
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

        public long getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(long updateUser) {
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
             * accId : 958589123373846529
             * accType : 0
             * address : 幻眼国际(朝阳北路与高安屯路交叉口东150米)
             * areaCode :
             * avatar : bc3513f9fab74ac0aef2db4be9d0ce40.png
             * birthday : 2014-06-03 00:00:00
             * email : 101@qq.com
             * gender : 1
             * idCard : 110101200001015778
             * mobile : 13800138010
             * nickName : 烟酒梁还不知道
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

        public static class CompanyEntityBeanX {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-01-31 14:36:35
             * updateUser : 0
             * verifyStatus : 0
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
            private long updateUser;
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

            public long getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(long updateUser) {
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
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-01-31 14:36:35
             * updateUser : 0
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
            private long updateUser;

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

            public long getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(long updateUser) {
                this.updateUser = updateUser;
            }
        }

        public static class TopCompanyEntityBeanX {
            /**
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-01-31 14:36:35
             * updateUser : 0
             * verifyStatus : 0
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
            private long updateUser;
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

            public long getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(long updateUser) {
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

    public static class ListBean implements Serializable {
        /**
         * alarmNum : 8
         * alarmReason : 1
         * assigneeCompanyId : 0
         * assigneeOrgCode : c
         * assigneeTopCompanyId : 0
         * assigneeUserId : 958589123440955393
         * createTime : 2018-05-21 15:29:55
         * createUserId : 958589123440955393
         * id : 998465895081390082
         * logType : 2
         * noteInfo : 误报信息测试
         * ownerCompanyId : 0
         * ownerOrgCode : c
         * ownerTopCompanyId : 0
         * ownerUserId : 958589123440955393
         * playLocaltion : 地铁草房站
         * protectionLogId : 998465894028619778
         * slipNum : #84X
         * status : 0
         */


        private int alarmNum;
        private int alarmReason;
        private long assigneeCompanyId;
        private String assigneeOrgCode;
        private long assigneeTopCompanyId;
        private String assigneeUserId;
        private String createTime;
        private String createUserId;
        private String id;
        private int logType;
        private String noteInfo;
        private long ownerCompanyId;
        private String ownerOrgCode;
        private long ownerTopCompanyId;
        private String ownerUserId;
        private String playLocaltion;
        private String protectionLogId;
        private String slipNum;
        private int status;

        public int getAlarmNum() {
            return alarmNum;
        }

        public void setAlarmNum(int alarmNum) {
            this.alarmNum = alarmNum;
        }

        public int getAlarmReason() {
            return alarmReason;
        }

        public void setAlarmReason(int alarmReason) {
            this.alarmReason = alarmReason;
        }

        public long getAssigneeCompanyId() {
            return assigneeCompanyId;
        }

        public void setAssigneeCompanyId(long assigneeCompanyId) {
            this.assigneeCompanyId = assigneeCompanyId;
        }

        public String getAssigneeOrgCode() {
            return assigneeOrgCode;
        }

        public void setAssigneeOrgCode(String assigneeOrgCode) {
            this.assigneeOrgCode = assigneeOrgCode;
        }

        public long getAssigneeTopCompanyId() {
            return assigneeTopCompanyId;
        }

        public void setAssigneeTopCompanyId(long assigneeTopCompanyId) {
            this.assigneeTopCompanyId = assigneeTopCompanyId;
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

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getLogType() {
            return logType;
        }

        public void setLogType(int logType) {
            this.logType = logType;
        }

        public String getNoteInfo() {
            return noteInfo;
        }

        public void setNoteInfo(String noteInfo) {
            this.noteInfo = noteInfo;
        }

        public long getOwnerCompanyId() {
            return ownerCompanyId;
        }

        public void setOwnerCompanyId(long ownerCompanyId) {
            this.ownerCompanyId = ownerCompanyId;
        }

        public String getOwnerOrgCode() {
            return ownerOrgCode;
        }

        public void setOwnerOrgCode(String ownerOrgCode) {
            this.ownerOrgCode = ownerOrgCode;
        }

        public long getOwnerTopCompanyId() {
            return ownerTopCompanyId;
        }

        public void setOwnerTopCompanyId(long ownerTopCompanyId) {
            this.ownerTopCompanyId = ownerTopCompanyId;
        }

        public String getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(String ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public String getPlayLocaltion() {
            return playLocaltion;
        }

        public void setPlayLocaltion(String playLocaltion) {
            this.playLocaltion = playLocaltion;
        }

        public String getProtectionLogId() {
            return protectionLogId;
        }

        public void setProtectionLogId(String protectionLogId) {
            this.protectionLogId = protectionLogId;
        }

        public String getSlipNum() {
            return slipNum;
        }

        public void setSlipNum(String slipNum) {
            this.slipNum = slipNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
