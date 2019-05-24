package com.eanfang.biz.model;

import java.util.List;

public class TechnicianDetailsBean {

    @Override
    public String toString() {
        return "TechnicianDetailsBean{" +
                "follow=" + follow +
                ", evaluate=" + evaluate +
                ", account=" + account +
                ", techWorker=" + techWorker +
                ", cases=" + cases +
                ", educationList=" + educationList +
                ", areaList=" + areaList +
                ", tagList=" + tagList +
                ", sysList=" + sysList +
                ", qualificationList=" + qualificationList +
                ", honorList=" + honorList +
                ", bizList=" + bizList +
                ", jobList=" + jobList +
                '}';
    }

    /**
     * cases : [{"budget":3,"budgetCost":"5千-1万","businessOneCode":"1.5","businessOneId":14,"businessOneName":"公共广播","connector":"易安防","newOrder":0}]
     * educationList : [{"beginDate":"2019-04","beginTime":"2019-04-26 09:41:34","endDate":"2019-04","endTime":"2019-04-26 09:41:38","majorName":"测试专业","schoolName":"测试培训机构"}]
     * areaList : ["北京","天津","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江省","上海","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆","四川省","贵州省","云南省","西藏自治区","陕西省"]
     * follow : 1
     * tagList : ["安装","低压","安全管理 "]
     * sysList : ["维修","设计","监理","分发包","保养"]
     * qualificationList : [{"awardOrg":"测试机构","certificateName":"测试技师证书","certificatePics":"default/default_avatar.png","endDate":"2019-04-26","endTime":"2019-04-26 09:34:28"}]
     * honorList : [{"awardDate":"2019-04","awardOrg":"测试颁发机构","awardTime":"2019-04-26 09:39:31","honorName":"测试荣誉证书","honorPics":"default/default_avatar.png"}]
     * bizList : ["电视监控","防盗报警","门禁、一卡通","可视对讲","停车场","EAS"]
     * jobList : [{"beginTime":"2019-04-26 09:38:15","companyName":"测试公司","endDate":"2019-04","endTime":"2019-04-26 09:38:18","job":"测试职位","startDate":"2019-04"}]
     * evaluate : {"accId":"979993411866378241","designNum":73,"evaluateNum":4,"goodRate":275000,"installNum":95,"item1":483,"item2":433,"item3":383,"item4":383,"item5":433,"publicPraise":124,"qualification":0,"repairCount":145,"trainStatus":1,"verifyStatus":0}
     * account : {"accId":"979993411866378241","avatar":"c29039799c434a6ab1c6fae0aa1e7fb7.png","mobile":"18600134480","nickName":"浮沉不定","realName":"刘保恩a","simplePwd":false}
     * techWorker : {"intro":"浮沉不定","workingYear":3}
     */

    private int follow;
    private EvaluateBean evaluate;
    private AccountBean account;
    private TechWorkerBean techWorker;
    private List<CasesBean> cases;
    private List<EducationListBean> educationList;
    private List<String> areaList;
    private List<String> tagList;
    private List<String> sysList;
    private List<QualificationListBean> qualificationList;
    private List<HonorListBean> honorList;
    private List<String> bizList;
    private List<JobListBean> jobList;

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public EvaluateBean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(EvaluateBean evaluate) {
        this.evaluate = evaluate;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public TechWorkerBean getTechWorker() {
        return techWorker;
    }

    public void setTechWorker(TechWorkerBean techWorker) {
        this.techWorker = techWorker;
    }

    public List<CasesBean> getCases() {
        return cases;
    }

    public void setCases(List<CasesBean> cases) {
        this.cases = cases;
    }

    public List<EducationListBean> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationListBean> educationList) {
        this.educationList = educationList;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public List<String> getSysList() {
        return sysList;
    }

    public void setSysList(List<String> sysList) {
        this.sysList = sysList;
    }

    public List<QualificationListBean> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<QualificationListBean> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public List<HonorListBean> getHonorList() {
        return honorList;
    }

    public void setHonorList(List<HonorListBean> honorList) {
        this.honorList = honorList;
    }

    public List<String> getBizList() {
        return bizList;
    }

    public void setBizList(List<String> bizList) {
        this.bizList = bizList;
    }

    public List<JobListBean> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobListBean> jobList) {
        this.jobList = jobList;
    }

