package com.eanfang.kit.sdk.alisdk.alioss;

import android.app.Activity;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.eanfang.kit.BuildConfig;
import com.eanfang.kit.bean.OSSBean;

/**
 * created by wtt
 * at 2019/4/15
 * summary:
 */
public class OSSStorage implements IOSSStorage {
    private OssService ossService;
    @Override
    public IOssService initOSS(Activity activity, OSSBean ossBean,String OSS_ENDPOINT,String OSS_BUCKET) {
        OSSCredentialProvider credentialProvider = new STSGetter(ossBean);
        ClientConfiguration conf = new ClientConfiguration();
        // 连接超时，默认15秒
        conf.setConnectionTimeout(15 * 1000);
        // socket超时，默认15秒
        conf.setSocketTimeout(15 * 1000);
        // 最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(10);
        // 失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(3);
        OSS oss = new OSSClient(activity, OSS_ENDPOINT, credentialProvider, conf);
         ossService=new OssService(activity, oss, OSS_BUCKET);
        return ossService;
    }
}
