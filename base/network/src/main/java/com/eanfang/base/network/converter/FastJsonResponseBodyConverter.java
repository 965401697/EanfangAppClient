package com.eanfang.base.network.converter;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /**
     * 转换方法
     */
    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        long contentLength = responseBody.contentLength();
        if (contentLength == 0) {
            return null;
        }

        String jsonStr = responseBody.string();
        return JSON.parseObject(jsonStr, type);
    }
}
