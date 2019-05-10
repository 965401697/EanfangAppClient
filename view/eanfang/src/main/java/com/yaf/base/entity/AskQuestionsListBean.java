package com.yaf.base.entity;


import java.util.List;

/**
 * Created by Our on 2019/1/29.
 */

public class AskQuestionsListBean {


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
                 * businessOneCode : 1.1
                 * dataCode : 1.1.1.9
                 * deviceFailureId : 1025400829223747586
                 * expertsCertification : {"accId":"980732504676810754","approveTime":"2019-02-22 16:20:02","approveType":0,"approveUserName":"管理员","avatarPhoto":"account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png","brandName":"啊啊啊","company":"海康威视","createTime":"2019-01-16 10:39:56","expertName":"赵子武","favorableRate":1,"gender":1,"id":8,"idCard":"110101199105051235","idCardFront":"avatar/30766a1474a447668771c985301e0ab3.jpg","idCardHand":"avatar/30766a1474a447668771c985301e0ab3.jpg","idCardSide":"avatar/30766a1474a447668771c985301e0ab3.jpg","impowerUrl":"account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png","intro":"再找找","jobLce":1,"jobLevel":1,"payAccount":"11111111","payType":1,"phonenumber":"12312312345","price":10,"responsibleBrand":"AB,JVC,东芝（Toshiba）","status":0,"systemType":"电视监控,防盗报警,可视对讲,公共广播,其他","updateTime":"2019-01-30 06:42:13","userId":"980732504693587970","workingAge":0}
                 * failureTypeId : 1025400823779540994
                 * modelCode : 5.1.21
                 * questionCompanyId : 979995434422681602
                 * questionContent : 罢了哦哦
                 * questionCreateDate : 2019-01-26 03:54:45
                 * questionId : 2
                 * questionPics : online/11428d3e21fc43ecae8da22e5fb4a2b8.png
                 * questionSketch : 黑屏
                 * questionStatus : 0
                 * questionTopCompanyId : 979995434422681602
                 * questionUserId : 1049116901888790530
                 * questionViewCount : 139
                 */

                private String businessOneCode;
                private String dataCode;
                private String deviceFailureId;
                private ExpertsCertificationBean expertsCertification;
                private String failureTypeId;
                private String modelCode;
                private String questionCompanyId;
                private String questionContent;
                private String questionCreateDate;
                private int questionId;
                private String questionPics;
                private String questionSketch;
                private int questionStatus;
                private String questionTopCompanyId;
                private String questionUserId;
                private int questionViewCount;

                public String getBusinessOneCode() {
                    return businessOneCode;
                }

                public void setBusinessOneCode(String businessOneCode) {
                    this.businessOneCode = businessOneCode;
                }

                public String getDataCode() {
                    return dataCode;
                }

                public void setDataCode(String dataCode) {
                    this.dataCode = dataCode;
                }

                public String getDeviceFailureId() {
                    return deviceFailureId;
                }

                public void setDeviceFailureId(String deviceFailureId) {
                    this.deviceFailureId = deviceFailureId;
                }

                public ExpertsCertificationBean getExpertsCertification() {
                    return expertsCertification;
                }

                public void setExpertsCertification(ExpertsCertificationBean expertsCertification) {
                    this.expertsCertification = expertsCertification;
                }

                public String getFailureTypeId() {
                    return failureTypeId;
                }

                public void setFailureTypeId(String failureTypeId) {
                    this.failureTypeId = failureTypeId;
                }

                public String getModelCode() {
                    return modelCode;
                }

                public void setModelCode(String modelCode) {
                    this.modelCode = modelCode;
                }

                public String getQuestionCompanyId() {
                    return questionCompanyId;
                }

                public void setQuestionCompanyId(String questionCompanyId) {
                    this.questionCompanyId = questionCompanyId;
                }

                public String getQuestionContent() {
                    return questionContent;
                }

                public void setQuestionContent(String questionContent) {
                    this.questionContent = questionContent;
                }

                public String getQuestionCreateDate() {
                    return questionCreateDate;
                }

                public void setQuestionCreateDate(String questionCreateDate) {
                    this.questionCreateDate = questionCreateDate;
                }

                public int getQuestionId() {
                    return questionId;
                }

                public void setQuestionId(int questionId) {
                    this.questionId = questionId;
                }

                public String getQuestionPics() {
                    return questionPics;
                }

                public void setQuestionPics(String questionPics) {
                    this.questionPics = questionPics;
                }

                public String getQuestionSketch() {
                    return questionSketch;
                }

                public void setQuestionSketch(String questionSketch) {
                    this.questionSketch = questionSketch;
                }

                public int getQuestionStatus() {
                    return questionStatus;
                }

                public void setQuestionStatus(int questionStatus) {
                    this.questionStatus = questionStatus;
                }

                public String getQuestionTopCompanyId() {
                    return questionTopCompanyId;
                }

                public void setQuestionTopCompanyId(String questionTopCompanyId) {
                    this.questionTopCompanyId = questionTopCompanyId;
                }

                public String getQuestionUserId() {
                    return questionUserId;
                }

                public void setQuestionUserId(String questionUserId) {
                    this.questionUserId = questionUserId;
                }

                public int getQuestionViewCount() {
                    return questionViewCount;
                }

                public void setQuestionViewCount(int questionViewCount) {
                    this.questionViewCount = questionViewCount;
                }

