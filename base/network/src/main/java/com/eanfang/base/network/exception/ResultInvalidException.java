package com.eanfang.base.network.exception;

import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class ResultInvalidException extends BaseException {

    public ResultInvalidException(int code) {
        super(HttpCode.CODE_RESULT_INVALID, "服务器连接失败 " + code);
    }

}
