package com.eanfang.network.exception.base;

import com.eanfang.network.config.HttpCode;
import com.eanfang.network.config.HttpConfig;

import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private int errorCode = HttpCode.CODE_UNKNOWN;

    public BaseException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

}