package com.eanfang.model;

import com.yaf.sys.entity.AccountEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by MrHou
 *
 * @on 2017/12/7  11:48
 * @email houzhongzhou@yeah.net
 * @desc
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginBean implements Serializable {
    /**
     * 权限列表
     */
    private List<String> perms;
    private String token;
    private AccountEntity account;
    /**
     * 0空闲，1工作中，2停止接单
     */
    private int workerStatus;

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
