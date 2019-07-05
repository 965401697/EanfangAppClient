package com.eanfang.base.kit.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.Getter;

/**
 * 自定义 TypeToken  通过 T 获取泛型类型
 *
 * @param <T>
 * @author jornl
 * @date 2019-07-01
 */
public abstract class TypeToken<T> {
    @Getter
    private final Type type;
    @Getter
    private final Class<T> clazz;

    public TypeToken() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        assert parameterizedType != null;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        type = typeArguments[0];

        clazz = (Class<T>) type.getClass().getGenericSuperclass();
    }
}
