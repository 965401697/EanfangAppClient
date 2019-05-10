package net.eanfang.client.ui.activity.worksapce.online;

import java.util.List;

/**
 * Created by  on 2019/3/14.
 */

class MyInfoBean {


        private int followers;
        private int likeCount;
        private int followee;
        private AccountBean account;
        private List<HistoryListBean> historyList;
        private List<LikesListBean> likesList;

        public int getFollowers() {
            return followers;
        }

        public void setFollowers(int followers) {
            this.followers = followers;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getFollowee() {
            return followee;
        }

        public void setFollowee(int followee) {
            this.followee = followee;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public List<HistoryListBean> getHistoryList() {
            return historyList;
        }

        public void setHistoryList(List<HistoryListBean> historyList) {
            this.historyList = historyList;
        }

        public List<LikesListBean> getLikesList() {
            return likesList;
        }

        public void setLikesList(List<LikesListBean> likesList) {
            this.likesList = likesList;
        }

        public static class AccountBean {
            /**
             * accId : 979985982284677121
             * accType : 2
             * accountExtInfo : {"accId":"979985982284677121","designNum":0,"evaluateNum":0,"generalEvaluation":1,"goodNum":0,"goodRate":10000,"id":358,"installNum":0,"item1":400,"item2":400,"item3":400,"item4":400,"item5":400,"lat":"39.925682","lon":"116.60795","maintainCount":0,"placeCode":"3.11.1.5","publicPraise":100,"qualification":0,"receiveCount":0,"repairCount":0,"trainStatus":0,"verifyStatus":0,"workStatus":0,"workerNumber":"1901151717337"}
             * address : 皇家鸡排(物资学院店)2
             * allowCurDomainCompanys : [0]
             * areaCode : 3.12.1.1
             * avatar : account/avatar/d6b8f38fe033460e94ce4a817eaf43f5.png
             * belongCompanys : [{"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0},{"adminUserId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":1000,"orgName":"易安防总平台","orgType":0,"orgUnitEntity":{"adminUserId":0,"defaultAddress":"永外大街车站路12号北京南站","defaultLat":"39.865208","defaultLon":"116.378596","defaultPlaceCode":"3.11.1.6","isEnable":0,"orgId":1000,"unitType":0}},{"adminUserId":"1064786022890483713","countStaff":0,"level":2,"orgCode":"c.c3","orgId":"1064786022882095106","orgName":"总平台分公司","orgType":1,"orgUnitEntity":{"adminUserId":"1064786022890483713","isEnable":0,"orgId":"1064786022882095106","status":0,"unitType":1}}]
             * belongDepartments : [0,1000,"1064786022882095106"]
             * birthday : 2018-06-01 00:00:00
             * defaultUser : {"accId":"979985982284677121","accountEntity":{"$ref":".."},"companyAdmin":false,"companyEntity":{"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0,"verifyStatus":0},"companyId":0,"departmentEntity":{"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0},"departmentId":0,"status":3,"superAdmin":false,"sysAdmin":false,"topCompanyEntity":{"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0,"verifyStatus":0},"topCompanyId":0,"userId":"979985982318231553","userType":11}
             * email : 13800138002@qq.com
             * gender : 0
             * idCard : 110101200001015372
             * mobile : 13800138002
             * nickName : 管理员
             * nullUser : 979985982318231553
             * qrCode : account/qr/4ad0548b6f5b434daf62884c13266e2d.png
             * realName : 管理员
             * regTime : 2018-03-31 15:37:20
             * simplePwd : false
             * status : 3
             */

            private String accId;
            private int accType;
            private AccountExtInfoBean accountExtInfo;
            private String address;
            private String areaCode;
            private String avatar;
            private String birthday;
            private DefaultUserBean defaultUser;
            private String email;
            private int gender;
            private String idCard;
            private String mobile;
            private String nickName;
            private String nullUser;
            private String qrCode;
            private String realName;
            private String regTime;
            private boolean simplePwd;
            private int status;
            private List<Integer> allowCurDomainCompanys;
            private List<BelongCompanysBean> belongCompanys;
            private List<Integer> belongDepartments;

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

            public AccountExtInfoBean getAccountExtInfo() {
                return accountExtInfo;
            }

            public void setAccountExtInfo(AccountExtInfoBean accountExtInfo) {
                this.accountExtInfo = accountExtInfo;
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

            public DefaultUserBean getDefaultUser() {
                return defaultUser;
            }

            public void setDefaultUser(DefaultUserBean defaultUser) {
                this.defaultUser = defaultUser;
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

            public String getNullUser() {
                return nullUser;
            }

            public void setNullUser(String nullUser) {
                this.nullUser = nullUser;
            }

            public String getQrCode() {
                return qrCode;
            }

            public void setQrCode(String qrCode) {
                this.qrCode = qrCode;
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

            public List<Integer> getAllowCurDomainCompanys() {
                return allowCurDomainCompanys;
            }

            public void setAllowCurDomainCompanys(List<Integer> allowCurDomainCompanys) {
                this.allowCurDomainCompanys = allowCurDomainCompanys;
            }

            public List<BelongCompanysBean> getBelongCompanys() {
                return belongCompanys;
            }

            public void setBelongCompanys(List<BelongCompanysBean> belongCompanys) {
                this.belongCompanys = belongCompanys;
            }

            public List<Integer> getBelongDepartments() {
                return belongDepartments;
            }

            public void setBelongDepartments(List<Integer> belongDepartments) {
                this.belongDepartments = belongDepartments;
            }

            public static class AccountExtInfoBean {
                /**
                 * accId : 979985982284677121
                 * designNum : 0
                 * evaluateNum : 0
                 * generalEvaluation : 1
                 * goodNum : 0
                 * goodRate : 10000
                 * id : 358
                 * installNum : 0
                 * item1 : 400
                 * item2 : 400
                 * item3 : 400
                 * item4 : 400
                 * item5 : 400
                 * lat : 39.925682
                 * lon : 116.60795
                 * maintainCount : 0
                 * placeCode : 3.11.1.5
                 * publicPraise : 100
                 * qualification : 0
                 * receiveCount : 0
                 * repairCount : 0
                 * trainStatus : 0
                 * verifyStatus : 0
                 * workStatus : 0
                 * workerNumber : 1901151717337
                 */

                private String accId;
                private int designNum;
                private int evaluateNum;
                private int generalEvaluation;
                private int goodNum;
                private int goodRate;
                private int id;
                private int installNum;
                private int item1;
                private int item2;
                private int item3;
                private int item4;
                private int item5;
                private String lat;
                private String lon;
                private int maintainCount;
                private String placeCode;
                private int publicPraise;
                private int qualification;
                private int receiveCount;
                private int repairCount;
                private int trainStatus;
                private int verifyStatus;
                private int workStatus;
                private String workerNumber;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public int getDesignNum() {
                    return designNum;
                }

                public void setDesignNum(int designNum) {
                    this.designNum = designNum;
                }

                public int getEvaluateNum() {
                    return evaluateNum;
                }

                public void setEvaluateNum(int evaluateNum) {
                    this.evaluateNum = evaluateNum;
                }

                public int getGeneralEvaluation() {
                    return generalEvaluation;
                }

                public void setGeneralEvaluation(int generalEvaluation) {
                    this.generalEvaluation = generalEvaluation;
                }

                public int getGoodNum() {
                    return goodNum;
                }

                public void setGoodNum(int goodNum) {
                    this.goodNum = goodNum;
                }

                public int getGoodRate() {
                    return goodRate;
                }

                public void setGoodRate(int goodRate) {
                    this.goodRate = goodRate;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getInstallNum() {
                    return installNum;
                }

                public void setInstallNum(int installNum) {
                    this.installNum = installNum;
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

                public int getMaintainCount() {
                    return maintainCount;
                }

                public void setMaintainCount(int maintainCount) {
                    this.maintainCount = maintainCount;
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

                public int getQualification() {
                    return qualification;
                }

                public void setQualification(int qualification) {
                    this.qualification = qualification;
                }

                public int getReceiveCount() {
                    return receiveCount;
                }

                public void setReceiveCount(int receiveCount) {
                    this.receiveCount = receiveCount;
                }

                public int getRepairCount() {
                    return repairCount;
                }

                public void setRepairCount(int repairCount) {
                    this.repairCount = repairCount;
                }

                public int getTrainStatus() {
                    return trainStatus;
                }

                public void setTrainStatus(int trainStatus) {
                    this.trainStatus = trainStatus;
                }

                public int getVerifyStatus() {
                    return verifyStatus;
                }

                public void setVerifyStatus(int verifyStatus) {
                    this.verifyStatus = verifyStatus;
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
            }

            public static class DefaultUserBean {
                /**
                 * accId : 979985982284677121
                 * accountEntity : {"$ref":".."}
                 * companyAdmin : false
                 * companyEntity : {"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0,"verifyStatus":0}
                 * companyId : 0
                 * departmentEntity : {"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0}
                 * departmentId : 0
                 * status : 3
                 * superAdmin : false
                 * sysAdmin : false
                 * topCompanyEntity : {"companyId":0,"countStaff":0,"level":1,"orgCode":"c","orgId":0,"orgName":"个人","orgType":0,"topCompanyId":0,"verifyStatus":0}
                 * topCompanyId : 0
                 * userId : 979985982318231553
                 * userType : 11
                 */

                private String accId;
                private AccountEntityBean accountEntity;
                private boolean companyAdmin;
                private CompanyEntityBean companyEntity;
                private int companyId;
                private DepartmentEntityBean departmentEntity;
                private int departmentId;
                private int status;
                private boolean superAdmin;
                private boolean sysAdmin;
                private TopCompanyEntityBean topCompanyEntity;
                private int topCompanyId;
                private String userId;
                private int userType;

                public String getAccId() {
                    return accId;
                }

                public void setAccId(String accId) {
                    this.accId = accId;
                }

                public AccountEntityBean getAccountEntity() {
                    return accountEntity;
                }

                public void setAccountEntity(AccountEntityBean accountEntity) {
                    this.accountEntity = accountEntity;
                }

                public boolean isCompanyAdmin() {
                    return companyAdmin;
                }

                public void setCompanyAdmin(boolean companyAdmin) {
                    this.companyAdmin = companyAdmin;
                }

                public CompanyEntityBean getCompanyEntity() {
                    return companyEntity;
                }

                public void setCompanyEntity(CompanyEntityBean companyEntity) {
                    this.companyEntity = companyEntity;
                }

                public int getCompanyId() {
                    return companyId;
                }

                public void setCompanyId(int companyId) {
                    this.companyId = companyId;
                }

                public DepartmentEntityBean getDepartmentEntity() {
                    return departmentEntity;
                }

                public void setDepartmentEntity(DepartmentEntityBean departmentEntity) {
                    this.departmentEntity = departmentEntity;
                }

                public int getDepartmentId() {
                    return departmentId;
                }

                public void setDepartmentId(int departmentId) {
                    this.departmentId = departmentId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public boolean isSuperAdmin() {
                    return superAdmin;
                }

                public void setSuperAdmin(boolean superAdmin) {
                    this.superAdmin = superAdmin;
                }

                public boolean isSysAdmin() {
                    return sysAdmin;
                }

                public void setSysAdmin(boolean sysAdmin) {
                    this.sysAdmin = sysAdmin;
                }

                public TopCompanyEntityBean getTopCompanyEntity() {
                    return topCompanyEntity;
                }

                public void setTopCompanyEntity(TopCompanyEntityBean topCompanyEntity) {
                    this.topCompanyEntity = topCompanyEntity;
                }

                public int getTopCompanyId() {
                    return topCompanyId;
                }

                public void setTopCompanyId(int topCompanyId) {
                    this.topCompanyId = topCompanyId;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public int getUserType() {
                    return userType;
                }

                public void setUserType(int userType) {
                    this.userType = userType;
                }

                public static class AccountEntityBean {
                    /**
                     * $ref : ..
                     */

                    private String $ref;

                    public String get$ref() {
                        return $ref;
                    }

                    public void set$ref(String $ref) {
                        this.$ref = $ref;
                    }
                }

                public static class CompanyEntityBean {
                    /**
                     * companyId : 0
                     * countStaff : 0
                     * level : 1
                     * orgCode : c
                     * orgId : 0
                     * orgName : 个人
                     * orgType : 0
                     * topCompanyId : 0
                     * verifyStatus : 0
                     */

                    private int companyId;
                    private int countStaff;
                    private int level;
                    private String orgCode;
                    private int orgId;
                    private String orgName;
                    private int orgType;
                    private int topCompanyId;
                    private int verifyStatus;

                    public int getCompanyId() {
                        return companyId;
                    }

                    public void setCompanyId(int companyId) {
                        this.companyId = companyId;
                    }

                    public int getCountStaff() {
                        return countStaff;
                    }

                    public void setCountStaff(int countStaff) {
                        this.countStaff = countStaff;
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

                    public int getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(int orgId) {
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

                    public int getTopCompanyId() {
                        return topCompanyId;
                    }

                    public void setTopCompanyId(int topCompanyId) {
                        this.topCompanyId = topCompanyId;
                    }

                    public int getVerifyStatus() {
                        return verifyStatus;
                    }

                    public void setVerifyStatus(int verifyStatus) {
                        this.verifyStatus = verifyStatus;
                    }
                }

                public static class DepartmentEntityBean {
                    /**
                     * companyId : 0
                     * countStaff : 0
                     * level : 1
                     * orgCode : c
                     * orgId : 0
                     * orgName : 个人
                     * orgType : 0
                     * topCompanyId : 0
                     */

                    private int companyId;
                    private int countStaff;
                    private int level;
                    private String orgCode;
                    private int orgId;
                    private String orgName;
                    private int orgType;
                    private int topCompanyId;

                    public int getCompanyId() {
                        return companyId;
                    }

                    public void setCompanyId(int companyId) {
                        this.companyId = companyId;
                    }

                    public int getCountStaff() {
                        return countStaff;
                    }

                    public void setCountStaff(int countStaff) {
                        this.countStaff = countStaff;
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

                    public int getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(int orgId) {
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

                    public int getTopCompanyId() {
                        return topCompanyId;
                    }

                    public void setTopCompanyId(int topCompanyId) {
                        this.topCompanyId = topCompanyId;
                    }
                }

                public static class TopCompanyEntityBean {
                    /**
                     * companyId : 0
                     * countStaff : 0
                     * level : 1
                     * orgCode : c
                     * orgId : 0
                     * orgName : 个人
                     * orgType : 0
                     * topCompanyId : 0
                     * verifyStatus : 0
                     */

                    private int companyId;
                    private int countStaff;
                    private int level;
                    private String orgCode;
                    private int orgId;
                    private String orgName;
                    private int orgType;
                    private int topCompanyId;
                    private int verifyStatus;

                    public int getCompanyId() {
                        return companyId;
                    }

                    public void setCompanyId(int companyId) {
                        this.companyId = companyId;
                    }

                    public int getCountStaff() {
                        return countStaff;
                    }

                    public void setCountStaff(int countStaff) {
                        this.countStaff = countStaff;
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

                    public int getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(int orgId) {
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

                    public int getTopCompanyId() {
                        return topCompanyId;
                    }

                    public void setTopCompanyId(int topCompanyId) {
                        this.topCompanyId = topCompanyId;
                    }

                    public int getVerifyStatus() {
                        return verifyStatus;
                    }

                    public void setVerifyStatus(int verifyStatus) {
                        this.verifyStatus = verifyStatus;
                    }
                }
            }

            public static class BelongCompanysBean {
                /**
                 * countStaff : 0
                 * level : 1
                 * orgCode : c
                 * orgId : 0
                 * orgName : 个人
                 * orgType : 0
                 * adminUserId : 0
                 * orgUnitEntity : {"adminUserId":0,"defaultAddress":"永外大街车站路12号北京南站","defaultLat":"39.865208","defaultLon":"116.378596","defaultPlaceCode":"3.11.1.6","isEnable":0,"orgId":1000,"unitType":0}
                 */

                private int countStaff;
                private int level;
                private String orgCode;
                private int orgId;
                private String orgName;
                private int orgType;
                private int adminUserId;
                private OrgUnitEntityBean orgUnitEntity;

                public int getCountStaff() {
                    return countStaff;
                }

                public void setCountStaff(int countStaff) {
                    this.countStaff = countStaff;
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

                public int getOrgId() {
                    return orgId;
                }

                public void setOrgId(int orgId) {
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

                public int getAdminUserId() {
                    return adminUserId;
                }

                public void setAdminUserId(int adminUserId) {
                    this.adminUserId = adminUserId;
                }

                public OrgUnitEntityBean getOrgUnitEntity() {
                    return orgUnitEntity;
                }

                public void setOrgUnitEntity(OrgUnitEntityBean orgUnitEntity) {
                    this.orgUnitEntity = orgUnitEntity;
                }

                public static class OrgUnitEntityBean {
                    /**
                     * adminUserId : 0
                     * defaultAddress : 永外大街车站路12号北京南站
                     * defaultLat : 39.865208
                     * defaultLon : 116.378596
                     * defaultPlaceCode : 3.11.1.6
                     * isEnable : 0
                     * orgId : 1000
                     * unitType : 0
                     */

                    private int adminUserId;
                    private String defaultAddress;
                    private String defaultLat;
                    private String defaultLon;
                    private String defaultPlaceCode;
                    private int isEnable;
                    private int orgId;
                    private int unitType;

                    public int getAdminUserId() {
                        return adminUserId;
                    }

                    public void setAdminUserId(int adminUserId) {
                        this.adminUserId = adminUserId;
                    }

                    public String getDefaultAddress() {
                        return defaultAddress;
                    }

                    public void setDefaultAddress(String defaultAddress) {
                        this.defaultAddress = defaultAddress;
                    }

                    public String getDefaultLat() {
                        return defaultLat;
                    }

                    public void setDefaultLat(String defaultLat) {
                        this.defaultLat = defaultLat;
                    }

                    public String getDefaultLon() {
                        return defaultLon;
                    }

                    public void setDefaultLon(String defaultLon) {
                        this.defaultLon = defaultLon;
                    }

                    public String getDefaultPlaceCode() {
                        return defaultPlaceCode;
                    }

                    public void setDefaultPlaceCode(String defaultPlaceCode) {
                        this.defaultPlaceCode = defaultPlaceCode;
                    }

                    public int getIsEnable() {
                        return isEnable;
                    }

                    public void setIsEnable(int isEnable) {
                        this.isEnable = isEnable;
                    }

                    public int getOrgId() {
                        return orgId;
                    }

                    public void setOrgId(int orgId) {
                        this.orgId = orgId;
                    }

                    public int getUnitType() {
                        return unitType;
                    }

                    public void setUnitType(int unitType) {
                        this.unitType = unitType;
                    }
                }
            }
        }

        public static class HistoryListBean {
            /**
             * abhId : 1
             * browseTime : 2019-02-27 16:59:57
             * browseUserId : 979985982318231553
             * questionId : 1
             * questionSketch : 无视频信号
             */

            private int abhId;
            private String browseTime;
            private String browseUserId;
            private int questionId;
            private String questionSketch;

            public int getAbhId() {
                return abhId;
            }

            public void setAbhId(int abhId) {
                this.abhId = abhId;
            }

            public String getBrowseTime() {
                return browseTime;
            }

            public void setBrowseTime(String browseTime) {
                this.browseTime = browseTime;
            }

            public String getBrowseUserId() {
                return browseUserId;
            }

            public void setBrowseUserId(String browseUserId) {
                this.browseUserId = browseUserId;
            }

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public String getQuestionSketch() {
                return questionSketch;
            }

            public void setQuestionSketch(String questionSketch) {
                this.questionSketch = questionSketch;
            }
        }

        public static class LikesListBean {
            /**
             * asCompanyId : 0
             * asId : 12
             * asTopCompanyId : 0
             * asUserId : 979985982318231553
             * id : 22
             * likeCompanyId : 0
             * likeCreateTime : 2019-02-28 16:37:19
             * likeStatus : 0
             * likeTopCompanyId : 0
             * likeUserId : 979985982318231553
             * type : 1
             */

            private int asCompanyId;
            private int asId;
            private int asTopCompanyId;
            private String asUserId;
            private int id;
            private int likeCompanyId;
            private String likeCreateTime;
            private int likeStatus;
            private int likeTopCompanyId;
            private String likeUserId;
            private int type;

            public int getAsCompanyId() {
                return asCompanyId;
            }

            public void setAsCompanyId(int asCompanyId) {
                this.asCompanyId = asCompanyId;
            }

            public int getAsId() {
                return asId;
            }

            public void setAsId(int asId) {
                this.asId = asId;
            }

            public int getAsTopCompanyId() {
                return asTopCompanyId;
            }

            public void setAsTopCompanyId(int asTopCompanyId) {
                this.asTopCompanyId = asTopCompanyId;
            }

            public String getAsUserId() {
                return asUserId;
            }

            public void setAsUserId(String asUserId) {
                this.asUserId = asUserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLikeCompanyId() {
                return likeCompanyId;
            }

            public void setLikeCompanyId(int likeCompanyId) {
                this.likeCompanyId = likeCompanyId;
            }

            public String getLikeCreateTime() {
                return likeCreateTime;
            }

            public void setLikeCreateTime(String likeCreateTime) {
                this.likeCreateTime = likeCreateTime;
            }

            public int getLikeStatus() {
                return likeStatus;
            }

            public void setLikeStatus(int likeStatus) {
                this.likeStatus = likeStatus;
            }

            public int getLikeTopCompanyId() {
                return likeTopCompanyId;
            }

            public void setLikeTopCompanyId(int likeTopCompanyId) {
                this.likeTopCompanyId = likeTopCompanyId;
            }

            public String getLikeUserId() {
                return likeUserId;
            }

            public void setLikeUserId(String likeUserId) {
                this.likeUserId = likeUserId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
}
