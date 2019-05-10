package com.eanfang.model;

import com.eanfang.model.sys.AccountEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  11:48
 * @email houzhongzhou@yeah.net
 * @desc
 */
class LoginBean implements Serializable {
    /**
     * 权限列表
     */
    private List<String> perms;
    private String token;
    private AccountEntity account;

    public LoginBean(List<String> perms, String token, AccountEntity account) {
        this.perms = perms;
        this.token = token;
        this.account = account;
    }

    public LoginBean() {
    }


    public List<String> getPerms() {
        if (perms == null) {
            return new ArrayList<>();
        }
        return perms;
    }

    public void setPerms(List<String> perms) {
        this.perms = perms;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
