package com.tengxunsdkutils.bugly;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * created by wtt
 * at 2019/4/2
 * summary:
 */
public class BuglyManager implements IBugly {

    /**
     * @param context
     * @param appId       AppId
     * @param appChanner  渠道名
     * @param version     版本号
     * @param packagename 当前程序版本名
     * @param isDebug
     */
    @Override
    public void init(Context context, String appChanner, String version, String packagename, String appId, boolean isDebug) {
        initCrashReport(context.getApplicationContext(), appChanner, version, packagename, appId, isDebug);
    }

    private static void initCrashReport(Context context, String appChanner, String version, String packagename, String appId, boolean isDebug) {
        // Set up the Report Process
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(appChanner);
        strategy.setAppVersion(version);
        strategy.setAppPackageName(packagename);
        initCrashReport(context, appId, isDebug, strategy);
    }

    private static void initCrashReport(Context var0, String var1, boolean var2, CrashReport.UserStrategy var3) {
        CrashReport.initCrashReport(var0, var1, var2, var3);
    }
}
