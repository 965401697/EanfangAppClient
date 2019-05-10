package com.eanfang.kit.ali.alioss;

import android.app.Activity;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.eanfang.network.config.HttpConfig;

/**
 * created by wtt
 * at 2019/4/15
 * summary:
 */
public class OSSStorage implements IOSSStorage {

    @Override
    public IOssService initOSS(Activity activity) {
        OSSCredentialProvider credentialProvider = new STSGetter();
        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(10);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(3);
        OSS oss = new OSSClient(activity, HttpConfig.get().getOssEndpoint(), credentialProvider, conf);
        return new OssService(activity, oss, HttpConfig.get().getOssBucket());
    }
}
