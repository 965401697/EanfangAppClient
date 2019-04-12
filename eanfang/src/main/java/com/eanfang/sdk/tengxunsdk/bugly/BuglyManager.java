package com.eanfang.sdk.tengxunsdk.bugly;

import android.content.Context;

import com.eanfang.BuildConfig;
import com.eanfang.util.ApkUtils;
import com.eanfang.util.ChannelUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * created by wtt
 * at 2019/4/2
 * summary:
 */
public class BuglyManager implements IBugly {

    /**
     * @param context
     * @param appid       AppId
     */
    @Override
    public void init(Context context, String appid) {
        initCrashReport(context.getApplicationContext(), appid);
    }

    private static void initCrashReport(Context context, String appId) {
        // Set up the Report Process
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(ChannelUtil.getChannelName(context));
        strategy.setAppVersion(ApkUtils.getAppVersionName(context));
        strategy.setAppPackageName(ApkUtils.getPackageName(context, null));
        CrashReport.initCrashReport(context, appId, BuildConfig.DEBUG, strategy);
    }
}
