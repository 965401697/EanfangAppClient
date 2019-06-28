package com.eanfang.base.network.model;

import lombok.Getter;

/**
 * @param <T>
 * @author jornl
 */
public class Optional<T> {
    @Getter
    public T value;

    public Optional(T value) {
        this.value = value;
    }
}
