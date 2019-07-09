package com.eanfang.base.network.kit;

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
public class TypeToken<T> {
    @Getter
    private final Type type;

    public TypeToken() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        assert parameterizedType != null;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        type = typeArguments[0];
    }

}
