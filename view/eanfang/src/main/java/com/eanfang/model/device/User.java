package com.eanfang.model.device;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by O u r on 2018/4/27.
 */

public class User implements Serializable {

    /**
     * accId : 983165293854535681
     * accType : 0
     * address : 中润通财富(朝阳北路)
     * avatar : 66a63142c54c4f48a8b22936ae7dcc88.png
     * email :
     * gender : 1
     * idCard : 410926199005104491
     * lastLoginTime : 2018-04-27 17:52:33
     * loginCount : 22
     * mobile : 18611154430
     * nickName : 子武4430
     * passwd : VsjQ8UWh2aUXJbg6Vwm5NbUJboc=
     * rcloudToken : uNVTN7kbI5GdTp4k61nGaw3qwon7dFclDIVQwIYTKyYdpIfmlrTWQY1WbUO3jRnGyZ9mdB3YzthPgF+0CT06WubRbkwxEv7a2U9YVOOCtpo=
     * realName : 啧啧啧
     * regTime : 2018-04-09 10:10:47
     * status : 0
     */

    private String accId;
    private int accType;
    private String address;
    private String avatar;
    private String email;
    private int gender;
    private String idCard;
    private String lastLoginTime;
    private int loginCount;
    private String mobile;
    private String nickName;
    private String passwd;
    private String rcloudToken;
    private String realName;
    private String regTime;
    private String areaCode;
    private int status;
    private Date birthday;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    public int getAccType() {
        return accType;
    }

    public void setAccType(int accType) {
        this.accType = accType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRcloudToken() {
        return rcloudToken;
    }

    public void setRcloudToken(String rcloudToken) {
        this.rcloudToken = rcloudToken;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
