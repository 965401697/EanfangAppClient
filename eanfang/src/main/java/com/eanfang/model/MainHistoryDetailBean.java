package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/6/17.
 */

public class MainHistoryDetailBean implements Serializable {


    /**
     * clientCompanyName : 北京物美
     * clientUserName : 刘妹妹
     * clientUserPhone : 1397989549
     * createCompanyId : 1100
     * createOrgCode : c.c1.2
     * createTime : 2017-12-19 10:55:43
     * createTopCompanyId : 1100
     * createUserId : 2
     * cycle : 0
     * id : 942951548685230082
     * maintainDetails : [{"businessFourCode":"1.1","cause":"有人风投","checkResult":0,"count":5,"entMaintainId":"942951548685230082","id":"942951548844613633","installPosition":"物美楼上","maintainLevel":1,"pictures":"d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png,","question":"涨了","solution":"跟风"}]
     * orderNum : MO201712190058
     * status : 0
     */

    private String clientCompanyName;
    private String clientUserName;
    private String clientUserPhone;
    private Long createCompanyId;
    private String createOrgCode;
    private String createTime;
    private int createTopCompanyId;
    private Long createUserId;
    private int cycle;
    private Long id;
    private String orderNum;
    private int status;
    private List<MaintainDetailsBean> maintainDetails;

    public String getClientCompanyName() {
        return clientCompanyName;
    }

    public void setClientCompanyName(String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
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

    public int getCreateTopCompanyId() {
        return createTopCompanyId;
    }

    public void setCreateTopCompanyId(int createTopCompanyId) {
        this.createTopCompanyId = createTopCompanyId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
         * cause : 有人风投
         * checkResult : 0
         * count : 5
         * entMaintainId : 942951548685230082
         * id : 942951548844613633
         * installPosition : 物美楼上
         * maintainLevel : 1
         * pictures : d4f137aa51fa4f4aad86a8b26f20c7c6.png,50a10dc744c541d6a690d26b17a6f7da.png,
         * question : 涨了
         * solution : 跟风
         */

        private String businessFourCode;
        private String cause;
        private int checkResult;
        private int count;
        private String entMaintainId;
        private Long id;
        private String installPosition;
        private int maintainLevel;
        private String pictures;
        private String question;
        private String solution;

        public String getBusinessFourCode() {
            return businessFourCode;
        }

        public void setBusinessFourCode(String businessFourCode) {
            this.businessFourCode = businessFourCode;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public int getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(int checkResult) {
            this.checkResult = checkResult;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getEntMaintainId() {
            return entMaintainId;
        }

        public void setEntMaintainId(String entMaintainId) {
            this.entMaintainId = entMaintainId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getSolution() {
            return solution;
        }

        public void setSolution(String solution) {
            this.solution = solution;
        }
    }
}

