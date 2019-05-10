package com.eanfang.network.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

import cn.hutool.core.util.ReflectUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) {
        JSONObject object = new JSONObject();

        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            //忽略掉transient
            if (field.toString().contains(" transient ")) {
                continue;
            }

            String fieldName = field.getName();
            Object val;
            if ((field.getType().toString().contains("androidx.databinding.Observable"))) {
                Object obj = ReflectUtil.getFieldValue(value, fieldName);
                val = ReflectUtil.invoke(obj, "get");
            } else {
                val = ReflectUtil.getFieldValue(value, fieldName);
            }
            if (val != null) {
                object.put(fieldName, val);
            }
        }

        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(object));
    }
}
