package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/5/5.
 */

public class MaintenanceBean implements Serializable {


    /**
     * clientCompanyName : 北京物美
     * cycle : 0
     * clientUserName : 刘妹妹
     * clientUserPhone : 1397989549
     * maintainDetails : [{"businessFourCode":"1.1","count":5,"installPosition":"物美楼上","maintainLevel":1,"checkResult":0,"question":"涨了","cause":"有人风投","solution":"跟风","pictures":"d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png,"}]
     */

    private String clientCompanyName;
    private int cycle;
    private String clientUserName;
    private String clientUserPhone;
    private List<MaintainDetailsBean> maintainDetails;

    public String getClientCompanyName() {
        return clientCompanyName;
    }

    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getClientUserPhone() {
        return clientUserPhone;
    }

    public void setClientUserPhone(String clientUserPhone) {
        this.clientUserPhone = clientUserPhone;
    }

    public List<MaintainDetailsBean> getMaintainDetails() {
        return maintainDetails;
    }

    public void setMaintainDetails(List<MaintainDetailsBean> maintainDetails) {
        this.maintainDetails = maintainDetails;
    }

    public static class MaintainDetailsBean implements Serializable {
        /**
         * businessFourCode : 1.1
         * count : 5
         * installPosition : 物美楼上
         * maintainLevel : 1
         * checkResult : 0
         * question : 涨了
         * cause : 有人风投
         * solution : 跟风
         * pictures : d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png,
         */

        private String businessFourCode;
        private String businessThreeCode;
        private int count;
        private String installPosition;
        private int maintainLevel;
        private int checkResult;
        private String question;
        private String cause;
        private String solution;
        private String pictures;

        public String getBusinessThreeCode() {
            return businessThreeCode;
        }

        public void setBusinessThreeCode(String businessThreeCode) {
            this.businessThreeCode = businessThreeCode;
        }

        public String getBusinessFourCode() {
            return businessFourCode;
        }

        public void setBusinessFourCode(String businessFourCode) {
            this.businessFourCode = businessFourCode;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getInstallPosition() {
            return installPosition;
        }

        public void setInstallPosition(String installPosition) {
            this.installPosition = installPosition;
        }

        public int getMaintainLevel() {
            return maintainLevel;
        }

        public void setMaintainLevel(int maintainLevel) {
            this.maintainLevel = maintainLevel;
        }

        public int getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(int checkResult) {
            this.checkResult = checkResult;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }
    }
}
