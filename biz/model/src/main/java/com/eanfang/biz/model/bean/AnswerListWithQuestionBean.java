package com.eanfang.biz.model.bean;


import java.util.List;

/**
 * Created by Our on 2019/1/29.
 */


public class AnswerListWithQuestionBean {

        private QuestionBean question;
        private List<CommonAnswersBean> commonAnswers;
        private List<ExpertAnswersBean> expertAnswers;

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public List<CommonAnswersBean> getCommonAnswers() {
            return commonAnswers;
        }

        public void setCommonAnswers(List<CommonAnswersBean> commonAnswers) {
            this.commonAnswers = commonAnswers;
        }

        public List<ExpertAnswersBean> getExpertAnswers() {
            return expertAnswers;
        }

        public void setExpertAnswers(List<ExpertAnswersBean> expertAnswers) {
            this.expertAnswers = expertAnswers;
        }

        public static class QuestionBean {
            /**
             * accountEntity : {"accId":"1059326429620158466","accType":2,"address":"正兴烧烤(五里桥一街3号院1号楼-1层-101)","areaCode":"3.11.1.5","avatar":"account/72eb26c502ca4a6d88cd467773192f7d.png","email":"","gender":0,"idCard":"130322199507243525","isNew":0,"lastLoginTime":"2019-03-15 17:28:32","loginCount":152,"mobile":"18600000006","nickName":"test零七","passwd":"Bf50YcYHwzIpdy1AJQVgEBan0Oo=","qrCode":"account/qr/635b2cb12f4246bd9491fd674d11afe9.png","rcloudToken":"vBPuRDUaF4GQiuE/Pkh4CUxrRiQKPOlpqRODuAaxpdkYa9bWuJ/buZyvclKjJjfSRBHbT3Xc5RFQQf8bW7kKA/dRKEUj8hcxGkMnlZQKljvlJREgd7+tuA==","realName":"零六","regFrom":0,"regTime":"2018-11-05 14:07:57","simplePwd":false,"status":0}
             * businessName : 电视监控
             * businessOneCode : 1.1
             * dataCode : 1.1.1.3
             * deviceFailureId : 1025400841517252609
             * failureTypeId : 6.1
             * modelCode : 5.1.18
             * modelName : 迪威乐（Devele）
             * questionAnswerCount : 1
             * questionCompanyId : 1082922712729456641
             * questionContent : 来了
             * questionCreateDate : 2019-03-15 16:46:22
             * questionCreateDateLong : 1552639582000
             * questionId : 38
             * questionLikeCount : 0
             * questionPics : online/e7498367285e416f9c7697e11d3c25e3.png
             * questionSketch : 有竖道
             * questionStatus : 0
             * questionTopCompanyId : 1082922712729456641
             * questionUserId : 1082922712729456642
             * questionViewCount : 17
             * weight : 70
             */

            private AccountEntityBean accountEntity;
            private String businessName;
            private String businessOneCode;
            private String dataCode;
            private String deviceFailureId;
            private String failureTypeId;
            private String modelCode;
            private String modelName;
            private int questionAnswerCount;
            private String questionCompanyId;
            private String questionContent;
            private String questionCreateDate;
            private long questionCreateDateLong;
            private int questionId;
            private int questionLikeCount;
            private String questionPics;
            private String questionSketch;
            private int questionStatus;
            private String questionTopCompanyId;
            private String questionUserId;
            private int questionViewCount;
            private int weight;

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

            public int getQuestionAnswerCount() {
                return questionAnswerCount;
            }

