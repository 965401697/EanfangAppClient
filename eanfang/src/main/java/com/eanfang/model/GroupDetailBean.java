package com.eanfang.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by O u r on 2018/4/20.
 */

public class GroupDetailBean implements Serializable {


    /**
     * list : [{"accId":"984379128553951234","accountEntity":{"accId":"984379128553951234","avatar":"512ab4856d414eb888a3a36284edfa73.png","nickName":"子武991","rcloudToken":"BZLuIKWUhbyrcAVTeGckHGrBMxym/D7/0IKow6Vk0ISA8rCBDD1waD2rnEuxtPks38uRG0fXyIhQcXx5YTp+HEDiCvb3L7n5nn65tjcnytc=","status":0},"status":0},{"accId":"984353134128418818","accountEntity":{"accId":"984353134128418818","avatar":"76307f5882ed4fbab37d883c385192db.png","nickName":"子武30","rcloudToken":"+M6UoZYVA/yHUyNez3sk7c7EGnNLVOeEfMo9eVLArDyNuDnIhn5imHi4t5+ZkgWenhw1HENt5T64ZOg5Ad4nn1cMV1Q/+jWwIFNnzqYc5qo=","status":0},"status":0},{"accId":"984655075047972865","accountEntity":{"accId":"984655075047972865","avatar":"c86b2b89a2384dc58f67ba69c8801ea5.png","nickName":"子武5131","rcloudToken":"Q8JXP/DnTgEWoPci7HgFhs7EGnNLVOeEfMo9eVLArDyNuDnIhn5imP2eOzu6KAHj0SQA0tMUbFOEDYms/xw7VOVbMoWsD+9JkPDME/A59uI=","status":0},"status":0},{"accId":"958615131317600257","accountEntity":{"accId":"958615131317600257","avatar":"658c044abd20497195aafc25fc43e995.png","nickName":"张监控","rcloudToken":"lULXIhorh5txbK5IpJ3/sGrBMxym/D7/0IKow6Vk0ISA8rCBDD1waG36+3Tx2qY5plXXshRZ+I2RzvYwcppon8X1Lf7L+cJiY1WG3Nszjrw=","status":0},"status":0}]
     * group : {"createTime":"2018-05-07 17:36:00","createUser":"984655075047972865","groupId":111,"groupName":"实施","headPortrait":"9f24f2ed208a4d89aafac920bdbddd63.png","rcloudGroupId":"bc0841de3dd646379f971d403af42ffc"}
     */

    private GroupBean group;
    private List<ListBean> list;

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class GroupBean {
        /**
         * createTime : 2018-05-07 17:36:00
         * createUser : 984655075047972865
         * groupId : 111
         * groupName : 实施
         * headPortrait : 9f24f2ed208a4d89aafac920bdbddd63.png
         * rcloudGroupId : bc0841de3dd646379f971d403af42ffc
         */

        private String createTime;
        private String createUser;
        private int groupId;
        private String groupName;
        private String headPortrait;
        private String rcloudGroupId;
        private String notice;
        private String qrCode;

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getRcloudGroupId() {
            return rcloudGroupId;
        }

        public void setRcloudGroupId(String rcloudGroupId) {
            this.rcloudGroupId = rcloudGroupId;
        }


    }

    public static class ListBean implements Serializable {
        /**
         * accId : 984379128553951234
         * accountEntity : {"accId":"984379128553951234","avatar":"512ab4856d414eb888a3a36284edfa73.png","nickName":"子武991","rcloudToken":"BZLuIKWUhbyrcAVTeGckHGrBMxym/D7/0IKow6Vk0ISA8rCBDD1waD2rnEuxtPks38uRG0fXyIhQcXx5YTp+HEDiCvb3L7n5nn65tjcnytc=","status":0}
         * status : 0
         */

        private String accId;
        private AccountEntityBean accountEntity;
        private int status;
        private int flag;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public static class AccountEntityBean implements Serializable, Comparator<AccountEntityBean> {
            /**
             * accId : 984379128553951234
             * avatar : 512ab4856d414eb888a3a36284edfa73.png
             * nickName : 子武991
             * rcloudToken : BZLuIKWUhbyrcAVTeGckHGrBMxym/D7/0IKow6Vk0ISA8rCBDD1waD2rnEuxtPks38uRG0fXyIhQcXx5YTp+HEDiCvb3L7n5nn65tjcnytc=
             * status : 0
             */

            private String accId;
            private String avatar;
            private String nickName;
            private String rcloudToken;
            private int status;
            private String firstLetter; // 拼音的首字母
            private String pinyin; // 姓名对应的拼音


            public String getPinyin() {
                return pinyin;
            }

            public void setPinyin(String pinyin) {
                this.pinyin = pinyin;
            }

            public String getFirstLetter() {
                return firstLetter;
            }

            public void setFirstLetter(String firstLetter) {
                this.firstLetter = firstLetter;
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

            public String getRcloudToken() {
                return rcloudToken;
            }

            public void setRcloudToken(String rcloudToken) {
                this.rcloudToken = rcloudToken;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            @Override
            public int compare(AccountEntityBean o1, AccountEntityBean o2) {
                //这里主要是用来对数据里面的数据根据ABCDEFG...来排序
                if (o2.getFirstLetter().equals("#")) {
                    return -1;
                } else if (o1.getFirstLetter().equals("#")) {
                    return 1;
                } else {
                    return o1.getFirstLetter().compareTo(o2.getFirstLetter());
                }
            }
        }
    }
}
