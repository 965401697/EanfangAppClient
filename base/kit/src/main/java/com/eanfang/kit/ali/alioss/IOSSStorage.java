package com.eanfang.kit.ali.alioss;

import android.app.Activity;

/**
 * created by wtt
 * at 2019/4/15
 * summary:
 */
public interface IOSSStorage {
    /**
     * 初始化OSS
     *
     * @return IOssService
     */
    IOssService initOSS(Activity activity);
}
