package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：交接班详情
 *
 * @author Guanluocang
 * @date on 2018/8/3$  20:43$
 */
public class WorkTransferDetailBean implements Serializable {


    private String assigneeCompanyId;
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
    private List<ChangeGoodEntityListBean> changeGoodEntityList;
    private List<FinishWorkEntityListBean> finishWorkEntityList;
    private List<FollowUpEntityListBean> followUpEntityList;
    private List<NotDidEntityListBean> notDidEntityList;
    private List<NoticeEntityListBean> noticeEntityList;

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

    public List<ChangeGoodEntityListBean> getChangeGoodEntityList() {
        return changeGoodEntityList;
    }

    public void setChangeGoodEntityList(List<ChangeGoodEntityListBean> changeGoodEntityList) {
        this.changeGoodEntityList = changeGoodEntityList;
    }

    public List<FinishWorkEntityListBean> getFinishWorkEntityList() {
        return finishWorkEntityList;
    }

    public void setFinishWorkEntityList(List<FinishWorkEntityListBean> finishWorkEntityList) {
        this.finishWorkEntityList = finishWorkEntityList;
    }

    public List<FollowUpEntityListBean> getFollowUpEntityList() {
        return followUpEntityList;
    }

    public void setFollowUpEntityList(List<FollowUpEntityListBean> followUpEntityList) {
        this.followUpEntityList = followUpEntityList;
    }

    public List<NotDidEntityListBean> getNotDidEntityList() {
        return notDidEntityList;
    }

    public void setNotDidEntityList(List<NotDidEntityListBean> notDidEntityList) {
        this.notDidEntityList = notDidEntityList;
    }

    public List<NoticeEntityListBean> getNoticeEntityList() {
        return noticeEntityList;
    }

    public void setNoticeEntityList(List<NoticeEntityListBean> noticeEntityList) {
        this.noticeEntityList = noticeEntityList;
    }

    public static class AssigneeUserEntityBean {
        /**
         * accId : 979177961461190657
         * accountEntity : {"accId":"979177961461190657","accType":3,"address":"首开东都汇(五里桥一街)","areaCode":"3.11.1.5","avatar":"2cea9218e3a0494c9db30f981e0e7345.png","email":"","gender":1,"idCard":"140311198806215047","mobile":"17600738557","nickName":"管罗苍啊","qrCode":"61a363e82e7148ffa6795dfe3693fe4a.png","realName":"管罗苍","regTime":"2018-03-29 10:06:33","status":0}
         * companyAdmin : false
         * companyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-06-29 14:10:08","updateUser":"958589807934590979","verifyStatus":0}
         * companyId : 958589807934590978
         * departmentEntity : {"companyId":"958589807934590978","countStaff":0,"level":2,"orgCode":"c.2","orgId":"973097885401395202","orgName":"防损部","orgType":2,"topCompanyId":"958589807934590978","updateTime":"2018-06-29 14:10:08","updateUser":"958589807934590979"}
         * departmentId : 973097885401395202
         * status : 0
         * superAdmin : false
         * sysAdmin : false
         * topCompanyEntity : {"companyId":"958589807934590978","countStaff":0,"level":1,"orgCode":"c","orgId":"958589807934590978","orgName":"褡裢坡烟酒连锁","orgType":0,"topCompanyId":"958589807934590978","updateTime":"2018-06-29 14:10:08","updateUser":"958589807934590979","verifyStatus":0}
         * topCompanyId : 958589807934590978
         * updateTime : 2018-06-29 14:10:08
         * updateUser : 958589807934590979
         * userId : 1012578947227197442
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
             * accId : 979177961461190657
             * accType : 3
             * address : 首开东都汇(五里桥一街)
             * areaCode : 3.11.1.5
             * avatar : 2cea9218e3a0494c9db30f981e0e7345.png
             * email :
             * gender : 1
             * idCard : 140311198806215047
             * mobile : 17600738557
             * nickName : 管罗苍啊
             * qrCode : 61a363e82e7148ffa6795dfe3693fe4a.png
             * realName : 管罗苍
             * regTime : 2018-03-29 10:06:33
             * status : 0
             */

