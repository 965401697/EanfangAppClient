package com.eanfang.biz.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author liangkailun
 * Date ：2019/4/3
 * Describe :用户主页信息
 */
public class UserHomePageBean implements Serializable {

    /**
     * 好友状态 1：不是好友 0：是好友
     */
    private int friendStatus;
    /**
     * 关注状态 1：未关注 0：已关注
     */
    private int followStatus;

    /**
     * 用户信息
     */
    private AccountBean account;
    /**
     * 工作经历
     */
    private List<JobListBean> jobList;

    private CompanyInfoBean companyInfo;

    /**
     * 返回好友状态
     *
     * @return true：是好友 false：不是好友
     */
    public boolean getFriendStatus() {
        return friendStatus == 0;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    /**
     * 返回关注状态
     *
     * @return 0：关注 1：未关注
     */
    public int getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(int followStatus) {
        this.followStatus = followStatus;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public List<JobListBean> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobListBean> jobList) {
        this.jobList = jobList;
    }

    public CompanyInfoBean getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInfoBean companyInfo) {
        this.companyInfo = companyInfo;
    }

    public static class AccountBean {

        /**
         * 用户accId
         */
        private String accId;
        /**
         * 地区编码
         */
        private String areaCode;
        /**
         * 地区信息
         */
        private String areaInfo;
        /**
         * 头像
         */
        private String avatar;
        /**
         * 生日
         */
        private String birthMonthDay;
        private String birthday;
        /**
         * 性别 0：女  1：男
         */
        private int gender;
        /**
         * 简介
         */
        private String intro;
        /**
         * 用户昵称
         */
        private String nickName;
        /**
         * 真实姓名
         */
        private String realName;
        private boolean simplePwd;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaInfo() {
            return areaInfo;
        }

        public void setAreaInfo(String areaInfo) {
            this.areaInfo = areaInfo;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBirthMonthDay() {
            return birthMonthDay;
        }

        public void setBirthMonthDay(String birthMonthDay) {
            this.birthMonthDay = birthMonthDay;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
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

        @Override
        public String toString() {
            return "AccountBean{" +
                    "accId='" + accId + '\'' +
                    ", areaCode='" + areaCode + '\'' +
                    ", areaInfo='" + areaInfo + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", birthMonthDay='" + birthMonthDay + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", gender=" + gender +
                    ", intro='" + intro + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", realName='" + realName + '\'' +
                    ", simplePwd=" + simplePwd +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserHomePageBean{" +
                "friendStatus=" + friendStatus +
                ", followStatus=" + followStatus +
                ", account=" + account +
                ", jobList=" + jobList +
                ", companyInfo=" + companyInfo +
                '}';
    }

    public static class JobListBean {

        /**
         * 职位结束时间
         */
        private String endDate;
        private String companyName;
        private String jobIntro;
        private int type;
        private String accId;
        private String cardPics;
        private String beginTime;
        private String endTime;
        private int id;
        /**
         * 职位
         */
        private String job;
        private String workplace;
        /**
         * 职位开始时间
         */
        private String startDate;
        private String companyImg;

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getJobIntro() {
            return jobIntro;
        }

        public void setJobIntro(String jobIntro) {
            this.jobIntro = jobIntro;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public String getCardPics() {
            return cardPics;
        }

        public void setCardPics(String cardPics) {
            this.cardPics = cardPics;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getWorkplace() {
            return workplace;
        }

        public void setWorkplace(String workplace) {
            this.workplace = workplace;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getCompanyImg() {
            return companyImg;
        }

        public void setCompanyImg(String companyImg) {
            this.companyImg = companyImg;
        }

        @Override
        public String toString() {
            return "JobListBean{" +
                    "endDate='" + endDate + '\'' +
                    ", companyName='" + companyName + '\'' +
                    ", jobIntro='" + jobIntro + '\'' +
                    ", type=" + type +
                    ", accId='" + accId + '\'' +
                    ", cardPics='" + cardPics + '\'' +
                    ", beginTime='" + beginTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", id=" + id +
                    ", job='" + job + '\'' +
                    ", workplace='" + workplace + '\'' +
                    ", startDate='" + startDate + '\'' +
                    ", companyImg='" + companyImg + '\'' +
                    '}';
        }
    }

    public static class CompanyInfoBean {

        private String accId;
        private boolean companyAdmin;
        private String companyId;
        private boolean superAdmin;
        private boolean sysAdmin;
        private String topCompanyId;
        private String userId;

        public String getAccId() {
            return accId;
        }

        public void setAccId(String accId) {
            this.accId = accId;
        }

        public boolean isCompanyAdmin() {
            return companyAdmin;
        }

        public void setCompanyAdmin(boolean companyAdmin) {
            this.companyAdmin = companyAdmin;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
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

        public String getTopCompanyId() {
            return topCompanyId;
        }

        public void setTopCompanyId(String topCompanyId) {
            this.topCompanyId = topCompanyId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "CompanyInfoBean{" +
                    "accId='" + accId + '\'' +
                    ", companyAdmin=" + companyAdmin +
                    ", companyId='" + companyId + '\'' +
                    ", superAdmin=" + superAdmin +
                    ", sysAdmin=" + sysAdmin +
                    ", topCompanyId='" + topCompanyId + '\'' +
                    ", userId='" + userId + '\'' +
                    '}';
        }
    }
}
