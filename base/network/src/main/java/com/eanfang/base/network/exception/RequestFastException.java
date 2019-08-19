package com.eanfang.base.network.exception;

import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class RequestFastException extends BaseException {

    public RequestFastException() {
        super(HttpCode.CODE_REQUEST_FAST, "请求太快了，歇一会儿在操作吧");
    }

}