            public void setQuestionAnswerCount(int questionAnswerCount) {
                this.questionAnswerCount = questionAnswerCount;
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

            public int getQuestionLikeCount() {
                return questionLikeCount;
            }

            public void setQuestionLikeCount(int questionLikeCount) {
                this.questionLikeCount = questionLikeCount;
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

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public static class AccountEntityBean {
                /**
                 * accId : 1059326429620158466
                 * accType : 2
                 * address : 正兴烧烤(五里桥一街3号院1号楼-1层-101)
                 * areaCode : 3.11.1.5
                 * avatar : account/72eb26c502ca4a6d88cd467773192f7d.png
                 * email :
                 * gender : 0
                 * idCard : 130322199507243525
                 * isNew : 0
                 * lastLoginTime : 2019-03-15 17:28:32
                 * loginCount : 152
                 * mobile : 18600000006
                 * nickName : test零七
                 * passwd : Bf50YcYHwzIpdy1AJQVgEBan0Oo=
                 * qrCode : account/qr/635b2cb12f4246bd9491fd674d11afe9.png
                 * rcloudToken : vBPuRDUaF4GQiuE/Pkh4CUxrRiQKPOlpqRODuAaxpdkYa9bWuJ/buZyvclKjJjfSRBHbT3Xc5RFQQf8bW7kKA/dRKEUj8hcxGkMnlZQKljvlJREgd7+tuA==
                 * realName : 零六
                 * regFrom : 0
                 * regTime : 2018-11-05 14:07:57
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
                private int isNew;
                private String lastLoginTime;
                private int loginCount;
                private String mobile;
                private String nickName;
                private String passwd;
                private String qrCode;
                private String rcloudToken;
                private String realName;
                private int regFrom;
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

                public int getIsNew() {
                    return isNew;
                }

                public void setIsNew(int isNew) {
                    this.isNew = isNew;
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

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
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

                public int getRegFrom() {
                    return regFrom;
                }

                public void setRegFrom(int regFrom) {
                    this.regFrom = regFrom;
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

        public static class CommonAnswersBean {
            /**
             * accountEntity : {"accId":"979993411866378241","accType":3,"address":"朝阳大悦城青年路1号","areaCode":"3.11.1.5","avatar":"c29039799c434a6ab1c6fae0aa1e7fb7.png","birthday":"2000-01-01 00:00:00","email":"10562331229@qq.com","gender":1,"idCard":"110101200001010870","isNew":0,"lastLoginTime":"2019-03-15 11:43:28","loginCount":381,"mobile":"18600134480","mpFrom":0,"nickName":"浮沉不定","openId":"owzakvz7ZUa_xQI5yA5-189cLttk","passwd":"0DPiKuNIrrVmD8IUCuw1hQxNqZc=","qrCode":"account/qr/5e9a6d16432448548ba6c1b36b766e22.png","rcloudToken":"fzyBWYvxei2Au3jW3jBCI37s2tSbN+XvbDqPHzdyLLUtcCQXSPO4lD1+fUAHIDu5zaO6jfU2ag3oGSi2aQlXm6LnQdDEiphRkFqJgXLMZxI=","realName":"刘保恩","regTime":"2018-03-31 16:06:51","simplePwd":false,"status":0}
             * answerCompanyId : 979995434422681602
             * answerContent : 什么测试
             * answerCreateTime : 2019-03-15 09:35:24
             * answerCreateTimeLong : 1552613724000
             * answerForwardNums : 0
             * answerId : 22
             * answerLikes : 0
             * answerPics : /expert/questionsbdbe9da2401e41ffb640a4beed619257.png
             * answerStatus : 0
             * answerTopCompanyId : 979995434422681602
             * answerUser : 浮沉不定
             * answerUserId : 980000524737757185
             * answerUserType : 0
             * likeStatus : 1
             * questionCompanyId : 1070259251163729922
             * questionId : 38
             * questionTopCompanyId : 1070259251163729922
             * questionUserId : 1077806071972753409
             * replyCount : 0
             */

            private AccountEntityBeanX accountEntity;
            private String answerCompanyId;
            private String answerContent;
            private String answerCreateTime;
            private long answerCreateTimeLong;
            private int answerForwardNums;
            private int answerId;
            private int answerLikes;
            private String answerPics;
            private int answerStatus;
            private String answerTopCompanyId;
            private String answerUser;
            private String answerUserId;
            private int answerUserType;
            private int likeStatus;
            private String questionCompanyId;
            private int questionId;
            private String questionTopCompanyId;
            private String questionUserId;
            private int replyCount;

            public AccountEntityBeanX getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountEntityBeanX accountEntity) {
                this.accountEntity = accountEntity;
            }

            public String getAnswerCompanyId() {
                return answerCompanyId;
            }

            public void setAnswerCompanyId(String answerCompanyId) {
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

            public String getAnswerPics() {
                return answerPics;
            }

            public void setAnswerPics(String answerPics) {
                this.answerPics = answerPics;
            }

            public int getAnswerStatus() {
                return answerStatus;
            }

            public void setAnswerStatus(int answerStatus) {
                this.answerStatus = answerStatus;
            }

            public String getAnswerTopCompanyId() {
                return answerTopCompanyId;
            }

            public void setAnswerTopCompanyId(String answerTopCompanyId) {
                this.answerTopCompanyId = answerTopCompanyId;
            }

            public String getAnswerUser() {
                return answerUser;
            }

            public void setAnswerUser(String answerUser) {
                this.answerUser = answerUser;
            }

            public String getAnswerUserId() {
                return answerUserId;
            }

            public void setAnswerUserId(String answerUserId) {
                this.answerUserId = answerUserId;
            }

            public int getAnswerUserType() {
                return answerUserType;
            }

            public void setAnswerUserType(int answerUserType) {
                this.answerUserType = answerUserType;
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

            public static class AccountEntityBeanX {
                /**
                 * accId : 979993411866378241
                 * accType : 3
                 * address : 朝阳大悦城青年路1号
                 * areaCode : 3.11.1.5
                 * avatar : c29039799c434a6ab1c6fae0aa1e7fb7.png
                 * birthday : 2000-01-01 00:00:00
                 * email : 10562331229@qq.com
                 * gender : 1
                 * idCard : 110101200001010870
                 * isNew : 0
                 * lastLoginTime : 2019-03-15 11:43:28
                 * loginCount : 381
                 * mobile : 18600134480
                 * mpFrom : 0
                 * nickName : 浮沉不定
                 * openId : owzakvz7ZUa_xQI5yA5-189cLttk
                 * passwd : 0DPiKuNIrrVmD8IUCuw1hQxNqZc=
                 * qrCode : account/qr/5e9a6d16432448548ba6c1b36b766e22.png
                 * rcloudToken : fzyBWYvxei2Au3jW3jBCI37s2tSbN+XvbDqPHzdyLLUtcCQXSPO4lD1+fUAHIDu5zaO6jfU2ag3oGSi2aQlXm6LnQdDEiphRkFqJgXLMZxI=
                 * realName : 刘保恩
                 * regTime : 2018-03-31 16:06:51
                 * simplePwd : false
                 * status : 0
                 */

                private String accId;
                private int accType;
                private String address;
                private String areaCode;
                private String avatar;
                private String birthday;
                private String email;
                private int gender;
                private String idCard;
                private int isNew;
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

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
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

                public int getIsNew() {
                    return isNew;
                }

                public void setIsNew(int isNew) {
                    this.isNew = isNew;
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

        public static class ExpertAnswersBean {
            /**
             * answerCompanyId : 1082922712729456641
             * answerContent : 沐浴露
             * answerCreateTime : 2019-03-15 16:44:08
             * answerCreateTimeLong : 1552639448000
             * answerForwardNums : 0
             * answerId : 27
             * answerLikes : 0
             * answerStatus : 0
             * answerTopCompanyId : 1082922712729456641
             * answerUser : test零七
             * answerUserId : 1082922712729456642
             * answerUserType : 5
             * expertsCertificationEntity : {"accId":"1059326429620158466","approveTime":"2019-03-16 16:05:53","approveType":0,"approveUserName":"管理员","avatarPhoto":"b265cb5269df4a5ab7fce6b7e2f79367.png","brandName":"testBrand0001","company":"ddd","createTime":"2019-03-15 15:57:24","expertName":"零六","favorableRate":0.66,"gender":1,"id":14,"idCard":"130322199608563284","idCardFront":"b026817b8ff848e7b6f7e047290cdb11.png","idCardHand":"b026817b8ff848e7b6f7e047290cdb11.png","idCardSide":"b026817b8ff848e7b6f7e047290cdb11.png","impowerUrl":"feb2da277dc647ddb9d7bfaff9919296.png","intro":"111","jobLce":1,"jobLevel":1,"payAccount":"222","payType":1,"phonenumber":"18600000002","price":13,"responsibleBrand":"AB,东芝（Toshiba）","status":0,"systemType":"防盗报警,可视对讲","updateTime":"2019-03-15 15:56:06","userId":"1082922712729456642","workingAge":0}
             * likeStatus : 1
             * questionCompanyId : 1082922712729456641
             * questionId : 38
             * questionTopCompanyId : 1082922712729456641
             * questionUserId : 1082922712729456642
             * replyCount : 0
             * answerPics : /expert/questions73927f99ddef4bd2a1ec5e5f518ac14c.png
             */

            private String answerCompanyId;
            private String answerContent;
            private String answerCreateTime;
            private long answerCreateTimeLong;
            private int answerForwardNums;
            private int answerId;
            private int answerLikes;
            private int answerStatus;
            private String answerTopCompanyId;
            private String answerUser;
            private String answerUserId;
            private int answerUserType;
            private ExpertsCertificationEntityBean expertsCertificationEntity;
            private int likeStatus;
            private String questionCompanyId;
            private int questionId;
            private String questionTopCompanyId;
            private String questionUserId;
            private int replyCount;
            private String answerPics;

            public String getAnswerCompanyId() {
                return answerCompanyId;
            }

            public void setAnswerCompanyId(String answerCompanyId) {
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

            public String getAnswerTopCompanyId() {
                return answerTopCompanyId;
            }

            public void setAnswerTopCompanyId(String answerTopCompanyId) {
                this.answerTopCompanyId = answerTopCompanyId;
            }

            public String getAnswerUser() {
                return answerUser;
            }

            public void setAnswerUser(String answerUser) {
                this.answerUser = answerUser;
            }

            public String getAnswerUserId() {
                return answerUserId;
            }

            public void setAnswerUserId(String answerUserId) {
                this.answerUserId = answerUserId;
            }

            public int getAnswerUserType() {
                return answerUserType;
            }

            public void setAnswerUserType(int answerUserType) {
                this.answerUserType = answerUserType;
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
                 * accId : 1059326429620158466
                 * approveTime : 2019-03-16 16:05:53
                 * approveType : 0
                 * approveUserName : 管理员
                 * avatarPhoto : b265cb5269df4a5ab7fce6b7e2f79367.png
                 * brandName : testBrand0001
                 * company : ddd
                 * createTime : 2019-03-15 15:57:24
                 * expertName : 零六
                 * favorableRate : 0.66
                 * gender : 1
                 * id : 14
                 * idCard : 130322199608563284
                 * idCardFront : b026817b8ff848e7b6f7e047290cdb11.png
                 * idCardHand : b026817b8ff848e7b6f7e047290cdb11.png
                 * idCardSide : b026817b8ff848e7b6f7e047290cdb11.png
                 * impowerUrl : feb2da277dc647ddb9d7bfaff9919296.png
                 * intro : 111
                 * jobLce : 1
                 * jobLevel : 1
                 * payAccount : 222
                 * payType : 1
                 * phonenumber : 18600000002
                 * price : 13
                 * responsibleBrand : AB,东芝（Toshiba）
                 * status : 0
                 * systemType : 防盗报警,可视对讲
                 * updateTime : 2019-03-15 15:56:06
                 * userId : 1082922712729456642
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
