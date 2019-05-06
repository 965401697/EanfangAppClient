package com.eanfang.kit.sdk.alisdk.alioss;

import android.app.Activity;

import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.eanfang.kit.bean.OSSBean;

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
    IOssService initOSS(Activity activity, OSSBean ossBean,String OSS_ENDPOINT,String OSS_BUCKET);
}
