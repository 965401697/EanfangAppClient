package com.eanfang.model;

import java.util.Comparator;

/**
 * Created by O u r on 2018/4/18.
 */

public class GroupsBean implements Comparator<GroupsBean> {

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
    private boolean isChecked;
    private String nickName;   // 姓名
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

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


    @Override
    public int compare(GroupsBean o1, GroupsBean o2) {
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
