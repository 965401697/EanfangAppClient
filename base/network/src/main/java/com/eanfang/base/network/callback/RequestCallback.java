package com.eanfang.base.network.callback;

import com.eanfang.base.network.holder.ToastHolder;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

    default void showToast(String message) {
        ToastHolder.showToast(message);
    }

}
