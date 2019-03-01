package com.yaf.base.entity;


import java.util.List;

/**
 * Created by Our on 2019/1/29.
 */

public class AnswerListWithQuestionBean {


        private QuestionBean question;
        private List<AnswersBean> answers;

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public List<AnswersBean> getAnswers() {
            return answers;
        }

        public void setAnswers(List<AnswersBean> answers) {
            this.answers = answers;
        }

        public static class QuestionBean {
            /**
             * accountEntity : {"accId":"980732504676810754","accType":5,"address":"聚朋友棋牌室(朝阳北路定福家园南里2号院3号楼底商附近)","areaCode":"3.11.1.1","avatar":"account/ae89cce13cc34856af5fc3c26847ca3d.png","email":"","gender":1,"idCard":"410926199005104491","lastLoginTime":"2019-02-16 06:36:37","loginCount":168,"mobile":"18519005131","mpFrom":-1,"nickName":"赵子武","openId":"1","passwd":"aK64wClE5PUBqWeyYSXunazwftw=","qrCode":"account/qr/de0182fb1ae541bba5e9d088199f9434.png","rcloudToken":"FcPTBWUNI4d6aVaNce3G8X7s2tSbN+XvbDqPHzdyLLUtcCQXSPO4lHO+5vbzX3xuayDoNTQV4R9zcn6LLCC6h3dxVT7rVHBDRUzCTbOVIS4=","realName":"赵子武","regTime":"2018-04-02 17:03:45","simplePwd":false,"status":0}
             * businessName : 电视监控
             * businessOneCode : 1.1
             * dataCode : 1.1.1.2
             * deviceFailureId : 1025400823779540994
             * failureTypeId : 1025400823779540994
             * modelCode : 5.1.21
             * modelName : 飞利浦（Philips）
             * questionCompanyId : 979995434422681602
             * questionContent : 罢了
             * questionCreateDate : 2019-01-26 03:53:13
             * questionCreateDateLong : 1548445993000
             * questionId : 1
             * questionPics : online/fc93c7e327914bc18567c7276f75269f.png,online/66db1245f9464e96a5935a898fe70258.png
             * questionSketch : 无视频信号
             * questionStatus : 0
             * questionTopCompanyId : 979995434422681602
             * questionUserId : 1049116901888790530
             * questionViewCount : 70
             */

            private AccountEntityBean accountEntity;
            private String businessName;
            private String businessOneCode;
            private String dataCode;
            private String deviceFailureId;
            private String failureTypeId;
            private String modelCode;
            private String modelName;
            private String questionCompanyId;
            private String questionContent;
            private String questionCreateDate;
            private long questionCreateDateLong;
            private int questionId;
            private String questionPics;
            private String questionSketch;
            private int questionStatus;
            private String questionTopCompanyId;
            private String questionUserId;
            private int questionViewCount;

            public AccountEntityBean getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountEntityBean accountEntity) {
                this.accountEntity = accountEntity;
            }

            public String getBusinessName() {
                return businessName;
            }

            public void setBusinessName(String businessName) {
                this.businessName = businessName;
            }

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

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
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

            public long getQuestionCreateDateLong() {
                return questionCreateDateLong;
            }

