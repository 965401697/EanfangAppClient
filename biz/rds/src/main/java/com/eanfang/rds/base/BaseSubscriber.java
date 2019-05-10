package com.eanfang.rds.base;

import com.eanfang.network.callback.RequestCallback;
import com.eanfang.network.callback.RequestMultiplyCallback;
import com.eanfang.network.config.HttpCode;
import com.eanfang.network.exception.base.BaseException;

import io.reactivex.observers.DisposableObserver;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private RequestCallback<T> requestCallback;

    BaseSubscriber(RequestCallback<T> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onNext(T t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestCallback instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallback;
            if (e instanceof BaseException) {
                callback.onFail((BaseException) e);
            } else {
                callback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
            }
        } else {
            if (requestCallback != null) {
                requestCallback.showToast(e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {

    }

}