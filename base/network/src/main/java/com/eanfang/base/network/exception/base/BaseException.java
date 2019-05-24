package com.eanfang.base.network.exception.base;

import com.eanfang.base.network.config.HttpCode;

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