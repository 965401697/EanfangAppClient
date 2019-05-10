package com.yaf.base.entity;

public class ZjZgBean {

    /**
     * accId : 1064451202649960449
     * answerCount : 0
     * approveTime : 2019-04-29 12:55:08
     * expertName : aaa
     * generalEvaluation : 1
     * id : 17
     * item1 : 5
     * item2 : 5
     * item3 : 5
     * item4 : 5
     * item5 : 5
     * phonenumber : 18600000002
     * status : 0
     * userId : 1064451202679320578
     * verifyOrg : 专家认定单位
     * verifyPicUrl : default/default_avatar.png
     */

    private String accId;
    private int answerCount;
    private String approveTime;
    private String expertName;
    private int generalEvaluation;
    private int id;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private String phonenumber;
    private int status;
    private String userId;
    private String verifyOrg;
    private String verifyPicUrl;

    @Override
    public String toString() {
        return "ZjZgBean{" +
                "accId='" + accId + '\'' +
                ", answerCount=" + answerCount +
                ", approveTime='" + approveTime + '\'' +
                ", expertName='" + expertName + '\'' +
                ", generalEvaluation=" + generalEvaluation +
                ", id=" + id +
                ", item1=" + item1 +
                ", item2=" + item2 +
                ", item3=" + item3 +
                ", item4=" + item4 +
                ", item5=" + item5 +
                ", phonenumber='" + phonenumber + '\'' +
                ", status=" + status +
                ", userId='" + userId + '\'' +
                ", verifyOrg='" + verifyOrg + '\'' +
                ", verifyPicUrl='" + verifyPicUrl + '\'' +
                '}';
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public int getGeneralEvaluation() {
        return generalEvaluation;
    }

    public void setGeneralEvaluation(int generalEvaluation) {
        this.generalEvaluation = generalEvaluation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVerifyOrg() {
        return verifyOrg;
    }

    public void setVerifyOrg(String verifyOrg) {
        this.verifyOrg = verifyOrg;
    }

    public String getVerifyPicUrl() {
        return verifyPicUrl;
    }

    public void setVerifyPicUrl(String verifyPicUrl) {
        this.verifyPicUrl = verifyPicUrl;
    }
}
