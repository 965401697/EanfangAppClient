package com.eanfang.network.exception;

import com.eanfang.network.config.HttpCode;
import com.eanfang.network.exception.base.BaseException;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super(HttpCode.CODE_ACCOUNT_INVALID, "账号或者密码错误");
    }

}
