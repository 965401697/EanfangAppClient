package com.eanfang.kit.tencent.bugly;

import android.content.Context;

import com.eanfang.kit.tencent.bugly.utils.ApkUtils;
import com.eanfang.kit.tencent.bugly.utils.ChannelUtil;
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
    public void init(Context context, String appid,boolean debug) {
        initCrashReport(context.getApplicationContext(), appid,debug);
    }

    private static void initCrashReport(Context context, String appId,boolean debug) {
        // Set up the Report Process
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(ChannelUtil.getChannelName(context));
        strategy.setAppVersion(ApkUtils.getAppVersionName(context));
        strategy.setAppPackageName(ApkUtils.getPackageName(context, null));
        CrashReport.initCrashReport(context, appId, debug, strategy);
    }
}
