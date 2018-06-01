package com.eanfang.model;

import android.support.annotation.NonNull;

import com.eanfang.util.Cn2Spell;

import java.io.Serializable;

/**
 * Created by O u r on 2018/4/12.
 */

public class FriendListBean implements Serializable, Comparable<FriendListBean> {

    /**
     * accId : 937871078913511425
     * avatar : 0
     * nickName : 侯
     */

    private String accId;
    private String avatar;
    private String email;
    private String mobile;
    private String address;
    private String areaCode;
    private int flag;//是否选中的flag
    private int status;//是不是禁言

    private String nickName;   // 姓名
    private String pinyin; // 姓名对应的拼音
    private String firstLetter; // 拼音的首字母


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
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
    public int compareTo(@NonNull FriendListBean o) {
        if (firstLetter.equals("#") && !o.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && o.getFirstLetter().equals("#")) {
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(o.getPinyin());
        }
    }
}
