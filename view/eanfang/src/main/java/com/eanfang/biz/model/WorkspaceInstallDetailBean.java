package com.eanfang.biz.model;

import java.io.Serializable;

/**
 * Created by yaosheng on 2017/5/19.
 */

public class WorkspaceInstallDetailBean implements Serializable {

    /**
     * assigneeCompanyId : 1100
     * assigneeOrgCode : c.c1.2
     * assigneeTopCompanyId : 1100
     * assigneeUserId : 2
     * assignessUser : {"accId":2,"accountEntity":{"accId":2,"email":"15940525612@163.com","mobile":"15940525612","nickName":"lixu","realName":"李旭","status":0},"companyAdmin":false,"companyEntity":{"companyId":0,"updateTime":"2017-11-28 11:05:20"},"companyId":0,"departmentEntity":{"companyId":0,"orgId":0,"updateTime":"2017-11-28 11:05:20"},"departmentId":0,"status":0,"superAdmin":false,"sysAdmin":true,"updateTime":"2017-11-28 11:05:20","userId":2,"userType":0}
     * budget : 4
     * businessOneCode : 1.1
     * clientCompanyName : 易安防北京运营公司
     * companyEntity : {"adminUserId":2,"areaCode":"1.1","createTime":"2017-12-21 17:31:03","intro":"安防运维服务第一平台","legalName":"祖蓝","licenseCode":"1.1","licensePic":"图片","logoPic":"图片","name":"安防公司","officeAddress":"北京海淀","orgId":1100,"registerAssets":"500万1元","scale":0,"status":1,"telPhone":"15873486758","unitType":3,"verifyMessage":"审核合格啊","verifyTime":"2017-12-21 17:31:07","verifyUserName":"管理员"}
     * connector : 锅子
     * connectorPhone : 18500320187
     * createTime : 2017-12-21 11:52:02
     * createUserId : 1
     * description : 啦啦啦
     * detailPlace : 北京金襄陵为民诊所
     * editTime : 2017-12-21 14:18:36
     * id : 943690495967100929
     * latitude : 39.923586
     * longitude : 116.567866
     * orderNo : EO1712211152146
     * ownerCompanyId : 1100
     * ownerOrgCode : c.c1.2
     * ownerTopCompanyId : 1100
     * ownerUserId : 1
     * predictTime : 7
     * revertTimeLimit : 0
     * status : 0
     * zone : 3.11.1.5
     */

    private Long assigneeCompanyId;
    private String assigneeOrgCode;
    private Long assigneeTopCompanyId;
    private Long assigneeUserId;
    private AssignessUserBean assignessUser;
    private int budget;
    private String businessOneCode;
    private String clientCompanyName;
    private CompanyEntityBeanX companyEntity;
    private String connector;
    private String connectorPhone;
    private String createTime;
    private Long createUserId;
    private String description;
    private String detailPlace;
    private String editTime;
    private Long id;
    private String latitude;
    private String longitude;
    private String orderNo;
    private Long ownerCompanyId;
    private String ownerOrgCode;
    private Long ownerTopCompanyId;
    private Long ownerUserId;
    private int predictTime;
    private int revertTimeLimit;
    private int status;
    private String zone;

    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    public String getAssigneeOrgCode() {
        return assigneeOrgCode == null ? "" : assigneeOrgCode;
    }

