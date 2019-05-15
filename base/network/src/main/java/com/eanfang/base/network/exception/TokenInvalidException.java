package com.eanfang.base.network.exception;

import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class TokenInvalidException extends BaseException {

    public TokenInvalidException() {
        super(HttpCode.CODE_TOKEN_INVALID, "登录失效，请重新登录");
    }
}