    public static class EvaluateBean {
        @Override
        public String toString() {
            return "EvaluateBean{" +
                    "accId='" + accId + '\'' +
                    ", designNum=" + designNum +
                    ", evaluateNum=" + evaluateNum +
                    ", goodRate=" + goodRate +
                    ", installNum=" + installNum +
                    ", item1=" + item1 +
                    ", item2=" + item2 +
                    ", item3=" + item3 +
                    ", item4=" + item4 +
                    ", item5=" + item5 +
                    ", publicPraise=" + publicPraise +
                    ", qualification=" + qualification +
                    ", repairCount=" + repairCount +
                    ", trainStatus=" + trainStatus +
                    ", verifyStatus=" + verifyStatus +
                    '}';
        }

        /**
         * accId : 979993411866378241
         * designNum : 73
         * evaluateNum : 4
         * goodRate : 275000
         * installNum : 95
         * item1 : 483
         * item2 : 433
         * item3 : 383
         * item4 : 383
         * item5 : 433
         * publicPraise : 124
         * qualification : 0
         * repairCount : 145
         * trainStatus : 1
         * verifyStatus : 0
         */

        private String accId;
        private int designNum;
        private int evaluateNum;
        private int goodRate;
        private int installNum;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private int publicPraise;
        private int qualification;
        private int repairCount;
        private int trainStatus;
        private int verifyStatus;

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

        public int getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(int goodRate) {
            this.goodRate = goodRate;
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
    }

    public static class AccountBean {
        @Override
        public String toString() {
            return "AccountBean{" +
                    "accId='" + accId + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", realName='" + realName + '\'' +
                    ", simplePwd=" + simplePwd +
                    '}';
        }

        /**
         * accId : 979993411866378241
         * avatar : c29039799c434a6ab1c6fae0aa1e7fb7.png
         * mobile : 18600134480
         * nickName : 浮沉不定
         * realName : 刘保恩a
         * simplePwd : false
         */

        private String accId;
        private String avatar;
        private String mobile;
        private String nickName;
        private String realName;
        private boolean simplePwd;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public boolean isSimplePwd() {
            return simplePwd;
        }

        public void setSimplePwd(boolean simplePwd) {
            this.simplePwd = simplePwd;
        }
    }

    public static class TechWorkerBean {
        @Override
        public String toString() {
            return "TechWorkerBean{" +
                    "intro='" + intro + '\'' +
                    ", workingYear=" + workingYear +
                    '}';
        }

        /**
         * intro : 浮沉不定
         * workingYear : 3
         */

        private String intro;
        private int workingYear;

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getWorkingYear() {
            return workingYear;
        }

        public void setWorkingYear(int workingYear) {
            this.workingYear = workingYear;
        }
    }

    public static class CasesBean {
        @Override
        public String toString() {
            return "CasesBean{" +
                    "budget=" + budget +
                    ", budgetCost='" + budgetCost + '\'' +
                    ", businessOneCode='" + businessOneCode + '\'' +
                    ", businessOneId=" + businessOneId +
                    ", businessOneName='" + businessOneName + '\'' +
                    ", connector='" + connector + '\'' +
                    ", newOrder=" + newOrder +
                    '}';
        }

        /**
         * budget : 3
         * budgetCost : 5千-1万
         * businessOneCode : 1.5
         * businessOneId : 14
         * businessOneName : 公共广播
         * connector : 易安防
         * newOrder : 0
         */

        private int budget;
        private String budgetCost;
        private String businessOneCode;
        private int businessOneId;
        private String businessOneName;
        private String connector;
        private int newOrder;

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getBudgetCost() {
            return budgetCost;
        }

        public void setBudgetCost(String budgetCost) {
            this.budgetCost = budgetCost;
        }

        public String getBusinessOneCode() {
            return businessOneCode;
        }

        public void setBusinessOneCode(String businessOneCode) {
            this.businessOneCode = businessOneCode;
        }

        public int getBusinessOneId() {
            return businessOneId;
        }

        public void setBusinessOneId(int businessOneId) {
            this.businessOneId = businessOneId;
        }

        public String getBusinessOneName() {
            return businessOneName;
        }

        public void setBusinessOneName(String businessOneName) {
            this.businessOneName = businessOneName;
        }

