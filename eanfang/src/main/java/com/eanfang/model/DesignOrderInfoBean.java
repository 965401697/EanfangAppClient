package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by jornl on 2017/9/8.
 */

public class DesignOrderInfoBean implements Serializable {


    /**
     * assigneeCompanyId : 956440623760154626
     * assigneeTopCompanyId : 956440623760154626
     * budgetLimit : 0
     * businessOneCode : 1.1
     * businessOneId : 10
     * companyEntity : {"adminUserId":"958233665207889923","areaCode":"3.11.1.5","createTime":"2018-01-30 15:01","intro":"小公司，大收益。","legalName":"老三","licenseCode":"987654321","licensePic":"1295d7527a254b88ab09885edb41ae76.png","logoPic":"aeaf0e0ae0b04ada93373c5701ea4395.png","name":"我的安防公司","officeAddress":"一线天云南风味主题餐厅","orgId":"956440623760154626","registerAssets":"200","scale":1,"status":2,"telPhone":"15010263711","tradeTypeCode":"","unitType":3}
     * contactPhone : 18500320187
     * contactUser : 锅子
     * createCompanyId : 956440059794038785
     * createOrgCode : c
     * createTime : 2018-02-07 13:56
     * createTopCompanyId : 956440059794038785
     * createUserId : 956440059794038786
     * detailPlace : 权健自然医学养生馆(朝阳店)
     * id : 961116462276775937
     * latitude : 39.923575
     * longitude : 116.568014
     * orderNum : DS1802071356411
     * predictTime : 0
     * remarkInfo : 调监控
     * revertTimeLimit : 0
     * status : 1
     * userName : mytest
     * zoneCode : 3.11.1.5
     * zoneId : 105
     */

    private Long assigneeCompanyId;
    private Long assigneeTopCompanyId;
    private int budgetLimit;
    private String businessOneCode;
    private Long businessOneId;
    private CompanyEntityBean companyEntity;
    private String contactPhone;
    private String contactUser;
    private Long createCompanyId;
    private String createOrgCode;
    private String createTime;
    private Long createTopCompanyId;
    private Long createUserId;
    private String detailPlace;
    private Long id;
    private String latitude;
    private String longitude;
    private String orderNum;
    private int predictTime;
    private String remarkInfo;
    private int revertTimeLimit;
    private int status;
    private String userName;
    private String zoneCode;
    private Long zoneId;

    public Long getAssigneeCompanyId() {
        return assigneeCompanyId;
    }

    public void setAssigneeCompanyId(Long assigneeCompanyId) {
        this.assigneeCompanyId = assigneeCompanyId;
    }

    public Long getAssigneeTopCompanyId() {
        return assigneeTopCompanyId;
    }

    public void setAssigneeTopCompanyId(Long assigneeTopCompanyId) {
        this.assigneeTopCompanyId = assigneeTopCompanyId;
    }

    public int getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(int budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public String getBusinessOneCode() {
        return businessOneCode;
    }

    public void setBusinessOneCode(String businessOneCode) {
        this.businessOneCode = businessOneCode;
    }

    public Long getBusinessOneId() {
        return businessOneId;
    }

    public void setBusinessOneId(Long businessOneId) {
        this.businessOneId = businessOneId;
    }

    public CompanyEntityBean getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntityBean companyEntity) {
        this.companyEntity = companyEntity;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public Long getCreateCompanyId() {
        return createCompanyId;
    }

    public void setCreateCompanyId(Long createCompanyId) {
        this.createCompanyId = createCompanyId;
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getDetailPlace() {
        return detailPlace;
    }

    public void setDetailPlace(String detailPlace) {
        this.detailPlace = detailPlace;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(int predictTime) {
        this.predictTime = predictTime;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public static class CompanyEntityBean {
        /**
         * adminUserId : 958233665207889923
         * areaCode : 3.11.1.5
         * createTime : 2018-01-30 15:01
         * intro : 小公司，大收益。
         * legalName : 老三
         * licenseCode : 987654321
         * licensePic : 1295d7527a254b88ab09885edb41ae76.png
         * logoPic : aeaf0e0ae0b04ada93373c5701ea4395.png
         * name : 我的安防公司
         * officeAddress : 一线天云南风味主题餐厅
         * orgId : 956440623760154626
         * registerAssets : 200
         * scale : 1
         * status : 2
         * telPhone : 15010263711
         * tradeTypeCode :
         * unitType : 3
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
        private String tradeTypeCode;
        private int unitType;

        public Long getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Long adminUserId) {
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
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

        public String getLicensePic() {
            return licensePic;
        }

        public void setLicensePic(String licensePic) {
            this.licensePic = licensePic;
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

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
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
    }
}

