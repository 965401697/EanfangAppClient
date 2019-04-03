package com.tengxunsdkutils.bugly;

import android.content.Context;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public class BuglyProxy implements IBugly {
    private IBugly mIBugly;

    public BuglyProxy(IBugly mIBugly) {
        this.mIBugly = mIBugly;
    }

    @Override
    public void init(Context context, String appChanner, String version, String packagename, String appId, boolean isDebug) {
        mIBugly.init(context, appChanner, version, packagename, appId, isDebug);
    }
}
