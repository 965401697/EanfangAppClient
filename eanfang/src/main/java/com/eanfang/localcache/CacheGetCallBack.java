package com.eanfang.localcache;

/**
 * Created by xudingbing on 2018/3/29.
 */

public interface CacheGetCallBack<T> {
    public void readValue(T result);
}
