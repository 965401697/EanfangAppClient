package com.eanfang.base.network;

import android.content.Context;

import com.eanfang.base.network.holder.ContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.Interceptor;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public enum Leaves {

    INSTANCE;

    public Leaves init(Context context) {
        ContextHolder.setContext(context);
        setRxErrorHandler();
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

    public void setRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                e = e.getCause();
            }
            if ((e instanceof IOException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return;
            }
            if (e instanceof InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return;
            }
            if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return;
            }
            if (e instanceof IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                return;
            }
            Logger.getLogger(Leaves.class.getName()).log(Level.WARNING, e.getMessage());
        });
    }
}
