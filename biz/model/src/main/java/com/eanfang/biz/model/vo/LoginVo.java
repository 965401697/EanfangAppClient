package com.eanfang.biz.model.vo;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import java.io.Serializable;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * @author jornl
 * @date 2019-04-19 15:34:57
 * 登录vo
 */
@Getter()
@Accessors(chain = true)
public class LoginVo implements Serializable {

    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> verifycode = new ObservableField<>();
    private ObservableField<String> captcha = new ObservableField<>();

    private transient ObservableField<String> legalText = new ObservableField<>();

    private transient ObservableBoolean legalCk = new ObservableBoolean(true);
    private transient ObservableBoolean showPwd = new ObservableBoolean(false);


}
