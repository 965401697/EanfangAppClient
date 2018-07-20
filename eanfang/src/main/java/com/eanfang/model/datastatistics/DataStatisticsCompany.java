package com.eanfang.model.datastatistics;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author Guanluocang
 * @date on 2018/7/17$  18:23$
 */
public class DataStatisticsCompany implements Serializable {


    /**
     * currPage : 1
     * list : [{"accountEntity":{"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"},"adminUserId":"958616139716362242","companyEntity":{"adminUserId":"958616139716362242","areaCode":"3.11.1.1","createTime":"2018-01-31 16:21:00","intro":"褡裢坡修监控连锁大公司","legalName":"张监控","licenseCode":"91158274852698","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"褡裢坡修监控连锁3","officeAddress":"定福家园","orgId":"958616139716362241","registerAssets":"500","scale":2,"son":"1","status":2,"telPhone":"010-65535","tradeTypeCode":"4.5.3","unitType":3,"verifyTime":"2018-02-10 10:57:28","verifyUserName":"平台管理"},"createTime":"2018-01-31 16:21:00","orgEntity":{"adminUserId":"958616139716362242","countStaff":0,"level":1,"orgCode":"c","orgId":"958616139716362241","topCompanyId":"958616139716362241"},"orgId":"958616139716362241"},{"accountEntity":{"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"},"adminUserId":"961429828970119171","companyEntity":{"adminUserId":"961429828970119171","createTime":"2018-02-08 10:41:49","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"我的分公司","orgId":"961429828970119170","registerAssets":"500","son":"1","status":2,"unitType":3},"createTime":"2018-02-08 10:41:49","orgEntity":{"adminUserId":"961429828970119171","countStaff":0,"level":2,"orgCode":"c.c1","orgId":"961429828970119170","topCompanyId":"958616139716362241"},"orgId":"961429828970119170"},{"accountEntity":{"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"},"adminUserId":"961432268314439683","companyEntity":{"adminUserId":"961432268314439683","createTime":"2018-02-08 10:51:30","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"我的分公司二","orgId":"961432268314439682","registerAssets":"500","son":"1","status":2,"unitType":3},"createTime":"2018-02-08 10:51:30","orgEntity":{"adminUserId":"961432268314439683","countStaff":0,"level":2,"orgCode":"c.c2","orgId":"961432268314439682","topCompanyId":"958616139716362241"},"orgId":"961432268314439682"},{"accountEntity":{"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"},"adminUserId":"961445226587258883","companyEntity":{"adminUserId":"961445226587258883","createTime":"2018-02-08 11:43:00","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"我的分公司三","orgId":"961445226587258882","registerAssets":"500","son":"1","status":2,"unitType":3},"createTime":"2018-02-08 11:43:00","orgEntity":{"adminUserId":"961445226587258883","countStaff":0,"level":2,"orgCode":"c.c3","orgId":"961445226587258882","topCompanyId":"958616139716362241"},"orgId":"961445226587258882"},{"accountEntity":{"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"},"adminUserId":"961476705866407938","companyEntity":{"adminUserId":"961476705866407938","createTime":"2018-02-08 13:48:05","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"我的分公司四","orgId":"961476705866407937","registerAssets":"500","son":"1","status":2,"unitType":3},"createTime":"2018-02-08 13:48:05","orgEntity":{"adminUserId":"961476705866407938","countStaff":0,"level":2,"orgCode":"c.c4","orgId":"961476705866407937","topCompanyId":"958616139716362241"},"orgId":"961476705866407937"}]
     * pageSize : 2147483647
     * totalCount : 5
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

    public static class ListBean {
        /**
         * accountEntity : {"avatar":"ecbd2972163f425bacfbfed943e71553.png","mobile":"13800138020","realName":"张监控"}
         * adminUserId : 958616139716362242
         * companyEntity : {"adminUserId":"958616139716362242","areaCode":"3.11.1.1","createTime":"2018-01-31 16:21:00","intro":"褡裢坡修监控连锁大公司","legalName":"张监控","licenseCode":"91158274852698","logoPic":"ecbd2972163f425bacfbfed943e71553.png","name":"褡裢坡修监控连锁3","officeAddress":"定福家园","orgId":"958616139716362241","registerAssets":"500","scale":2,"son":"1","status":2,"telPhone":"010-65535","tradeTypeCode":"4.5.3","unitType":3,"verifyTime":"2018-02-10 10:57:28","verifyUserName":"平台管理"}
         * createTime : 2018-01-31 16:21:00
         * orgEntity : {"adminUserId":"958616139716362242","countStaff":0,"level":1,"orgCode":"c","orgId":"958616139716362241","topCompanyId":"958616139716362241"}
         * orgId : 958616139716362241
         */

        private AccountEntityBean accountEntity;
        private String adminUserId;
        private CompanyEntityBean companyEntity;
        private String createTime;
        private OrgEntityBean orgEntity;
        private String orgId;

        public AccountEntityBean getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntityBean accountEntity) {
            this.accountEntity = accountEntity;
        }

        public String getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(String adminUserId) {
            this.adminUserId = adminUserId;
        }

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public OrgEntityBean getOrgEntity() {
            return orgEntity;
        }

        public void setOrgEntity(OrgEntityBean orgEntity) {
            this.orgEntity = orgEntity;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public static class AccountEntityBean {
            /**
             * avatar : ecbd2972163f425bacfbfed943e71553.png
             * mobile : 13800138020
             * realName : 张监控
             */

            private String avatar;
            private String mobile;
            private String realName;

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

        public static class CompanyEntityBean {
            /**
             * adminUserId : 958616139716362242
             * areaCode : 3.11.1.1
             * createTime : 2018-01-31 16:21:00
             * intro : 褡裢坡修监控连锁大公司
             * legalName : 张监控
             * licenseCode : 91158274852698
             * logoPic : ecbd2972163f425bacfbfed943e71553.png
             * name : 褡裢坡修监控连锁3
             * officeAddress : 定福家园
             * orgId : 958616139716362241
             * registerAssets : 500
             * scale : 2
             * son : 1
             * status : 2
             * telPhone : 010-65535
             * tradeTypeCode : 4.5.3
             * unitType : 3
             * verifyTime : 2018-02-10 10:57:28
             * verifyUserName : 平台管理
             */

            private String adminUserId;
            private String areaCode;
            private String createTime;
            private String intro;
            private String legalName;
            private String licenseCode;
            private String logoPic;
            private String name;
            private String officeAddress;
            private String orgId;
            private String registerAssets;
            private int scale;
            private String son;
            private int status;
            private String telPhone;
            private String tradeTypeCode;
            private int unitType;
            private String verifyTime;
            private String verifyUserName;

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

            public String getSon() {
                return son;
            }

            public void setSon(String son) {
                this.son = son;
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

        public static class OrgEntityBean {
            /**
             * adminUserId : 958616139716362242
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 958616139716362241
             * topCompanyId : 958616139716362241
             */

            private String adminUserId;
            private int countStaff;
            private int level;
            private String orgCode;
            private String orgId;
            private String topCompanyId;

            public String getAdminUserId() {
                return adminUserId;
            }

            public void setAdminUserId(String adminUserId) {
                this.adminUserId = adminUserId;
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

            public String getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(String topCompanyId) {
                this.topCompanyId = topCompanyId;
            }
        }
    }
}
