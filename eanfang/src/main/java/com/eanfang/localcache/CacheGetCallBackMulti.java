package com.eanfang.localcache;

import java.util.List;

/**
 * Created by xudingbing on 2018/3/29.
 */

public interface CacheGetCallBackMulti<T> {
    public void readValue(List<T> result);
}
