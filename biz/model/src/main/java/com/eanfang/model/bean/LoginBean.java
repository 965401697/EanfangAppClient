package com.eanfang.model.bean;


import com.eanfang.model.sys.AccountEntity;

import java.io.Serializable;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginBean implements Serializable {
    private String token;
    private AccountEntity account;
    private Set<String> perms;
    private String constMD5;
    private String baseDataMD5;
    private Integer workerVerifyStatus;
}
