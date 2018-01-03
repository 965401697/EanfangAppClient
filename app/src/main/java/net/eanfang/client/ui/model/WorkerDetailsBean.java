package net.eanfang.client.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class WorkerDetailsBean implements Serializable {


        /**
         * accId : 936487014348365825
         * businessList : [10,11,12]
         * companyEntity : {"companyId":2120,"isVerify":0,"level":3,"orgCode":"c.c1.c2","orgId":2120,"orgName":"家乐福三元桥公司","orgType":1,"parentOrgId":2100,"sortNum":0,"topCompanyId":2000,"updateTime":"2017-12-05 13:34:47","updateUser":1}
         * goodRate : 88
         * id : 6
         * item1 : 4
         * item2 : 4
         * item3 : 4
         * item4 : 5
         * item5 : 3
         * lat : 39.929004
         * lon : 116.575179
         * placeCode : 3.11.1.5
         * publicPraise : 442
         * regionList : [103,104,105]
         * repairCount : 7
         * serviceList : [31,32]
         * verifyEntity : {"accId":"936487014348365825","accidentPics":"11111","birthday":"1990-01-01 00:00:00","businessList":[10,11,12],"commitPic":"2222222","contactName":"张三","contactPhone":"18500125171","createTime":"2017-12-05 16:44:38","email":"123@qq.com","gender":1,"headPic":"33333333","honorPics":"44444444","id":5,"idCard":"85487518652841","idCardFront":"444444444","idCardHand":"555555555","idCardSide":"666666666","intro":"个人简介","payAccount":"123@qq.com","payType":"1","phone":"13800138000","placeAddress":"定福家园南里2号院","realName":"张三","regionList":[103,104,105],"serviceList":[31,32],"status":1,"userId":"936487014465806337","verifyMessage":"认证通过","verifyTime":"2017-12-05 16:51:55","verifyUserName":"111","workingLevel":"5","workingYear":"5"}
         * verifyId : 5
         * workStatus : 0
         * workerNumber : 1712051651921
         */

        private Long accId;
        private CompanyEntityBean companyEntity;
        private int goodRate;
        private Long id;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private String lat;
        private String lon;
        private String placeCode;
        private int publicPraise;
        private int repairCount;
        private VerifyEntityBean verifyEntity;
        private Long verifyId;
        private int workStatus;
        private String workerNumber;
        private List<Integer> businessList;
        private List<Integer> regionList;
        private List<Integer> serviceList;

        public Long getAccId() {
            return accId;
        }

        public void setAccId(Long accId) {
            this.accId = accId;
        }

        public CompanyEntityBean getCompanyEntity() {
            return companyEntity;
        }

        public void setCompanyEntity(CompanyEntityBean companyEntity) {
            this.companyEntity = companyEntity;
        }

        public int getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(int goodRate) {
            this.goodRate = goodRate;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getPlaceCode() {
            return placeCode;
        }

        public void setPlaceCode(String placeCode) {
            this.placeCode = placeCode;
        }

        public int getPublicPraise() {
            return publicPraise;
        }

        public void setPublicPraise(int publicPraise) {
            this.publicPraise = publicPraise;
        }

        public int getRepairCount() {
            return repairCount;
        }

        public void setRepairCount(int repairCount) {
            this.repairCount = repairCount;
        }

        public VerifyEntityBean getVerifyEntity() {
            return verifyEntity;
        }

        public void setVerifyEntity(VerifyEntityBean verifyEntity) {
            this.verifyEntity = verifyEntity;
        }

        public Long getVerifyId() {
            return verifyId;
        }

        public void setVerifyId(Long verifyId) {
            this.verifyId = verifyId;
        }

        public int getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(int workStatus) {
            this.workStatus = workStatus;
        }

        public String getWorkerNumber() {
            return workerNumber;
        }

        public void setWorkerNumber(String workerNumber) {
            this.workerNumber = workerNumber;
        }

        public List<Integer> getBusinessList() {
            return businessList;
        }

        public void setBusinessList(List<Integer> businessList) {
            this.businessList = businessList;
        }

        public List<Integer> getRegionList() {
            return regionList;
        }

        public void setRegionList(List<Integer> regionList) {
            this.regionList = regionList;
        }

        public List<Integer> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<Integer> serviceList) {
            this.serviceList = serviceList;
        }

        public static class CompanyEntityBean implements Serializable{
            /**
             * companyId : 2120
             * isVerify : 0
             * level : 3
             * orgCode : c.c1.c2
             * orgId : 2120
             * orgName : 家乐福三元桥公司
             * orgType : 1
             * parentOrgId : 2100
             * sortNum : 0
             * topCompanyId : 2000
             * updateTime : 2017-12-05 13:34:47
             * updateUser : 1
             */

            private Long companyId;
            private int isVerify;
            private int level;
            private String orgCode;
            private Long orgId;
            private String orgName;
            private int orgType;
            private Long parentOrgId;
            private int sortNum;
            private Long topCompanyId;
            private String updateTime;
            private int updateUser;

            public Long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Long companyId) {
                this.companyId = companyId;
            }

            public int getIsVerify() {
                return isVerify;
            }

            public void setIsVerify(int isVerify) {
                this.isVerify = isVerify;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public Long getOrgId() {
                return orgId;
            }

            public void setOrgId(Long orgId) {
                this.orgId = orgId;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public Long getParentOrgId() {
                return parentOrgId;
            }

            public void setParentOrgId(Long parentOrgId) {
                this.parentOrgId = parentOrgId;
            }

            public int getSortNum() {
                return sortNum;
            }

            public void setSortNum(int sortNum) {
                this.sortNum = sortNum;
            }

            public Long getTopCompanyId() {
                return topCompanyId;
            }

            public void setTopCompanyId(Long topCompanyId) {
                this.topCompanyId = topCompanyId;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(int updateUser) {
                this.updateUser = updateUser;
            }
        }

        public static class VerifyEntityBean implements Serializable{
            /**
             * accId : 936487014348365825
             * accidentPics : 11111
             * birthday : 1990-01-01 00:00:00
             * businessList : [10,11,12]
             * commitPic : 2222222
             * contactName : 张三
             * contactPhone : 18500125171
             * createTime : 2017-12-05 16:44:38
             * email : 123@qq.com
             * gender : 1
             * headPic : 33333333
             * honorPics : 44444444
             * id : 5
             * idCard : 85487518652841
             * idCardFront : 444444444
             * idCardHand : 555555555
             * idCardSide : 666666666
             * intro : 个人简介
             * payAccount : 123@qq.com
             * payType : 1
             * phone : 13800138000
             * placeAddress : 定福家园南里2号院
             * realName : 张三
             * regionList : [103,104,105]
             * serviceList : [31,32]
             * status : 1
             * userId : 936487014465806337
             * verifyMessage : 认证通过
             * verifyTime : 2017-12-05 16:51:55
             * verifyUserName : 111
             * workingLevel : 5
             * workingYear : 5
             */

            private Long accId;
            private String accidentPics;
            private String birthday;
            private String commitPic;
            private String contactName;
            private String contactPhone;
            private String createTime;
            private String email;
            private int gender;
            private String headPic;
            private String honorPics;
            private Long id;
            private Long idCard;
            private String idCardFront;
            private String idCardHand;
            private String idCardSide;
            private String intro;
            private String payAccount;
            private String payType;
            private String phone;
            private String placeAddress;
            private String realName;
            private int status;
            private Long userId;
            private String verifyMessage;
            private String verifyTime;
            private String verifyUserName;
            private int workingLevel;
            private int workingYear;
            private List<Integer> businessList;
            private List<Integer> regionList;
            private List<Integer> serviceList;

            public Long getAccId() {
                return accId;
            }

            public void setAccId(Long accId) {
                this.accId = accId;
            }

            public String getAccidentPics() {
                return accidentPics;
            }

            public void setAccidentPics(String accidentPics) {
                this.accidentPics = accidentPics;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCommitPic() {
                return commitPic;
            }

            public void setCommitPic(String commitPic) {
                this.commitPic = commitPic;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getContactPhone() {
                return contactPhone;
            }

            public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getHonorPics() {
                return honorPics;
            }

            public void setHonorPics(String honorPics) {
                this.honorPics = honorPics;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getIdCard() {
                return idCard;
            }

            public void setIdCard(Long idCard) {
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

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getPayAccount() {
                return payAccount;
            }

            public void setPayAccount(String payAccount) {
                this.payAccount = payAccount;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPlaceAddress() {
                return placeAddress;
            }

            public void setPlaceAddress(String placeAddress) {
                this.placeAddress = placeAddress;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Long getUserId() {
                return userId;
            }

            public void setUserId(Long userId) {
                this.userId = userId;
            }

            public String getVerifyMessage() {
                return verifyMessage;
            }

            public void setVerifyMessage(String verifyMessage) {
                this.verifyMessage = verifyMessage;
            }

            public String getVerifyTime() {
                return verifyTime;
            }

            public void setVerifyTime(String verifyTime) {
                this.verifyTime = verifyTime;
            }

            public String getVerifyUserName() {
                return verifyUserName;
            }

            public void setVerifyUserName(String verifyUserName) {
                this.verifyUserName = verifyUserName;
            }

            public int getWorkingLevel() {
                return workingLevel;
            }

            public void setWorkingLevel(int workingLevel) {
                this.workingLevel = workingLevel;
            }

            public int getWorkingYear() {
                return workingYear;
            }

            public void setWorkingYear(int workingYear) {
                this.workingYear = workingYear;
            }

            public List<Integer> getBusinessList() {
                return businessList;
            }

            public void setBusinessList(List<Integer> businessList) {
                this.businessList = businessList;
            }

            public List<Integer> getRegionList() {
                return regionList;
            }

            public void setRegionList(List<Integer> regionList) {
                this.regionList = regionList;
            }

            public List<Integer> getServiceList() {
                return serviceList;
            }

            public void setServiceList(List<Integer> serviceList) {
                this.serviceList = serviceList;
            }
        }
    }

