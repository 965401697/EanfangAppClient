package com.eanfang.base.network.interceptor;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class FilterInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl.Builder httpBuilder = originalRequest.url().newBuilder();

        Request.Builder requestBuilder = originalRequest.newBuilder();
        //登录、获取验证码 取消token
        if (originalRequest.url().toString().contains("login") ||
                originalRequest.url().toString().contains("sendverify")) {
            requestBuilder.removeHeader("YAF-Token");
        }
        requestBuilder.url(httpBuilder.build());
        return chain.proceed(requestBuilder.build());
    }

}
