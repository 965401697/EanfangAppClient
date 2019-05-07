package com.eanfang.kit.tencent.bugly;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.eanfang.kit.utils.ApkUtils;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * created by wtt
 * at 2019/4/2
 * summary:
 */
public class BuglyManager implements IBugly {

    /**
     * @param context
     * @param appid   AppId
     */
    @Override
    public void init(Context context, String appid, boolean debug) {
        initCrashReport(context.getApplicationContext(), appid, debug);
    }

    private static void initCrashReport(Context context, String appId, boolean debug) {
        // Set up the Report Process
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(getChannelName(context));
        strategy.setAppVersion(ApkUtils.getAppVersionName(context));
        strategy.setAppPackageName(ApkUtils.getPackageName(context, null));
        CrashReport.initCrashReport(context, appId, debug, strategy);
    }

    private static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("com.tencent.tac.channel"));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }
}
