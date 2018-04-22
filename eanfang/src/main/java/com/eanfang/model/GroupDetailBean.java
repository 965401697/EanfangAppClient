package com.eanfang.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by O u r on 2018/4/20.
 */

public class GroupDetailBean implements Serializable {

    /**
     * list : [{"accId":2,"avatar":"ce5ade25c6d74d6a9f5ed0c64b1c3579.png","nickName":"李旭","rcloudToken":"DN0jaV3yi9do9vzFDe4Bww3qwon7dFclDIVQwIYTKyYdpIfmlrTWQTGA3faZvvPwMoHn4HImfwmB0X291WXy9Q=="}]
     * group : {"createTime":"2018-04-20 13:25:09","createUser":2,"groupId":35,"groupName":"测试一下","rcloudGroupId":"510b888c6d0f4b55b180c59e91e7a032"}
     */

    private GroupBean group;
    private List<FriendListBean> list;

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public List<FriendListBean> getList() {
        return list;
    }

    public void setList(List<FriendListBean> list) {
        this.list = list;
    }

    public static class GroupBean {
        /**
         * createTime : 2018-04-20 13:25:09
         * createUser : 2
         * groupId : 35
         * groupName : 测试一下
         * rcloudGroupId : 510b888c6d0f4b55b180c59e91e7a032
         */

        private String createTime;
        private String createUser;
        private int groupId;
        private String groupName;
        private String rcloudGroupId;

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

        public String getRcloudGroupId() {
            return rcloudGroupId;
        }

        public void setRcloudGroupId(String rcloudGroupId) {
            this.rcloudGroupId = rcloudGroupId;
        }
    }

}
