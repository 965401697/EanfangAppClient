package net.eanfang.client.ui.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/12.
 */

public class SelectWorkerBean implements Serializable {

        /**
         * accId : 936487014348365825
         * accountEntity : {"accId":"936487014348365825","avatar":"0","realName":"张三","subsystemAdmin":false,"superAdmin":false}
         * companyEntity : {"orgName":"家乐福双井公司"}
         * goodRate : 100
         * id : 6
         * lat : 39.929004
         * lon : 116.575179
         * placeCode : 3.11.1.5
         * publicPraise : 0
         * verifyEntity : {"accId":"936487014348365825","realName":"张三","userId":"936487014465806338","workingYear":"5"}
         * verifyId : 5
         */

        private Long accId;
        private AccountEntityBean accountEntity;
        private CompanyEntityBean companyEntity;
        private int goodRate;
        private Long id;
        private String lat;
        private String lon;
        private String placeCode;
        private int publicPraise;
        private VerifyEntityBean verifyEntity;
        private Long verifyId;

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

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public int getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(int goodRate) {
            this.goodRate = goodRate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getPlaceCode() {
            return placeCode;
        }

        public void setPlaceCode(String placeCode) {
            this.placeCode = placeCode;
        }

        public int getPublicPraise() {
            return publicPraise;
        }

        public void setPublicPraise(int publicPraise) {
            this.publicPraise = publicPraise;
        }

        public VerifyEntityBean getVerifyEntity() {
            return verifyEntity;
        }

        public void setVerifyEntity(VerifyEntityBean verifyEntity) {
            this.verifyEntity = verifyEntity;
        }

        public Long getVerifyId() {
            return verifyId;
        }

        public void setVerifyId(Long verifyId) {
            this.verifyId = verifyId;
        }

        public static class AccountEntityBean implements Serializable{
            /**
             * accId : 936487014348365825
             * avatar : 0
             * realName : 张三
             * subsystemAdmin : false
             * superAdmin : false
             */

            private Long accId;
            private String avatar;
            private String realName;
            private boolean subsystemAdmin;
            private boolean superAdmin;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
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

        public static class CompanyEntityBean implements Serializable{
            /**
             * orgName : 家乐福双井公司
             */

            private String orgName;

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }
        }

        public static class VerifyEntityBean implements Serializable{
            /**
             * accId : 936487014348365825
             * realName : 张三
             * userId : 936487014465806338
             * workingYear : 5
             */

            private Long accId;
            private String realName;
            private Long userId;
            private String workingYear;

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

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public String getWorkingYear() {
                return workingYear;
            }

            public void setWorkingYear(String workingYear) {
                this.workingYear = workingYear;
            }
        }
    }

