package com.eanfang.biz.model;

import java.util.List;

public class Testw {

    private List<EvaluateBean> evaluate;

    public List<EvaluateBean> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<EvaluateBean> evaluate) {
        this.evaluate = evaluate;
    }

    public static class EvaluateBean {
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
