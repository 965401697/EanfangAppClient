package net.eanfang.worker.ui.activity.worksapce.online;

import java.util.List;

/**
 * Created by 匹诺曹 on 2019/2/25.
 */

class MyReplyListBean {


        private int replyCount;
        private AnswerInfoBean answerInfo;
        private List<MyReplyListBean> replyList;

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

        public List<MyReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<MyReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public static class AnswerInfoBean {
            /**
             * answerCompanyId : 0
             * answerContent : nt00110101
             * answerCreateTime : 2019-01-28 11:39:15
             * answerCreateTimeLong : 1548646755000
             * answerForwardNums : 1
             * answerId : 1
             * answerLikes : 5
             * answerStatus : 0
             * answerTopCompanyId : 0
             * answerUserId : 1057887495614709762
             * expertsCertificationEntity : {"accId":"1057887495589543937","approveTime":"2019-02-22 16:20:17","approveType":0,"approveUserName":"管理员","avatarPhoto":"145bbddd9890479a931005a7bd7da828.png","brandName":"testBrand0001","company":"水康威看","createTime":"2019-01-16 18:13:16","expertName":"测试专家001","favorableRate":0.88,"gender":1,"id":9,"idCard":"110101199105051211","idCardFront":"feb2da277dc647ddb9d7bfaff9919296.png","idCardHand":"145bbddd9890479a931005a7bd7da828.png","idCardSide":"b265cb5269df4a5ab7fce6b7e2f79367.png","impowerUrl":"account/3750d8072f264aa49bafce3f7a2be35d.png","intro":"ceshiintrp0001","jobLce":1,"jobLevel":1,"payAccount":"321321","payType":2,"phonenumber":"14796325896","price":20,"status":2,"systemType":"防盗报警,可视对讲","updateTime":"2019-01-17 09:41:18","userId":"1057887495614709762","workingAge":0}
             * likeStatus : 1
             * questionCompanyId : 979995434422681602
             * questionId : 1
             * questionTopCompanyId : 979995434422681602
             * questionUserId : 1049116901888790530
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


            /**
             * areAnswerId : 1
             * areId : 1
             * replyContent : 这是一条专家回答的回复
             * replyTime : 2019-02-25 15:05:05
             * replyTimeLong : 1551078305000
             * replyUser : {"accId":"979985982284677121","accType":2,"address":"皇家鸡排(物资学院店)2","areaCode":"3.12.1.1","avatar":"account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png","birthday":"2018-06-01 00:00:00","email":"13800138002@qq.com","gender":0,"idCard":"110101200001015372","lastLoginTime":"2019-02-25 15:03:55","loginCount":680,"mobile":"13800138002","nickName":"管理员","passwd":"Ph3EDGqydyNbzkrLhhpuEkrLIG8=","qrCode":"account/qr/4ad0548b6f5b434daf62884c13266e2d.png","rcloudToken":"jla3YZd66R1O2G4aYIM1j0xrRiQKPOlpqRODuAaxpdkYa9bWuJ/bufzAJ92EjG82kKn5OgspXlX91fTBYoMaloztbpKlkMs1BqOUHqdui+0=","realName":"管理员","regTime":"2018-03-31 15:37:20","simplePwd":false,"status":0}
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
                 * avatar : account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png
                 * birthday : 2018-06-01 00:00:00
                 * email : 13800138002@qq.com
                 * gender : 0
                 * idCard : 110101200001015372
                 * lastLoginTime : 2019-02-25 15:03:55
                 * loginCount : 680
                 * mobile : 13800138002
                 * nickName : 管理员
                 * passwd : Ph3EDGqydyNbzkrLhhpuEkrLIG8=
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
