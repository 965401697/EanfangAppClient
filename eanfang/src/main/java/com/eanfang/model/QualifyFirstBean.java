package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author guanluocang
 * @data 2018/10/26
 * @description
 */

public class QualifyFirstBean implements Serializable {

    /**
     * company2baseDataList : []
     * orgUnit : {"shopCompanyEntity":{"adminUserId":"1052858847536410627","createTime":"2018-10-19 06:48:05","designCount":0,"evaluateNum":0,"generalEvaluation":1,"goodNum":0,"goodRate":10000,"installCount":0,"isManufacturer":0,"maintainCount":0,"orgId":"1052858847536410626","publicPraise":100,"receiveCount":0,"repairCount":0,"showWww":1,"workingLevel":1,"workingYear":1}}
     */

    private OrgUnitBean orgUnit;
    private List<?> company2baseDataList;

    public OrgUnitBean getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitBean orgUnit) {
        this.orgUnit = orgUnit;
    }

    public List<?> getCompany2baseDataList() {
        return company2baseDataList;
    }

    public void setCompany2baseDataList(List<?> company2baseDataList) {
        this.company2baseDataList = company2baseDataList;
    }

    public static class OrgUnitBean {
        /**
         * shopCompanyEntity : {"adminUserId":"1052858847536410627","createTime":"2018-10-19 06:48:05","designCount":0,"evaluateNum":0,"generalEvaluation":1,"goodNum":0,"goodRate":10000,"installCount":0,"isManufacturer":0,"maintainCount":0,"orgId":"1052858847536410626","publicPraise":100,"receiveCount":0,"repairCount":0,"showWww":1,"workingLevel":1,"workingYear":1}
         */

        private ShopCompanyEntityBean shopCompanyEntity;

        public ShopCompanyEntityBean getShopCompanyEntity() {
            return shopCompanyEntity;
        }

        public void setShopCompanyEntity(ShopCompanyEntityBean shopCompanyEntity) {
            this.shopCompanyEntity = shopCompanyEntity;
        }

        public static class ShopCompanyEntityBean {
            /**
             * adminUserId : 1052858847536410627
             * createTime : 2018-10-19 06:48:05
             * designCount : 0
             * evaluateNum : 0
             * generalEvaluation : 1
             * goodNum : 0
             * goodRate : 10000
             * installCount : 0
             * isManufacturer : 0
             * maintainCount : 0
             * orgId : 1052858847536410626
             * publicPraise : 100
             * receiveCount : 0
             * repairCount : 0
             * showWww : 1
             * workingLevel : 1
             * workingYear : 1
             */

            private String adminUserId;
            private String createTime;
            private int designCount;
            private int evaluateNum;
            private int generalEvaluation;
            private int goodNum;
            private int goodRate;
            private int installCount;
            private int isManufacturer;
            private int maintainCount;
            private String orgId;
            private int publicPraise;
            private int receiveCount;
            private int repairCount;
            private int showWww;
            private int workingLevel;
            private int workingYear;

            public String getAdminUserId() {
                return adminUserId;
            }

            public void setAdminUserId(String adminUserId) {
                this.adminUserId = adminUserId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getDesignCount() {
                return designCount;
            }

            public void setDesignCount(int designCount) {
                this.designCount = designCount;
            }

            public int getEvaluateNum() {
                return evaluateNum;
            }

            public void setEvaluateNum(int evaluateNum) {
                this.evaluateNum = evaluateNum;
            }

            public int getGeneralEvaluation() {
                return generalEvaluation;
            }

            public void setGeneralEvaluation(int generalEvaluation) {
                this.generalEvaluation = generalEvaluation;
            }

            public int getGoodNum() {
                return goodNum;
            }

            public void setGoodNum(int goodNum) {
                this.goodNum = goodNum;
            }

            public int getGoodRate() {
                return goodRate;
            }

            public void setGoodRate(int goodRate) {
                this.goodRate = goodRate;
            }

            public int getInstallCount() {
                return installCount;
            }

            public void setInstallCount(int installCount) {
                this.installCount = installCount;
            }

            public int getIsManufacturer() {
                return isManufacturer;
            }

            public void setIsManufacturer(int isManufacturer) {
                this.isManufacturer = isManufacturer;
            }

            public int getMaintainCount() {
                return maintainCount;
            }

            public void setMaintainCount(int maintainCount) {
                this.maintainCount = maintainCount;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public int getPublicPraise() {
                return publicPraise;
            }

            public void setPublicPraise(int publicPraise) {
                this.publicPraise = publicPraise;
            }

            public int getReceiveCount() {
                return receiveCount;
            }

            public void setReceiveCount(int receiveCount) {
                this.receiveCount = receiveCount;
            }

            public int getRepairCount() {
                return repairCount;
            }

            public void setRepairCount(int repairCount) {
                this.repairCount = repairCount;
            }

            public int getShowWww() {
                return showWww;
            }

            public void setShowWww(int showWww) {
                this.showWww = showWww;
            }

            public int getWorkingLevel() {
                return workingLevel;
            }

            public void setWorkingLevel(int workingLevel) {
                this.workingLevel = workingLevel;
            }

            public int getWorkingYear() {
                return workingYear;
            }

            public void setWorkingYear(int workingYear) {
                this.workingYear = workingYear;
            }
        }
    }
}
