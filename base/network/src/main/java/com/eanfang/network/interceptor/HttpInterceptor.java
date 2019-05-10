package com.eanfang.network.interceptor;


import androidx.annotation.NonNull;

import com.eanfang.network.exception.ConnectionException;
import com.eanfang.network.exception.ResultInvalidException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) {
        Request request = chain.request();
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            throw new ConnectionException();
        }
        if (originalResponse.code() != 200) {
            throw new ResultInvalidException(originalResponse.code());
        }
        return originalResponse;
//        assert originalResponse.body() != null;
//        BufferedSource source = originalResponse.body().source();
//        source.request(Integer.MAX_VALUE);
//        String byteString = source.buffer().snapshot().utf8();
//        ResponseBody responseBody = ResponseBody.create(MediaType.parse("application/json; charset=UTF-8"), byteString);
//        return originalResponse.newBuilder().body(responseBody).build();
    }

}