        public String getConnector() {
            return connector;
        }

        public void setConnector(String connector) {
            this.connector = connector;
        }

        public int getNewOrder() {
            return newOrder;
        }

        public void setNewOrder(int newOrder) {
            this.newOrder = newOrder;
        }
    }

    public static class EducationListBean {
        @Override
        public String toString() {
            return "EducationListBean{" +
                    "beginDate='" + beginDate + '\'' +
                    ", beginTime='" + beginTime + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", majorName='" + majorName + '\'' +
                    ", schoolName='" + schoolName + '\'' +
                    '}';
        }

        /**
         * beginDate : 2019-04
         * beginTime : 2019-04-26 09:41:34
         * endDate : 2019-04
         * endTime : 2019-04-26 09:41:38
         * majorName : 测试专业
         * schoolName : 测试培训机构
         */

        private String beginDate;
        private String beginTime;
        private String endDate;
        private String endTime;
        private String majorName;
        private String schoolName;

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getMajorName() {
            return majorName;
        }

        public void setMajorName(String majorName) {
            this.majorName = majorName;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }
    }

    public static class QualificationListBean {
        @Override
        public String toString() {
            return "QualificationListBean{" +
                    "awardOrg='" + awardOrg + '\'' +
                    ", certificateName='" + certificateName + '\'' +
                    ", certificatePics='" + certificatePics + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }

        /**
         * awardOrg : 测试机构
         * certificateName : 测试技师证书
         * certificatePics : default/default_avatar.png
         * endDate : 2019-04-26
         * endTime : 2019-04-26 09:34:28
         */

        private String awardOrg;
        private String certificateName;
        private String certificatePics;
        private String endDate;
        private String endTime;

        public String getAwardOrg() {
            return awardOrg;
        }

        public void setAwardOrg(String awardOrg) {
            this.awardOrg = awardOrg;
        }

        public String getCertificateName() {
            return certificateName;
        }

        public void setCertificateName(String certificateName) {
            this.certificateName = certificateName;
        }

        public String getCertificatePics() {
            return certificatePics;
        }

        public void setCertificatePics(String certificatePics) {
            this.certificatePics = certificatePics;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

    public static class HonorListBean {
        @Override
        public String toString() {
            return "HonorListBean{" +
                    "awardDate='" + awardDate + '\'' +
                    ", awardOrg='" + awardOrg + '\'' +
                    ", awardTime='" + awardTime + '\'' +
                    ", honorName='" + honorName + '\'' +
                    ", honorPics='" + honorPics + '\'' +
                    '}';
        }

        /**
         * awardDate : 2019-04
         * awardOrg : 测试颁发机构
         * awardTime : 2019-04-26 09:39:31
         * honorName : 测试荣誉证书
         * honorPics : default/default_avatar.png
         */

        private String awardDate;
        private String awardOrg;
        private String awardTime;
        private String honorName;
        private String honorPics;

        public String getAwardDate() {
            return awardDate;
        }

        public void setAwardDate(String awardDate) {
            this.awardDate = awardDate;
        }

        public String getAwardOrg() {
            return awardOrg;
        }

        public void setAwardOrg(String awardOrg) {
            this.awardOrg = awardOrg;
        }

        public String getAwardTime() {
            return awardTime;
        }

        public void setAwardTime(String awardTime) {
            this.awardTime = awardTime;
        }

        public String getHonorName() {
            return honorName;
        }

        public void setHonorName(String honorName) {
            this.honorName = honorName;
        }

        public String getHonorPics() {
            return honorPics;
        }

        public void setHonorPics(String honorPics) {
            this.honorPics = honorPics;
        }
    }

    public static class JobListBean {
        /**
         * beginTime : 2019-04-26 09:38:15
         * companyName : 测试公司
         * endDate : 2019-04
         * endTime : 2019-04-26 09:38:18
         * job : 测试职位
         * startDate : 2019-04
         */

        private String beginTime;
        private String companyName;
        private String endDate;
        private String endTime;
        private String job;
        private String startDate;

        @Override
        public String toString() {
            return "JobListBean{" +
                    "beginTime='" + beginTime + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", job='" + job + '\'' +
                    ", startDate='" + startDate + '\'' +
                    '}';
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