            public void setQuestionCreateDateLong(long questionCreateDateLong) {
                this.questionCreateDateLong = questionCreateDateLong;
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

            public static class AccountEntityBean {
                /**
                 * accId : 980732504676810754
                 * accType : 5
                 * address : 聚朋友棋牌室(朝阳北路定福家园南里2号院3号楼底商附近)
                 * areaCode : 3.11.1.1
                 * avatar : account/ae89cce13cc34856af5fc3c26847ca3d.png
                 * email :
                 * gender : 1
                 * idCard : 410926199005104491
                 * lastLoginTime : 2019-02-16 06:36:37
                 * loginCount : 168
                 * mobile : 18519005131
                 * mpFrom : -1
                 * nickName : 赵子武
                 * openId : 1
                 * passwd : aK64wClE5PUBqWeyYSXunazwftw=
                 * qrCode : account/qr/de0182fb1ae541bba5e9d088199f9434.png
                 * rcloudToken : FcPTBWUNI4d6aVaNce3G8X7s2tSbN+XvbDqPHzdyLLUtcCQXSPO4lHO+5vbzX3xuayDoNTQV4R9zcn6LLCC6h3dxVT7rVHBDRUzCTbOVIS4=
                 * realName : 赵子武
                 * regTime : 2018-04-02 17:03:45
                 * simplePwd : false
                 * status : 0
                 */

                private String accId;
                private int accType;
                private String address;
                private String areaCode;
                private String avatar;
                private String email;
                private int gender;
                private String idCard;
                private String lastLoginTime;
                private int loginCount;
                private String mobile;
                private int mpFrom;
                private String nickName;
                private String openId;
                private String passwd;
                private String qrCode;
                private String rcloudToken;
                private String realName;
                private String regTime;
                private boolean simplePwd;
                private int status;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getAccType() {
                    return accType;
                }

                public void setAccType(int accType) {
                    this.accType = accType;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getAreaCode() {
                    return areaCode;
                }

                public void setAreaCode(String areaCode) {
                    this.areaCode = areaCode;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public String getIdCard() {
                    return idCard;
                }

                public void setIdCard(String idCard) {
                    this.idCard = idCard;
                }

                public String getLastLoginTime() {
                    return lastLoginTime;
                }

                public void setLastLoginTime(String lastLoginTime) {
                    this.lastLoginTime = lastLoginTime;
                }

                public int getLoginCount() {
                    return loginCount;
                }

                public void setLoginCount(int loginCount) {
                    this.loginCount = loginCount;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getMpFrom() {
                    return mpFrom;
                }

                public void setMpFrom(int mpFrom) {
                    this.mpFrom = mpFrom;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getOpenId() {
                    return openId;
                }

                public void setOpenId(String openId) {
                    this.openId = openId;
                }

                public String getPasswd() {
                    return passwd;
                }

                public void setPasswd(String passwd) {
                    this.passwd = passwd;
                }

                public String getQrCode() {
                    return qrCode;
                }

                public void setQrCode(String qrCode) {
                    this.qrCode = qrCode;
                }

                public String getRcloudToken() {
                    return rcloudToken;
                }

                public void setRcloudToken(String rcloudToken) {
                    this.rcloudToken = rcloudToken;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getRegTime() {
                    return regTime;
                }

                public void setRegTime(String regTime) {
                    this.regTime = regTime;
                }

                public boolean isSimplePwd() {
                    return simplePwd;
                }

                public void setSimplePwd(boolean simplePwd) {
                    this.simplePwd = simplePwd;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }

        public static class AnswersBean {
            /**
             * answerCompanyId : 0
             * answerContent : nt00110101
             * answerCreateTime : 2019-01-28 11:39:15
             * answerCreateTimeLong : 1548646755000
             * answerForwardNums : 1
             * answerId : 1
             * answerLikes : 0
             * answerStatus : 0
             * answerTopCompanyId : 0
             * answerUserId : 1057887495614709762
             * expertsCertificationEntity : {"accId":"1057887495589543937","approveTime":"2019-02-22 16:20:17","approveType":0,"approveUserName":"管理员","avatarPhoto":"145bbddd9890479a931005a7bd7da828.png","brandName":"testBrand0001","company":"水康威看","createTime":"2019-01-16 18:13:16","expertName":"测试专家001","favorableRate":0.88,"gender":1,"id":9,"idCard":"110101199105051211","idCardFront":"feb2da277dc647ddb9d7bfaff9919296.png","idCardHand":"145bbddd9890479a931005a7bd7da828.png","idCardSide":"b265cb5269df4a5ab7fce6b7e2f79367.png","impowerUrl":"account/3750d8072f264aa49bafce3f7a2be35d.png","intro":"ceshiintrp0001","jobLce":1,"jobLevel":1,"payAccount":"321321","payType":2,"phonenumber":"14796325896","price":20,"status":2,"systemType":"防盗报警,可视对讲","updateTime":"2019-01-17 09:41:18","userId":"1057887495614709762","workingAge":0}
             * likeStatus : 1
             * questionCompanyId : 979995434422681602
             * questionId : 1
             * questionTopCompanyId : 979995434422681602
             * questionUserId : 1049116901888790530
             * replyCount : 5
             * answerPics : aaa.img
             */

            private int answerCompanyId;
            private String answerContent;
            private String answerCreateTime;
            private long answerCreateTimeLong;
            private int answerForwardNums;
            private int answerId;
            private int answerLikes;
            private int answerStatus;
            private int answerTopCompanyId;
            private String answerUserId;
            private ExpertsCertificationEntityBean expertsCertificationEntity;
            private int likeStatus;
            private String questionCompanyId;
            private int questionId;
            private String questionTopCompanyId;
            private String questionUserId;
            private int replyCount;
            private String answerPics;

            public int getAnswerCompanyId() {
                return answerCompanyId;
            }

            public void setAnswerCompanyId(int answerCompanyId) {
                this.answerCompanyId = answerCompanyId;
            }

            public String getAnswerContent() {
                return answerContent;
            }

            public void setAnswerContent(String answerContent) {
                this.answerContent = answerContent;
            }

            public String getAnswerCreateTime() {
                return answerCreateTime;
            }

            public void setAnswerCreateTime(String answerCreateTime) {
                this.answerCreateTime = answerCreateTime;
            }

            public long getAnswerCreateTimeLong() {
                return answerCreateTimeLong;
            }

            public void setAnswerCreateTimeLong(long answerCreateTimeLong) {
                this.answerCreateTimeLong = answerCreateTimeLong;
            }

            public int getAnswerForwardNums() {
                return answerForwardNums;
            }

            public void setAnswerForwardNums(int answerForwardNums) {
                this.answerForwardNums = answerForwardNums;
            }

            public int getAnswerId() {
                return answerId;
            }

            public void setAnswerId(int answerId) {
                this.answerId = answerId;
            }

            public int getAnswerLikes() {
                return answerLikes;
            }

            public void setAnswerLikes(int answerLikes) {
                this.answerLikes = answerLikes;
            }

            public int getAnswerStatus() {
                return answerStatus;
            }

            public void setAnswerStatus(int answerStatus) {
                this.answerStatus = answerStatus;
            }

            public int getAnswerTopCompanyId() {
                return answerTopCompanyId;
            }

            public void setAnswerTopCompanyId(int answerTopCompanyId) {
                this.answerTopCompanyId = answerTopCompanyId;
            }

            public String getAnswerUserId() {
                return answerUserId;
            }

            public void setAnswerUserId(String answerUserId) {
                this.answerUserId = answerUserId;
            }

            public ExpertsCertificationEntityBean getExpertsCertificationEntity() {
                return expertsCertificationEntity;
            }

            public void setExpertsCertificationEntity(ExpertsCertificationEntityBean expertsCertificationEntity) {
                this.expertsCertificationEntity = expertsCertificationEntity;
            }

            public int getLikeStatus() {
                return likeStatus;
            }

            public void setLikeStatus(int likeStatus) {
                this.likeStatus = likeStatus;
            }

            public String getQuestionCompanyId() {
                return questionCompanyId;
            }

            public void setQuestionCompanyId(String questionCompanyId) {
                this.questionCompanyId = questionCompanyId;
            }

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
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

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public String getAnswerPics() {
                return answerPics;
            }

            public void setAnswerPics(String answerPics) {
                this.answerPics = answerPics;
            }

            public static class ExpertsCertificationEntityBean {
                /**
                 * accId : 1057887495589543937
                 * approveTime : 2019-02-22 16:20:17
                 * approveType : 0
                 * approveUserName : 管理员
                 * avatarPhoto : 145bbddd9890479a931005a7bd7da828.png
                 * brandName : testBrand0001
                 * company : 水康威看
                 * createTime : 2019-01-16 18:13:16
                 * expertName : 测试专家001
                 * favorableRate : 0.88
                 * gender : 1
                 * id : 9
                 * idCard : 110101199105051211
                 * idCardFront : feb2da277dc647ddb9d7bfaff9919296.png
                 * idCardHand : 145bbddd9890479a931005a7bd7da828.png
                 * idCardSide : b265cb5269df4a5ab7fce6b7e2f79367.png
                 * impowerUrl : account/3750d8072f264aa49bafce3f7a2be35d.png
                 * intro : ceshiintrp0001
                 * jobLce : 1
                 * jobLevel : 1
                 * payAccount : 321321
                 * payType : 2
                 * phonenumber : 14796325896
                 * price : 20
                 * status : 2
                 * systemType : 防盗报警,可视对讲
                 * updateTime : 2019-01-17 09:41:18
                 * userId : 1057887495614709762
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
                private double favorableRate;
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

                public double getFavorableRate() {
                    return favorableRate;
                }

                public void setFavorableRate(double favorableRate) {
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
