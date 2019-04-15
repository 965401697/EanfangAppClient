package com.eanfang.sdk.alisdk.alioss;

import android.app.Activity;

/**
 * created by wtt
 * at 2019/4/15
 * summary:
 */
public interface IOSSStorage {
    /**
     * 初始化OSS
     * @param activity
     * @return
     */
    IOssService initOSS(Activity activity);
}
