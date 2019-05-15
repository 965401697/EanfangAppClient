package com.eanfang.base.network.exception;


import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class ParameterInvalidException extends BaseException {

    public ParameterInvalidException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "参数有误");
    }

}
