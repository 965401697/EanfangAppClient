package com.tengxunsdkutils.bugly;

import android.content.Context;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public interface IBugly {
    void init(Context context, String appId, String appChanner, String version, String packagename , boolean isDebug);
}
