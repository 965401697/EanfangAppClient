package com.eanfang.model;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsBean {

    /**
     * groupId : 24
     * groupName : 测试一下
     * rcloudGroupId : 37f32609109f48609a1ee5f896550bcf
     */

    private int groupId;
    private String groupName;
    private String groupIcon;
    private String rcloudGroupId;
    private String headPortrait;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
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
