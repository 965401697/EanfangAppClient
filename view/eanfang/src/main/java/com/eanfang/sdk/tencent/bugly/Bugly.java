package com.eanfang.sdk.tencent.bugly;

import android.content.Context;

import com.eanfang.BuildConfig;
import com.eanfang.util.ApkUtils;
import com.eanfang.util.ChannelUtil;
import com.tencent.bugly.crashreport.CrashReport;

public class Bugly implements IBugly {

    @Override
    public void init(Context context, String key) {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(ChannelUtil.getChannelName(context));
        strategy.setAppVersion(ApkUtils.getAppVersionName(context));
        strategy.setAppPackageName(ApkUtils.getPackageName(context, null));
        CrashReport.initCrashReport(context, key, BuildConfig.DEBUG, strategy);
    }
}
