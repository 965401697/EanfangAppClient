package com.tengxunsdk.bugly;

import android.content.Context;

/**
 * created by wtt
 * at 2019/4/3
 * summary:
 */
public class BuglyProxy implements IBugly {
    private IBugly mIBugly;
    private static BuglyProxy mBuglyProxy;

    public static BuglyProxy getInstance(IBugly mIBugly){
        if(mBuglyProxy==null){
            mBuglyProxy=new BuglyProxy(mIBugly);
        }
        return mBuglyProxy;
    }
    private BuglyProxy(IBugly mIBugly) {
        this.mIBugly = mIBugly;
    }

    @Override
    public void init(Context context, String appChanner, String version, String packagename, String appId, boolean isDebug) {
        mIBugly.init(context, appChanner, version, packagename, appId, isDebug);
    }
}