            private String accId;
            private int accType;
            private String address;
            private String areaCode;
            private String avatar;
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
             * companyId : 958589807934590978
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958589807934590978
             * orgName : 褡裢坡烟酒连锁
             * orgType : 0
             * topCompanyId : 958589807934590978
             * updateTime : 2018-06-29 14:10:08
             * updateUser : 958589807934590979
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
             * level : 2
             * orgCode : c.2
             * orgId : 973097885401395202
             * orgName : 防损部
             * orgType : 2
             * topCompanyId : 958589807934590978
             * updateTime : 2018-06-29 14:10:08
             * updateUser : 958589807934590979
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
             * updateTime : 2018-06-29 14:10:08
             * updateUser : 958589807934590979
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

    public static class OwnerCompanyEntityBean {
        /**
         * adminUserId : 958589807934590979
         * companyId : 958589807934590978
         * countStaff : 0
         * level : 1
         * orgCode : c
         * orgId : 958589807934590978
         * orgName : 褡裢坡烟酒连锁
         * orgType : 0
         * orgUnitEntity : {"accId":"958589123373846529","adminUserId":"958589807934590979","areaCode":"3.11.1.1","createTime":"2018-02-06 14:57:00","defaultAddress":"朝阳北路青年路大悦城3层超市","defaultLat":"39.929287","defaultLon":"116.576013","defaultPlaceCode":"3.11.1.5","legalName":"","licenseCode":"911625884574135","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"褡裢坡烟酒连锁","officeAddress":"定福家园","orgId":"958589807934590978","registerAssets":"500","scale":1,"status":0,"telPhone":"010-65535","tradeTypeCode":"4.5.3","unitType":2,"verifyTime":"2018-02-06 15:38:35","verifyUserName":"平台管理"}
         * parentOrgId : 0
         * sortNum : 0
         * topCompanyId : 958589807934590978
         * updateTime : 2018-01-31 14:36:35
         * updateUser : 958589807934590979
         * verifyStatus : 0
         */

        private String adminUserId;
        private String companyId;
        private int countStaff;
        private int level;
        private String orgCode;
        private String orgId;
        private String orgName;
        private int orgType;
        private OrgUnitEntityBean orgUnitEntity;
        private int parentOrgId;
        private int sortNum;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private int verifyStatus;

