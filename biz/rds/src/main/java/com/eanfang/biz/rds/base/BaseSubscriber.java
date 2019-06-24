package com.eanfang.biz.rds.base;

import com.eanfang.base.network.callback.RequestCallback;
import com.eanfang.base.network.callback.RequestMultiplyCallback;
import com.eanfang.base.network.config.HttpCode;
import com.eanfang.base.network.exception.base.BaseException;
import com.eanfang.base.network.model.Optional;

import io.reactivex.observers.DisposableObserver;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class BaseSubscriber<T> extends DisposableObserver<Optional<T>> {

    private RequestCallback<T> requestCallback;

    BaseSubscriber(RequestCallback<T> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onNext(Optional<T> t) {
        if (requestCallback != null) {
            requestCallback.onSuccess(t.value);
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