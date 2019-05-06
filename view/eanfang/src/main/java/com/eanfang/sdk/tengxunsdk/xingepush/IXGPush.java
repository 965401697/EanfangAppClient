package com.eanfang.sdk.tengxunsdk.xingepush;

import android.app.Activity;
import android.content.Context;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public interface IXGPush {

    void registerPush(String account);

    void unregisterPush();

    void delAccount(final String account);

    IXGPushClickedResult onActivityStarted(Activity activity);

    void enableOtherPush(boolean flag);

    void enableDebug(boolean debugMode);

    void setHuaweiDebug(boolean isHuaweiDebug);

    void setMiPush(String appid, String appkey);

    void setMzPush(String appid, String appkey);
}
