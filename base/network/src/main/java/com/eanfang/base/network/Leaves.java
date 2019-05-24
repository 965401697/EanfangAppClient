package com.eanfang.base.network;

import android.content.Context;

import com.eanfang.base.network.holder.ContextHolder;

import java.util.List;

import okhttp3.Interceptor;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public enum Leaves {

    INSTANCE;

    public Leaves init(Context context) {
        ContextHolder.setContext(context);
        return INSTANCE;
    }

    public Leaves log(boolean log) {
        RetrofitManagement.getInstance().setLog(log);
        return INSTANCE;
    }

    public Leaves addInterceptor(List<Interceptor> interceptorList) {
        RetrofitManagement.getInstance().addInterceptor(interceptorList);
        return INSTANCE;
    }

}
