package com.yaf.base.entity;

import java.util.List;

/**
 * Created by on 2019/2/1.
 */

public class CommonFaultListBeanEntity {

        private SimilarQuestionListBean similarQuestionList;
        private List<ExpertsListBean> expertsList;

        public SimilarQuestionListBean getSimilarQuestionList() {
            return similarQuestionList;
        }

        public void setSimilarQuestionList(SimilarQuestionListBean similarQuestionList) {
            this.similarQuestionList = similarQuestionList;
        }

        public List<ExpertsListBean> getExpertsList() {
            return expertsList;
        }

        public void setExpertsList(List<ExpertsListBean> expertsList) {
            this.expertsList = expertsList;
        }

        public static class SimilarQuestionListBean {
            /**
             * currPage : 1
             * list : [{"businessOneCode":"1.1","commonWeight":255,"dataCode":"1.1.1.3","deviceFailureId":"1025400829223747586","failureTypeId":"6.1","modelCode":"5.1.4","questionAnswerCount":4,"questionCompanyId":"1082922712729456641","questionContent":"扣扣","questionCreateDate":"2019-03-15 10:23:39","questionId":33,"questionLikeCount":2,"questionPics":"online/ea32c1cbb5124f7b9b3c37f0c1ec3bff.png,online/bf99cef1db3744139c96b2e76f6f7cc0.png","questionSketch":"黑屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":113,"weight":238},{"businessOneCode":"1.1","commonWeight":273,"dataCode":"1.1.1.13","deviceFailureId":"1025400829223747586","failureTypeId":"6.1","modelCode":"5.1.20","questionAnswerCount":4,"questionCompanyId":"1082922712729456641","questionContent":"你就疼我","questionCreateDate":"2019-03-15 10:25:32","questionId":34,"questionLikeCount":1,"questionPics":"online/589079b1517443298bcc18b97abcf7b9.png,online/a12ed2c8f57e46b2aa6525a836413495.png","questionSketch":"黑屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":110,"weight":230},{"businessOneCode":"1.1","commonWeight":507,"dataCode":"1.1.1.2","deviceFailureId":"1025400837469749249","failureTypeId":"6.1","modelCode":"5.1.4","questionAnswerCount":6,"questionCompanyId":"1082922712729456641","questionContent":"1不理我滴","questionCreateDate":"2019-03-15 14:47:43","questionId":35,"questionLikeCount":4,"questionPics":"online/596da9175f46451c90fd37f701b022be.png,online/c40b2f6c01044ebca35de728e72339f4.png","questionSketch":"有水波纹,木纹","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":250,"weight":518},{"businessOneCode":"1.1","dataCode":"1.1.1.11","deviceFailureId":"1025400849285103618","failureTypeId":"6.1","modelCode":"5.1.8","questionAnswerCount":1,"questionCompanyId":"1082922712729456641","questionContent":"你知道","questionCreateDate":"2019-03-15 16:43:19","questionId":36,"questionLikeCount":0,"questionPics":"online/a15cdd43449a4379bd38cd7a5a1d295d.png","questionSketch":"白屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":26,"weight":81},{"businessOneCode":"1.1","dataCode":"1.1.1.3","deviceFailureId":"1025400841517252609","failureTypeId":"6.1","modelCode":"5.1.18","questionAnswerCount":0,"questionCompanyId":"1082922712729456641","questionContent":"来了","questionCreateDate":"2019-03-15 16:46:22","questionId":37,"questionLikeCount":0,"questionPics":"online/c8cc65abf54146b0bc84e51ebcaf4fe6.png","questionSketch":"有竖道","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":3,"weight":9},{"businessOneCode":"1.1","commonWeight":186,"dataCode":"1.1.1.3","deviceFailureId":"1025400841517252609","failureTypeId":"6.1","modelCode":"5.1.18","questionAnswerCount":4,"questionCompanyId":"1082922712729456641","questionContent":"来了","questionCreateDate":"2019-03-15 16:46:22","questionId":38,"questionLikeCount":3,"questionPics":"online/e7498367285e416f9c7697e11d3c25e3.png","questionSketch":"有竖道","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":55,"weight":186},{"businessOneCode":"1.1","dataCode":"1.1.1.8","deviceFailureId":"1025400833225113602","failureTypeId":"6.1","modelCode":"5.1.18","questionAnswerCount":0,"questionCompanyId":"1082922712729456641","questionContent":"看我的","questionCreateDate":"2019-03-15 16:47:12","questionId":39,"questionLikeCount":0,"questionPics":"online/c48929c02a214aed84e48ddd41501809.png","questionSketch":"蓝屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":10,"weight":30},{"businessOneCode":"1.1","dataCode":"1.1.1.12","deviceFailureId":"1025400829223747586","failureTypeId":"6.1","modelCode":"5.1.2","questionAnswerCount":0,"questionCompanyId":"1082922712729456641","questionContent":"咯告诉我","questionCreateDate":"2019-03-15 16:47:38","questionId":40,"questionLikeCount":0,"questionPics":"online/c03e628a0da14065b2a3bbf5a8438378.png","questionSketch":"黑屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":4,"weight":12},{"businessOneCode":"1.1","dataCode":"1.1.1.11","deviceFailureId":"1025400823779540994","failureTypeId":"6.1","modelCode":"5.1.20","questionAnswerCount":0,"questionCompanyId":"1082922712729456641","questionContent":"没有记录","questionCreateDate":"2019-03-15 16:48:33","questionId":41,"questionLikeCount":0,"questionPics":"online/0a9dd1df33cc45b1bbdb3eeafea87049.png","questionSketch":"无视频信号","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":6,"weight":18},{"businessOneCode":"1.1","dataCode":"1.1.1.8","deviceFailureId":"1025400829223747586","failureTypeId":"6.1","modelCode":"5.1.12","questionAnswerCount":0,"questionCompanyId":"1082922712729456641","questionContent":"初中","questionCreateDate":"2019-03-15 17:46:28","questionId":42,"questionLikeCount":0,"questionPics":"online/3dee1b8676f4490595bdf018d833d3e3.png","questionSketch":"黑屏","questionStatus":0,"questionTopCompanyId":"1082922712729456641","questionUserId":"1082922712729456642","questionViewCount":3,"weight":9}]
             * pageSize : 10
             * totalCount : 59
             * totalPage : 6
             */

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
                 * commonWeight : 255
                 * dataCode : 1.1.1.3
                 * deviceFailureId : 1025400829223747586
                 * failureTypeId : 6.1
                 * modelCode : 5.1.4
                 * questionAnswerCount : 4
                 * questionCompanyId : 1082922712729456641
                 * questionContent : 扣扣
                 * questionCreateDate : 2019-03-15 10:23:39
                 * questionId : 33
                 * questionLikeCount : 2
                 * questionPics : online/ea32c1cbb5124f7b9b3c37f0c1ec3bff.png,online/bf99cef1db3744139c96b2e76f6f7cc0.png
                 * questionSketch : 黑屏
                 * questionStatus : 0
                 * questionTopCompanyId : 1082922712729456641
                 * questionUserId : 1082922712729456642
                 * questionViewCount : 113
                 * weight : 238
                 */

                private String businessOneCode;
                private int commonWeight;
                private String dataCode;
                private String deviceFailureId;
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
            }
        }

        public static class ExpertsListBean {
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