                public static class ExpertsCertificationBean {
                    /**
                     * accId : 980732504676810754
                     * approveTime : 2019-02-22 16:20:02
                     * approveType : 0
                     * approveUserName : 管理员
                     * avatarPhoto : account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png
                     * brandName : 啊啊啊
                     * company : 海康威视
                     * createTime : 2019-01-16 10:39:56
                     * expertName : 赵子武
                     * favorableRate : 1
                     * gender : 1
                     * id : 8
                     * idCard : 110101199105051235
                     * idCardFront : avatar/30766a1474a447668771c985301e0ab3.jpg
                     * idCardHand : avatar/30766a1474a447668771c985301e0ab3.jpg
                     * idCardSide : avatar/30766a1474a447668771c985301e0ab3.jpg
                     * impowerUrl : account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png
                     * intro : 再找找
                     * jobLce : 1
                     * jobLevel : 1
                     * payAccount : 11111111
                     * payType : 1
                     * phonenumber : 12312312345
                     * price : 10
                     * responsibleBrand : AB,JVC,东芝（Toshiba）
                     * status : 0
                     * systemType : 电视监控,防盗报警,可视对讲,公共广播,其他
                     * updateTime : 2019-01-30 06:42:13
                     * userId : 980732504693587970
                     * workingAge : 0
                     */

                    private String accId;
                    private String approveTime;
                    private int approveType;
                    private String approveUserName;
                    private String avatarPhoto;
                    private String brandName;
                    private String company;
                    private String createTime;
                    private String expertName;
                    private int favorableRate;
                    private int gender;
                    private int id;
                    private String idCard;
                    private String idCardFront;
                    private String idCardHand;
                    private String idCardSide;
                    private String impowerUrl;
                    private String intro;
                    private int jobLce;
                    private int jobLevel;
                    private String payAccount;
                    private int payType;
                    private String phonenumber;
                    private int price;
                    private String responsibleBrand;
                    private int status;
                    private String systemType;
                    private String updateTime;
                    private String userId;
                    private int workingAge;

                    public String getAccId() {
                        return accId;
                    }

                    public void setAccId(String accId) {
                        this.accId = accId;
                    }

                    public String getApproveTime() {
                        return approveTime;
                    }

                    public void setApproveTime(String approveTime) {
                        this.approveTime = approveTime;
                    }

                    public int getApproveType() {
                        return approveType;
                    }

                    public void setApproveType(int approveType) {
                        this.approveType = approveType;
                    }

                    public String getApproveUserName() {
                        return approveUserName;
                    }

                    public void setApproveUserName(String approveUserName) {
                        this.approveUserName = approveUserName;
                    }

                    public String getAvatarPhoto() {
                        return avatarPhoto;
                    }

                    public void setAvatarPhoto(String avatarPhoto) {
                        this.avatarPhoto = avatarPhoto;
                    }

                    public String getBrandName() {
                        return brandName;
                    }

                    public void setBrandName(String brandName) {
                        this.brandName = brandName;
                    }

                    public String getCompany() {
                        return company;
                    }

                    public void setCompany(String company) {
                        this.company = company;
                    }

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getExpertName() {
                        return expertName;
                    }

                    public void setExpertName(String expertName) {
                        this.expertName = expertName;
                    }

                    public int getFavorableRate() {
                        return favorableRate;
                    }

                    public void setFavorableRate(int favorableRate) {
                        this.favorableRate = favorableRate;
                    }

                    public int getGender() {
                        return gender;
                    }

                    public void setGender(int gender) {
                        this.gender = gender;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getIdCard() {
                        return idCard;
                    }

                    public void setIdCard(String idCard) {
                        this.idCard = idCard;
                    }

                    public String getIdCardFront() {
                        return idCardFront;
                    }

                    public void setIdCardFront(String idCardFront) {
                        this.idCardFront = idCardFront;
                    }

                    public String getIdCardHand() {
                        return idCardHand;
                    }

                    public void setIdCardHand(String idCardHand) {
                        this.idCardHand = idCardHand;
                    }

                    public String getIdCardSide() {
                        return idCardSide;
                    }

                    public void setIdCardSide(String idCardSide) {
                        this.idCardSide = idCardSide;
                    }

                    public String getImpowerUrl() {
                        return impowerUrl;
                    }

                    public void setImpowerUrl(String impowerUrl) {
                        this.impowerUrl = impowerUrl;
                    }

                    public String getIntro() {
                        return intro;
                    }

                    public void setIntro(String intro) {
                        this.intro = intro;
                    }

                    public int getJobLce() {
                        return jobLce;
                    }

                    public void setJobLce(int jobLce) {
                        this.jobLce = jobLce;
                    }

                    public int getJobLevel() {
                        return jobLevel;
                    }

                    public void setJobLevel(int jobLevel) {
                        this.jobLevel = jobLevel;
                    }

                    public String getPayAccount() {
                        return payAccount;
                    }

                    public void setPayAccount(String payAccount) {
                        this.payAccount = payAccount;
                    }

                    public int getPayType() {
                        return payType;
                    }

                    public void setPayType(int payType) {
                        this.payType = payType;
                    }

                    public String getPhonenumber() {
                        return phonenumber;
                    }

                    public void setPhonenumber(String phonenumber) {
                        this.phonenumber = phonenumber;
                    }

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public String getResponsibleBrand() {
                        return responsibleBrand;
                    }

                    public void setResponsibleBrand(String responsibleBrand) {
                        this.responsibleBrand = responsibleBrand;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getSystemType() {
                        return systemType;
                    }

                    public void setSystemType(String systemType) {
                        this.systemType = systemType;
                    }

                    public String getUpdateTime() {
                        return updateTime;
                    }

                    public void setUpdateTime(String updateTime) {
                        this.updateTime = updateTime;
                    }

                    public String getUserId() {
                        return userId;
                    }

                    public void setUserId(String userId) {
                        this.userId = userId;
                    }

                    public int getWorkingAge() {
                        return workingAge;
                    }

                    public void setWorkingAge(int workingAge) {
                        this.workingAge = workingAge;
                    }
                }
            }
        }
