package com.tengxunsdk.wechatsdk;

import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * created by wtt
 * at 2019/4/8
 * summary:
 */
public interface IWXPayAPI extends IWXAPI {
    @Override
    boolean registerApp(String s);

    @Override
    void unregisterApp();
}
