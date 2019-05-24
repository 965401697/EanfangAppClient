package com.eanfang.biz.model;

/**
 * Created by O u r on 2018/4/17.
 */

public class GroupCreatBean {

    /**
     * createTime : 2018-04-17 17:05:58
     * createUser : 984379128553951234
     * groupId : 15
     * groupName : 测试一下
     * rcloudGroupId : 3b85b041f29c4e5da67e4ca0521ab568
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
