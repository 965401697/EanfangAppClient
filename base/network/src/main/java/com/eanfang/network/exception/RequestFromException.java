package com.eanfang.network.exception;

import com.eanfang.network.config.HttpCode;
import com.eanfang.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class RequestFromException extends BaseException {

    public RequestFromException() {
        super(HttpCode.CODE_TOKEN_INVALID, "请求来源错误，请退出后重试");
    }

}
