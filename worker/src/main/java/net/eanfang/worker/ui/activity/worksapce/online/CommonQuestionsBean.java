package net.eanfang.worker.ui.activity.worksapce.online;

import java.util.List;

/**
 * Created by 匹诺曹 on 2019/3/12.
 */

class CommonQuestionsBean {


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
             * commonWeight : 50
             * dataCode : 1.1.1.3
             * deviceFailureId : 1025400823779540994
             * expertsCertification : {"accId":"979985982284677121","approveTime":"2019-03-03 20:48:08","approveType":0,"avatarPhoto":"b265cb5269df4a5ab7fce6b7e2f79367.png","brandName":"testBrand0002","company":"河康威见","createTime":"2019-01-18 09:17:29","expertName":"测试专家002","favorableRate":0.75,"gender":0,"id":10,"idCard":"110101199105051234","idCardFront":"145bbddd9890479a931005a7bd7da828.png","idCardHand":"b265cb5269df4a5ab7fce6b7e2f79367.png","idCardSide":"feb2da277dc647ddb9d7bfaff9919296.png","impowerUrl":"feb2da277dc647ddb9d7bfaff9919296.png","intro":"ceshiintro0002","jobLce":1,"jobLevel":1,"payAccount":"2222","payType":1,"phonenumber":"15963521458","price":15,"responsibleBrand":"AB,东芝（Toshiba）","status":1,"systemType":"可视对讲","updateTime":"2019-01-18 11:45:24","userId":"979985982318231553","workingAge":0}
             * failureTypeId : 6.1
             * modelCode : 5.1.4
             * questionAnswerCount : 0
             * questionCompanyId : 1070259251163729922
             * questionContent : 胡泽君
             * questionCreateDate : 2019-03-06 17:29:06
             * questionId : 22
             * questionLikeCount : 0
             * questionPics : online/746883e58d4c4f408b43203aba37f96c.png,online/9b1b8e2087da476980f5cd9f7495b3d7.png
             * questionSketch : 无视频信号
             * questionStatus : 0
             * questionTopCompanyId : 1070259251163729922
             * questionUserId : 1077806071972753409
             * questionViewCount : 25
             * weight : 50
             */

            private String businessOneCode;
            private int commonWeight;
            private String dataCode;
            private String deviceFailureId;
            private ExpertsCertificationBean expertsCertification;
            private String failureTypeId;
            private String modelCode;
            private int questionAnswerCount;
            private String questionCompanyId;
            private String questionContent;
            private String questionCreateDate;
            private int questionId;
            private int questionLikeCount;
            private String questionPics;
            private String questionSketch;
            private int questionStatus;
            private String questionTopCompanyId;
            private String questionUserId;
            private int questionViewCount;
            private int weight;

            public String getBusinessOneCode() {
                return businessOneCode;
            }

            public void setBusinessOneCode(String businessOneCode) {
                this.businessOneCode = businessOneCode;
            }

            public int getCommonWeight() {
                return commonWeight;
            }

            public void setCommonWeight(int commonWeight) {
                this.commonWeight = commonWeight;
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

            public static class ExpertsCertificationBean {
                /**
                 * accId : 979985982284677121
                 * approveTime : 2019-03-03 20:48:08
                 * approveType : 0
                 * avatarPhoto : b265cb5269df4a5ab7fce6b7e2f79367.png
                 * brandName : testBrand0002
                 * company : 河康威见
                 * createTime : 2019-01-18 09:17:29
                 * expertName : 测试专家002
                 * favorableRate : 0.75
                 * gender : 0
                 * id : 10
                 * idCard : 110101199105051234
                 * idCardFront : 145bbddd9890479a931005a7bd7da828.png
                 * idCardHand : b265cb5269df4a5ab7fce6b7e2f79367.png
                 * idCardSide : feb2da277dc647ddb9d7bfaff9919296.png
                 * impowerUrl : feb2da277dc647ddb9d7bfaff9919296.png
                 * intro : ceshiintro0002
                 * jobLce : 1
                 * jobLevel : 1
                 * payAccount : 2222
                 * payType : 1
                 * phonenumber : 15963521458
                 * price : 15
                 * responsibleBrand : AB,东芝（Toshiba）
                 * status : 1
                 * systemType : 可视对讲
                 * updateTime : 2019-01-18 11:45:24
                 * userId : 979985982318231553
                 * workingAge : 0
                 */

                private String accId;
                private String approveTime;
                private int approveType;
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
