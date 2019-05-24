package com.eanfang.base.network.exception;

import com.eanfang.base.network.exception.base.BaseException;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class ServerResultException extends BaseException {

    public ServerResultException(int code, String message) {
        super(code, message);
    }

}
