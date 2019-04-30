package com.eanfang.model;

import com.yaf.sys.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/6/13.
 */

public class CooperationSearchBean implements Serializable{

    /**
     * currPage : 1
     * list : [{"accountEntity":{"realName":"林技师"},"adminUserId":"993405421694017539","companyEntity":{"adminUserId":"993405421694017539","areaCode":"","createTime":"2018-05-07 16:21:24","defaultAddress":"中润通财富(朝阳北路)","defaultLat":"39.925748","defaultLon":"116.608023","defaultPlaceCode":"3.11.1.5","intro":"哟哟哟哟哟哟","legalName":"哦了","licenseCode":"421529465852","licensePic":"4435bb71736a4054a0ef95af15ca405b.png","logoPic":"4435bb71736a4054a0ef95af15ca405b.png","name":"北京修监控科技","officeAddress":"中润通财富(朝阳北路)","orgId":"993405421694017538","registerAssets":"500","scale":0,"status":2,"telPhone":"552459","tradeTypeCode":"","unitType":2,"verifyMessage":"","verifyTime":"2018-06-01 17:51:06","verifyUserName":"平台管理"},"createTime":"2018-05-07 16:21:24","orgEntity":{"adminUserId":"993405421694017539","countStaff":0,"level":1,"orgCode":"c","orgId":"993405421694017538","topCompanyId":"993405421694017538"},"orgId":"993405421694017538"}]
     * pageSize : 2147483647
     * totalCount : 1
     * totalPage : 1
     */


    private List<ListBean> list;


    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * accountEntity : {"realName":"林技师"}
         * adminUserId : 993405421694017539
         * companyEntity : {"adminUserId":"993405421694017539","areaCode":"","createTime":"2018-05-07 16:21:24","defaultAddress":"中润通财富(朝阳北路)","defaultLat":"39.925748","defaultLon":"116.608023","defaultPlaceCode":"3.11.1.5","intro":"哟哟哟哟哟哟","legalName":"哦了","licenseCode":"421529465852","licensePic":"4435bb71736a4054a0ef95af15ca405b.png","logoPic":"4435bb71736a4054a0ef95af15ca405b.png","name":"北京修监控科技","officeAddress":"中润通财富(朝阳北路)","orgId":"993405421694017538","registerAssets":"500","scale":0,"status":2,"telPhone":"552459","tradeTypeCode":"","unitType":2,"verifyMessage":"","verifyTime":"2018-06-01 17:51:06","verifyUserName":"平台管理"}
         * createTime : 2018-05-07 16:21:24
         * orgEntity : {"adminUserId":"993405421694017539","countStaff":0,"level":1,"orgCode":"c","orgId":"993405421694017538","topCompanyId":"993405421694017538"}
         * orgId : 993405421694017538
         */

        private AccountEntity accountEntity;
        private String adminUserId;
        private CompanyEntityBean companyEntity;
        private String createTime;
        private OrgEntityBean orgEntity;
        private String orgId;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public AccountEntity getAccountEntity() {
            return accountEntity;
        }

        public void setAccountEntity(AccountEntity accountEntity) {
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
             * realName : 林技师
             */

            private String realName;

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }
        }

        public static class CompanyEntityBean implements Serializable{
            /**
             * adminUserId : 993405421694017539
             * areaCode :
             * createTime : 2018-05-07 16:21:24
             * defaultAddress : 中润通财富(朝阳北路)
             * defaultLat : 39.925748
             * defaultLon : 116.608023
             * defaultPlaceCode : 3.11.1.5
             * intro : 哟哟哟哟哟哟
             * legalName : 哦了
             * licenseCode : 421529465852
             * licensePic : 4435bb71736a4054a0ef95af15ca405b.png
             * logoPic : 4435bb71736a4054a0ef95af15ca405b.png
             * name : 北京修监控科技
             * officeAddress : 中润通财富(朝阳北路)
             * orgId : 993405421694017538
             * registerAssets : 500
             * scale : 0
             * status : 2
             * telPhone : 552459
             * tradeTypeCode :
             * unitType : 2
             * verifyMessage :
             * verifyTime : 2018-06-01 17:51:06
             * verifyUserName : 平台管理
             */

            private String adminUserId;
            private String areaCode;
            private String createTime;
            private String defaultAddress;
            private String defaultLat;
            private String defaultLon;
            private String defaultPlaceCode;
            private String intro;
            private String legalName;
            private String licenseCode;
            private String licensePic;
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
            private String verifyMessage;
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

            public String getVerifyMessage() {
                return verifyMessage;
            }

            public void setVerifyMessage(String verifyMessage) {
                this.verifyMessage = verifyMessage;
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

        public static class OrgEntityBean implements Serializable{
            /**
             * adminUserId : 993405421694017539
             * countStaff : 0
             * level : 1
             * orgCode : c
             * orgId : 993405421694017538
             * topCompanyId : 993405421694017538
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
