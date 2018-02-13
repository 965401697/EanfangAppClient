package com.eanfang.model;

import com.yaf.sys.entity.AccountEntity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
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
public class LoginBean implements Serializable {
    /**
     * 权限列表
     */
    private List<String> perms;
    private String token;
    private AccountEntity account;

}