    public void setAssigneeOrgCode(String assigneeOrgCode) {
        this.assigneeOrgCode = assigneeOrgCode;
    }

    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    public Long getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(Long assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    public AssignessUserBean getAssignessUser() {
        return assignessUser;
    }

    public void setAssignessUser(AssignessUserBean assignessUser) {
        this.assignessUser = assignessUser;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getBusinessOneCode() {
        return businessOneCode == null ? "" : businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public String getClientCompanyName() {
        return clientCompanyName == null ? "" : clientCompanyName;
    }

    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }

    public CompanyEntityBeanX getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntityBeanX companyEntity) {
        this.companyEntity = companyEntity;
    }

    public String getConnector() {
        return connector == null ? "" : connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorPhone() {
        return connectorPhone == null ? "" : connectorPhone;
    }

    public void setConnectorPhone(String connectorPhone) {
        this.connectorPhone = connectorPhone;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailPlace() {
        return detailPlace == null ? "" : detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public String getEditTime() {
        return editTime == null ? "" : editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrderNo() {
        return orderNo == null ? "" : orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOwnerCompanyId() {
        return ownerCompanyId;
    }

    public void setOwnerCompanyId(Long ownerCompanyId) {
        this.ownerCompanyId = ownerCompanyId;
    }

    public String getOwnerOrgCode() {
        return ownerOrgCode == null ? "" : ownerOrgCode;
    }

    public void setOwnerOrgCode(String ownerOrgCode) {
        this.ownerOrgCode = ownerOrgCode;
    }

    public Long getOwnerTopCompanyId() {
        return ownerTopCompanyId;
    }

    public void setOwnerTopCompanyId(Long ownerTopCompanyId) {
        this.ownerTopCompanyId = ownerTopCompanyId;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public int getRevertTimeLimit() {
        return revertTimeLimit;
    }

    public void setRevertTimeLimit(int revertTimeLimit) {
        this.revertTimeLimit = revertTimeLimit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getZone() {
        return zone == null ? "" : zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public static class AssignessUserBean implements Serializable {
        /**
         * accId : 2
         * accountEntity : {"accId":2,"email":"15940525612@163.com","mobile":"15940525612","nickName":"lixu","realName":"李旭","status":0}
         * companyAdmin : false
         * companyEntity : {"companyId":0,"updateTime":"2017-11-28 11:05:20"}
         * companyId : 0
         * departmentEntity : {"companyId":0,"orgId":0,"updateTime":"2017-11-28 11:05:20"}
         * departmentId : 0
         * status : 0
         * superAdmin : false
         * sysAdmin : true
         * updateTime : 2017-11-28 11:05:20
         * userId : 2
         * userType : 0
         */

        private Long accId;
        private AccountEntityBean accountEntity;
        private boolean companyAdmin;
        private CompanyEntityBean companyEntity;
        private Long companyId;
        private DepartmentEntityBean departmentEntity;
        private Long departmentId;
        private int status;
        private boolean superAdmin;
        private boolean sysAdmin;
        private String updateTime;
        private Long userId;
        private int userType;

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

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public Long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Long companyId) {
            this.companyId = companyId;
        }

        public DepartmentEntityBean getDepartmentEntity() {
            return departmentEntity;
        }

        public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
            this.departmentEntity = departmentEntity;
        }

        public Long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Long departmentId) {
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

        public String getUpdateTime() {
            return updateTime == null ? "" : updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public static class AccountEntityBean implements Serializable {
            /**
             * accId : 2
             * email : 15940525612@163.com
             * mobile : 15940525612
             * nickName : lixu
             * realName : 李旭
             * status : 0
             */

            private Long accId;
            private String email;
            private String mobile;
            private String nickName;
            private String realName;
            private int status;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
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
        }

        public static class CompanyEntityBean implements Serializable {
            /**
             * companyId : 0
             * updateTime : 2017-11-28 11:05:20
             */

            private Long companyId;
            private String updateTime;

            public Long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public String getUpdateTime() {
                return updateTime == null ? "" : updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class DepartmentEntityBean implements Serializable {
            /**
             * companyId : 0
             * orgId : 0
             * updateTime : 2017-11-28 11:05:20
             */

            private Long companyId;
            private Long orgId;
            private String updateTime;

            public Long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public Long getOrgId() {
                return orgId;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public String getUpdateTime() {
                return updateTime == null ? "" : updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }

    public static class CompanyEntityBeanX implements Serializable {
        /**
         * adminUserId : 2
         * areaCode : 1.1
         * createTime : 2017-12-21 17:31:03
         * intro : 安防运维服务第一平台
         * legalName : 祖蓝
         * licenseCode : 1.1
         * licensePic : 图片
         * logoPic : 图片
         * name : 安防公司
         * officeAddress : 北京海淀
         * orgId : 1100
         * registerAssets : 500万1元
         * scale : 0
         * status : 1
         * telPhone : 15873486758
         * unitType : 3
         * verifyMessage : 审核合格啊
         * verifyTime : 2017-12-21 17:31:07
         * verifyUserName : 管理员
         */

        private Long adminUserId;
        private String areaCode;
        private String createTime;
        private String intro;
        private String legalName;
        private String licenseCode;
        private String licensePic;
        private String logoPic;
        private String name;
        private String officeAddress;
        private Long orgId;
        private String registerAssets;
        private int scale;
        private int status;
        private String telPhone;
        private int unitType;
        private String verifyMessage;
        private String verifyTime;
        private String verifyUserName;

        public Long getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Long adminUserId) {
            this.adminUserId = adminUserId;
        }

        public String getAreaCode() {
            return areaCode == null ? "" : areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getCreateTime() {
            return createTime == null ? "" : createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIntro() {
            return intro == null ? "" : intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getLegalName() {
            return legalName == null ? "" : legalName;
        }

        public void setLegalName(String legalName) {
            this.legalName = legalName;
        }

        public String getLicenseCode() {
            return licenseCode == null ? "" : licenseCode;
        }

        public void setLicenseCode(String licenseCode) {
            this.licenseCode = licenseCode;
        }

        public String getLicensePic() {
            return licensePic == null ? "" : licensePic;
        }

        public void setLicensePic(String licensePic) {
            this.licensePic = licensePic;
        }

        public String getLogoPic() {
            return logoPic == null ? "" : logoPic;
        }

        public void setLogoPic(String logoPic) {
            this.logoPic = logoPic;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOfficeAddress() {
            return officeAddress == null ? "" : officeAddress;
        }

        public void setOfficeAddress(String officeAddress) {
            this.officeAddress = officeAddress;
        }

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
        }

        public String getRegisterAssets() {
            return registerAssets == null ? "" : registerAssets;
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
            return telPhone == null ? "" : telPhone;
        }

        public void setTelPhone(String telPhone) {
            this.telPhone = telPhone;
        }

        public int getUnitType() {
            return unitType;
        }

        public void setUnitType(int unitType) {
            this.unitType = unitType;
        }

        public String getVerifyMessage() {
            return verifyMessage == null ? "" : verifyMessage;
        }

        public void setVerifyMessage(String verifyMessage) {
            this.verifyMessage = verifyMessage;
        }

        public String getVerifyTime() {
            return verifyTime == null ? "" : verifyTime;
        }

        public void setVerifyTime(String verifyTime) {
            this.verifyTime = verifyTime;
        }

        public String getVerifyUserName() {
            return verifyUserName == null ? "" : verifyUserName;
        }

        public void setVerifyUserName(String verifyUserName) {
            this.verifyUserName = verifyUserName;
        }
    }
}

