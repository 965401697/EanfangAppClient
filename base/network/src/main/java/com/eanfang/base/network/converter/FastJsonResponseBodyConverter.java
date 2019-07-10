package com.eanfang.base.network.converter;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * retrofit2  Response转换器
 *
 * @param <T>
 * @author jornl
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        long contentLength = responseBody.contentLength();
        if (contentLength == 0) {
            return null;
        }
        String jsonStr = responseBody.string();
        JSONObject object = JSON.parseObject(jsonStr);
        if (object.get("data") instanceof String) {
            object.put("data", null);
        }
        return object.toJavaObject(type);
    }
}