        public String getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(String adminUserId) {
            this.adminUserId = adminUserId;
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

        public int getOrgType() {
            return orgType;
        }

        public void setOrgType(int orgType) {
            this.orgType = orgType;
        }

        public OrgUnitEntityBean getOrgUnitEntity() {
            return orgUnitEntity;
        }

        public void setOrgUnitEntity(OrgUnitEntityBean orgUnitEntity) {
            this.orgUnitEntity = orgUnitEntity;
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

        public static class OrgUnitEntityBean {
            /**
             * accId : 958589123373846529
             * adminUserId : 958589807934590979
             * areaCode : 3.11.1.1
             * createTime : 2018-02-06 14:57:00
             * defaultAddress : 朝阳北路青年路大悦城3层超市
             * defaultLat : 39.929287
             * defaultLon : 116.576013
             * defaultPlaceCode : 3.11.1.5
             * legalName :
             * licenseCode : 911625884574135
             * logoPic : ecbd2972163f425bacfbfed943e71553.png
             * name : 褡裢坡烟酒连锁
             * officeAddress : 定福家园
             * orgId : 958589807934590978
             * registerAssets : 500
             * scale : 1
             * status : 0
             * telPhone : 010-65535
             * tradeTypeCode : 4.5.3
             * unitType : 2
             * verifyTime : 2018-02-06 15:38:35
             * verifyUserName : 平台管理
             */

            private String accId;
            private String adminUserId;
            private String areaCode;
            private String createTime;
            private String defaultAddress;
            private String defaultLat;
            private String defaultLon;
            private String defaultPlaceCode;
            private String legalName;
            private String licenseCode;
            private String logoPic;
            private String name;
            private String officeAddress;
            private String orgId;
            private String registerAssets;
            private int scale;
            private int status;
            private String telPhone;
            private String tradeTypeCode;
            private int unitType;
            private String verifyTime;
            private String verifyUserName;

            public String getAccId() {
                return accId;
            }

            public void setAccId(String accId) {
                this.accId = accId;
            }

            public String getAdminUserId() {
                return adminUserId;
            }

            public void setAdminUserId(String adminUserId) {
                this.adminUserId = adminUserId;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDefaultAddress() {
                return defaultAddress;
            }

            public void setDefaultAddress(String defaultAddress) {
                this.defaultAddress = defaultAddress;
            }

            public String getDefaultLat() {
                return defaultLat;
            }

            public void setDefaultLat(String defaultLat) {
                this.defaultLat = defaultLat;
            }

            public String getDefaultLon() {
                return defaultLon;
            }

            public void setDefaultLon(String defaultLon) {
                this.defaultLon = defaultLon;
            }

            public String getDefaultPlaceCode() {
                return defaultPlaceCode;
            }

            public void setDefaultPlaceCode(String defaultPlaceCode) {
                this.defaultPlaceCode = defaultPlaceCode;
            }

            public String getLegalName() {
                return legalName;
            }

            public void setLegalName(String legalName) {
                this.legalName = legalName;
            }

            public String getLicenseCode() {
                return licenseCode;
            }

            public void setLicenseCode(String licenseCode) {
                this.licenseCode = licenseCode;
            }

            public String getLogoPic() {
                return logoPic;
            }

            public void setLogoPic(String logoPic) {
                this.logoPic = logoPic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOfficeAddress() {
                return officeAddress;
            }

            public void setOfficeAddress(String officeAddress) {
                this.officeAddress = officeAddress;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getRegisterAssets() {
                return registerAssets;
            }

            public void setRegisterAssets(String registerAssets) {
                this.registerAssets = registerAssets;
            }

            public int getScale() {
                return scale;
            }

            public void setScale(int scale) {
                this.scale = scale;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTelPhone() {
                return telPhone;
            }

            public void setTelPhone(String telPhone) {
                this.telPhone = telPhone;
            }

            public String getTradeTypeCode() {
                return tradeTypeCode;
            }

            public void setTradeTypeCode(String tradeTypeCode) {
                this.tradeTypeCode = tradeTypeCode;
            }

            public int getUnitType() {
                return unitType;
            }

            public void setUnitType(int unitType) {
                this.unitType = unitType;
            }

            public String getVerifyTime() {
                return verifyTime;
            }

            public void setVerifyTime(String verifyTime) {
                this.verifyTime = verifyTime;
            }

            public String getVerifyUserName() {
                return verifyUserName;
            }

            public void setVerifyUserName(String verifyUserName) {
                this.verifyUserName = verifyUserName;
            }
        }
    }

    public static class OwnerDepartmentEntityBean {
        /**
         * adminUserId : 958589807934590979
         * companyId : 958589807934590978
         * countStaff : 0
         * level : 1
         * orgCode : c
         * orgId : 958589807934590978
         * orgName : 褡裢坡烟酒连锁
         * orgType : 0
         * parentOrgId : 0
         * sortNum : 0
         * topCompanyId : 958589807934590978
         * updateTime : 2018-01-31 14:36:35
         * updateUser : 958589807934590979
         * verifyStatus : 0
         */

        private String adminUserId;
        private String companyId;
        private int countStaff;
        private int level;
        private String orgCode;
        private String orgId;
        private String orgName;
        private int orgType;
        private int parentOrgId;
        private int sortNum;
        private String topCompanyId;
        private String updateTime;
        private String updateUser;
        private int verifyStatus;

        public String getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(String adminUserId) {
            this.adminUserId = adminUserId;
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

    public static class OwnerUserEntityBean {
        /**
         * accId : 980695065603649538
         * accountEntity : {"accId":"980695065603649538","accType":0,"avatar":"ecbd2972163f425bacfbfed943e71553.png","email":"15940525877@163.com","mobile":"15900000005","nickName":"李旭","qrCode":"4e70fea2a725445a85433ef94eff56c2.png","realName":"李旭","regTime":"2018-04-02 14:34:59","status":0}
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
             * accId : 980695065603649538
             * accType : 0
             * avatar : ecbd2972163f425bacfbfed943e71553.png
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

    public static class ChangeGoodEntityListBean implements Serializable {
        /**
         * content : 太阳雨
         * context : 快快乐乐
         * description : 好好聚
         * id : 1027075433961119745
         * oaExchangeLogId : 1027075433927565313
         * picture : af2c2e5161d447f9ac4fcee8c3427a28.png
         * status : 1
         * type : 1
         */

        private String content;
        private String context;
        private String description;
        private String id;
        private String oaExchangeLogId;
        private String picture;
        private int status;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOaExchangeLogId() {
            return oaExchangeLogId;
        }

        public void setOaExchangeLogId(String oaExchangeLogId) {
            this.oaExchangeLogId = oaExchangeLogId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
    }

    public static class FinishWorkEntityListBean implements Serializable {
        /**
         * content : 雨衣
         * context : 几口怕9
         * description : 不将就
         * id : 1027075433982091265
         * oaExchangeLogId : 1027075433927565313
         * picture : 1e8f62e859d64e09bb074efc8a25fb70.png
         * status : 1
         * type : 2
         */

        private String content;
        private String context;
        private String description;
        private String id;
        private String oaExchangeLogId;
        private String picture;
        private int status;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOaExchangeLogId() {
            return oaExchangeLogId;
        }

        public void setOaExchangeLogId(String oaExchangeLogId) {
            this.oaExchangeLogId = oaExchangeLogId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
    }

    public static class FollowUpEntityListBean implements Serializable {
        /**
         * content : 让他与你
         * context : 好几节课看
         * description : 推理
         * id : 1027075434053394434
         * oaExchangeLogId : 1027075433927565313
         * picture :
         * status : 1
         * type : 4
         */

        private String content;
        private String context;
        private String description;
        private String id;
        private String oaExchangeLogId;
        private String picture;
        private int status;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOaExchangeLogId() {
            return oaExchangeLogId;
        }

        public void setOaExchangeLogId(String oaExchangeLogId) {
            this.oaExchangeLogId = oaExchangeLogId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
    }

    public static class NotDidEntityListBean implements Serializable {
        /**
         * content : 天天鱼鱼
         * context : 刚刚还回家
         * description : 把好好聚聚
         * id : 1027075434003062785
         * oaExchangeLogId : 1027075433927565313
         * picture :
         * status : 1
         * type : 3
         */

        private String content;
        private String context;
        private String description;
        private String id;
        private String oaExchangeLogId;
        private String picture;
        private int status;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOaExchangeLogId() {
            return oaExchangeLogId;
        }

        public void setOaExchangeLogId(String oaExchangeLogId) {
            this.oaExchangeLogId = oaExchangeLogId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
    }

    public static class NoticeEntityListBean implements Serializable {
        /**
         * content : 让他雨
         * context : 回家看看
         * description : 任天堂雨
         * id : 1027075434070171649
         * oaExchangeLogId : 1027075433927565313
         * picture : b119545ae57d4e819e8216a677b73817.png
         * status : 0
         * type : 5
         */

        private String content;
        private String context;
        private String description;
        private String id;
        private String oaExchangeLogId;
        private String picture;
        private int status;
        private int type;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOaExchangeLogId() {
            return oaExchangeLogId;
        }

        public void setOaExchangeLogId(String oaExchangeLogId) {
            this.oaExchangeLogId = oaExchangeLogId;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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
    }
}

