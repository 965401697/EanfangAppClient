package com.eanfang.base.network.converter;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eanfang.base.network.kit.VoKit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * retrofit2 Request转换器
 *
 * @param <T>
 * @author jornl
 */
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(@NonNull T value) {
        JSONObject object = VoKit.vo2Json(value);
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(object));
    }
}
