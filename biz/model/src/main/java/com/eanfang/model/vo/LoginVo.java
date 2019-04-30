package com.eanfang.model.vo;

import androidx.databinding.BaseObservable;
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
public class LoginVo extends BaseObservable implements Serializable {

    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> password = new ObservableField<>();
    private ObservableField<String> verifycode = new ObservableField<>();
    private ObservableField<String> captcha = new ObservableField<>();

    private transient ObservableField<String> legalText = new ObservableField<>();

    private transient ObservableField<Boolean> legalCk = new ObservableField<>(true);
    private transient ObservableField<Boolean> showPwd = new ObservableField<>(false);


}
