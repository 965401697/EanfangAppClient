package com.eanfang.network.callback;

import com.eanfang.network.exception.base.BaseException;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public interface RequestMultiplyCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);

}