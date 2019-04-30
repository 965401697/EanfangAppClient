package com.eanfang.network.interceptor;

import androidx.annotation.NonNull;

import com.eanfang.network.config.HttpConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class HeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
        if (HttpConfig.get().isGzip()) {
            requestBuilder.addHeader("Accept-Encoding", "gzip");
        }
        if (HttpConfig.get().getToken() != null) {
            requestBuilder.addHeader("YAF-Token", HttpConfig.get().getToken());
        }
        requestBuilder.addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("Request-From", HttpConfig.get().getRequestFrom());


        requestBuilder.method(originalRequest.method(), originalRequest.body());
        return chain.proceed(requestBuilder.build());
    }

}
