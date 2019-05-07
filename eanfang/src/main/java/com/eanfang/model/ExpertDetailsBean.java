package com.eanfang.model;

import java.util.List;

public class ExpertDetailsBean {


    /**
     * expert : {"answerCount":6,"favorableRate":0.66,"item1":5,"item2":5,"item3":5,"item4":5,"item5":5,"responsibleBrand":"AB,东芝（Toshiba）","systemType":"防盗报警,可视对讲","trainStatus":1}
     * evaluateCount : 1
     * follow : 1
     * evaluate : [{"createAccount":{"accId":"979993224590704641","avatar":"55d1aa8292ab4cf7a7d5446cea4a274f.png","nickName":"刘墨迪","realName":"刘墨迪","simplePwd":false},"createUserId":"979993225060466690","describes":"收多了几分微软","favorableRate":1,"userId":"1082922712729456642"}]
     * account : {"accId":"1059326429620158466","avatar":"account/72eb26c502ca4a6d88cd467773192f7d.png","nickName":"test零七","realName":"零六","simplePwd":false}
     */

    private ExpertBean expert;
    private int evaluateCount;
    private int follow;
    private AccountBean account;
    private List<EvaluateBean> evaluate;

    @Override
    public String toString() {
        return "ExpertDetailsBean{" +
                "expert=" + expert +
                ", evaluateCount=" + evaluateCount +
                ", follow=" + follow +
                ", account=" + account +
                ", evaluate=" + evaluate +
                '}';
    }

    public ExpertBean getExpert() {
        return expert;
    }

    public void setExpert(ExpertBean expert) {
        this.expert = expert;
    }

    public int getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(int evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public List<EvaluateBean> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateBean> evaluate) {
        this.evaluate = evaluate;
    }

    public static class ExpertBean {
        @Override
        public String toString() {
            return "ExpertBean{" +
                    "answerCount=" + answerCount +
                    ", favorableRate=" + favorableRate +
                    ", item1=" + item1 +
                    ", item2=" + item2 +
                    ", item3=" + item3 +
                    ", item4=" + item4 +
                    ", item5=" + item5 +
                    ", responsibleBrand='" + responsibleBrand + '\'' +
                    ", systemType='" + systemType + '\'' +
                    ", trainStatus=" + trainStatus +
                    '}';
        }

        /**
         * answerCount : 6
         * favorableRate : 0.66
         * item1 : 5
         * item2 : 5
         * item3 : 5
         * item4 : 5
         * item5 : 5
         * responsibleBrand : AB,东芝（Toshiba）
         * systemType : 防盗报警,可视对讲
         * trainStatus : 1
         */

        private int answerCount;
        private double favorableRate;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private String responsibleBrand;
        private String systemType;
        private String goodRate;

        public String getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(String goodRate) {
            this.goodRate = goodRate;
        }

        private int trainStatus;
        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAnswerCount() {
            return answerCount;
        }

        public void setAnswerCount(int answerCount) {
            this.answerCount = answerCount;
        }

        public double getFavorableRate() {
            return favorableRate;
        }

        public void setFavorableRate(double favorableRate) {
            this.favorableRate = favorableRate;
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

        public String getResponsibleBrand() {
            return responsibleBrand;
        }

        public void setResponsibleBrand(String responsibleBrand) {
            this.responsibleBrand = responsibleBrand;
        }

        public String getSystemType() {
            return systemType;
        }

        public void setSystemType(String systemType) {
            this.systemType = systemType;
        }

        public int getTrainStatus() {
            return trainStatus;
        }

        public void setTrainStatus(int trainStatus) {
            this.trainStatus = trainStatus;
        }
    }

    public static class AccountBean {
        @Override
        public String toString() {
            return "AccountBean{" +
                    "accId='" + accId + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", realName='" + realName + '\'' +
                    ", simplePwd=" + simplePwd +
                    '}';
        }

        /**
         * accId : 1059326429620158466
         * avatar : account/72eb26c502ca4a6d88cd467773192f7d.png
         * nickName : test零七
         * realName : 零六
         * simplePwd : false
         */

        private String accId;
        private String avatar;
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

    public static class EvaluateBean {
        @Override
        public String toString() {
            return "EvaluateBean{" +
                    "createAccount=" + createAccount +
                    ", createUserId='" + createUserId + '\'' +
                    ", describes='" + describes + '\'' +
                    ", favorableRate=" + favorableRate +
                    ", userId='" + userId + '\'' +
                    '}';
        }

        /**
         * createAccount : {"accId":"979993224590704641","avatar":"55d1aa8292ab4cf7a7d5446cea4a274f.png","nickName":"刘墨迪","realName":"刘墨迪","simplePwd":false}
         * createUserId : 979993225060466690
         * describes : 收多了几分微软
         * favorableRate : 1
         * userId : 1082922712729456642
         */

        private CreateAccountBean createAccount;
        private String createUserId;
        private String describes;
        private int favorableRate;
        private String userId;

        public CreateAccountBean getCreateAccount() {
            return createAccount;
        }

        public void setCreateAccount(CreateAccountBean createAccount) {
            this.createAccount = createAccount;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public int getFavorableRate() {
            return favorableRate;
        }

        public void setFavorableRate(int favorableRate) {
            this.favorableRate = favorableRate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class CreateAccountBean {
            /**
             * accId : 979993224590704641
             * avatar : 55d1aa8292ab4cf7a7d5446cea4a274f.png
             * nickName : 刘墨迪
             * realName : 刘墨迪
             * simplePwd : false
             */

            private String accId;
            private String avatar;
            private String nickName;
            private String realName;
            private boolean simplePwd;

            @Override
            public String toString() {
                return "CreateAccountBean{" +
                        "accId='" + accId + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", nickName='" + nickName + '\'' +
                        ", realName='" + realName + '\'' +
                        ", simplePwd=" + simplePwd +
                        '}';
            }

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
    }
}
