package com.eanfang.network.exception;

import com.eanfang.network.config.HttpCode;
import com.eanfang.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class ConnectionException extends BaseException {

    public ConnectionException() {
        super(HttpCode.CODE_CONNECTION_FAILED, "网络请求失败");
    }

}
