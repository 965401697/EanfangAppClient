package net.eanfang.client.ui.activity.worksapce.online;

import java.util.List;

/**
 * Created by on 2019/2/25.
 */

class MyReplyListBean {


        private int replyCount;
        private AnswerInfoBean answerInfo;
        private int likes;
        private List<ReplyListBean> replyList;

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public AnswerInfoBean getAnswerInfo() {
            return answerInfo;
        }

        public void setAnswerInfo(AnswerInfoBean answerInfo) {
            this.answerInfo = answerInfo;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class AnswerInfoBean {
            /**
             * accountEntity : {"accId":"979985982284677121","accType":2,"address":"皇家鸡排(物资学院店)2","areaCode":"3.12.1.1","avatar":"account/avatar/7cff7eb2ddf34dd4b59b0e674499e2fc.png","birthday":"2018-06-01 00:00:00","email":"13800138002@qq.com","gender":0,"idCard":"110101200001015372","isNew":0,"lastLoginTime":"2019-03-19 15:32:33","loginCount":813,"mobile":"13800138002","nickName":"管理员","passwd":"o36zKRVp1PIjbkC6jWzofmWDYDk=","qrCode":"account/qr/4ad0548b6f5b434daf62884c13266e2d.png","rcloudToken":"jla3YZd66R1O2G4aYIM1j0xrRiQKPOlpqRODuAaxpdkYa9bWuJ/bufzAJ92EjG82kKn5OgspXlX91fTBYoMaloztbpKlkMs1BqOUHqdui+0=","realName":"管理员","regTime":"2018-03-31 15:37:20","simplePwd":false,"status":0}
             * answerCompanyId : 1082922712729456641
             * answerContent : 沐浴露
             * answerCreateTime : 2019-03-15 16:44:14
             * answerCreateTimeLong : 1552639454000
             * answerForwardNums : 0
             * answerId : 28
             * answerLikes : 0
             * answerStatus : 0
             * answerPics : /expert/questionsa467e20aaf8641aba93d870e89f7b2a1.png
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
             */

            private AccountEntityBean accountEntity;
            private String answerCompanyId;
            private String answerContent;
            private String answerCreateTime;
            private long answerCreateTimeLong;
            private int answerForwardNums;
            private int answerId;
            private int answerLikes;
            private int answerStatus;
            private String answerPics;
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

            public AccountEntityBean getAccountEntity() {
                return accountEntity;
            }

            public void setAccountEntity(AccountEntityBean accountEntity) {
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

            public int getAnswerStatus() {
                return answerStatus;
            }

            public void setAnswerStatus(int answerStatus) {
                this.answerStatus = answerStatus;
            }

            public String getAnswerPics() {
                return answerPics;
            }

            public void setAnswerPics(String answerPics) {
                this.answerPics = answerPics;
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

            public static class AccountEntityBean {
                /**
                 * accId : 979985982284677121
                 * accType : 2
                 * address : 皇家鸡排(物资学院店)2
                 * areaCode : 3.12.1.1
                 * avatar : account/avatar/7cff7eb2ddf34dd4b59b0e674499e2fc.png
                 * birthday : 2018-06-01 00:00:00
                 * email : 13800138002@qq.com
                 * gender : 0
                 * idCard : 110101200001015372
                 * isNew : 0
                 * lastLoginTime : 2019-03-19 15:32:33
                 * loginCount : 813
                 * mobile : 13800138002
                 * nickName : 管理员
                 * passwd : o36zKRVp1PIjbkC6jWzofmWDYDk=
                 * qrCode : account/qr/4ad0548b6f5b434daf62884c13266e2d.png
                 * rcloudToken : jla3YZd66R1O2G4aYIM1j0xrRiQKPOlpqRODuAaxpdkYa9bWuJ/bufzAJ92EjG82kKn5OgspXlX91fTBYoMaloztbpKlkMs1BqOUHqdui+0=
                 * realName : 管理员
                 * regTime : 2018-03-31 15:37:20
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
                private String nickName;
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

        public static class ReplyListBean {
            /**
             * areAnswerId : 28
             * areId : 15
             * replyContent : 这是一条专家回答的回复
             * replyTime : 2019-02-26 14:25:06
             * replyTimeLong : 1551162306000
             * replyUser : {"accId":"979985982284677121","accType":2,"address":"皇家鸡排(物资学院店)2","areaCode":"3.12.1.1","avatar":"account/avatar/7cff7eb2ddf34dd4b59b0e674499e2fc.png","birthday":"2018-06-01 00:00:00","email":"13800138002@qq.com","gender":0,"idCard":"110101200001015372","isNew":0,"lastLoginTime":"2019-03-16 20:41:28","loginCount":792,"mobile":"13800138002","nickName":"管理员","passwd":"Bf50YcYHwzIpdy1AJQVgEBan0Oo=","qrCode":"account/qr/4ad0548b6f5b434daf62884c13266e2d.png","rcloudToken":"jla3YZd66R1O2G4aYIM1j0xrRiQKPOlpqRODuAaxpdkYa9bWuJ/bufzAJ92EjG82kKn5OgspXlX91fTBYoMaloztbpKlkMs1BqOUHqdui+0=","realName":"管理员","regTime":"2018-03-31 15:37:20","simplePwd":false,"status":0}
             * replyUserId : 979985982318231553
             */

            private int areAnswerId;
            private int areId;
            private String replyContent;
            private String replyTime;
            private long replyTimeLong;
            private ReplyUserBean replyUser;
            private String replyUserId;

            public int getAreAnswerId() {
                return areAnswerId;
            }

            public void setAreAnswerId(int areAnswerId) {
                this.areAnswerId = areAnswerId;
            }

            public int getAreId() {
                return areId;
            }

            public void setAreId(int areId) {
                this.areId = areId;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }

            public String getReplyTime() {
                return replyTime;
            }

            public void setReplyTime(String replyTime) {
                this.replyTime = replyTime;
            }

            public long getReplyTimeLong() {
                return replyTimeLong;
            }

            public void setReplyTimeLong(long replyTimeLong) {
                this.replyTimeLong = replyTimeLong;
            }

            public ReplyUserBean getReplyUser() {
                return replyUser;
            }

            public void setReplyUser(ReplyUserBean replyUser) {
                this.replyUser = replyUser;
            }

            public String getReplyUserId() {
                return replyUserId;
            }

            public void setReplyUserId(String replyUserId) {
                this.replyUserId = replyUserId;
            }

            public static class ReplyUserBean {
                /**
                 * accId : 979985982284677121
                 * accType : 2
                 * address : 皇家鸡排(物资学院店)2
                 * areaCode : 3.12.1.1
                 * avatar : account/avatar/7cff7eb2ddf34dd4b59b0e674499e2fc.png
                 * birthday : 2018-06-01 00:00:00
                 * email : 13800138002@qq.com
                 * gender : 0
                 * idCard : 110101200001015372
                 * isNew : 0
                 * lastLoginTime : 2019-03-16 20:41:28
                 * loginCount : 792
                 * mobile : 13800138002
                 * nickName : 管理员
                 * passwd : Bf50YcYHwzIpdy1AJQVgEBan0Oo=
                 * qrCode : account/qr/4ad0548b6f5b434daf62884c13266e2d.png
                 * rcloudToken : jla3YZd66R1O2G4aYIM1j0xrRiQKPOlpqRODuAaxpdkYa9bWuJ/bufzAJ92EjG82kKn5OgspXlX91fTBYoMaloztbpKlkMs1BqOUHqdui+0=
                 * realName : 管理员
                 * regTime : 2018-03-31 15:37:20
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
                private String nickName;
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
}

