package com.eanfang.model;

import java.io.Serializable;

/**
 * Created by O u r on 2018/4/12.
 */

public class FriendListBean implements Serializable {

    /**
     * accId : 937871078913511425
     * avatar : 0
     * nickName : 侯
     */

    private String accId;
    private String avatar;
    private String nickName;
    private String email;
    private String mobile;

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
}
