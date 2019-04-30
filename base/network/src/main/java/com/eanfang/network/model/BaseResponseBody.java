package com.eanfang.network.model;


import lombok.Getter;
import lombok.Setter;

/**
 * @author jornl
 * @date 2019-04-17 18:11:04
 */
@Getter
@Setter
public class BaseResponseBody<T> {

    private String cacheKey;

    private int code;

    private String message;

    private int noticeCount;

    private T data;

}
