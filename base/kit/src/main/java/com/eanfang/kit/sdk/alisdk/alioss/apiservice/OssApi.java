package com.eanfang.kit.sdk.alisdk.alioss.apiservice;


import com.eanfang.kit.BuildConfig;
import com.eanfang.network.config.HttpConfig;

/**
 * Created by MrHou
 *
 * @on 2017/11/6  16:39
 * @email houzhongzhou@yeah.net
 * @desc 阿里云oss
 */

public interface OssApi {
    /**
     * oss 的sts生成器url
     */
    String STS_URL = HttpConfig.get().getApiUrl() + "/yaf_sys/oss/sts";
}
