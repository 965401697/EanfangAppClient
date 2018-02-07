package com.eanfang.oss;


import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.eanfang.BuildConfig;


public class OSSUtils {
    private static volatile OssService instance = null;

    //初始化一个OssService用来上传下载
    public static OssService initOSS(Context context) {
        if (instance == null) {
            synchronized (OSSUtils.class) {
                if (instance == null) {
                    OSSCredentialProvider credentialProvider = new STSGetter();
                    ClientConfiguration conf = new ClientConfiguration();
                    // 连接超时，默认15秒
                    conf.setConnectionTimeout(60 * 1000);
                    // socket超时，默认15秒
                    conf.setSocketTimeout(60 * 1000);
                    // 最大并发请求书，默认5个
                    conf.setMaxConcurrentRequest(5);
                    // 失败后最大重试次数，默认2次
                    conf.setMaxErrorRetry(5);
                    OSS oss = new OSSClient(context, BuildConfig.OSS_ENDPOINT, credentialProvider, conf);
                    instance = new OssService(context, oss, BuildConfig.OSS_BUCKET);
                }
            }
        }
        return instance;
    }


}