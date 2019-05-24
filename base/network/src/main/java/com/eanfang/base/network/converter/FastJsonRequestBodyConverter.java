package com.eanfang.base.network.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
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
            //解决viewModel的Observable数据类型
            if ((field.getType().toString().contains("androidx.databinding.Observable"))) {
                Object obj = ReflectUtil.getFieldValue(value, fieldName);
                val = ReflectUtil.invoke(obj, "get");
            } else {
                val = ReflectUtil.getFieldValue(value, fieldName);
            }
            if (val != null) {
                //如果是空map  空list  空字符 则跳过参数
                if (val instanceof Map && ((Map) val).isEmpty()) {
                    continue;
                }
                if (val instanceof List && ((List) val).isEmpty()) {
                    continue;
                }
                if (StrUtil.isEmpty(val.toString())) {
                    continue;
                }
                object.put(fieldName, val);
            }
        }

        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(object));
    }
}
